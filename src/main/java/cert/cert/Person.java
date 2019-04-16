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

public class Person {
	
	private static String surname;
	private static String name;
	private static  String patronymic;
	private static String birthday;
	private static String ipn;
	private static boolean ipnRefuse;
	private static String phone;
	private static String type;
	private static String series;
	private static String number;
	private static String postCode;
	private static String koatyyCode;
	private static String streetType;
	private static String streetName;
	private static String houseNum;
	private static String buildingNum;
	private static String flatNum;
	private static String transactionDate;
	private static String amount;
	private static String bankCode;
	private static String settlementAccount;
	private static String iban;
	private static String ebktransact;
	private static String olspRecId;
	private static String regId;
	private static String timestamp;
	private static String quantity;
	private static String totalAmount;
	private static String olspId;
	private static String trustee;
	private static int ipnRef;
	

	
	public void setPersons(String path) throws IOException, JAXBException,NullPointerException {
	
	
		
		
		
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
					
				}
			}
					
			
			System.out.println("Root element name :- " + document.getDocumentElement().getNodeName());
	 
			NodeList nodeList = document.getElementsByTagName("transferenceData");
	 
			int a=nodeList.getLength();
			for (int i = 0; i < nodeList.getLength(); i++) {


                                                  Role node = nodeList.item(i);
	 
				Node node = nodeList.item(i);
	 
				System.out.println("\nCurrent element name :- " + node.getNodeName());
				System.out.println("-------------------------------------");
	 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
	 
					Element element = (Element) node;
					
									
	            try {
					olspRecId=element.getElementsByTagName("olspRecId").item(0).getTextContent();
	            }catch (NullPointerException e) {
				    olspRecId="";
				}	 
	            
	            
	            try {
			        ebktransact=element.getElementsByTagName("ebkTransactId").item(0).getTextContent();
	            }catch (NullPointerException  e) {
					ebktransact="";
				}
	            
                 try {	            
					surname=element.getElementsByTagName("surname").item(0).getTextContent().replace("'","’");
                 }catch (NullPointerException e) {
					surname="";
				 }	
                 
                 try {
					name=element.getElementsByTagName("name").item(0).getTextContent().replace("'","’");
                 }catch (NullPointerException e) {
					name="";
				 }  
                 
                 try {
					patronymic = element.getElementsByTagName("patronymic").item(0).getTextContent().replace("'","’");
                 }catch (NullPointerException e) {
					patronymic="";
				}  
                 
                 
                 try {
					birthday=element.getElementsByTagName("birthday").item(0).getTextContent();
                 }catch (NullPointerException e) {
					birthday="";
				} 
                 
                 try {
					ipn=element.getElementsByTagName("ipn").item(0).getTextContent();
                 }catch(NullPointerException e) {
                	 ipn="0";
                 }
                    ipnRefuse=Boolean.valueOf(element.getElementsByTagName("ipnRefuse").item(0).getTextContent());
                 
                    ipnRef=0;
                    if(ipnRefuse)
                	   ipnRef=1;
     
                    
             try {
                    phone=element.getElementsByTagName("phone").item(0).getTextContent()==null?"":phone;
             }catch (NullPointerException e) {
				phone="";
			}
                
             try {
                    type=element.getElementsByTagName("type").item(0).getTextContent();
             }catch (NullPointerException  e) {
				    type="";
			}      
             
             try {
                    series=element.getElementsByTagName("series").item(0).getTextContent();
             }catch (NullPointerException e) {
				   series="";
			}      
             
             try {
                    number=element.getElementsByTagName("number").item(0).getTextContent();
             }catch(NullPointerException e) {
            	 
            	 number="";
             }
   
             
             
               try {
                    postCode=element.getElementsByTagName("postCode").item(0).getTextContent();
                
               }catch (NullPointerException e) {
				 postCode="";
		        }
               
                try {
                    koatyyCode=element.getElementsByTagName("koatyyCode").item(0).getTextContent();
                }catch (NullPointerException e) {
					koatyyCode="";
				} 
				  
                
                
                
                  try { 
					streetType=element.getElementsByTagName("streetTypeName").item(0).getTextContent().replace("'","’");
                  } catch (NullPointerException e) {
					streetType="";
				}			    
				    	
                  try {
                    streetName=element.getElementsByTagName("streetName").item(0).getTextContent().replace("'","’");
                  }catch (NullPointerException e) {
					streetName="";
				}  
                  
                  try {
                    houseNum=element.getElementsByTagName("houseNum").item(0).getTextContent().replace("'","’");
                  }catch (NullPointerException e) {
					houseNum="";
				}  
                  
                  
                  try {
                    buildingNum=element.getElementsByTagName("buildingNum").item(0).getTextContent().replace("'","’");
                  }catch (NullPointerException e) {
					buildingNum="";
				} 
                  
                  try {
                    flatNum=element.getElementsByTagName("flatNum").item(0).getTextContent().replace("'","’");
                  }catch (NullPointerException e) {
					flatNum="";
				} 
                  
                  
             try {
                    transactionDate=element.getElementsByTagName("transactionDate").item(0).getTextContent();
             }catch (NullPointerException e) {
                        transactionDate="0";
			}
                  
                  try {
                    amount=element.getElementsByTagName("amount").item(0).getTextContent();
                  }catch (NullPointerException e) {
					amount="0";
				} 
                  
                  try {
                    bankCode=element.getElementsByTagName("bankCode").item(0).getTextContent();
                  }catch (NullPointerException e) {
					bankCode="";
					}
                  
                  try {
                    settlementAccount=element.getElementsByTagName("settlementAccount").item(0).getTextContent();
                  }catch (NullPointerException e) {
					settlementAccount="";
				}
                  
                  
                  try {
                    iban=element.getElementsByTagName("iban").item(0).getTextContent();
                  }catch (NullPointerException e) {
					iban="";
				}
	                 
                  try {
                      trustee=element.getElementsByTagName("trustee").item(0).getTextContent();
                    }catch (NullPointerException e) {
  					trustee="";
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
		

			String insertSQL ="Insert into SUBS_MONEY_2 (REG_ID,REG_DATE,OLSP_ID,QUANTITY,TOTAL_AMOUNT,OLSP_REC_ID,EBK_TRANSACT_ID,SURNAME,NAME,PATRONYMIC,BITHDAY,"
					+ "IPN,IPN_REFUSE,PHONE,DOC_TYPE,DOC_SERIES,DOC_NUMBER,POST_CODE,KOATUU,STREET_TYPE_NAME,STREET_NAME,HOUSE_NUM,BUILDING_NUM,FLAT_NUM,TRANSACTION_DATE,AMOUNT,BANK_CODE,SETTLEMENT_ACCOUNT,IBAN,TRUSTEE)"
				+ " values('"+regId+"',to_date('"+timestamp+"','YYYY-MM-DD\"T\"HH24:MI:SS'),'"+olspId+"','"+quantity+"',"+totalAmount+",'"+olspRecId+"','"+ebktransact+"','"+surname+"','"+name+"','"+patronymic+"',to_date('"+birthday+"','YYYY-MM-DD'),"+ipn+","+ipnRef+",'"+phone+"','"+type+"','"+series+"','"+number+"','"+postCode+"','"+koatyyCode+"','"+streetType+"','"+streetName+"','"+houseNum+"','"+buildingNum+"','"+flatNum+"',to_date('"+transactionDate+"','YYYY-MM-DD'),"+amount+",'"+bankCode+"','"+settlementAccount+"','"+iban+"','"+trustee+"')";
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
		
			}

		}
	

	
	
	}


