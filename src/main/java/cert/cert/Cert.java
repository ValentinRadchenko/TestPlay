package cert.cert;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerId;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Base64;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.security.cert.X509Certificate;
import javax.xml.XMLConstants;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.net.URL;
import org.xml.sax.SAXException;

//pasichenko j
public class Cert {
	
	public static  String verifSignedData(String filename) throws IOException, CMSException, CertificateException, SQLException{
	//File f = new File("C:\\Users\\1\\Downloads\\cat-blue-eyes.jpg.p7s");
	   File f = new File(filename);
	   byte[] buffer = new byte[(int) f.length()];
	   DataInputStream in = new DataInputStream(new FileInputStream(f));
	   in.readFully(buffer);
	   in.close();
	   CMSSignedData signature = new CMSSignedData(buffer);
	   Store cs = signature.getCertificates();
	   SignerInformationStore signers = signature.getSignerInfos();
	   Collection c = signers.getSigners();
	   Iterator it = c.iterator();

	   //the following array will contain the content of xml document
	   

	   while (it.hasNext()) {
	        SignerInformation signer = (SignerInformation) it.next();
	        Collection certCollection = cs.getMatches(signer.getSID());
	        Iterator certIt = certCollection.iterator();
	        X509CertificateHolder cert = (X509CertificateHolder) certIt.next();
	        //to xml text
	        System.out.println(DatatypeConverter.printBase64Binary(cert.getEncoded()));
	        StringWriter sw = new StringWriter();
	        try (PemWriter pw = new PemWriter(sw)) {
	        	  PemObjectGenerator gen = new JcaMiscPEMGenerator(cert);
	        	  pw.writeObject(gen);
	        	}
	        /*
	        List<X509Certificate> chain = new ArrayList<>();
	        try (PEMParser parser = new PEMParser(new StringReader(sw.toString()))) {

	            JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
	            X509CertificateHolder certificateHolder;
	            while ((certificateHolder = (X509CertificateHolder) parser.readObject()) != null) {
	                chain.add(converter.getCertificate(certificateHolder));
	            }
	        } catch (IOException | CertificateException e) {
	            throw new RuntimeException("Failed to create certificate: " );
	        }
            BigInteger seria = chain.get(0).getSerialNumber();
	        String x = Hex.toHexString(seria.toByteArray());  */
	        return toHex(sw.toString());
	        //
	        /*
	        X500Name x = cert.getSubject();
	        String serial = x.getRDNs()[4].getFirst().getValue().toString();
	        OracleConnection connection = null;
			try {
				connection =(OracleConnection) CreateConnect();
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from CLIENT c where c.C_PERSONSERIALNUMBER = \'"+serial+"\'");
			
			while (rs.next()) {
				java.sql.Date date = rs.getDate("D_NOTAFTER");
				if(date.compareTo(new Date())>0)
					return true;
				else 
					return false;
				}
				*/
			   
	    }
	//return false;
	return "";
	  	   
}
	public static  void validateXml() throws IOException{
		
		File schemaFile = new File("C:\\xml\\recipientCheckInput_new.xsd"); // etc.
		Source xmlFile = new StreamSource(new File("C:\\xml\\RO21110000010012019.xml"));
		SchemaFactory schemaFactory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
		  Schema schema = schemaFactory.newSchema(schemaFile);
		  Validator validator = schema.newValidator();
		  validator.validate(xmlFile);
		  System.out.println(xmlFile.getSystemId() + " is valid");
		} catch (SAXException e) {
		  System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
		} catch (IOException e) {}
	}
	public static String toHex(String arg) throws UnsupportedEncodingException {
	    return String.format("%040x", new BigInteger(1, arg.getBytes("UTF-8")));
	}
	public String toHex2(String arg) throws UnsupportedEncodingException {
		  return String.format("%x", new BigInteger(1, arg.getBytes("UTF-8")));
		}
	
	public static void CreateXml(String data , String cert1 , String cert2){
		
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("envelope");
			doc.appendChild(rootElement);
			
			
			Element mode = doc.createElement("mode");
			rootElement.appendChild(mode);
			
			Element requestID = doc.createElement("requestID");
			rootElement.appendChild(requestID);
			
			Element Message = doc.createElement("Message");
			rootElement.appendChild(Message);
			/*
			// staff elements
			Element staff = doc.createElement("Staff");
			rootElement.appendChild(staff);

			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");
*/
			// firstname elements
			Element Data = doc.createElement("Data");
			Data.appendChild(doc.createTextNode(data));
			Message.appendChild(Data);
			
			Element Sign = doc.createElement("Sign");
			Sign.appendChild(doc.createTextNode(cert1));
			Message.appendChild(Sign);
			
			Element Stamp = doc.createElement("Stamp");
			Stamp.appendChild(doc.createTextNode(cert2));
			Message.appendChild(Stamp);
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\file.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	 public static   Connection CreateConnect() throws SQLException, FileNotFoundException, IOException {
		    OracleDataSource ods = new OracleDataSource();
		    ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.0.0.195)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=center)))");    
		    ods.setUser("root");
		    ods.setPassword("root");
		    OracleConnection connection = (OracleConnection) ods.getConnection();
		    
		    return  connection;
		  }
	
}
