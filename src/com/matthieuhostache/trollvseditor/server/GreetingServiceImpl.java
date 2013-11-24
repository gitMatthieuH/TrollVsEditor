package com.matthieuhostache.trollvseditor.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.matthieuhostache.trollvseditor.client.GreetingService;
import com.matthieuhostache.trollvseditor.shared.FieldVerifier;
import com.matthieuhostache.trollvseditor.shared.Troll;
import com.thoughtworks.xstream.XStream;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private ArrayList<Troll> savedTrollList;

	public String greetServer(ArrayList<Troll> trollList) throws IllegalArgumentException {
		// Verify that the input is valid. 
		/*if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}*/
		
		this.savedTrollList = trollList;
		
		String trollName = "undefined";
		
		XStream xstream = new XStream();
		String xml = null;
		for(Troll troll : trollList){
			xml = xstream.toXML(troll);
			trollName = troll.getNom();
		}

		String dest = "troll.xml"; 
		Document doc = null; 
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		
		try{ 
		        db = dbf.newDocumentBuilder(); 
		} 
		catch(ParserConfigurationException pce){ 
		        System.err.println("Errore per DocumentBuilder"); 
		} 
		

		
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		
		
		
		trollName = escapeHtml(trollName);
		userAgent = escapeHtml(userAgent);

		return xml;
	}
	
	public ArrayList<Troll> greetServer(String name) throws IllegalArgumentException {
		return savedTrollList;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
