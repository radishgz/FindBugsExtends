package AiExtend;



import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo 这个规则类用于判断直接使用中文这种情况
 */
public class ForbiddenChineseMethod extends OpcodeStackDetector {
	private BugReporter bugReporter;
	private static transient Log logger = LogFactory.getLog(ForbiddenChineseMethod.class);

	
	public ForbiddenChineseMethod(BugReporter bugReporter) {
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
		//seen == INVOKESTATIC ||
		if (seen==LDC && getConstantRefOperand().getTag()==0x08){
			String s="";
		 
			try{
			  s=getStringConstantOperand();
			  }
			catch(IllegalStateException e){
				logger.error("LDC getSigConstantOperand err: "+getPC());
			}
			logger.debug("LDC getSigConstantOperand: "+s+" tag"+getConstantRefOperand().getTag());
			if (isChiness(s)){
				BugInstance bug = new BugInstance(this, "CHINESE_ERROR",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}
	
	public boolean isChiness(String s)   
	{   
	    for (int i=0;i<s.length();i++){
	    	char oneChar = s.charAt(i);
	    	if((oneChar >= '\u4e00' && oneChar <= '\u9fa5')
	    			||(oneChar >= '\uf900' && oneChar <='\ufa2d'))
	    			return true;
	    	 
	    }
	    return false;  
	}
}