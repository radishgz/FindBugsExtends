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

public class AiSvMethod extends PreorderVisitor implements Detector {

	private static transient Log log = LogFactory.getLog(AiSvMethod.class);

	private BugReporter bugReporter;

	public AiSvMethod(BugReporter bugReporter) {
		super();
		this.bugReporter = bugReporter;
		//log.debug("className:AiSvMethod.....");
	}

	//@Override
	public void report() {
		// TODO Auto-generated method stub

	}

	//@Override
	public void visitClassContext(ClassContext classContext) {

		classContext.getJavaClass().accept(this);

	}

	private String visitClassName;

	@Override
	public void visitJavaClass(JavaClass javaClass) {
		// TODO Auto-generated method stub

		visitClassName = javaClass.getClassName();
		
		if (visitClassName.endsWith("SV") || visitClassName.endsWith("SVImpl")){
				 
				if (visitClassName.endsWith("SVImpl")){
					//System.err.println("for debuging...");
					//log.debug("for debuging...");
					//log.debug("for debuging...");
					
					/*for (int i=0;i<javaClass.getFields().length;i++){
						//System.err.println("for debuging..."+javaClass.getAttributes()[i].toString());
						log.debug("getAttributes debuging..."+javaClass.getFields()[i].toString());
					}*/
				/*
					//定义了属性
					if (javaClass.getFields().length>0){
 
						bugReporter.reportBug(new BugInstance(this, "SV_ATTRIBUTE_BUG",
								NORMAL_PRIORITY).addClass(javaClass));
					}
					
					//使用了static 代码
					for (int i=0;i<javaClass.getMethods().length;i++){
						//System.err.println("for debuging..."+javaClass.getAttributes()[i].toString());
						Method m=javaClass.getMethods()[i];

						//log.debug("getAttributes debuging..."+javaClass.getMethods()[i].toString()+"   "+m.isStatic());
						if (m.getName().equals("<clinit>")){
							bugReporter.reportBug(new BugInstance(this, "SV_CLINIT_BUG",
									NORMAL_PRIORITY).addClass(javaClass));
							break;
						}
					}
					*/
					String[] interfaces=javaClass.getInterfaceNames();
					 
					String packageName=javaClass.getPackageName();
					
					if (interfaces.length!=1 ){
						bugReporter.reportBug(new BugInstance(this, "SV_INTERFACE_BUG",
								NORMAL_PRIORITY).addClass(javaClass));
						return;
					}else{
						//log.debug(interfaces[0]);

						int last=packageName.lastIndexOf(".");
						
						String shortClass=visitClassName.substring(packageName.length()+1);
						//log.debug("packageName:"+packageName);
						//log.debug("shortClass:"+shortClass);
						
						if (last>=0)
							packageName=packageName.substring(0,last);
						//log.debug("Super packageName:"+packageName);
						String interPackage=packageName+"interfaces.";
						
						//log.debug("interPackage packageName:"+packageName);

						String inter=interfaces[0];
						if (!inter.startsWith(interPackage)){
							
							bugReporter.reportBug(new BugInstance(this, "SV_INTERFACE_BUG", NORMAL_PRIORITY).addClass(javaClass));
							//bugReporter.reportBug(new BugInstance(this, "SV_EXCEPTION_BUG",
							//		NORMAL_PRIORITY).addClass(this));
							return;
						}
						String interName=inter.substring(interPackage.length()+1);
						//log.debug("interName:"+interName);

						if (interName.equals("I"+visitClassName.subSequence(0,visitClassName.length()-4))){
							bugReporter.reportBug(new BugInstance(this, "SV_INTERFACE_BUG",
									NORMAL_PRIORITY).addClass(javaClass));
							return;
						}
						
					}
				}
			}
		
		if (visitClassName.endsWith("SV") || visitClassName.endsWith("SVImpl"))
			super.visitJavaClass(javaClass);	

	}

 
	
	@Override
	public void visit(Method obj) {
		String mName = getMethodName();
		// String className = obj.getClass().getName();

		// if (visitClassName.endsWith("SV") ||
		// visitClassName.endsWith("SVImpl")) {

		// log.debug("mName:+"+mName);
		if (obj.isPublic() == false || mName.equalsIgnoreCase("<init>"))
			return;
		ExceptionTable Exceptions = obj.getExceptionTable();
		if (Exceptions == null) {
			bugReporter.reportBug(new BugInstance(this, "SV_EXCEPTION_BUG",
					NORMAL_PRIORITY).addClassAndMethod(this));
			return;
		}
		String ExcptionName[] = Exceptions.getExceptionNames();
		//boolean ExistsRemoteException = false, 
		boolean ExistsException = false;

		for (int i = 0; i < ExcptionName.length; i++) {
			//if (ExcptionName[i].equals("java.rmi.RemoteException"))
			//	ExistsRemoteException = true;
			if (ExcptionName[i].equals("java.lang.Exception")){
				ExistsException = true;
				break;
			}
			// log.debug("ExcptionName:+"+ExcptionName[i]);
		}
		//ExistsRemoteException == false ||
		if ( ExistsException == false)

			bugReporter.reportBug(new BugInstance(this, "SV_EXCEPTION_BUG",
					NORMAL_PRIORITY).addClassAndMethod(this));

		// }

	}

}
