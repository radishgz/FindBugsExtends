package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.OpcodeStack;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class ExceptionCheck extends OpcodeStackDetector {
	private BugReporter bugReporter;
	private static transient Log logger = LogFactory
			.getLog(ExceptionCheck.class);

	/**
	 * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code obj) {
		// logger.debug("ExceptionCheck...");

		super.visit(obj);
	}

	String allowHead[] = { "AMS", "BAS", "BES", "BSS", "CAS", "CMS", "DPS",
			"EAS", "INS", "IOS", "OFS", "OMS", "PDS", "RPS", "RSS", "SCS",
			"SES", "SFS", "SOS", "SSS", "SUS" };

	@Override
	public void sawOpcode(int seen) {
		if (seen != INVOKESTATIC && seen != INVOKEINTERFACE && seen !=INVOKESPECIAL)
				return;
		if ( getClassConstantOperand().equals(
						"com/ai/appframe2/common/BusinessException")) {
			BugInstance bug = new BugInstance(this, "CHINESE_EXCEPTION_ERROR",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
		}
		// com.ai.common.util.ExceptionUtil

		if (getClassConstantOperand().equals("java/lang/Exception")
				|| getClassConstantOperand().equals("java/lang/Error")
				|| getClassConstantOperand().equals(
						"com/ai/common/i18n/BusinessException")) {
			// logger.debug("getSigConstantOperand..." +
			// getNameConstantOperand()
			// + "_" + getSigConstantOperand());
			if (getSigConstantOperand().indexOf("Ljava/lang/String;") < 0
					|| !getNameConstantOperand().equals("<init>"))
				return;
			getStackParameter();

		}

		if (getClassConstantOperand()
				.equals("com/ai/common/util/ExceptionUtil")
				&& getNameConstantOperand().equals("throwBusinessException")) {

			getStackParameter();

		}
	}

	public void getStackParameter() {
		String s = "...";
		for (int i = 0; i < stack.getStackDepth(); i++) {
			OpcodeStack.Item item = stack.getStackItem(i);
			Object o = item.getConstant();
			if (o != null)
				logger.debug("ExceptionCheck Constant Type:"
						+ o.getClass().toString());
			if (o != null && o instanceof String) {
				// stack.getStackItem(i).

				String t = (String) o;
				if (t != null)
					s += t;
			}
		}

		logger.debug("ExceptionCheck>>>" + s + " tag__" + stack.getStackDepth());
		if (isChiness(s) ||!findValidHead(s) ) {
			BugInstance bug = new BugInstance(this, "CHINESE_EXCEPTION_ERROR",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
		}
	}

	public ExceptionCheck(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	public boolean isChiness(String s) {
		for (int i = 0; i < s.length(); i++) {
			char oneChar = s.charAt(i);
			if ((oneChar >= '\u4e00' && oneChar <= '\u9fa5')
					|| (oneChar >= '\uf900' && oneChar <= '\ufa2d'))
				return true;

		}
		
		return false;
	}
	
	public boolean findValidHead(String s){
		for (int i=0;i<allowHead.length;i++){
			if (s.indexOf(allowHead[i])>=0)
					return true;
		}
		return false;
	}
}
