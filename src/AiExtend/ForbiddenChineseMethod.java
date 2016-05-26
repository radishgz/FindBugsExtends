package AiExtend;



import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo ��������������ж�ֱ��ʹ�������������
 */
public class ForbiddenChineseMethod extends OpcodeStackDetector {
	private BugReporter bugReporter;
	private static transient Log logger = LogFactory.getLog(ForbiddenChineseMethod.class);

	
	public ForbiddenChineseMethod(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit��������ÿ�ν����ֽ��뷽����ʱ����� ��ÿ�ν����·�����ʱ����ձ�־λ
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}

	/**
	 * ÿɨ��һ���ֽ���ͻ����sawOpcode����
	 * 
	 * @param seen
	 *            �ֽ����ö��ֵ
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