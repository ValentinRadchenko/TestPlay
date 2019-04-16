




package cert.cert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;


import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class ReportRG {
	
	
	
	


	
	private static  String regId;
	private static String timestamp;
	private static String olspId;
	private static String quantity;
	private static String olspRecId;
	private static String ebkRecId;
	private static String ipn;
	private static String accruedAmt;
	private static String fundedAmt;
	private static Boolean isFinanced;
	private static int isFin;
	private static String enrolledAmt;
	private static String returnedAmt;
	private static String balanceAmt;
	


	//static OracleConnection connection;
	
	
	public void setReports(String path) throws IOException, JAXBException,NullPointerException {
	

		
	  try {
	
		  if (Main.connection == null )
			  Main.connection = (OracleConnection) Main.CreateConnect();
			File xmlFile = new File(path);
			DocumentBuilderFactory docbuildFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docbuildFactory.newDocumentBuilder();
			Document document = docBuilder.parse(xmlFile);
	 
			document.getDocumentElement().normalize();
	 
					    
			NodeList nodeList1 = document.getElementsByTagName("info");
			for (int i = 0; i < nodeList1.getLength(); i++) {
				 
				Node node = nodeList1.item(i);
	 
				System.out.println("\nCurrent element name :- " + node.getNodeName());
				System.out.println("-------------------------------------");
	 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
	 
					Element element = (Element) node;
					regId=element.getElementsByTagName("regId").item(0).getTextContent();
					timestamp=element.getElementsByTagName("timestamp").item(0).getTextContent().substring(0,19);
					olspId=element.getElementsByTagName("olspId").item(0).getTextContent();
					quantity=element.getElementsByTagName("quantity").item(0).getTextContent();
				
				}
			}
					
			
			System.out.println("Root element name :- " + document.getDocumentElement().getNodeName());
	 
			NodeList nodeList = document.getElementsByTagName("transaction");
	 
			int a=nodeList.getLength();
			for (int i = 0; i < nodeList.getLength(); i++) {
	 
				Node node = nodeList.item(i);
	 
				System.out.println("\nCurrent element name :- " + node.getNodeName());
				System.out.println("-------------------------------------");
	 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
	 
					Element element = (Element) node;
					
									
	            try {
					olspRecId=element.getElementsByTagName("olspRecId").item(0).getTextContent();
	            }catch (NullPointerException e) {
				    olspRecId="0";
				}	 
	            
	            

				try {
	            	ebkRecId=element.getElementsByTagName("ebkRecId").item(0).getTextContent();
	            }catch (NullPointerException  e) {
	            	ebkRecId="";
				}
	            
                 try {	            
                	 ipn=element.getElementsByTagName("ipn").item(0).getTextContent();
                 }catch (NullPointerException e) {
                	 ipn="";
				 }	
                 
                 try {
                	 accruedAmt=element.getElementsByTagName("accruedAmt").item(0).getTextContent();
                 }catch (NullPointerException e) {
                	 accruedAmt="0";
				 }  
                 
                 try {
                	 fundedAmt = element.getElementsByTagName("fundedAmt").item(0).getTextContent();
                 }catch (NullPointerException e) {
                	 fundedAmt="0";
				}  
                 
                 
               
            
                 isFinanced=Boolean.valueOf(element.getElementsByTagName("isFinanced").item(0).getTextContent());
                 
                 isFin=0;
                 if(isFinanced)
                	 isFin=1;
                 
                 
                 
                 
                 try {
                	 enrolledAmt=element.getElementsByTagName("enrolledAmt").item(0).getTextContent();
                 }catch(NullPointerException e) {
                	 enrolledAmt="0";
                 }
                
                    
       
                
             try {
            	 returnedAmt=element.getElementsByTagName("returnedAmt").item(0).getTextContent();
             }catch (NullPointerException  e) {
            	 returnedAmt="0";
			}      
             
             try {
            	 balanceAmt=element.getElementsByTagName("balanceAmt").item(0).getTextContent();
             }catch (NullPointerException e) {
            	 balanceAmt="0";
			}      
             
             
                 
          try {
                 InsertSql();
          }catch (SQLException e) {
			e.printStackTrace();
		}   
				
				
				}
			}
		
		
	  }
		     catch (Exception e) {
			e.printStackTrace();
		    }
	 
	  }
	
	
	public static void InsertSql() throws SQLException {
		Statement stmt = null;
		
				String insertSQL ="Insert into SM_1_REPORT3 (REG_ID,REG_DATE,UPSZN,ROWS_COUNT,PERSON_ID,EBK_NUMBER,ITN,SUBS_SUM,SUBS_SUM_FIN,IS_FINANCE,SUM_FOR_ACCOUNT,"
					+ "SUM_RETURNED,SUM_REMAINDER)"
				+ " values('"+regId+"',to_date('"+timestamp+"','YYYY-MM-DD\"T\"HH24:MI:SS'),'"+olspId+"','"+quantity+"',"+olspRecId+",'"+ebkRecId+"','"+ipn+"','"+accruedAmt+"','"+fundedAmt+"','"+isFin+"',"+enrolledAmt+","+returnedAmt+",'"+balanceAmt+"')";
		try {
			    stmt = Main.connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeQuery(insertSQL);

				System.out.println("Record is inserted into SM_1_REPORT3 table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

				

			}

		}
	

	
	
	}


