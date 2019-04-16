package cert.cert;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import oracle.jdbc.OracleConnection;

public class ReportRX {
	

	

	


	private static String regId;
	private static String timestamp;
	private static String olspId;
	private static String quantity;
	private static String totalAmount;
	private static String paidQuantity;
	private static String paidTotalAmount;
	private static String rejectQuantity;
	private static String rejectTotalAmount;
	private static String olspRecId;
	private static String ebkTransactId;
	private static String paymentStatus;
	private static String amount;


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
					totalAmount=element.getElementsByTagName("totalAmount").item(0).getTextContent();
					paidQuantity=element.getElementsByTagName("paidQuantity").item(0).getTextContent();
					paidTotalAmount=element.getElementsByTagName("paidTotalAmount").item(0).getTextContent();
					rejectQuantity=element.getElementsByTagName("rejectQuantity").item(0).getTextContent();
					rejectTotalAmount=element.getElementsByTagName("rejectTotalAmount").item(0).getTextContent();
					
//			
//				
				}
			}
					
			
			System.out.println("Root element name :- " + document.getDocumentElement().getNodeName());
	 
			NodeList nodeList = document.getElementsByTagName("transference");
	 
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
					ebkTransactId=element.getElementsByTagName("ebkTransactId").item(0).getTextContent();
	            }catch (NullPointerException  e) {
	            	ebkTransactId="";
				}
	            
                 try {	            
                	 paymentStatus=element.getElementsByTagName("paymentStatus").item(0).getTextContent();
                 }catch (NullPointerException e) {
                	 paymentStatus="0";
				 }	
                 
                 try {
                	 amount=element.getElementsByTagName("amount").item(0).getTextContent();
                 }catch (NullPointerException e) {
                	 amount="0";
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
		
				String insertSQL ="Insert into SM_2_REPORT1 (REG_ID,REG_DATE,OLSP_ID,QUANTITY,TOTAL_AMOUNT,PAID_QUANTITY,PAID_TOTAL_AMOUNT,REJECT_QUANTITY,REJECT_TOTAL_AMOUNT,OLSP_REC_ID,EBK_TRANSACT_ID,"
					+ "PAYMENT_STATUS,AMOUNT)"
				+ " values('"+regId+"',to_date('"+timestamp+"','YYYY-MM-DD\"T\"HH24:MI:SS'),'"+olspId+"','"+quantity+"','"+totalAmount+"','"+paidQuantity+"','"+paidTotalAmount+"','"+rejectQuantity+"','"+rejectTotalAmount+"',"+olspRecId+",'"+ebkTransactId+"','"+paymentStatus+"','"+amount+"')";
	
				
		
				
				
				try {
			    stmt = Main.connection.createStatement();
				System.out.println(insertSQL);
				stmt.executeQuery(insertSQL);

				System.out.println("Record is inserted into SM_2_REPORT1 table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (stmt != null) {
					stmt.close();
				}

				

			}

		}
	

	
	
	

}
