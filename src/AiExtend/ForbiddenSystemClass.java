package AiExtend;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo ��������������ж�System.out��System.error�������
 */
public class ForbiddenSystemClass extends OpcodeStackDetector {
	private BugReporter bugReporter;

	public ForbiddenSystemClass(BugReporter bugReporter) {
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
		if (seen == GETSTATIC) {
			if (getClassConstantOperand().equals("java/lang/System")
					&& (getNameConstantOperand().equals("out") || getNameConstantOperand()
							.equals("error"))) {
				BugInstance bug = new BugInstance(this, "System_Out_Err_BUG",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}
}