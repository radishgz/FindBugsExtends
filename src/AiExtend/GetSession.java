package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author  ��������������ж�ServiceManager.getSession���ã�һ�����������ָ���û�
 */
public class GetSession extends OpcodeStackDetector {
	private BugReporter bugReporter;

	private static transient Log log = LogFactory.getLog(GetSession.class);

	public GetSession(BugReporter bugReporter) {
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