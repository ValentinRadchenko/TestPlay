package cert.cert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;
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

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.cms.CMSException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CreateXML {

	static String pattern = "ddMMyyyHHmmss";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    
	
	
	
	
	public static void createXml(String data , String cert1 , String cert2,String path) throws IOException{
		
	
		FileInputStream  fXmlFile = new FileInputStream(new File("D:\\OSCHADBANK\\FromFTP\\"+path+".xml"));
		
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document docp = docBuilder.parse(fXmlFile);
	
			String dateFromXml=docp.getElementsByTagName("timestamp").item(0).getTextContent();
 	 			
			Document docy = docBuilder.newDocument();
			
			Element doc = docy.createElement("Doc");
			
			docy.appendChild(doc);
			
			Element Command = docy.createElement("Command");
			Command.appendChild(docy.createTextNode("Add"));
			doc.appendChild(Command);
			
			Element Timestamp = docy.createElement("timestamp");
			Timestamp.appendChild(docy.createTextNode(dateFromXml));
			doc.appendChild(Timestamp);
			
			Element regId = docy.createElement("regId");
			regId.appendChild(docy.createTextNode(path));
			doc.appendChild(regId);
			
			
			Element Data = docy.createElement("Data");
			Data.appendChild(docy.createTextNode(data));
			doc.appendChild(Data);
			
			Element Sign = docy.createElement("Sign");
			Sign.appendChild(docy.createTextNode(cert1));
			doc.appendChild(Sign);
			
			Element Stamp = docy.createElement("Stamp");
			Stamp.appendChild(docy.createTextNode(cert2));
			doc.appendChild(Stamp);
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(docy);
			StreamResult result = new StreamResult(new File("D:\\OSCHADBANK\\BINARY.xml"));
			

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\OSCHADBANK\\BINARY.xml")));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			String filetxt =sb.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Doc>", "");
			filetxt = filetxt.replace("</Doc>", "");
			
			String date =simpleDateFormat.format(new Date());
			try (PrintWriter out = new PrintWriter("D:\\OSCHADBANK\\BUFFER\\"+path);				
					PrintWriter out2 = new PrintWriter("D:\\OSCHADBANK\\PUSH\\ToBank\\"+path+"_"+date)) {
			    out.println(filetxt);
			    out2.println(filetxt);
			    
			}
			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fXmlFile.close();
		}
	}
	
	
	public static void createCheck(String file,String msgId) throws IOException{
		
		try {

			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			
			

			// root elements
			

			
			Element root = doc.createElement("Doc");
			doc.appendChild(root);
			
			Element Command = doc.createElement("Command");
			Command.appendChild(doc.createTextNode("GetStatus"));
			root.appendChild(Command);
			
					
			Element MsgIds = doc.createElement("MsgIDs");
			root.appendChild(MsgIds);	
			
			
			Element MsgId = doc.createElement("MsgID");
			MsgId.appendChild(doc.createTextNode(msgId));
			MsgIds.appendChild(MsgId);	
			
			
			
			
						
			
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:\\OSCHADBANK\\BINARY.xml"));
			

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\OSCHADBANK\\BINARY.xml")));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			String filetxt =sb.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Doc>", "");
			filetxt = filetxt.replace("</Doc>", "");
			
			String date =simpleDateFormat.format(new Date());
			try (PrintWriter out = new PrintWriter("D:\\OSCHADBANK\\BUFFER\\"+file);
					PrintWriter out2 = new PrintWriter("D:\\OSCHADBANK\\CHECK\\ToBank\\"+file+"_"+date)		) {
			    out.println(filetxt);
			    out2.println(filetxt);
			}
			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	
	
	
	public static void createPull(String file,String msgId) throws IOException{
		
		try {

			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			// root elements
					
			Element root = doc.createElement("Doc");
			doc.appendChild(root);
			
			Element Command = doc.createElement("Command");
			Command.appendChild(doc.createTextNode("GetResult"));
			root.appendChild(Command);
			
					
//			Element MsgIds = doc.createElement("MsgIDs");
//			root.appendChild(MsgIds);	
			
			
			Element MsgId = doc.createElement("MsgID");
			MsgId.appendChild(doc.createTextNode(msgId));
			root.appendChild(MsgId);	
					
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:\\OSCHADBANK\\BINARY.xml"));
			

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\OSCHADBANK\\BINARY.xml")));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			String filetxt =sb.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Doc>", "");
			filetxt = filetxt.replace("</Doc>", "");
			
			String date =simpleDateFormat.format(new Date());
			try (PrintWriter out = new PrintWriter("D:\\OSCHADBANK\\BUFFER\\"+file);
					PrintWriter out2 = new PrintWriter("D:\\OSCHADBANK\\PULL\\ToBank\\"+file+"_"+date)		) {
			    out.println(filetxt);
			    out2.println(filetxt);
			}
			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	
	
public static boolean createXmlPull(File file,String service) throws IOException, DecoderException, CMSException, SQLException{
		
//	File fXmlFile = new File("D:\\OSCHADBANK\\PULL\\FromBank\\"+file+".xml");
	XmlCheck xml = new XmlCheck();
	CreateXML createXML= new CreateXML();
	String name=createXML.getRegId(file);
	String path= "D:\\OSCHADBANK\\PULL\\FromBank\\"+name+".xml";
	
	File source ;
	File dest;
	
	
	byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	String data2 = new String(encoded);
	final Pattern pattern = Pattern.compile("<Data>(.+?)</Data>", Pattern.DOTALL);
	final Matcher matcher = pattern.matcher(data2);
	matcher.find();
	System.out.println(matcher.group(1)); // Prints String I want to extract
	data2= matcher.group(1);
	data2 = new String(Hex.decodeHex(data2.toCharArray()),"UTF-8");	
	
	try {
		PrintWriter out = new PrintWriter(//new FileWriter(path));
	
			new OutputStreamWriter(new FileOutputStream(path),
	                StandardCharsets.UTF_8), true);
		out.write(data2);		
		System.out.println("File created!");
		out.close();
	
	}catch (FileNotFoundException e) {
		e.printStackTrace();
  } 
	
	
	
	if(xml.xmlCheckOut(path,service)) {
		
	     source = new File(path);
	     String repName=source.getName();
	     
	     if(repName.substring(0,2).equalsIgnoreCase("RA")) {
	    	 Report rep =new Report();
	    	 try {
				rep.setReports(path);
			} catch (NullPointerException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	     
	     if(repName.substring(0,2).equalsIgnoreCase("RG")) {
	    	 ReportRG reprg =new ReportRG();
	    	 try {
				reprg.setReports(path);
			} catch (NullPointerException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	     if(repName.substring(0,2).equalsIgnoreCase("RB")) {
	    	 ReportRB reprb =new ReportRB();
	    	 try {
				reprb.setReports(path);
			} catch (NullPointerException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	     
	     if(repName.substring(0,2).equalsIgnoreCase("RX")) {
	    	 ReportRX reprx =new ReportRX();
	    	 try {
				reprx.setReports(path);
			} catch (NullPointerException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	     
	     
	     
		 dest = new File(path.replace("PULL\\FromBank", "ToFtp"));

		try {
		    FileUtils.copyFile(source, dest);
		    source.delete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	
	return true;
	
	}else {
		 source = new File(path);
		 dest = new File(path.replace("PULL", "ERROR"));

		try {
		    FileUtils.copyFile(source, dest);
		    source.delete();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return false;
	}
	
  } 
	

   public String getMsgId(File file) {
	   
	   
	   
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<MsgID>(.+?)</MsgID>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data2= matcher.group(1);
   
	   return data2;
 }

 public String getStatus(File file) {
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<Status>(.+?)</Status>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data2= matcher.group(1);
   
	   return data2;
  
   }
 
 public String getStatusDesc(File file) throws IOException {
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
	try {
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<StatusDescription>(.+?)</StatusDescription>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		data2= matcher.group(1);
		return data2;
	}
	catch (IllegalStateException e){
		return "";
	}
	   

 }
 
 
 public String getRegId(File file) {
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<regId>(.+?)</regId>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data2= matcher.group(1);
 
	   return data2;

 }

 
 public BigDecimal getTotal(File file) {
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<totalAmount>(.+?)</totalAmount>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data2= matcher.group(1);

	   return new BigDecimal(data2);

}
 public BigDecimal getTotalRM(File file,String node) {
	   byte[] encoded = null;
	try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
		String data2 = new String(encoded);
		final Pattern pattern = Pattern.compile("<"+node+">(.+?)</"+node+">", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data2);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data2= matcher.group(1);

	   return new BigDecimal(data2);

}

 
 
 public int getQuantity(File file) {
	
	 byte[] encoded = null;
	
	 int sum = 0;
	 try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	 
	    String data = new String(encoded);
		final Pattern pattern = Pattern.compile("<quantity>(.+?)</quantity>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data= matcher.group(1);
		sum=Integer.parseInt(data);
		
		return  sum;
		
	}
 
 public int getQuantityRM(File file,String node) {
		
	 byte[] encoded = null;
	
	 int sum = 0;
	 try {
		encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	 
	    String data = new String(encoded);
		final Pattern pattern = Pattern.compile("<"+node+">(.+?)</"+node+">", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data);
		matcher.find();
		System.out.println(matcher.group(1)); // Prints String I want to extract
		data= matcher.group(1);
		sum=Integer.parseInt(data);
		
		return  sum;
		
	}
 
 
}
 
 

