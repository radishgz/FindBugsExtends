package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo 这个规则类用于判断System.out和System.error这种情况
 */
public class DBConnectionClass extends OpcodeStackDetector {
	private BugReporter bugReporter;

	private static transient Log log = LogFactory.getLog(LoggingClass.class);

	public DBConnectionClass(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}

	private String visitClassName;

	/**
	 * 每扫描一条字节码就会进入sawOpcode方法
	 * 
	 * @param seen
	 *            字节码的枚举值
	 */
	@Override
	public void sawOpcode(int seen) {

		if ((seen == INVOKEINTERFACE || seen == INVOKESTATIC)) {

			if (getSigConstantOperand() != null
					&& getSigConstantOperand()
							.endsWith("Ljava/sql/Connection;")) {
				/*
				 * log.debug("Connection Create by " + getClassConstantOperand()
				 * + ":" + getNameConstantOperand()); if
				 * (!getClassConstantOperand().equals(
				 * "com/ai/appframe2/common/Session") ||
				 * !getNameConstantOperand().equals("getConnection")) {
				 * bugReporter.reportBug(new BugInstance(this,
				 * "JDBC_CREATE_ERROR",
				 * NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this,
				 * getPC())); return; }
				 */

				// BO*Engine || DAOImpl
				if (log.isDebugEnabled())
					log.debug("className:+" + visitClassName);
				if ((visitClassName.endsWith("DAOImpl") && visitClassName
						.indexOf(".dao.") > 0)
						|| (visitClassName.endsWith("Engine") && visitClassName
								.indexOf(".bo.") > 0))
					return;
				bugReporter.reportBug(new BugInstance(this,
						"JDBC_INVOKE_ERROR", NORMAL_PRIORITY)
						.addClassAndMethod(this).addSourceLine(this, getPC()));
				// return;
			}
		}

		// 不允许调用Statement
		if (seen == INVOKEINTERFACE
				&&  getClassConstantOperand()
						.equals("java/sql/Statement")) {
			if (getNameConstantOperand().equals("execute")||
					getNameConstantOperand().equals("executeQuery")
					|| getNameConstantOperand().equals("executeUpdate") 
					||getNameConstantOperand().equals("executeBatch")){
			BugInstance bug = new BugInstance(this, "JDBC_STATEMENT",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			}
		}

		if (seen == INVOKEINTERFACE
				&& (getClassConstantOperand().equals(
						"java/sql/PreparedStatement"))) {
			// (visitClassName.endsWith("DAOImpl") && visitClassName
			// .indexOf(".dao.") > 0)

			if ((visitClassName.endsWith("Engine") && visitClassName
					.indexOf(".bo.") > 0)) {
				return;
			}
			if ( getNameConstantOperand().equals("executeQuery")) {

				
				BugInstance bug = new BugInstance(this, "NO_BO_JDBC",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}else if (getNameConstantOperand().equals("execute")
					|| getNameConstantOperand().equals("executeUpdate") 
					||getNameConstantOperand().equals("executeBatch")){
				BugInstance bug = new BugInstance(this, "NO_BO_JDBC_EXE",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}

	}

	@Override
	public void visitJavaClass(JavaClass arg0) {
		// TODO Auto-generated method stub
		visitClassName = arg0.getClassName();
		super.visitJavaClass(arg0);
	}

}