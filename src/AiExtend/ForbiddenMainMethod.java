package AiExtend;

import org.apache.bcel.classfile.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.visitclass.PreorderVisitor;

public class ForbiddenMainMethod extends PreorderVisitor implements Detector  {

	private BugReporter bugReporter;
	//private static transient Log log = LogFactory.getLog(ForbiddenMainMethod.class);
	
	
	public ForbiddenMainMethod(BugReporter bugReporter) {
		super();
		this.bugReporter = bugReporter;
	}

	//@Override
	public void report() {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void visitClassContext(ClassContext classContext) {
		  classContext.getJavaClass().accept(this);
		
	}

	
	@Override
    public void visit(Method obj) {
        String mName = getMethodName();
        if (mName.equalsIgnoreCase("main"))
           
           bugReporter.reportBug(new BugInstance(this, "Main_Method_BUG",
					NORMAL_PRIORITY).addClassAndMethod(this));
      //  log.debug("find a bug");
          
 
    }
}
