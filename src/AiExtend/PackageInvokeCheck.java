package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author
 */
public class PackageInvokeCheck extends OpcodeStackDetector {
	private BugReporter bugReporter;

	private String visitClassName;
	private static transient Log log = LogFactory.getLog(PackageInvokeCheck.class);
	
	@Override
	public void visitJavaClass(JavaClass javaClass) {
		// TODO Auto-generated method stub

		visitClassName = javaClass.getClassName();

	    log.debug("className:+"+visitClassName);
		super.visitJavaClass(javaClass);

	}

	public PackageInvokeCheck(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit��������ÿ�ν����ֽ��뷽����ʱ����� ��ÿ�ν����·�����ʱ����ձ�־λ
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}


  private String constantOperant;
  
	/**
	 * ÿɨ��һ���ֽ���ͻ����sawOpcode����
	 * 
	 * @param seen
	 *            �ֽ����ö��ֵ
	 */
	@Override
	public void sawOpcode(int seen) {

		if (seen != INVOKEINTERFACE && seen != INVOKESTATIC)
			return;
		constantOperant=getClassConstantOperand();

		if (!constantOperant.startsWith("com/asiainfo/crm/"))
			return;

		// ֻ��ʹ�ö���ģ�������
		if (getNameConstantOperand().equals("<init>")) {
			return;
		}
		
		if (visitClassName.endsWith("SVImpl")) {
			SVImplCheck();
		}

		if (visitClassName.endsWith("DAOImpl")) {
			DAOImplCheck();
		}

		if (visitClassName.endsWith("Action")) {
			ActionCheck();

		}

		if (visitClassName.endsWith("CacheImpl")) {
			
			CacheImplCheck();

		}

	}

