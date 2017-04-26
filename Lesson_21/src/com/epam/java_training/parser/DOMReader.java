package com.epam.java_training.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.java_training.model.Product;

public class DOMReader {
	
	private String file_path;
	
	private Product       current_product;
	private List<Product> list_products;
	
	public DOMReader(String file_path){
		
		this.file_path = file_path;
		this.list_products = new ArrayList<Product>();
		this.current_product = null;
	}
	
	public List<Product> getList_products() {
		return this.list_products;
	}
	
	public void parse(){
		
		System.out.println("DOM parser XML document - start.");
		
		DOMParser parser = new DOMParser();
		
		try {
			
			parser.parse(file_path);
			Document document = parser.getDocument();
			Element root = document.getDocumentElement();
			
			NodeList productNodes = root.getElementsByTagName("ProductDetail");
			
			for (int i = 0; i < productNodes.getLength(); i++) {
				
				current_product = new Product();
				
				Element productElement = (Element) productNodes.item(i);
			
				current_product.setCode(productElement.getAttribute("Code"));
				current_product.setName(productElement.getAttribute("Name"));
				
				current_product.setAmount(Integer.parseInt(getSingleChild(productElement, "Amount").getTextContent().trim()));
				current_product.setPrice(Integer.parseInt(getSingleChild(productElement, "Price").getTextContent().trim()));
				
				current_product.setDescription(getSingleChild(productElement, "Description").getTextContent().trim());
				current_product.setType(getSingleChild(productElement, "Type").getTextContent().trim());
				current_product.setStatus(getSingleChild(productElement, "Status").getTextContent().trim());
				
				current_product.setCountry_code(getComplexChild(productElement, "AdditionalInfo", "Country").getAttribute("Code"));
				current_product.setProvider_name(getComplexChild(productElement, "AdditionalInfo",  "Provider").getAttribute("Name"));
				
				this.list_products.add(current_product);
				
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("DOM parser XML document - end.");
	}
	
	private static Element getSingleChild(Element element, String childName){
		NodeList nlist = element.getElementsByTagName(childName);
		Element child = (Element) nlist.item(0);
		return child;
	}
	
	private static Element getComplexChild(Element element, String elementName, String childName){
		NodeList nlist = element.getElementsByTagName(elementName);
		
		Element child_1 = (Element)nlist.item(0);
		
		NodeList nlist_2 = child_1.getElementsByTagName(childName);
		
		Element child_2 = (Element)nlist_2.item(0);
		
		return child_2;
	}
	
	public void printList_products(){
		
		for (Product product : this.list_products) {
			System.out.println(product);
		}
	}
}
