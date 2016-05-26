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

public class AiDaoMethod extends PreorderVisitor implements Detector {

	private static transient Log log = LogFactory.getLog(AiDaoMethod.class);

	private BugReporter bugReporter;

	public AiDaoMethod(BugReporter bugReporter) {
		super();
		this.bugReporter = bugReporter;
		// log.debug("className:AiSvMethod.....");
	}

	// @Override
	public void report() {
		// TODO Auto-generated method stub

	}

	// @Override
	public void visitClassContext(ClassContext classContext) {

		classContext.getJavaClass().accept(this);

	}

	private String visitClassName;

	@Override
	public void visitJavaClass(JavaClass javaClass) {
/*
		visitClassName = javaClass.getClassName();

		if (visitClassName.endsWith("SVImpl")) {
			 
			// 定义了属性
			if (javaClass.getFields().length > 0) {
				 
				bugReporter.reportBug(new BugInstance(this, "DAO_ATTRIBUTE_BUG",
						NORMAL_PRIORITY).addClass(javaClass));
			}

			// 使用了static 代码
			for (int i = 0; i < javaClass.getMethods().length; i++) {
				// System.err.println("for debuging..."+javaClass.getAttributes()[i].toString());
				Method m = javaClass.getMethods()[i];

				// log.debug("getAttributes debuging..."+javaClass.getMethods()[i].toString()+"   "+m.isStatic());
				if (m.getName().equals("<clinit>")) {
					bugReporter.reportBug(new BugInstance(this,
							"DAO_CLINIT_BUG", NORMAL_PRIORITY)
							.addClass(javaClass));
					break;
				}
			}
		}
 */
	}

	@Override
	public void visit(Method obj) {

	}

}
