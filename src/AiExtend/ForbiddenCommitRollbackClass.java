package AiExtend;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo 这个规则类用于判断Commit,rollback这种情况
 */
public class ForbiddenCommitRollbackClass extends OpcodeStackDetector {
	private BugReporter bugReporter;

	public ForbiddenCommitRollbackClass(BugReporter bugReporter) {
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