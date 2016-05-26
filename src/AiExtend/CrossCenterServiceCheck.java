package AiExtend;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

/**
 * @author 
 */
public class CrossCenterServiceCheck extends OpcodeStackDetector {
	private BugReporter bugReporter;

	
	private String visitClassName;
	@Override
	public void visitJavaClass(JavaClass javaClass) {
		// TODO Auto-generated method stub
		
		
		visitClassName=javaClass.getClassName();
		
		//log.debug("className:+"+visitClassName);
		super.visitJavaClass(javaClass);
		
	}
	
	public CrossCenterServiceCheck(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}

	/**
	 * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
	 */
	@Override
	public void visit(Code obj) {
		super.visit(obj);
	}

	
	private int getCrossCenterServicePC=-1;
	@Override
	public void visit(Method obj) {
		getCrossCenterServicePC=-1;
		//log.debug("visit method "+obj.getName());
		
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
		
		
		if (( visitClassName.endsWith("SV") || visitClassName.endsWith("SVImpl") )
				&& visitClassName.indexOf(".service.")>0 ){
			if (seen == INVOKESTATIC  && getClassConstantOperand().equals("com/ai/appframe2/service/ServiceFactory")
						&& (getNameConstantOperand().equals("getCrossCenterService") )) {
					BugInstance bug = new BugInstance(this, "CROSS_CENTER_BUG",
							NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
							this, getPC());
					bug.addInt(getPC());
					bugReporter.reportBug(bug);
				}
			return;
		}
		//检查  调用cross是否调用了jdbc方法
		if (seen == INVOKESTATIC  && getClassConstantOperand().equals("com/ai/appframe2/service/ServiceFactory")
				&& (getNameConstantOperand().equals("getCrossCenterService") )) {
			getCrossCenterServicePC=getPC();
			 
		}
		
		
		//在CROSS后发现调用了PreparedStatement,Statement
		if (getCrossCenterServicePC>=0){
			if (seen == INVOKEINTERFACE  && 
					( getClassConstantOperand().equals("java/sql/PreparedStatement") ||
							getClassConstantOperand().equals("java/sql/Statement"))
					) {
				BugInstance bug = new BugInstance(this, "DB_CROSS_BUG",
						NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
						this, getPC());
				bug.addInt(getPC());
				bugReporter.reportBug(bug);
			}
		}
		
		
		
	}
}