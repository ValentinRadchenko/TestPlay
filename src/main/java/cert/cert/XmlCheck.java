package cert.cert;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlCheck {

	
	
	public boolean xmlCheckIn(String path,String service) throws IOException, CMSException, SQLException, SAXException{
		/*File schemaFile = new File("D:\\OSCHADBANK\\recipientCheckInput.xsd"); // etc.
		Source xmlFile = new StreamSource(new File(path));
		SchemaFactory schemaFactory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Validator validator = null;
		try {
		  Schema schema = schemaFactory.newSchema(schemaFile);
		  validator = schema.newValidator();
		  validator.validate(xmlFile);
		  System.out.println(xmlFile.getSystemId() + " is valid");
		  return true;
		} catch (SAXException e) {
		  System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
		  
		  return false;
		} catch (IOException e) {
			
			return false;
		}
		finally {
			
		}*/
		 
		    File tempFolder = null;
		    FileInputStream file = null;
		    Source xml = null;
		    try {
		    	
		    	//
		    	InputStream inputStream= new FileInputStream(path);
		    	Reader reader = new InputStreamReader(inputStream,"UTF-8");
		    	 
		    	
		    	
		        file = new FileInputStream(path );
		        xml = new StreamSource(file);
		        boolean checkxml =validatePkgXml(xml, path,service);
		        file.close();
		         
		        inputStream.close();
		        reader.close();
		        
		        
		        xml = null;
		        return checkxml;
		    } catch (IOException e) {
		    	 
		    	 file.close();
		    	 
		    	 
		    
		    	 
		        return false;
		    }
		    
		    
	}
	
	
	
	public boolean xmlCheckOut(String path,String service) throws IOException, CMSException, SQLException{
		FileInputStream file = null;
		 Source xml = null;
		    try {
		        file = new FileInputStream(path );
		        xml = new StreamSource(file);
		        //boolean checkxml =validatePkgXml(xml, path,service);
		        //file.close();
		        
		       
		        //return checkxml;
		    } catch (IOException e) {
		    	 System.out.println(e);
		    	 file.close();
		        return false;
		    }
		 SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    try {
		    	Schema schema = null;
		    if(service=="transfer")
		     schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferOutput.xsd"));
		    
		    else if(service=="finance")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\financeOutput.xsd"));
		    
		    else if(service=="transferencePens")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceDataPens.xsd"));		
		    
		    else if(service=="transferenceData")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceOutputBank.xsd"));		
		    
		    else if(service=="transferenceConsolidate")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceConsolidateOutput.xsd"));	
		    
		    else if(service=="recipientsinfo")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\mspuRecipientsInfo.xsd"));		
		    
		    else if(service=="transactionsinfo")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\mspuTransactionsInfo.xsd"));		
		   
		    else if(service=="amountsinfo")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\mspuAmountsInfo.xsd"));	
		   
		    else if(service=="transferenceCorrection")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceCorrectionOutput.xsd"));	
	
		    else if(service=="reportTransference")
		    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\mspuTransferenceInfo.xsd"));	

          	
		    
	
		    
		    
		    
		    
		    else
		    	schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\recipientOutput.xsd"));
		    Validator validator = schema.newValidator();
		    validator.validate(xml);
			  System.out.println(xml.getSystemId() + " is valid");
			  file.close();
			  return true;
			} catch (SAXException e) {
				file.close();
			  System.out.println(xml.getSystemId() + " is NOT valid reason:" + e);
			  return false;
			} catch (IOException e) {
				 System.out.println(e);
				file.close();
				return false;
			}
		
	}
	private boolean validatePkgXml(Source xmlStream, String xmlPath,String service) throws IOException, SAXException{
	    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    try {
	    	Schema schema = null;
	    if(service=="transfer")
	     schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferInput.xsd"));
	    
	    else if(service=="finance")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\financeInput.xsd"));
	    
	    
	    else if(service=="transferencePens")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceDataPens.xsd"));	
	    
	    
	    else if(service=="transferenceData")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceDataBank.xsd"));		
	    
	    else if(service=="transferenceConsolidate")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceConsolidateInput.xsd"));		
	    
	    else if(service=="transferenceDuplicate")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\duplicateInput.xsd"));		
	   

	    else if(service=="transferenceCorrection")
	    	 schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\transferenceCorrectionInput.xsd"));	

	    
	    
	    else
	    	schema = schemaFactory.newSchema(new File("D:\\OSCHADBANK\\recipientInput.xsd"));
	    Validator validator = schema.newValidator();
	    validator.validate(xmlStream);
		  System.out.println(xmlStream.getSystemId() + " is valid");
		  return true;
		} catch (SAXException e) {
		  System.out.println(xmlStream.getSystemId() + " is NOT valid reason:" + e);
		  return false;
		} catch (IOException e) {
			return false;
		}
	}
}