	public void CacheImplCheck() {
		log.debug("CacheImplCheck:+"+visitClassName+":"+constantOperant);
		// Action ���������dao
		if (constantOperant.indexOf("/dao/") >= 0
				|| constantOperant.indexOf("/web/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_CACHE_DAO",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			return;
		}
		/*
		if ( constantOperant.indexOf("/service/") >= 0 ) {
			String packagename = visitClassName.substring(0,
					visitClassName.indexOf(".cache."));
			
			String BeInvokePackagename="";
			BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/service/"));
			 
			BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			
			//  ֻ�ܵ��ñ�package�µķ���
			if (!packagename.equalsIgnoreCase(BeInvokePackagename)) {
				BugInstance bug = new BugInstance(this, "INVOKE_CACHE_OUTPACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}

		}
		*/
		if (constantOperant.indexOf("/bo/") >= 0) {
			if (!getNameConstantOperand().startsWith("set")
					&& !getNameConstantOperand().startsWith("get")
					&& !getNameConstantOperand().startsWith("transfer")) {
				BugInstance bug = new BugInstance(this, "INVOKE_CACHE_BO_ERR",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}

	public void ActionCheck() {
		// Action ���������dao
		if (constantOperant.indexOf("/dao/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_ACT_DAO",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
		}
		if ( constantOperant.indexOf("/service/") >= 0 ) {
			String packagename = visitClassName.substring(0,
					visitClassName.indexOf(".web."));
			String BeInvokePackagename = "";
			if (constantOperant.indexOf("/service/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/service/"));
			
			else if (constantOperant.indexOf("/bo/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/bo/"));
			
			else if (constantOperant.indexOf("/ivalues/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/ivalues/"));
			BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			// DAO ֻ�ܵ��ñ�package�µ�dao ����
			if (!packagename.equalsIgnoreCase(BeInvokePackagename)) {
				BugInstance bug = new BugInstance(this, "INVOKE_ACT_OUTPACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}

		}
		if (constantOperant.indexOf("/bo/") >= 0) {
			if (!getNameConstantOperand().startsWith("set")
					&& !getNameConstantOperand().startsWith("get")
					&& !getNameConstantOperand().startsWith("transfer")) {
				BugInstance bug = new BugInstance(this, "INVOKE_ACT_BO_ERR",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}

	public void DAOImplCheck() {
		log.debug("DAOImplCheck:+"+visitClassName+":"+constantOperant);
		// DAO ���������WEB��SV
		if (constantOperant.indexOf("/web/") >= 0
				|| constantOperant.indexOf("/service/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_DAO_WEB_SV",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			return;
		}
		
		
		
		// DAO �������dao
		if (constantOperant.indexOf("/dao/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_DAO_DAO",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			return;
		}
		 
		//BeInvokePackagename=BeInvokePackagename.replace("/", ".");
		/* ��ͬһPACKAGE��BOֻ�ܵ���GET/SET
		if (constantOperant.indexOf("/bo/") >= 0) {
			String packagename = visitClassName.substring(0,
					visitClassName.indexOf(".dao."));
			
			packagename=PackageDb.getDBUserFromPackage(packagename);
			
			String BeInvokePackagename=constantOperant.substring(0,
					constantOperant.indexOf("/bo/"));
			BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			BeInvokePackagename=PackageDb.getDBUserFromPackage(BeInvokePackagename);
			//BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			if (!getNameConstantOperand().startsWith("set")
					&& !getNameConstantOperand().startsWith("get") 
					&& !getNameConstantOperand().startsWith("transfer")
					&& !packagename.equalsIgnoreCase(BeInvokePackagename)){
				BugInstance bug = new BugInstance(this, "INVOKE_DAO_BO",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
		 
		*/
		/*
		if (constantOperant.indexOf("/bo/") >= 0
				|| constantOperant.indexOf("/ivalues/") >= 0) {
			String packagename = visitClassName.substring(0,
					visitClassName.indexOf(".dao."));
			
			String BeInvokePackagename = "";
			if (constantOperant.indexOf("/bo/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/bo/"));
			
			else if (constantOperant.indexOf("/ivalues/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/ivalues/"));
			
			BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			// DAO ֻ�ܵ��ñ�package�µ�dao ����
			if (!packagename.equalsIgnoreCase(BeInvokePackagename)) {
				BugInstance bug = new BugInstance(this, "INVOKE_DAO_OUTPACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}

		}
		*/
	}

	public void SVImplCheck() {
		
		//
		if (visitClassName.indexOf(".service.")<0){
			
		}
		// SV ���������WEB
		if (constantOperant.indexOf("/web/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_SV_WEB",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
		}
		
		//constantOperant.indexOf("/bo/") >= 0 
		if ( constantOperant.indexOf("/dao/") >= 0 ) {
			String packagename = visitClassName.substring(0,
					visitClassName.indexOf(".service."));
			String BeInvokePackagename = "";
			/*if (constantOperant.indexOf("/bo/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/bo/"));
			
			else */
			
			//if (constantOperant.indexOf("/dao/") >= 0)
				BeInvokePackagename=constantOperant.substring(0,
						constantOperant.indexOf("/dao/"));
			//else if (constantOperant.indexOf("/ivalues/") >= 0)
			//	BeInvokePackagename=constantOperant.substring(0,
			//			constantOperant.indexOf("/ivalues/"));
			BeInvokePackagename=BeInvokePackagename.replace("/", ".");
			
			// DAO ֻ�ܵ��ñ�package�µ�dao ����
			if (!packagename.equalsIgnoreCase(BeInvokePackagename)) {
				BugInstance bug = new BugInstance(this, "INVOKE_SV_OUTPACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}

		}
		// BOֻ�ܵ���GET/SET
		if (constantOperant.indexOf("/bo/") >= 0) {
			if (!getNameConstantOperand().startsWith("set")
					&& !getNameConstantOperand().startsWith("get") 
					&& !getNameConstantOperand().startsWith("transfer")) {
				BugInstance bug = new BugInstance(this, "INVOKE_SV_BO_ERROR",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
	}
}