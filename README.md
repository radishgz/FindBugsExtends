# FindBugsExtends

这里是一些对findbug 的规则扩展，具体见message
<?xml version="1.0" encoding="UTF-8"?>
<MessageCollection xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="messagecollection.xsd">

	<Plugin>
		<ShortDescription>AIEXTEND Plugin</ShortDescription>
		<Details>Provides AIEXTEND detectors as part of the FindBugs detector
			plugin
			tutorial.</Details>
	</Plugin>

	<Detector class="AiExtend.ForbiddenSystemClass">
		<Details>
			Using System.err or System out bug.
                </Details>

	</Detector>

	<BugPattern type="System_Out_Err_BUG">
		<ShortDescription>Used System.err or System out bug</ShortDescription>
		<LongDescription>Used System.err or System out bug,Please replace with
			log</LongDescription>
		<Details>
			<![CDATA[
			<p>Used System.err or System out bug,Please replace with log.</p>
			]]>
		</Details>
	</BugPattern>
	<BugCode abbrev="SYS">Forbidden System out and err</BugCode>

	<Detector class="AiExtend.ForbiddenMainMethod">
		<Details>
			Using Main Method.
                </Details>

	</Detector>

	<BugPattern type="Main_Method_BUG">
		<ShortDescription>Using Main Method</ShortDescription>
		<LongDescription>Using Main Method,Please replace with junit
		</LongDescription>
		<Details>
			<![CDATA[
			<p>Using Main Method,Please replace with junit.</p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="MAIN">Forbidden Main Method</BugCode>



	<Detector class="AiExtend.AiSvMethod">
		<Details>
			SV/SVIMPL METHOD DON'T THROW REMOTE EXCEPTION
                </Details>

	</Detector>

	<BugPattern type="SV_EXCEPTION_BUG">
		<ShortDescription>SV/SVIMPL METHOD DON'T THROW REMOTE EXCEPTION
		</ShortDescription>
		<LongDescription>SV/SVIMPL public method don't throw
			 java.lang.Exception</LongDescription>
		<Details>
			<![CDATA[
			<p>SV/SVIMPL public method don't throw   java.lang.Exception</p>
			]]>
		</Details>
	</BugPattern>





	<BugPattern type="SV_INTERFACE_BUG">
		<ShortDescription>SV/SVIMPL Must and only implements interfaces i*SV 
		</ShortDescription>
		<LongDescription>SV/SVIMPL Must and only implements interfaces i*SV</LongDescription>
		<Details>
			<![CDATA[
			<p>SV/SVIMPL Must and only implements interfaces i*SV</p>
			]]>
		</Details>
	</BugPattern>
	


   <BugPattern type="SV_ATTRIBUTE_BUG">
		<ShortDescription>SV/SVIMPL HAVE ATTRIBUTE
		</ShortDescription>
		<LongDescription>SV/SVIMPL HAVE ATTRIBUTE,It is limited</LongDescription>
		<Details>
			<![CDATA[
			<p>SV/SVIMPL HAVE ATTRIBUTE,It is limited</p>
			]]>
		</Details>
	</BugPattern>
	
	
	
	
	 <BugPattern type="SV_CLINIT_BUG">
		<ShortDescription>SV/SVIMPL HAVE STATIC CODE
		</ShortDescription>
		<LongDescription>SV/SVIMPL HAVE STATIC CODE,It is limited</LongDescription>
		<Details>
			<![CDATA[
			<p>SV/SVIMPL HAVE STATIC CODE,It is limited</p>
			]]>
		</Details>
	</BugPattern>
	
	<BugCode abbrev="SVEX">SV method/Class define error</BugCode>




	<Detector class="AiExtend.LoggingClass">
		<Details>
			Check using log4j
                </Details>

	</Detector>

	<BugPattern type="UNPROTECTED_LOGGING">
		<ShortDescription>don't use if (log.isXXXEnabled) before
			log.debug/info/error</ShortDescription>
		<LongDescription>don't use if (log.isXXXEnabled) before
			log.debug/info/error</LongDescription>
		<Details>
			<![CDATA[
			<p>don't use if (log.isXXXEnabled) before log.debug/info/error</p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="LOG4J">USE LOG4J ERROR</BugCode>


	<BugPattern type="LIMIT_LOG_METHOD">
		<ShortDescription>use limited log4j method</ShortDescription>
		<LongDescription>warn,fatal,trace of log4j is limited
		</LongDescription>
		<Details>
			<![CDATA[
			<p>warn,fatal,trace of log4j is limited</p>
			]]>
		</Details>
	</BugPattern>




	<Detector class="AiExtend.DBConnectionClass">
		<Details>
			Check using JDBC Connection
                </Details>

	</Detector>

	<!-- <BugPattern type="JDBC_CREATE_ERROR"> <ShortDescription>jdbc connect 
		create error</ShortDescription> <LongDescription>jdbc connect must create 
		by com.ai.appframe2.common.Session.getConnection()</LongDescription> <Details> 
		<![CDATA[ <p>jdbc connect must create by com.ai.appframe2.common.Session.getConnection()</p> 
		]]> </Details> </BugPattern> <BugCode abbrev="JDCA">JDBC_CREATE_ERROR</BugCode> -->


	<BugPattern type="JDBC_STATEMENT">
		<ShortDescription>use JDBC STATEMENT</ShortDescription>
		<LongDescription>use jdbc statement ,please replace with
			PreparedStatement</LongDescription>
		<Details>
			<![CDATA[
			<p>use jdbc statment ,please replace with PreparedStatement</p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="JDBC">Use JBDC error  </BugCode>


	<BugPattern type="NO_BO_JDBC_EXE">
		<ShortDescription>use JDBC EXECUTE outside bo*class </ShortDescription>
		<LongDescription>use JDBC EXECUTE outside bo*class ,it is limited
		</LongDescription>
		<Details>
			<![CDATA[
			<p>use JDBC EXECUTE outside bo*class ,it is limited</p>
			]]>
		</Details>
	</BugPattern>

	 

	<BugPattern type="JDBC_INVOKE_ERROR">
		<ShortDescription>DB OPERATION ERROR</ShortDescription>
		<LongDescription>DB CONNECTION ONLY INVOKE BY BO Engine class and
			daoImpl class </LongDescription>
		<Details>
			<![CDATA[
			<p>DB CONNECTION ONLY INVOKE BY BO Engine class and daoImpl class </p>
			]]>
		</Details>
	</BugPattern>

	 

	<BugPattern type="NO_BO_JDBC">
		<ShortDescription>JDBC operation invoke error     </ShortDescription>
		<LongDescription>JDBC PreparedStatement/Statement is limited invoke by
			bo*engineer class   </LongDescription>
		<Details>
			<![CDATA[
			<p>JDBC PreparedStatement/Statement method is limited invoke by bo*engineer class    </p>
			]]>
		</Details>
	</BugPattern>

	 


	<Detector class="AiExtend.CrossCenterServiceCheck">
		<Details>
			Find SV/SVIMPL class using getCrossCenterService
                </Details>

	</Detector>


	<BugPattern type="CROSS_CENTER_BUG">
		<ShortDescription>using getCrossCenterService error </ShortDescription>
		<LongDescription>SV/SVIMPL class using getCrossCenterService,it is
			limited </LongDescription>
		<Details>
			<![CDATA[
			<p>SV/SVIMPL class using  getCrossCenterService,it is limited  </p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="CRCE">CROSS CENTER BUG</BugCode>


	<BugPattern type="DB_CROSS_BUG">
		<ShortDescription>USE DB OPERATION AFITER USED getCrossCenterService
		</ShortDescription>
		<LongDescription>USE DB OPERATION AFITER USED getCrossCenterService
		</LongDescription>
		<Details>
			<![CDATA[
			<p>USE DB OPERATION AFITER USED  getCrossCenterService   </p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="DBCR">DB_CROSS_BUG</BugCode>



	<Detector class="AiExtend.GetSession">
		<Details>
			ServiceManager.getSession check.
                </Details>

	</Detector>

	<BugPattern type="GET_SESSION">
		<ShortDescription>ServiceManager.getSession Invoke error
		</ShortDescription>
		<LongDescription>ServiceManager.getSession Invoke error,please don't
			set username   </LongDescription>
		<Details>
			<![CDATA[
			<p>ServiceManager.getSession Invoke error,please don't set username    </p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="GESE">invoke ServiceManager.getSession  error</BugCode>


	<Detector class="AiExtend.AiCacheMethod">
		<Details>CacheImpl Class check for package ,method and return type</Details>

	</Detector>

	<BugPattern type="CACHE_EXCEPTION_BUG">
		<ShortDescription>CacheImpl METHOD Define error</ShortDescription>
		<LongDescription>CacheImpl public method don't throw
			java.lang.Exception or returnType is not HashMap</LongDescription>
		<Details>
			<![CDATA[
			<p>CacheImpl public method don't throw   java.lang.Exception or returnType is not HashMap</p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="AICACHE">CacheImpl class error</BugCode>


	<BugPattern type="CACHE_EXTENDS_BUG">
		<ShortDescription>CacheImpl class Define error</ShortDescription>
		<LongDescription>class name not ends With "CacheImpl" or not in correct
			package </LongDescription>
		<Details>
			<![CDATA[
			<p>A class extends com.ai.appframe2.complex.cache.impl.AbstractCache 
				not in 
				1:com.asiainfo.crm.common.cache
				2:com.asiainfo.crm.ams.common.cache
				3:com.asiainfo.crm.so.common.cache
				package  or class name not ends With "CacheImpl" </p>
			]]>
		</Details>
	</BugPattern>



	<Detector class="AiExtend.PackageInvokeCheck">
		<Details>
			COM.ASIAINFO Package Invoke Analyse                </Details>
	</Detector>


	<BugPattern type="INVOKE_CACHE_DAO">
		<ShortDescription>CACHEIMPL invoke dao</ShortDescription>
		<LongDescription>invoke dao method in an cacheImpl calss</LongDescription>
		<Details>
			<![CDATA[
			<p>invoke dao method in an cacheImpl calss</p>
			]]>
		</Details>
	</BugPattern>
	



	<BugPattern type="INVOKE_CACHE_OUTPACK">
		<ShortDescription>CACHEIMPL invoke Other package's class</ShortDescription>
		<LongDescription>CACHEIMPL invoke Other package's  service class</LongDescription>
		<Details>
			<![CDATA[
			<p>CACHEIMPL invoke Other package's service  class</p>
			]]>
		</Details>
	</BugPattern>
	


	<BugPattern type="INVOKE_CACHE_BO_ERR">
		<ShortDescription>CACHE INVOKE BO METHOD ERROR</ShortDescription>
		<LongDescription>Cache only limit invoke bo's init,set,get method</LongDescription>
		<Details>
			<![CDATA[
			<p>Cache only limit invoke bo's init,set,get method</p>
			]]>
		</Details>
	</BugPattern>
	


	<BugPattern type="INVOKE_ACT_DAO">
		<ShortDescription>action class invoke dao method</ShortDescription>
		<LongDescription>action class invoke dao method</LongDescription>
		<Details>
			<![CDATA[
			<p>action class invoke dao method</p>
			]]>
		</Details>
	</BugPattern>
	


	<BugPattern type="INVOKE_ACT_OUTPACK">
		<ShortDescription>action class invoke Other package's class</ShortDescription>
		<LongDescription>action class invoke Other package's   service  class</LongDescription>
		<Details>
			<![CDATA[
			<p>action class invoke Other package's   service  class</p>
			]]>
		</Details>
	</BugPattern>
	

	<BugPattern type="INVOKE_ACT_BO_ERR">
		<ShortDescription>action invoke bo method error</ShortDescription>
		<LongDescription>action only limit invoke bo's init,set,get method</LongDescription>
		<Details>
			<![CDATA[
			<p>action only limit invoke bo's init,set,get method</p>
			]]>
		</Details>
	</BugPattern>
	

	<BugPattern type="INVOKE_DAO_WEB_SV">
		<ShortDescription>dao class invoke action or serv class</ShortDescription>
		<LongDescription>dao class invoke action or serv class</LongDescription>
		<Details>
			<![CDATA[
			<p>dao class invoke action or serv class</p>
			]]>
		</Details>
	</BugPattern>
	
 
	<BugPattern type="INVOKE_DAO_BO">
		<ShortDescription>dao class invoke Other package's bo class' Save method</ShortDescription>
		<LongDescription>dao class invoke Other package's bo class' Save method</LongDescription>
		<Details>
			<![CDATA[
			<p>dao class invoke Other package's bo class' Save method,only limited invoke get,set,transfer</p>
			]]>
		</Details>
	</BugPattern>
 
	<BugPattern type="INVOKE_SV_WEB">
		<ShortDescription>sv class invoke action class</ShortDescription>
		<LongDescription>sv class invoke action class</LongDescription>
		<Details>
			<![CDATA[
			<p>sv class invoke action class</p>
			]]>
		</Details>
	</BugPattern>


	<BugPattern type="INVOKE_SV_OUTPACK">
		<ShortDescription>sv class invoke Other package's class</ShortDescription>
		<LongDescription>sv class invoke Other package's dao class</LongDescription>
		<Details>
			<![CDATA[
			<p>sv class invoke Other package's dao class </p>
			]]>
		</Details>
	</BugPattern>
	


	<BugPattern type="INVOKE_SV_BO_ERROR">
		<ShortDescription>SV class invoke bo's method error</ShortDescription>
		<LongDescription>sv calss only limit invoke bo's init,set,get method</LongDescription>
		<Details>
			<![CDATA[
			<p>sv class only limit invoke bo's init,set,get method</p>
			]]>
		</Details>
	</BugPattern>
		<BugPattern type="INVOKE_DAO_DAO">
		<ShortDescription>dao class invoke dao class</ShortDescription>
		<LongDescription>dao class invoke dao class</LongDescription>
		<Details>
			<![CDATA[
			<p>dao class invoke dao class</p>
			]]>
		</Details>
	</BugPattern>
	

 

	<BugCode abbrev="AIPACKAGE">AICRM invoke error</BugCode> 

<Detector class="AiExtend.ForbiddenCommitRollbackClass">
		<Details>check use jdbc commit or rollback in asiainfo package</Details>

	</Detector>

	<BugPattern type="DB_COMMIT_ROLLBACK">
		<ShortDescription>Us commit or rollback </ShortDescription>
		<LongDescription>commit or rollback method is be limited in asiainfo package </LongDescription>
		<Details>
			<![CDATA[
			<p>commit or rollback method is be limited in asiainfo package</p>
			]]>
		</Details>
	</BugPattern>
	
	
	<Detector class="AiExtend.ExceptionCheck">
		<Details>
			Check using chinese in throws exception code 
                </Details>

	</Detector>

	<BugPattern type="CHINESE_EXCEPTION_ERROR">
		<ShortDescription>don't use  chinese is throws exception code </ShortDescription>
		<LongDescription>don't use  chinese is throws exception code </LongDescription>
		<Details>
			<![CDATA[
			<p>don't use  chinese is throws exception code </p>
			]]>
		</Details>
	</BugPattern>
	
		<BugCode abbrev="CHINESE_EXCEPTION_ERROR">USE CHINESE_EXCEPTION_ERROR ERROR</BugCode>
	
<Detector class="AiExtend.ForbiddenChineseMethod">
		<Details>
			Check using chinese 
                </Details>

	</Detector>

	<BugPattern type="CHINESE_ERROR">
		<ShortDescription>don't use  chinese is java code </ShortDescription>
		<LongDescription>don't use  chinese is java code </LongDescription>
		<Details>
			<![CDATA[
			<p>don't use  chinese is java code </p>
			]]>
		</Details>
	</BugPattern>

	<BugCode abbrev="CHINESE_ERROR">USE CHINESE ERROR</BugCode>

	
	<Detector class="AiExtend.AiDaoMethod">
		<Details>
			Check daoImpl method and static code 
                </Details>

	</Detector>
	
	<BugPattern type="DAO_ATTRIBUTE_BUG">
		<ShortDescription>DAO/DAOIMPL HAVE ATTRIBUTE
		</ShortDescription>
		<LongDescription>DAO/DAOIMPL HAVE ATTRIBUTE,It is limited</LongDescription>
		<Details>
			<![CDATA[
			<p>DAO/DAOIMPL HAVE ATTRIBUTE,It is limited</p>
			]]>
		</Details>
	</BugPattern>
	
	
	
	
	 <BugPattern type="DAO_CLINIT_BUG">
		<ShortDescription>DAO/DAOIMPL HAVE STATIC CODE
		</ShortDescription>
		<LongDescription>DAO/DAOIMPL HAVE STATIC CODE,It is limited</LongDescription>
		<Details>
			<![CDATA[
			<p>DAO/DAOIMPL HAVE STATIC CODE,It is limited</p>
			]]>
		</Details>
	</BugPattern>
	
	
	<BugCode abbrev="DAO_ERROR">DaoImpl class error</BugCode>
	 
		
	
</MessageCollection>
