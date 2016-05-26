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
	 * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}


  private String constantOperant;
  
	/**
	 * 每扫描一条字节码就会进入sawOpcode方法
	 * 
	 * @param seen
	 *            字节码的枚举值
	 */
	@Override
	public void sawOpcode(int seen) {

		if (seen != INVOKEINTERFACE && seen != INVOKESTATIC)
			return;
		constantOperant=getClassConstantOperand();

		if (!constantOperant.startsWith("com/asiainfo/crm/"))
			return;

		// 只是使用对象的，不限制
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
		// Action 不允许调用dao
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
			
			//  只能调用本package下的方法
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
		// Action 不允许调用dao
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
			// DAO 只能调用本package下的dao 方法
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
		// DAO 不允许调用WEB和SV
		if (constantOperant.indexOf("/web/") >= 0
				|| constantOperant.indexOf("/service/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_DAO_WEB_SV",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			return;
		}
		
		
		
		// DAO 不允许调dao
		if (constantOperant.indexOf("/dao/") >= 0) {
			BugInstance bug = new BugInstance(this, "INVOKE_DAO_DAO",
					NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
					this, getPC());
			bug.addInt(getPC());
			bugReporter.reportBug(bug);
			return;
		}
		 
		//BeInvokePackagename=BeInvokePackagename.replace("/", ".");
		/* 非同一PACKAGE的BO只能调用GET/SET
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
			// DAO 只能调用本package下的dao 方法
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
		// SV 不允许调用WEB
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
			
			// DAO 只能调用本package下的dao 方法
			if (!packagename.equalsIgnoreCase(BeInvokePackagename)) {
				BugInstance bug = new BugInstance(this, "INVOKE_SV_OUTPACK",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}

		}
		// BO只能调用GET/SET
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