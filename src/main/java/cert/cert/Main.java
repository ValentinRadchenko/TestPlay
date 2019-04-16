package cert.cert;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Stream;





import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

public class Main {
 
	private static String status="_PUSH";
	private static String i_mode;
	private static String msgId;
	private static String stDescript;
	private static String fileName;
	static String pattern ="ddMMyyyHHmmss";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	static String date;
	private static String service;
	static BigDecimal total=new BigDecimal(0);
	static BigDecimal sumall=new BigDecimal(0);
	static int quantity=0;
	static int alltransfers=0;
	static  boolean daterc;
	static OracleConnection connection;
    static int errorCode; 
    static int error;
    static LocalDate currentDate = LocalDate.now();
    static int month;
    static int year; 
    
	
    //static Statament stmt;
	public static void main(String[] args) throws SQLException, SecurityException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CMSException, CertificateException, DecoderException, ParseException, SAXException, ParserConfigurationException, JAXBException {
     

		
		OracleConnection conn = (OracleConnection) CreateConnect2();
							
			File folder = new File("D:\\OSCHADBANK\\FromFTP");
			File folderPush=new File("D:\\OSCHADBANK\\PUSH\\FromBank");
			File folderCheck=new File("D:\\OSCHADBANK\\CHECK\\FromBank");
			File folderPull = new File("D:\\OSCHADBANK\\PULL\\FromBank");
			
			File[] listOfFiles = folder.listFiles();
			File[] listOfFilesPush = folderPush.listFiles();
			File[] listOfFilesCheck = folderCheck.listFiles();
			File[] listOfFilesPull = folderPull.listFiles();
			connection = (OracleConnection) Main.CreateConnect();
			//stmt = connection.createStatement();     
			
			
			
			
			
			for (File file : listOfFiles) {
			
						
				
				
				switch(file.getName().substring(0,2)) {
				case "RC": service="transfer";
				
				CheckTotal ct= new CheckTotal();
		        CreateXML xml =new CreateXML();
		        
		        if(file.getName().substring(file.getName().length() - 3).equals("xml")) {
         
                CheckRcDate rcdate = new CheckRcDate();
                daterc = rcdate.checkRcDate(file.getName());
                
                }
              	break;
              	
				case "RF": service="finance";
		
		        
		        if(file.getName().substring(file.getName().length() - 3).equals("xml")) {
            
                }
              	break;
              	
              	
				case "WB": service="transferenceDuplicate";
			
	              	break;
					         	  	
              	
				case "RO": service="recipient";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;	
				
				case "RD": service="recipient";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;
				
				case "RM": service="finance";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;	
							
				
				case "RI": service="transferenceData";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;	
				
				case "PI": service="transferencePens";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;	
				
				//---------------------------------
				case "RJ": service="transferenceData";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;	
				
				case "RW": service="transferenceConsolidate";
				total=new BigDecimal(0);
				sumall=new BigDecimal(0);
				quantity=0;
				alltransfers=0;
				break;
				
				
				case "ZA": service="recipientsinfo";
				
              	break;
	            
				case "ZB": service="transactionsinfo";
				
              	break;
				
		        case "ZG": service="amountsinfo";
				
              	break;
              	
                 case "ZR": service="reportTransference";
				
              	break;
              	
                
		        case "RQ": service="transferenceCorrection";
				
              	break;
                 case "RV": service="transferenceCorrection";
				
              	break;    
				
				
				//-----------------------------------
				
				default: service="noname";
				break;
				
				}
				
				
				
				
				
		    if (file.isFile()&&file.getName().substring(file.getName().length() - 3).equals("xml")) {
		    	
		    	
		    	String name=file.getName().substring(0,file.getName().length() - 4);
	    		String repDate="01."+name.subSequence(13, 15)+"."+name.substring(15, 19);		
	    		String dateInFile=getTagValue(file.getAbsolutePath(), "timestamp");
	    		String regId=getTagValue(file.getAbsolutePath(), "regId");
	    		
	    		
	    	
	    		
	    		
	    		if(currentDate.toString().compareTo(dateInFile.substring(0, 10))<0) {
	    			
	    			InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","	IOC-016:Помилка - дата та час формування реєстру більше поточної дати",total,0,0, repDate );
	    			replaceFile(false,file.getAbsolutePath());
			    	DeleteFile(file.getAbsolutePath());
	    			
	    			break;
	    		}
	    		
	    		if(!name.toLowerCase().equals(regId.toLowerCase())) {
	    			 InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-012	:Помилка - розбіжність назви реестру з назвою у файлі",total,0,0, repDate );
	    			 replaceFile(false,file.getAbsolutePath());
	 		    	 DeleteFile(file.getAbsolutePath());
	    			 
	    			 break;
	    		}
	    		
	               
//	    		CheckRcMonth checkMonth=new CheckRcMonth();
//	    		
//	    		if(service=="transfer"&& checkMonth.checkDoubleMonth(name)==1) {
//	    			 InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-012	:Помилка - розбіжність назви реестру з назвою у файлі",total,0,0, repDate );
//	    			 replaceFile(false,file.getAbsolutePath());
//	 		    	 DeleteFile(file.getAbsolutePath());
//	    			 
//	    			 break;
//	    		}
	    		
	    		
	    		
	    		
	    		
	    		
		    	
		    	
	    		
	    		
	    		
		    	if(file.getName().substring(file.getName().length() - 3).equals("xml")&&checkDoubleReject(name)==0){
		    		    				    		
		    		Boolean xmlchk;
		    		String certchk;
		    		
		    		XmlCheck xml = new XmlCheck();
		    		xmlchk=xml.xmlCheckIn(file.getAbsolutePath(),service);
		    		
		    		if(xmlchk==false) {
		    	
		    			  
			    			InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-001:Помилка XSD-схеми",total,0,0, repDate );
		    	    		
		    		}
		    	
		    		if((service.equals("transfer")|| service.equals("finance")||service.equals("transferenceData")||service.equals("transferencePens"))&& xmlchk==true)
		    		
		    		{
		    		
		    		CheckTotal ct1= new CheckTotal();
		    	    CreateXML xml1 =new CreateXML();
		    		sumall=ct1.getTotal(file.getAbsolutePath());
		            total=xml1.getTotal(file);
		                   
		            quantity=xml1.getQuantity(file);
		            if(service.equals("finance"))
		            alltransfers=ct1.getAllRegisters(file.getAbsolutePath());
		            else if (service.equals("transfer"))       
		            alltransfers=ct1.getAllTransfers(file.getAbsolutePath()); 
		            else 
		            alltransfers=ct1.getAllTransferenseData(file.getAbsolutePath());
		    		}
		    		FileChecker fl = new FileChecker();
		    		certchk=fl.fileChk(file.getAbsolutePath(),conn,name.substring(0,2));
		    		System.out.println(file.getName());
		    		if(certchk!="" && xmlchk == true) {
		    	
		    		
		    			InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed",certchk,total,0,0,repDate);
		    			
		    		}   	
		    		
		    		  if(name.substring(0,2).equalsIgnoreCase("RC") && total.compareTo(sumall)!=0 && daterc ==true ) {
		            
		    			
		    			  InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-007:Помилка - розбіжність суми заголовку по сумі у файлі",total,0,0, repDate );
		    			
		                }
		    		  if(name.substring(0,2).equalsIgnoreCase("RC") && quantity!=alltransfers && daterc ==true) {
			            
		    			
		    			  InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-008:Помилка - розбіжність кількості заголовку по кількості у файлі",total,0,0, repDate );
		    		  			  
			                }
		    		
		    		  if(name.substring(0,2).equalsIgnoreCase("RC") && daterc ==false ) {
		    		   			  
		    			 
		    			  InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-011:Помилка - невірна звітна дата",total,0,0, repDate );
		    			//---------
		    			  
		    			  
		    		  }
		    		  
		    		  
		    		  if(!name.substring(0,2).equalsIgnoreCase("RC") && total.compareTo(sumall)!=0 ) {
			          
		    			
		    			  InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-007:Помилка - розбіжність суми заголовку по сумі у файлі",total,0,0, repDate );
		    			
		    			  
			                }
			    	  if(!name.substring(0,2).equalsIgnoreCase("RC") && quantity!=alltransfers) {
				      
			    		  
			    		 
			    		  InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-008:Помилка - розбіжність кількості заголовку по кількості у файлі",total,0,0, repDate );
			    		
				                }
			    	  
			 		  
		    		
		    		if(xmlchk && certchk=="" && total.compareTo(sumall)==0 && quantity==alltransfers ){    //&& daterc ==true
		    			replaceFile(true,file.getAbsolutePath());
		    			String data =Hex.encodeHexString
		    					(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		    			String sign =Hex.encodeHexString
		    					(Files.readAllBytes(Paths.get(file.getAbsolutePath()+".p7s")));
		    			String stamp =Hex.encodeHexString
		    					(Files.readAllBytes(Paths.get(file.getAbsolutePath()+".p7s.p7s")));
		    			CreateXML xmlfile = new CreateXML();
		    			
		    			xmlfile.createXml(data, sign, stamp,name);
		    				    			
		    	
		    			
		    			
		    			if(file.getName().substring(0,2).equals("RC") && daterc==true)
	           
		    		
		    			InsertSql(name,"40",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","1","ECCSuccess","IOC-000:Помилки відсутні",total,0,0,repDate,quantity);
		    		  		
		    		
		    			
		    			if(!file.getName().substring(0,2).equals("RC")&&!file.getName().substring(0,2).equals("PI"))
		    				
		    			
		    			InsertSql(name,"40",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","1","ECCSuccess","IOC-000:Помилки відсутні",total,0,0,repDate,quantity);
		    			
		    			
		    		
		    			
	                    if(file.getName().substring(0,2).equals("PI"))
		    				
	                    	
		    				InsertSql(name,"70",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","1","ECCSuccess","IOC-000:Помилки відсутні",total,0,0,repDate,quantity);
	                    		    			
		    			
	                    
		    			if(file.getName().substring(0,2).equals("RI")||file.getName().substring(0,2).equals("RJ")||file.getName().substring(0,2).equals("PI") && error==0) {
			    		
				    			Person person=new Person();
				    			person.setPersons(file.getAbsolutePath());
				    	
				    			}
		    		
		    				    			
		    			
		    		    	DeleteFile(file.getAbsolutePath());
		    				listOfFiles = folder.listFiles();
		    			
		    			
		    		}
		    		else{
		    			replaceFile(false,file.getAbsolutePath());
		    			DeleteFile(file.getAbsolutePath());
		    			
		    			
		    		}
		    	
		    		
		    }else if(name.length()>19){
		    	InsertSql(name.substring(0,19),"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-015:Помилка-невірне накладання ЕЦП",total,0,0, repDate );
		    //	replaceFile(false,file.getAbsolutePath());
		    	file.delete();
		    }else {
		    	InsertSql(name,"41",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"push","-1","ECCFailed","IOC-014:Помилка-regId реєстру співпадає з отриманим раніше, cформуйте повторний реєстр з новою назвою",total,0,0, repDate );
		    	replaceFile(false,file.getAbsolutePath());
		    	DeleteFile(file.getAbsolutePath());
		    }
		    	
		}
		    	  		    
		//----------------------------------------------------------------------	
		    if (file.isFile()) {
		    	if(file.getName().substring(0,2).equalsIgnoreCase("ZA")||file.getName().substring(0,2).equalsIgnoreCase("ZB")||file.getName().substring(0,2).equalsIgnoreCase("ZG")||file.getName().substring(0,2).equalsIgnoreCase("ZR")){
		    		String name=file.getName();
		    		String repDate;
		    
		    			repDate="01."+name.subSequence(13, 15)+"."+name.substring(15, 19);
		    		
		    			InsertSql(name,"40",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"check","1","ECCSuccess","IOC-000:Помилки відсутні",total,0,0,repDate,quantity);	
		    			
		    			File source = new File("D:\\OSCHADBANK\\FromFtp\\"+file.getName());
					    File dest=new File("D:\\OSCHADBANK\\BUFFER\\"+file.getName());
		   		    	FileUtils.copyFile(source, dest); 
					    source.delete();
		    	}
		    }
		 //-----------------------------------------------------------------------   			
		    			
		    			//}
		    
		    
		  //  if(connection!=null)
			//	connection.close();
		
	
		}
			
			
			
		
		for (File pushFile : listOfFilesPush) {
			CreateXML xmlfile = new CreateXML();
			
			if (pushFile.isFile()){		  
				msgId= xmlfile.getMsgId(pushFile);
		  	}
		  		if(isPush(pushFile.getName())==true) {
		    		xmlfile.createCheck(pushFile.getName(), msgId);
		   	    InsertSqlCheck(pushFile.getName(),msgId,"ECCSuccess",1,"check");
			 	pushFile.delete();
		    	}
	         }	
		
		
		
		
		for (File checkFile : listOfFilesCheck) {
			CreateXML xmlfile = new CreateXML();
			AllMessage am= new AllMessage();
		
         switch(checkFile.getName().substring(0,2)) {
	 		case "ZB": service="transactionsinfo";
			break;	
			case "ZG": service="amountsinfo";
			break;	
			case "ZR": service="reportTransference";
			break;	
			
			
         }
			
			  if(checkFile.getName().substring(0,2).equalsIgnoreCase("ZB")||checkFile.getName().substring(0,2).equalsIgnoreCase("ZG")||checkFile.getName().substring(0,2).equalsIgnoreCase("ZR")){
				        
				   InsertSqlCheck(checkFile.getName(),70,"","ESBSuccess",0,"finish","");
				  			    
		
				   
				   am.setMessage(checkFile.toString(),service);
				   
				  
				   File source= new File("D:\\OSCHADBANK\\CHECK\\FromBank\\"+checkFile.getName());
				   File dest = new File("D:\\OSCHADBANK\\OKEY\\"+checkFile.getName());
				   FileUtils.copyFile(source, dest);
				   checkFile.delete();
				   			   
				   break;
			    
			}
			
			
			if (checkFile.isFile()&&!(checkFile.getName().substring(0,2).equalsIgnoreCase("ZB")||checkFile.getName().substring(0,2).equalsIgnoreCase("ZG"))){	
				msgId=xmlfile.getMsgId(checkFile);
				status= xmlfile.getStatus(checkFile);
				stDescript=xmlfile.getStatusDesc(checkFile);
				
				
		  	}
		  		
			
			if(isCheck(checkFile.getName())==true && status.equalsIgnoreCase("ESBSuccess") ) {
		        xmlfile.createPull(checkFile.getName(), msgId);
		   	    InsertSqlCheck(checkFile.getName(),40,msgId,status,1,"pull",stDescript);
			 	checkFile.delete();
		    }else if (status.equalsIgnoreCase("EBKFailed")){
		    		 InsertSqlCheck(checkFile.getName(),61,msgId,status,-2,"check",stDescript);
		    		 File source = new File(checkFile.getAbsolutePath());
		    		 source.delete();
		    		 File filebuff = new File("D:\\OSCHADBANK\\BUFFER\\"+checkFile.getName());
		   		     filebuff.delete();
		    		
		    	}else if((checkFile.getName().substring(0,2)).equalsIgnoreCase("RC")&&status.equalsIgnoreCase("EBKInprogress"))	{
					
					 InsertSqlCheck(checkFile.getName(),70,msgId,status,1,"check");
					 
					    File source = new File("D:\\OSCHADBANK\\BUFFER\\"+checkFile.getName());
					    File dest=new File("D:\\OSCHADBANK\\CHECK\\CheckRC\\"+checkFile.getName());
		   		    	FileUtils.copyFile(source, dest); 
					    source.delete();
					    File cfb=new File("D:\\OSCHADBANK\\CHECK\\FromBank\\"+checkFile.getName());
					    cfb.delete();
					 
					
			    }else if(status.equalsIgnoreCase("ESBInprogress")||status.equalsIgnoreCase("EBKInprogress"))	{
					
					 InsertSqlCheck(checkFile.getName(),msgId,status,1,"check");
			    }else{
		    		InsertSqlCheck(checkFile.getName(),51,msgId,status,-2,"check",stDescript);
		    		File source = new File(checkFile.getAbsolutePath());
    			    source.delete();
    			    File filebuff = new File("D:\\OSCHADBANK\\BUFFER\\"+checkFile.getName());
	   		    	 filebuff.delete();
		    	   	}
		  		  		
	         }	
		
		

		
		
		
		
		
		for (File pullFile : listOfFilesPull) {
		   	CreateXML xmlfile = new CreateXML();
		   	switch(pullFile.getName().substring(0,2)) {
			
			case "RC": service="transfer";
			break;
			case "RF": service="finance";
			break;
			case "RD": service="recipient";
			break;
			case "RO": service="recipient";
			break;	
			case "RI": service="transferenceData";
			break;	
			case "ZA": service="recipientsinfo";
			break;	
			case "ZB": service="transactionsinfo";
			break;	
			case "ZG": service="amountsinfo";
			break;	
			case "ZR": service="reportTransference";
			break;	
			
			case "RB": service="amountsinfo";
			break;				
			case "RG": service="transactionsinfo";
			break;	
			case "RQ": service="transferenceCorrection";
			break;	
			case "RX": service="reportTransference";
			break;
			
			
			}

			if (pullFile.isFile()) {
				
		    	if(isPull(pullFile.getName())==true) {
		    		
		    		if(xmlfile.createXmlPull(pullFile,service)) {
		    			CreateXML xml1 =new CreateXML();
		    			String name=xml1.getRegId(pullFile);
		    			String path= "D:\\OSCHADBANK\\toFTp\\"+name+".xml";
			    			CheckTotal ct1= new CheckTotal();
			    			File source = new File(path);
			    			   //sumall=ct1.getTotalRM(pullFile);
			    			BigDecimal totalrej =new BigDecimal(0);
			    			int quantityrej=0;
			    			   if(!(service.equals("recipient")||service.equals("transferenceData")||service.equals("recipientsinfo")||service.equals("transactionsinfo")||service.equals("amountsinfo")||service.equals("transferenceCorrection")||service.equals("reportTransference")))
			    			   {
			                   total=xml1.getTotalRM(source,"proceedsTotalAmount");
			                   totalrej = xml1.getTotalRM(source,"notProceedsTotalAmount");
			                   quantity=xml1.getQuantityRM(source,"proceedsQuantity");
			                   quantityrej = xml1.getQuantityRM(source,"notProceedsQuantity");
			    			   }
			    			   else {
			    				   total = new BigDecimal(0);
			    				   
			    				   quantity = 0;
			    				   
			    			   }
			                   
			    			
		    			    	
		     	InsertSqlPull(pullFile.getName(), 70, "finish",1, "ESBSuccess","IOC-000:Помилки відсутні",xmlfile.getRegId(pullFile),total,totalrej,quantity,quantityrej);
		    	
		     	
		     	
		     	//InsertSqlPull(xmlfile.getRegId(pullFile));
		    	 pullFile.delete();
		    	 File filebuff = new File("D:\\OSCHADBANK\\BUFFER\\"+pullFile.getName());
		    	 filebuff.delete();   
		       }else {
		        	InsertSqlPull(pullFile.getName(), 71, "notfinish",-2, "ECCFailed","IOC-111:Помилка XSD-схеми(відповіді)",xmlfile.getRegId(pullFile),new BigDecimal(0),new BigDecimal(0),0,0);    	   
		            FileToError(pullFile.getAbsolutePath());
		            pullFile.delete();
		       //----------------------------------------------- 
		            File filebuff = new File("D:\\OSCHADBANK\\BUFFER\\"+pullFile.getName());
			    	 filebuff.delete();   

		       }
         	}
		}
		} 	
		
		
		
		
		if(isCheck()==true) {
			CreateXML xmlfile = new CreateXML();
//			xmlfile.createCheck(fileName,msgId);
//			xmlfile.createPull(fileName, msgId);
		}
		
		
		//if(conn!=null)
		//	conn.close();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	if(conn!=null)
					try {
					
						conn.close();
						connection.close();
						System.out.print("Connections close");
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		}));
	
		}
	
	
	
	public static void replaceFile(boolean repl , String path)
	{
		if(repl==true)
		{
			File source = new File(path);
			File dest = new File(path.replace("FromFTP", "OKEY"));
			try {
			    FileUtils.copyFile(source, dest);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			source = new File(path+".p7s");
			dest = new File(source.getAbsolutePath().replace("FromFTP", "OKEY"));
			try {
			    FileUtils.copyFile(source, dest);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			source = new File(path+".p7s.p7s");
			dest = new File(source.getAbsolutePath().replace("FromFTP", "OKEY"));
			try {
			    FileUtils.copyFile(source, dest);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		else {
		  date =simpleDateFormat.format(new Date());					
			File source = new File(path);
			File dest = new File(path.replace("FromFTP", "ERROR"));
	
			try {
			    FileUtils.copyFile(source, dest);
			    
			} catch (IOException e) {
			    e.printStackTrace();
			}
			source = new File(path+".p7s");
			dest = new File(source.getAbsolutePath().replace("FromFTP", "ERROR"));
			try {
			    FileUtils.copyFile(source, dest);
			  
			} catch (IOException e) {
			    e.printStackTrace();
			}
			source = new File(path+".p7s.p7s");
			dest = new File(source.getAbsolutePath().replace("FromFTP", "ERROR"));
			try {
			    FileUtils.copyFile(source, dest);
			  
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
		}
		
	}
	
	public static void DeleteFile( String path) 	{
	
		
		File source = new File(path);
		File dest = new File(path.replace("FromFTP", "DEPOT"));
		try {
		    FileUtils.copyFile(source, dest);	
		    	
		    source.delete();
		
		} catch (IOException e) {
		    e.printStackTrace();
		}
		source = new File(path+".p7s");
		dest = new File(source.getAbsolutePath().replace("FromFTP", "DEPOT"));
		try {
		    FileUtils.copyFile(source, dest);
		    source.delete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		source = new File(path+".p7s.p7s");
		dest = new File(source.getAbsolutePath().replace("FromFTP", "DEPOT"));
		try {
		    FileUtils.copyFile(source, dest);
		    source.delete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void FileToError( String path)
	{
		File source = new File(path);
		File dest = new File(path.replace("PULL\\FromBank", "ERROR\\FromBank"));
		try {
		    FileUtils.copyFile(source, dest);
		    source.delete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		}
	
	
	
	public static   Connection CreateConnect() throws SQLException, FileNotFoundException, IOException {
	    OracleDataSource ods = new OracleDataSource();
//	 ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.2.0.18)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=oshad)))");    
//     ods.setUser("OBU_MONEY");
// 	 ods.setPassword("OBU_MONEY");
    ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.0.0.188)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=center)))");    
    ods.setUser("HSPWUGW");
   ods.setPassword("HSPWUGW");
	//  ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.0.0.181)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=center)))");    
	//  ods.setUser("HSPWUGW");
    //  ods.setPassword("HSPWUGW");
	  
	    //Properties properties = new Properties();
	   // properties.put("connectTimeout", "2000000");
	   // properties.put("user", "HSPWUGW");
	   // properties.put("password", "HSPWUGW");
		 //DriverManager.setLoginTimeout(5600);
	    //OracleConnection connection = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@10.0.0.188:1521:center",properties);
	   // return connection;
	    Locale.setDefault(Locale.ENGLISH);
	   // OracleConnection connection = (OracleConnection) ods.getConnection();
	    return  ods.getConnection();
	  }
		
	
	public static   Connection CreateConnect2() throws SQLException, FileNotFoundException, IOException {
	    OracleDataSource ods = new OracleDataSource();
	    ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.0.0.195)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=center)))");    
	    ods.setUser("root");
	    ods.setPassword("root");
	    ods.setLoginTimeout(30);
	    
	    OracleConnection connection = (OracleConnection) ods.getConnection();
	    
	    return  ods.getConnection();
	  }
	
	
	public static boolean isCheck() throws SQLException {
	   
		
		ResultSet rs=null;
		Statement stmt = null;
		//OracleConnection connection = null;
			/*try {
				connection =(OracleConnection) CreateConnect();
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
	      try {
				String select="select * from O_SESSION  where I_STATE=50 AND I_MODE='check'";
	    	  stmt = connection.createStatement();
		      rs=stmt.executeQuery(select);
		        if(rs.next()) {   
		        i_mode = rs.getString("I_MODE");
		        msgId=rs.getString("MSGID");
		        fileName=rs.getString("C_REQUESTFILE").substring(0, 19);
		        
		        System.out.println(i_mode);
		        
            return true;
		
		        
		        }
		              
		        
		        
	      } catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

		
			}
	
		return false;
		
	}
	
	
	public static boolean isSend(String file) throws SQLException {
	  	ResultSet rs=null;
		Statement stmt = null;
		
			
	      try {
				String select="select * from O_SESSION  where C_REQUESTFILE=\'"+file+"' AND I_STATE=41 AND I_MODE='push' AND STATE=1  ";
	    	  stmt = connection.createStatement();
		      rs=stmt.executeQuery(select);
		        if(rs.next()) {   
		        i_mode = rs.getString("I_MODE");
		        fileName=rs.getString("C_REQUESTFILE");
		        
		        System.out.println("C_REQUESTFILE  is valid");
		        
            return true;
		        }
		   
	      } catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			
			}
	
		return false;
		}
	
	
	
	public static boolean isPush(String file) throws SQLException {
	  	ResultSet rs=null;
		Statement stmt = null;
	
	      try {
				String select="select * from O_SESSION  where C_REQUESTFILE=\'"+file+"' AND I_STATE=40 AND I_MODE='push' AND STATE=0  ";
	    	  stmt = connection.createStatement();
		      rs=stmt.executeQuery(select);
		        if(rs.next()) {   
		        i_mode = rs.getString("I_MODE");
		        fileName=rs.getString("C_REQUESTFILE");
		        
		        System.out.println("C_REQUESTFILE  is valid");
		        
            return true;
		        }
		   
	      } catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
		
			}
	
		return false;
		}
	
	public static boolean isCheck(String file) throws SQLException {
	  	ResultSet rs=null;
		Statement stmt = null;
	
	      try {
				String select="select * from O_SESSION  where C_REQUESTFILE=\'"+file+"' AND I_STATE=40 AND I_MODE='check' AND STATE=0  ";
	    	  stmt = connection.createStatement();
		      rs=stmt.executeQuery(select);
		        if(rs.next()) {   
		        i_mode = rs.getString("I_MODE");
		        fileName=rs.getString("C_REQUESTFILE");
		        
		        System.out.println("C_REQUESTFILE  is valid");
		        
            return true;
		        }
		   
	      } catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
		
			}
	
		return false;
		}
	
	
	public static boolean isPull(String file) throws SQLException {
	  	ResultSet rs=null;
		Statement stmt = null;
	
	      try {
				String select="select * from O_SESSION  where C_REQUESTFILE=\'"+file+"' AND I_STATE=40 AND I_MODE='pull' AND STATE=0  ";
	    	  stmt = connection.createStatement();
		      rs=stmt.executeQuery(select);
		        if(rs.next()) {   
		        i_mode = rs.getString("I_MODE");
		        fileName=rs.getString("C_REQUESTFILE");
		        
		        System.out.println("C_REQUESTFILE  is valid");
		        
            return true;
		        }
		   
	      } catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
	
			}
	
		return false;
		}
	

	public static void InsertSql(String fileName,String istate, String rr, String aa, String fff, String nnn,String dd, String mm, String yyyy,String imode, String state, String status, String stdescript,BigDecimal total2, int granted, int denied, String repDate) throws SQLException{
		
		
		Statement stmt = null;
		month=Integer.valueOf(fileName.substring(13, 15));
	    year=Integer.valueOf(fileName.substring(15, 19));	
			
			
			String insertSQL ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE,DATE_UPSZN,RECEIPT_CREATE_FLAG,"
					+ "C_REQUESTFILE,C_RESPONSEFILE,SERVICE,I_MODE,STATE,STATUS, STATUSDESCRIPTION,SUM_TOTAL,SUM_GRANTED,SUM_DENIED,REPORT_DATE)"
				+ " values(\'"+istate+"',\'"+rr+"',\'"+aa+"',\'"+fff+"',\'"+nnn+"',\'"+dd+"',\'"+mm+"',\'"+yyyy+"',sysdate,sysdate,'0',\'"+fileName+"', \'"+fileName+"','"+service+"',\'"+imode+"',\'"+state+"',\'"+status+"','"+stdescript+"','"+total2+"','"+granted+"','"+denied+"',	to_date('"+repDate+"', 'dd.mm.yyyy'))";
			
			
			
			
			try {
				 stmt = connection.createStatement();
			
//				 error=checkDouble(fileName);
//				   if(checkDouble(fileName)==0)
//								   
		   error=checkDoubleMonth(fileName, month, year);
		  if(checkDoubleMonth(fileName, month, year)==0){
				 				   
				   stmt.executeQuery(insertSQL);
						System.out.println("Record is inserted into O_SESSION table!");
					}
					else {
						throw new SQLException();
					}

			} catch (SQLException e) {

				System.out.println(e.getErrorCode());
	             errorCode=e.getErrorCode(); 
	             String insertSQLErr ="";
			
	            	  if(service.equals("transferenceData")||service.equals("transferencePens")||service.equals("transfer")) {	            	  
	            	  insertSQLErr ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE,DATE_UPSZN,RECEIPT_CREATE_FLAG,"
	      				+ "C_REQUESTFILE,C_RESPONSEFILE,SERVICE,I_MODE,STATE,STATUS, STATUSDESCRIPTION,SUM_TOTAL,SUM_GRANTED,SUM_DENIED,REPORT_DATE,STATEDESCRIPTION)"
	      				+ " values(\'"+41+"',\'"+rr+"',\'"+aa+"',\'"+fff+"',\'"+nnn+"',\'"+dd+"',\'"+mm+"',\'"+yyyy+"',sysdate,sysdate,'0',\'"+fileName+"', \'"+fileName+"','"+service+"',\'"+imode+"',\'"+-1+"',\'"+status+"','"+stdescript+"','"+total2+"','"+granted+"','"+denied+"',	to_date('"+repDate+"', 'dd.mm.yyyy'),'IOC-013:Помилка -  реєстр за звітний період вже завантажено')";
	      			
	            	  
	            	  stmt.executeQuery(insertSQLErr);
	            	  System.out.println("Record is doubledError into O_SESSION table!");
	    	           
	            	  
	            	  
	              }	else {			
	      
	            	    stmt.executeQuery(insertSQL);
	  	        	  System.out.println("Record is doubled into O_SESSION table!");
	              
	              }

				

			} finally {

				if (stmt != null) {
					stmt.close();
				}


			}

		}

	public static void InsertSql(String fileName,String istate, String rr, String aa, String fff, String nnn,String dd, String mm, String yyyy,String imode, String state, String status, String stdescript,BigDecimal total2, int granted, int denied, String repDate,Integer count) throws SQLException{
		Statement stmt = null;
	   
		month=Integer.valueOf(fileName.substring(13, 15));
	    year=Integer.valueOf(fileName.substring(15, 19));		
		 
    	String insertSQL ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE,DATE_UPSZN,RECEIPT_CREATE_FLAG,"
					+ "C_REQUESTFILE,C_RESPONSEFILE,SERVICE,I_MODE,STATE,STATUS, STATUSDESCRIPTION,SUM_TOTAL,SUM_GRANTED,SUM_DENIED,REPORT_DATE,COUNT_TOTAL)"
				+ " values(\'"+istate+"',\'"+rr+"',\'"+aa+"',\'"+fff+"',\'"+nnn+"',\'"+dd+"',\'"+mm+"',\'"+yyyy+"',sysdate,sysdate,'0',\'"+fileName+"', \'"+fileName+"','"+service+"',\'"+imode+"',\'"+state+"',\'"+status+"','"+stdescript+"',"+total2+",'"+granted+"','"+denied+"',	to_date('"+repDate+"', 'dd.mm.yyyy'),"+count+")";
						
			try {
				
			    stmt = connection.createStatement();
			    
//			    error=checkDouble(fileName);
//				if(checkDouble(fileName)==0)
			    
	
			    error=checkDoubleMonth(fileName, month, year);
				if(checkDoubleMonth(fileName, month, year)==0){
					
					stmt.executeQuery(insertSQL);
					System.out.println("Record is inserted into O_SESSION table!");
				}
				else {
					throw new SQLException();
				}
				
			} catch (SQLException e) {
				
			  System.out.println(e.getErrorCode());
              errorCode=e.getErrorCode();
              String insertSQLErr ="";
			
            	  if(service.equals("transferenceData")||service.equals("transferencePens")||service.equals("transfer")) {
            	  
            	  insertSQLErr ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE,DATE_UPSZN,RECEIPT_CREATE_FLAG,"
      					+ "C_REQUESTFILE,C_RESPONSEFILE,SERVICE,I_MODE,STATE,STATUS, STATUSDESCRIPTION,SUM_TOTAL,SUM_GRANTED,SUM_DENIED,REPORT_DATE,COUNT_TOTAL,STATEDESCRIPTION)"
      					+ " values(\'"+41+"',\'"+rr+"',\'"+aa+"',\'"+fff+"',\'"+nnn+"',\'"+dd+"',\'"+mm+"',\'"+yyyy+"',sysdate,sysdate,'0',\'"+fileName+"', \'"+fileName+"','"+service+"',\'"+imode+"',\'"+-1+"',\'"+status+"','"+stdescript+"',"+total2+",'"+granted+"','"+denied+"',	to_date('"+repDate+"', 'dd.mm.yyyy'),"+count+",'IOC-013:Помилка -  реєстр за звітний період вже завантажено')";
             
            	  stmt.executeQuery(insertSQLErr);
            	  System.out.println("Record is doubledError into O_SESSION table!");
    	           
            	  
            	  }				
      		  else
      		  {
        	 	  
      			 stmt.executeQuery(insertSQL);
           	  System.out.println("Record is doubled into O_SESSION table!");
   	           
      		  }
        
            
							
			} finally {

				if (stmt != null) {
					stmt.close();
				}

			}

		}

	
	
	public static void InsertSqlCheck(String fileName, String msgId, String status, int state, String imode) throws SQLException{
		Statement stmt = null;
		
			
			String insertSQL= "update  O_SESSION set I_MODE=\'"+imode+"',STATE=\'"+state+"',STATUS=\'"+status+"',MSGID=\'"+msgId+"' where  C_REQUESTFILE = \'"+fileName+"'";
					
					
			try {
				
			    stmt = connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeUpdate(insertSQL);

				System.out.println("Record is inserted into O_SESSION table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

			}

		}
	

	
	
	
	public static void InsertSqlCheck(String fileName,int istate, String msgId, String status, int state, String imode) throws SQLException{
		Statement stmt = null;
	
			
			String insertSQL= "update  O_SESSION set I_MODE=\'"+imode+"',I_STATE=\'"+istate+"',STATE=\'"+state+"',STATUS=\'"+status+"',MSGID=\'"+msgId+"' where  C_REQUESTFILE = \'"+fileName+"'";
					
					
			try {
				
			    stmt = connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeUpdate(insertSQL);

				System.out.println("Record is inserted into O_SESSION table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

			}

		}
	
	public static void InsertSqlCheck(String fileName, int istate, String msgId, String status, int state, String imode,String stDesc) throws SQLException{
		Statement stmt = null;
	
			String insertSQL= "update  O_SESSION set I_MODE=\'"+imode+"',I_STATE=\'"+istate+"',STATE=\'"+state+"',STATUS=\'"+status+"',STATUSDESCRIPTION=\'"+stDesc+"',MSGID=\'"+msgId+"' where  C_REQUESTFILE = \'"+fileName+"'";
			
	
					
			try {
				
			    stmt = connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeUpdate(insertSQL);

				System.out.println("Record is inserted into O_SESSION table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

				
			}

		}
		
	
	
	
	
	
	public static void InsertSqlPull(String fileName, int istate, String imode, int state, String status, String stdescr,String responsefile,
			BigDecimal sum ,BigDecimal sumrej,Integer quant , Integer quantrej) throws SQLException{
		Statement stmt = null;
		
			String insertSQL= "update  O_SESSION set I_STATE='"+istate+"',I_MODE='"+imode+"',STATE='"+state+"',STATUS='"+status+"',C_RESPONSEFILE='"+responsefile+"',STATUSDESCRIPTION='"+stdescr+"',SUM_GRANTED="+sum+",SUM_DENIED="+sumrej+",COUNT_GRANTED="+quant+",COUNT_DENIED="+quantrej+", DATE_UPSZN=sysdate   where  C_REQUESTFILE = \'"+fileName+"'";
			
	
					
			try {
				
			    stmt = connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeUpdate(insertSQL);

				System.out.println("Record is inserted into O_SESSION table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				
				      
				
				
				if (stmt != null) {
					stmt.close();
				}

				

			}

		}
	
	
	
	public static void InsertSqlError(String fileName) throws SQLException{
		Statement stmt = null;
		
			
			
			
			String insertSQLerror ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE_UPSZN,"
					+ "C_REQUESTFILE,SERVICE,I_MODE,STATE,STATEDESCRIPTION,NSGID,STATUS,STATUSDESCRIPTION)"
				+ " values(41,'hhhh','rr','aa','fff','NNNNNNNNNN','dd','mm','yyyy','sysd',sysdate,sysdate,sysdate,\'"+fileName+"','"+fileName+"_"+date+"','sysdate','sysdate','sysdate','push','-1','ECCFailed')";
			
		
			
			
			
			try {
				
			    stmt = connection.createStatement();
				System.out.println(insertSQLerror);
				stmt.executeQuery(insertSQLerror);

				System.out.println("Record is inserted into O_SESSION table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

			
			}

		}
	
	
	
	public static int checkDouble(String fileName) throws SQLException{
		CallableStatement callableStatement = connection.prepareCall("{call SUBS_RST_2.File_Already_Exists(?, ?)}");
  	 
  	  callableStatement.setString(1, fileName);
  	  callableStatement.registerOutParameter(2, OracleTypes.INTEGER);
  	  
  	  callableStatement.executeUpdate();
  	  Integer res =  (Integer) callableStatement.getObject(2);
    	  
  	
  
	 return res;
	}
	
	public static int checkDoubleReject(String fileName) throws SQLException{
		CallableStatement callableStatement = connection.prepareCall("{call SUBS_RST_2.File_Already_Exists_Full(?, ?)}");
  	 
  	  callableStatement.setString(1, fileName);
  	  callableStatement.registerOutParameter(2, OracleTypes.INTEGER);
  	  
  	  callableStatement.executeUpdate();
  	  Integer res =  (Integer) callableStatement.getObject(2);
    	  
  	
  
	 return res;
	 
	 
	}
	
	public static int checkDoubleMonth(String fileName, int month, int year) throws SQLException{
		CallableStatement callableStatement = connection.prepareCall("{call SUBS_RST_2.File_Already_Exists_Month(?, ?,?,?)}");
  	 
  	  callableStatement.setString(1, fileName);
  	callableStatement.setInt(2, month);
  	callableStatement.setInt(3, year);
  	  callableStatement.registerOutParameter(4, OracleTypes.INTEGER);
  	  
  	  callableStatement.executeUpdate();
  	  Integer res =  (Integer) callableStatement.getObject(4);
    	  
  	
  
	 return res;
	 
	 
	}
	
	
	
	
	public static String getTagValue(String xml, String tagName) throws ParserConfigurationException, SAXException, IOException {
		
		
		File xmlFile = new File(xml);
		DocumentBuilderFactory docbuildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docbuildFactory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlFile);
		String	result = null;
		document.getDocumentElement().normalize();
 
				    
		NodeList nodeList1 = document.getElementsByTagName("info");
		for (int i = 0; i < nodeList1.getLength(); i++) {
			 
			Node node = nodeList1.item(i);
 
			System.out.println("\nCurrent element name :- " + node.getNodeName());
			System.out.println("-------------------------------------");
 
			if (node.getNodeType() == Node.ELEMENT_NODE) {
 
				Element element = (Element) node;
		result=element.getElementsByTagName(tagName).item(0).getTextContent().substring(0,19);
						
			}
		
		}		
		return result;
	}
	
	
	}
	

	
	

