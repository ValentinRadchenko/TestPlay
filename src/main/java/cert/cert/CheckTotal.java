package cert.cert;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CheckTotal {
	
	
	
	public BigDecimal getTotal(String path) throws ParserConfigurationException, SAXException, IOException {
		 Double totalAmount = null ;
		 BigDecimal totalSum = new BigDecimal(0);
		 BigDecimal sum = new BigDecimal(0);
		 
		 try {
			            // Создается построитель документа
	            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            // Создается дерево DOM документа из файла
	            Document document = documentBuilder.parse(path);
	 
	            // Получаем корневой элемент
	            Node root = document.getDocumentElement();
	            
	            System.out.println("List of books:");
	            System.out.println();
	            // Просматриваем все подэлементы корневого - т.е. книги
	            //NodeList books = root.getChildNodes();
	            NodeList books = document.getElementsByTagName("info");
	            for (int i = 0; i < books.getLength(); i++) {
	                Node book = books.item(i);
	                // Если нода не текст, то это книга - заходим внутрь
	                if (book.getNodeType() != Node.TEXT_NODE) {
	                    NodeList bookProps = book.getChildNodes();
	                    for(int j = 0; j < bookProps.getLength(); j++) {
	                        Node bookProp = bookProps.item(j);
	                        // Если нода не текст, то это один из параметров книги - печатаем
	                        if (bookProp.getNodeType() != Node.TEXT_NODE && bookProp.getNodeName().equals("totalAmount") ) {
	                            System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
	                            totalAmount = Double.parseDouble(bookProp.getChildNodes().item(0).getTextContent());
	                            
	                        }
	                    }
	                    System.out.println("===========>>>>");
	                }
	            }
	            
	            
	            
	            
	            books = document.getElementsByTagName("transfer");
	            
	            if(books.getLength()<1)
	            	books= document.getElementsByTagName("register");
	            
	            if(books.getLength()<1)
	            books = document.getElementsByTagName("transferenceData");
	            for (int i = 0; i < books.getLength(); i++) {
	                Node book = books.item(i);
	                // Если нода не текст, то это книга - заходим внутрь
	                if (book.getNodeType() != Node.TEXT_NODE) {
	                    NodeList bookProps = book.getChildNodes();
	                    for(int j = 0; j < bookProps.getLength(); j++) {
	                        Node bookProp = bookProps.item(j);
	                        // Если нода не текст, то это один из параметров книги - печатаем
	                        if (bookProp.getNodeType() != Node.TEXT_NODE && bookProp.getNodeName().equals("amount")) {
	                            System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
	                            //sum=new BigDecimal(bookProp.getChildNodes().item(0).getTextContent());
	                            totalSum=totalSum.add(  new BigDecimal(bookProp.getChildNodes().item(0).getTextContent()));
	                        }
	                    }
	                    System.out.println("===========>>>>");
	                }
	            }
	            DecimalFormat df = new DecimalFormat("#.##");

	            df.setRoundingMode(RoundingMode.FLOOR);
	            //totalSum=new BigDecimal((df.format(new BigDecimal(totalSum)));
	            
	            //totalSum = new DecimalFormat("#.##").format(totalSum);
	        } catch (ParserConfigurationException ex) {
	            ex.printStackTrace(System.out);
	        } catch (SAXException ex) {
	            ex.printStackTrace(System.out);
	        } catch (IOException ex) {
	            ex.printStackTrace(System.out);
	        } catch (NumberFormatException ex){
	        	ex.printStackTrace(System.out);
	        }
		 
		    
	

	return totalSum;

}
	
	
	public int getAllTransfers(String path) {
		NodeList list = null;
		
		try {
			String filepath = path;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			list = doc.getElementsByTagName("transfer");

			System.out.println("Total of elements : " + list.getLength());

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	 
	
	return list.getLength();
	}
	
	public int getAllRegisters(String path) {
		NodeList list = null;
		
		try {
			String filepath = path;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			list = doc.getElementsByTagName("register");

			System.out.println("Total of elements : " + list.getLength());

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	 
	
	return list.getLength();
	}
	
	public int getAllTransferenseData(String path) {
		NodeList list = null;
		
		try {
			String filepath = path;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			list = doc.getElementsByTagName("transferenceData");

			System.out.println("Total of elements : " + list.getLength());

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	 
	
	return list.getLength();
	}
	
	
	
}
		
		
		
	
	
