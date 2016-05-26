package AiExtend;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo ��������������ж�Commit,rollback�������
 */
public class ForbiddenCommitRollbackClass extends OpcodeStackDetector {
	private BugReporter bugReporter;

	public ForbiddenCommitRollbackClass(BugReporter bugReporter) {
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
		if (seen==INVOKEINTERFACE) {
			if (getClassConstantOperand().equals("java/sql/Connection")
					&& (getNameConstantOperand().equals("commit") || getNameConstantOperand()
							.equals("rollback"))) {
				BugInstance bug = new BugInstance(this, "DB_COMMIT_ROLLBACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}
}