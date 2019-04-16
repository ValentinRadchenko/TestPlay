


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

public class Report {
	
	private static String upszn;
	private static String rowsCount;
	
	private static String regId;
	private static String timestamp;
	

	
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
					
					
				}
			}
					
			
			System.out.println("Root element name :- " + document.getDocumentElement().getNodeName());
	 
			NodeList nodeList = document.getElementsByTagName("olsp");
	 
			int a=nodeList.getLength();
			for (int i = 0; i < nodeList.getLength(); i++) {
	 
				Node node = nodeList.item(i);
	 
				System.out.println("\nCurrent element name :- " + node.getNodeName());
				System.out.println("-------------------------------------");
	 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
	 
					Element element = (Element) node;
					
									
	            try {
	            	upszn=element.getElementsByTagName("olspId").item(0).getTextContent();
	            }catch (NullPointerException e) {
	            	upszn="";
				}	 
	            
	            
	            try {
	            	rowsCount=element.getElementsByTagName("quantityRecAcc").item(0).getTextContent();
	            }catch (NullPointerException  e) {
	            	rowsCount="";
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
		
		
			String insertSQL ="Insert into SM_1_REPORT1 (REG_ID,REG_DATE,UPSZN,ROWS_COUNT)"
				+ " values('"+regId+"',to_date('"+timestamp+"','YYYY-MM-DD\"T\"HH24:MI:SS'),'"+upszn+"','"+rowsCount+"')";
		try {
			    stmt = Main.connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeQuery(insertSQL);

				System.out.println("Record is inserted into SM_1_REPORT1 table!");

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


