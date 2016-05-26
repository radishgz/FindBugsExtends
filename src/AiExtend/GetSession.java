package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author  这个规则类用于判断ServiceManager.getSession调用，一般情况不允许指定用户
 */
public class GetSession extends OpcodeStackDetector {
	private BugReporter bugReporter;

	private static transient Log log = LogFactory.getLog(GetSession.class);

	public GetSession(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}

	 

	/**
	 * 每扫描一条字节码就会进入sawOpcode方法
	 * 
	 * @param seen
	 *            字节码的枚举值
	 */
	@Override
	public void sawOpcode(int seen) {
		 
		if (seen ==  INVOKESTATIC &&
				getClassConstantOperand().equals("com/ai/appframe2/common/ServiceManager")
				&& getNameConstantOperand().equals("getSession") 
				) {
			
			if (!getSigConstantOperand().equals("()Lcom/ai/appframe2/common/Session;")){
				BugInstance bug = new BugInstance(this, "GET_SESSION",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
				 
			 
			return;
		 
		}
	}
}