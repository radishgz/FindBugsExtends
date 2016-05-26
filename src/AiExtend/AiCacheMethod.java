package AiExtend;

import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

public class AiCacheMethod extends PreorderVisitor implements Detector {

	private static transient Log log = LogFactory.getLog(AiCacheMethod.class);

	private BugReporter bugReporter;

	public AiCacheMethod(BugReporter bugReporter) {
		super();
		this.bugReporter = bugReporter;
		//log.debug("className:AiCacheMethod.....");
	}

	
	public  void  report() {
		// TODO Auto-generated method stub

	}

	//@Override
	public void visitClassContext(ClassContext classContext) {

		classContext.getJavaClass().accept(this);

	}

	private String visitClassName;

	private JavaClass visitclass;
	@Override
	public void visitJavaClass(JavaClass javaClass) {
		// TODO Auto-generated method stub

		visitClassName = javaClass.getClassName();
		//visitclass=javaClass;
		//if (visitClassName.endsWith("CacheImpl"))
		JavaClass superClass;
		try {
			superClass = javaClass.getSuperClass();
			if (superClass != null && 
					superClass.getClassName()
					.equalsIgnoreCase("com.ai.appframe2.complex.cache.impl.AbstractCache"))
					
					super.visitJavaClass(javaClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private static String packageName="com.asiainfo.crm.common.cache,com.asiainfo.crm.ams.common.cache,com.asiainfo.crm.so.common.cache,";
	
	@Override
	public void visit(Method obj) {
		
		
		String mName = getMethodName();
		// String className = obj.getClass().getName();
		if (mName.equalsIgnoreCase("<init>")){
			
			if (!visitClassName.endsWith("CacheImpl")
					|| packageName.indexOf(visitclass.getPackageName())<0) {
						bugReporter.reportBug(
								new BugInstance(this,"CACHE_EXTENDS_BUG", NORMAL_PRIORITY)
									.addClassAndMethod(this)
								);
					
			}
			return;
		}
		else if ( mName.equalsIgnoreCase("getData")) {
			ExceptionTable Exceptions = obj.getExceptionTable();
			if (Exceptions == null) {
				bugReporter.reportBug(new BugInstance(this,
						"CACHE_EXCEPTION_BUG", NORMAL_PRIORITY)
						.addClassAndMethod(this));
				return;
			}
			String ExcptionName[] = Exceptions.getExceptionNames();
			boolean ExistsException = false;
			if (ExcptionName==null){
				bugReporter.reportBug(new BugInstance(this,
						"CACHE_EXCEPTION_BUG", NORMAL_PRIORITY)
						.addClassAndMethod(this));
				return;
				
			}
			// CacheImpl
			for (int i = 0; i < ExcptionName.length; i++) {
				// if (ExcptionName[i].equals("java.rmi.RemoteException"))
				// ExistsRemoteException = true;
				if (ExcptionName[i].equals("java.lang.Exception")) {
					ExistsException = true;
					break;
				}

				// log.debug("ExcptionName:+"+ExcptionName[i]);
			}
			
			//返回类型不对或者没有抛出异常
			if (ExistsException == false ||
					!obj.getReturnType().getSignature().equalsIgnoreCase("Ljava/util/HashMap;"))
				bugReporter.reportBug(new BugInstance(this,
						"CACHE_EXCEPTION_BUG", NORMAL_PRIORITY)
						.addClassAndMethod(this));
			// if (.get)
		}

	}

}
