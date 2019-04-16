package cert.cert;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CertChk {
		public String certChk(byte[] file,OracleConnection connection,int type,String filename) throws IOException, CMSException, SQLException{
			//byte[] buffer = new byte[(int) f.length()];
			  // DataInputStream in = new DataInputStream(new FileInputStream(f));
			  // in.readFully(buffer);
			  // in.close();
			   CMSSignedData signature;
			   try {
			   signature = new CMSSignedData(file);
			   }
			   catch	(Exception e) {
		        	 if(type ==1)
			            	return "IOC-002:Помилка 1 підпису(валідність)";
			            else
			            	return "IOC-003:Помилка 2 підпису(валідність)";
		            
		           
		        }
			   try {
			   Object data = signature.getSignedContent().getContent();
			   if(data!=null) return "IOC-010	:Помилка - наявність даних у підпису";
			   }
			   catch	(Exception e) {
		        	 
			         System.out.println("Vse OK");   	
  
		        }
			   
			   Store cs = signature.getCertificates();
			   SignerInformationStore signers = signature.getSignerInfos();
			   Collection c = signers.getSigners();
			   Iterator it = c.iterator();
			

			   //the following array will contain the content of xml document
			   

			   while (it.hasNext()) {
				   
			        SignerInformation signer = (SignerInformation) it.next();
			        Collection certCollection = cs.getMatches(signer.getSID());
			        Iterator certIt = certCollection.iterator();
			        X509CertificateHolder cert = null;
			        try {
			        cert = (X509CertificateHolder) certIt.next();
			        //to xml text
			        }
			        catch (NoSuchElementException e) {
			        	 if(type ==1)
				            	return "IOC-002:Помилка 1 підпису(валідність)";
				            else
				            	return "IOC-003:Помилка 2 підпису(валідність)";
			            
			           
			        }
			        System.out.println(DatatypeConverter.printBase64Binary(cert.getEncoded()));
			        StringWriter sw = new StringWriter();
				   
			        try (PemWriter pw = new PemWriter(sw)) {
			        	  PemObjectGenerator gen = new JcaMiscPEMGenerator(cert);
			        	  pw.writeObject(gen);
			        	}
			        
			        List<X509Certificate> chain = new ArrayList<>();
			        try (PEMParser parser = new PEMParser(new StringReader(sw.toString()))) {

			            JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			            X509CertificateHolder certificateHolder;
			            while ((certificateHolder = (X509CertificateHolder) parser.readObject()) != null) {
			                chain.add(converter.getCertificate(certificateHolder));
			            }
			        } catch (IOException | CertificateException e) {
			        	 if(type ==1)
				            	return "IOC-002:Помилка 1 підпису(валідність)";
				            else
				            	return "IOC-003:Помилка 2 підпису(валідність)";
			            
			           
			        }
			        /*X509Certificate certificate;
			        byte[] encodedExtensionValue = chain.get(0).getExtensionValue("2.5.29.9");
			        if (encodedExtensionValue != null) {
			            ASN1Primitive extensionValue = JcaX509ExtensionUtils
			                    .parseExtensionValue(encodedExtensionValue);
			            String values = extensionValue.toString();          
			        }*/
			        
			        
			        
			        String decoded = null;
			        try {
			        byte[] extensionValue = chain.get(0).getExtensionValue("2.5.29.9");
			        
			        
			        //
			        
			       // ASN1OctetString akiOc = ASN1OctetString.getInstance(extensionValue);
			       // AuthorityKeyIdentifier aki = AuthorityKeyIdentifier.getInstance(akiOc.getOctets());
			        ///
			        Set<String> set = chain.get(0).getNonCriticalExtensionOIDs();
			        if (extensionValue != null)
			        {
			            ASN1Primitive derObject = toDERObject(extensionValue);
			            if (derObject instanceof DEROctetString)
			            {
			                DEROctetString derOctetString = (DEROctetString) derObject;

			                derObject = toDERObject(derOctetString.getOctets());
			              
			                
			                String oid = ((DLSequence) derObject).getObjectAt(1).toString();
			                oid= oid.split(",")[1];
			                decoded=oid.replace("[", "").replace("]", "").replace(" ", "");
			        			            	
			            	
			            }

			            }
			        }    catch(ArrayIndexOutOfBoundsException e) {
			        	
			        	return "IOC-009:Відсутність підпису фізичної особи";
			        }
			        //return decoded;
			        
			       // Set<String> set = chain.get(0).getNonCriticalExtensionOIDs();
			        
			        
		            BigInteger seria = chain.get(0).getSerialNumber();
			        String x = Hex.toHexString(seria.toByteArray()); 
			        
			        if(x.substring(0,1).equalsIgnoreCase("0")) {
			        
			        	x=x.substring(1);
			        	
			        }
			        
					Statement stmt = connection.createStatement();
					//ResultSet rs = stmt.executeQuery("select * from CLIENT c where c.C_CERTIFICATESERIALNUMBER = \'"+x+"\'");
					ResultSet rs = stmt.executeQuery("Select cr.i_role from CLIENT_ROLE cr where cr.I_CLIENT = (Select c.id from CLIENT c where c.C_DRFO = \'"+decoded+"\')" ); 
					while (rs.next()) {
						
						int role = rs.getInt(1);
						if(role == 9061 && type ==1  &&!filename.equals("RF"))
							return "";
						if(role == 9062 && type ==2 && !filename.equals("RF"))
							return "";
						if(role == 11303 && type ==1  && (filename.equals("RF")||filename.equals("RW")||filename.equals("WB")))
							return "";
						if(role == 11304 && type ==2 && (filename.equals("RF")||filename.equals("RW")||filename.equals("WB")))
							return "";
						
						
						}
					
					if(type==1)
						return "IOC-004:Помилка 1 підпису(невідповідність ролі)";
					else
						return "IOC-005:Помилка 2 підпису(невідповідність ролі)";
							
				//	connection.close();
						
					   
			    }
			   if(type==1)
					return "IOC-002:Помилка 1 підпису(валідність)";
				else
					return "IOC-003:Помилка 2 підпису(валідність)";
		}
			      
		
		public static   Connection CreateConnect() throws SQLException, FileNotFoundException, IOException {
		    OracleDataSource ods = new OracleDataSource();
		    ods.setURL("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=10.0.0.195)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=center)))");    
		    ods.setUser("root");
		    ods.setPassword("root");
		    OracleConnection connection = (OracleConnection) ods.getConnection();
		    
		    return  ods.getConnection();
		  }
		
		
		
		
		
		
		private ASN1Primitive toDERObject(byte[] data) throws IOException
		{
		    ByteArrayInputStream inStream = new ByteArrayInputStream(data);
		    ASN1InputStream asnInputStream = new ASN1InputStream(inStream);

		    return asnInputStream.readObject();
		}
}
