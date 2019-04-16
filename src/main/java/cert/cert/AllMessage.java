package cert.cert;





import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.Message;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;


import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;




public class AllMessage {
	
	public  static  String msgID;
	public  static  String status;
	public static  String statusDescription;
	


	
	
	public void setMessage(String path,String service) throws IOException, JAXBException,NullPointerException {
	
		
	
		CreateXML  xmlfile =new CreateXML();
	    File file= new File(path);
	
        ArrayList<String> messsage=getData(file);
            
     
     for (int i=0;i<messsage.size();i++) {        
	      
	    	   
	   
          msgID=getMsgId(messsage.get(i));
          status=getStatus(messsage.get(i));
          statusDescription=getStDescription(messsage.get(i));
		   String name=statusDescription;    
				
			String	repDate="01."+statusDescription.subSequence(13, 15)+"."+statusDescription.substring(15, 19);
              
                 
          try {
    	
        	  if(status.equals("ESBSuccess")) {

  InsertSql(name,"40",name.substring(2,4),name.substring(4,6), name.substring(6,9),name.substring(9,13),"01",name.substring(13,15), name.substring(15,19),"pull","1","ECCSuccess","IOC-000:Помилки відсутні",msgID,service,new BigDecimal(0),0,0,repDate,0);	
  			
  			xmlfile.createPull(name,msgID);
        	  }else {
        		  continue;
        	  }
  			
          }catch (SQLException e) {
			e.printStackTrace();
		}   
		
          
     }  
          
				
	}
				
				
		
	
	 public ArrayList<String> getData(File file) {
	
		  final ArrayList<String> tagValues = new ArrayList<String>();
		 
		 byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
			String data2 = new String(encoded);
			final Pattern pattern = Pattern.compile("<Registry>(.+?)</Registry>", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(data2);
		
			while(matcher.find()) {
			tagValues.add(matcher.group(1)); // Prints String I want to extract
		 
	   }
			return tagValues;
		
	
	
	 }
	 
	 
	 
	 

	 private static String getMsgId( String str) {
	 
		 final Pattern TAG_REGEX = Pattern.compile("<MsgID>(.+?)</MsgID>", Pattern.DOTALL);
		 
		 String tagValues = "";
	     final Matcher matcher = TAG_REGEX.matcher(str);
	     while (matcher.find()) {
	         tagValues+=(matcher.group(1));
	     }
	     return tagValues;
	 }
	 
	 private static String getStatus( String str) {
		 
		 final Pattern TAG_REGEX = Pattern.compile("<Status>(.+?)</Status>", Pattern.DOTALL);
		 
		 String tagValues ="";
	     final Matcher matcher = TAG_REGEX.matcher(str);
	     while (matcher.find()) {
	         tagValues+=(matcher.group(1));
	     }
	     return tagValues;
	 }
	 private static String getStDescription( String str) {
		 
		 final Pattern TAG_REGEX = Pattern.compile("<StatusDescription>(.+?)</StatusDescription>", Pattern.DOTALL);
		 
		 String tagValues = "";
	     final Matcher matcher = TAG_REGEX.matcher(str);
	     while (matcher.find()) {
	         tagValues+=(matcher.group(1));
	     }
	     return tagValues;
	 }
	 

		
	 
	 public static void InsertSql(String fileName,String istate, String rr, String aa, String fff, String nnn,String dd, String mm, String yyyy,String imode, String state, String status, String stdescript,String msgId,String service, BigDecimal total2, int granted, int denied, String repDate,Integer count) throws SQLException{
			Statement stmt = null;
		
			 
	    	String insertSQL ="Insert into O_SESSION (I_STATE,RR,AA,FFF,NNNNNNNNNN,DD,MM,YYYY,UPSZN_DATE,DATE_UPSZN,RECEIPT_CREATE_FLAG,"
						+ "C_REQUESTFILE,C_RESPONSEFILE,SERVICE,I_MODE,STATE,STATUS, STATUSDESCRIPTION,MSGID,SUM_TOTAL,SUM_GRANTED,SUM_DENIED,REPORT_DATE,COUNT_TOTAL)"
					+ " values(\'"+istate+"',\'"+rr+"',\'"+aa+"',\'"+fff+"',\'"+nnn+"',\'"+dd+"',\'"+mm+"',\'"+yyyy+"',sysdate,sysdate,'0',\'"+fileName+"', \'"+fileName+"',\'"+service+"',\'"+imode+"',\'"+state+"',\'"+status+"','"+stdescript+"',\'"+msgId+"',"+total2+",'"+granted+"','"+denied+"',	to_date('"+repDate+"', 'dd.mm.yyyy'),"+count+")";
							
			
		
				try {
				    stmt = Main.connection.createStatement();
					System.out.println(insertSQL);
					stmt.executeQuery(insertSQL);

					System.out.println("Record is inserted into SUBS_MONEY_2 table!");

				} catch (SQLException e) {

					System.out.println(e.getMessage());

				} finally {

					if (stmt != null) {
						stmt.close();
					}

					//if (connection != null) {
						//connection.close();
					//}

				}

			}
	 
	 
	 
	 
	
	  }
	

	
	

	
		
	
		
	
	
	
	
	

	
	
	


