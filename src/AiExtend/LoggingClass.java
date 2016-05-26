package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author bo ��������������ж�System.out��System.error�������
 */
public class LoggingClass extends OpcodeStackDetector {
	private BugReporter bugReporter;

	private static transient Log log = LogFactory.getLog(LoggingClass.class);

	public LoggingClass(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit��������ÿ�ν����ֽ��뷽����ʱ����� ��ÿ�ν����·�����ʱ����ձ�־λ
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}

	private int isDebugseenGuardClauseAt = 0;
	private int isDebugLogBlockStart = 0;
	private int isDebugLogBlockEnd = 0;

	/*
	private int isInfoseenGuardClauseAt=0;

	private int isErrorseenGuardClauseAt;

	private int isInfoLogBlockStart;

	private int isInfoLogBlockEnd;

	private int isErrorLogBlockStart;

	private int isErrorLogBlockEnd;
	*/

	/**
	 * ÿɨ��һ���ֽ���ͻ����sawOpcode����
	 * 
	 * @param seen
	 *            �ֽ����ö��ֵ
	 */
	@Override
	public void sawOpcode(int seen) {
		 
		if (seen == INVOKEINTERFACE
				&& getClassConstantOperand().equals(
						"org/apache/commons/logging/Log")
				&& this.getSigConstantOperand().equals("()Z")) {
			
			if (getNameConstantOperand().equals("isDebugEnabled"))
				isDebugseenGuardClauseAt = this.getPC();
			/*else if (getNameConstantOperand().equals("isInfoEnabled"))
				isInfoseenGuardClauseAt = this.getPC();
			else if (getNameConstantOperand().equals("isErrorEnabled"))
				isErrorseenGuardClauseAt = this.getPC();
			// log.debug("seenGuardClauseAt:"+seenGuardClauseAt); */
			return;
		}
		
		
		
		if (seen == IFEQ){
			if  (this.getPC() >= isDebugseenGuardClauseAt + 3 && this
						.getPC() < isDebugseenGuardClauseAt + 7) {

			isDebugLogBlockStart = this.getBranchFallThrough();
			isDebugLogBlockEnd = this.getBranchTarget();
						}
		/*	else if (this.getPC() >= isInfoseenGuardClauseAt + 3 && this
					.getPC() < isInfoseenGuardClauseAt + 7) {

				isInfoLogBlockStart = this.getBranchFallThrough();
				isInfoLogBlockEnd = this.getBranchTarget();
							}
			else if (this.getPC() >= isErrorseenGuardClauseAt + 3 && this
					.getPC() < isErrorseenGuardClauseAt + 7) {

				isErrorLogBlockStart = this.getBranchFallThrough();
				isErrorLogBlockEnd = this.getBranchTarget();
							} */
			return ;
		}
		if (seen == INVOKEINTERFACE
				&& getClassConstantOperand().equals(
						"org/apache/commons/logging/Log")
				  ) {
			
			//log.debug("debuging ....:" + getPC());
			if (getNameConstantOperand().equals("debug") 
			   && ( getPC() < isDebugLogBlockStart || getPC() >= isDebugLogBlockEnd)) {
				bugReporter.reportBug(new BugInstance(
						"UNPROTECTED_LOGGING",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this, getPC()));
			}
			/*
			else if (getNameConstantOperand().equals("info") 
					   && ( getPC() < isInfoLogBlockStart || getPC() >= isInfoLogBlockEnd)) {
						bugReporter.reportBug(new BugInstance(
								"UNPROTECTED_LOGGING", HIGH_PRIORITY)
								.addClassAndMethod(this).addSourceLine(this));
						
					}
			
			else if (getNameConstantOperand().equals("error") 
					   && ( getPC() < isErrorLogBlockStart || getPC() >= isErrorLogBlockEnd)) {
						bugReporter.reportBug(new BugInstance(
								"UNPROTECTED_LOGGING", HIGH_PRIORITY)
								.addClassAndMethod(this).addSourceLine(this));
					} */
			
			else if (getNameConstantOperand().equals("warn") ||
					getNameConstantOperand().equals("fatal") ||
					getNameConstantOperand().equals("trace")){
				bugReporter.reportBug(new BugInstance(
						"LIMIT_LOG_METHOD",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(this, getPC()));
			}
		}
	}
}