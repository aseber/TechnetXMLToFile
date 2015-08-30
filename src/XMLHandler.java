import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler {
	
	private SAXParserFactory factory;
	private SAXParser parser;
	private DefaultHandler handler;
	
	public XMLHandler() {
		
		try {
			
			factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();
			handler = new DefaultHandler() {
				
				String currentElement = "";
				String content = "";
				String[] parentElementData = new String[4];
				
				public void startElement(String uri, String localName, String qName, Attributes attributes) {
		 
					currentElement = qName;
					
					if (currentElement.equals("Product_Key")) {
						
						parentElementData[0] = attributes.getValue(0);
						FolderConstructor.createFolder("C://output//" + parentElementData[0]);
						
						// Move the file writing to the end of the element, we can process the whole of it
						// there and avoid the errors of it not seeing the files?
						
						// Clean up strings, they should not contain newline characters as they screw
						// up the file system and finding the files
						
					}
					
					if (currentElement.equals("Key")) {
						
						for (int i = 0; i < 3; i++) {
							
							parentElementData[i + 1] = attributes.getValue(i);
							
						}
						
					}
					
					System.out.println("Start Element: " + qName);
					for (int i = 0; i < attributes.getLength(); i++) {
						
						System.out.println("	Attribute " + i + ": " + attributes.getLocalName(i) + " - " + attributes.getValue(i));
						
					}
					
				}
				
				public void endElement(String uri, String localName, String qName) {
					
					System.out.println("End Element: " + qName);
					
					if (qName.equals("Product_Key")) {
						
						FolderConstructor.writeToFile("C://output//" + parentElementData[0] + "//keys.txt", content);
						content = "";
						
					}
					
				}
				
				public void characters (char ch[], int start, int length) {
					
					// Must detect that a change in folder name is in order and work accordingly, the
					// data added should append and not overwrite. Need to find a way to set a changed variable
					// for this method in order to enumerate the keys as they go in.
					
					// Write in handling for custom keys (They seem to have instructions written in where the
					// Key would be.
					
					String data = new String(ch, start, length).trim();
					
					if (!data.isEmpty()) {
						
						//FolderConstructor.writeToFile("C://output//" + parentElementData[0] + "//keys.txt", data + "\n");
						content += "Key Information:\n	ID: " + parentElementData[1] + "\n	Type: " + parentElementData[2] + "\n	Date Claimed: " + parentElementData[3] + "\n	Key: " + data + "\n\n";
						System.out.println("	Data: " + new String(ch, start, length));
					
					}
					
				}
				
			};
			
		}
		
		catch(SAXException | ParserConfigurationException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void ParseXML(String filename) {
		
		try {
		
			parser.parse(filename, this.handler);
			
		}
		
		catch (SAXException | IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
