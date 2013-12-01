package com.matthieuhostache.trollvseditor.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.tools.ant.util.StringUtils;

import com.matthieuhostache.trollvseditor.client.GreetingService;
import com.matthieuhostache.trollvseditor.shared.Troll;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private ArrayList<Troll> savedTrollList;
	
	public String greetServer(ArrayList<Troll> trollList) throws IllegalArgumentException {
		String etatSauvegarde = "Erreur lors de la sauvegarde";
		
		System.out.println("Sauvegarde");

		this.savedTrollList = greetServer("");
		ArrayList<Troll> tList = this.savedTrollList;
		System.out.println("tList" + tList);

		for(Troll troll: trollList) {
			int trollToReplaceIndex = -1;
			if (tList != null) {
				for(Troll troll2 : tList) {
					if (troll.getNom().compareTo(troll2.getNom()) == 0) {
						trollToReplaceIndex = tList.indexOf(troll2);
					}
				}
			} else {
				this.savedTrollList = new ArrayList<Troll>();
			}
			
			if (trollToReplaceIndex != -1)
				savedTrollList.set(trollToReplaceIndex, troll);
			else
				savedTrollList.add(troll);
				
		}
		
		XStream xstream = new XStream();

        try{   
        	FileOutputStream outputStream = new FileOutputStream(this.getServletContext().getRealPath("trolls.xml"));
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
        	xstream.toXML(this.savedTrollList, writer);
        	etatSauvegarde = "Sauvegarde OK";
        }catch (Exception e){
            System.err.println("Error in XML Write: " + e.getMessage());
        }

        return etatSauvegarde;
	}
	
	public ArrayList<Troll> greetServer(String name) throws IllegalArgumentException {
		ArrayList<Troll> trolls = null;
		
		
		try {
            XStream xstream = new XStream(new DomDriver());
 
            FileInputStream fis = new FileInputStream(new File(this.getServletContext().getRealPath("trolls.xml")));
            
            try {
            	trolls = (ArrayList<Troll>) xstream.fromXML(fis);
            } finally {
                fis.close();
            }
			} catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
		
		return trolls;
	}
	
	public String delTroll(String name) throws IllegalArgumentException {
		
		System.out.println("Suppression");
		
		List< Troll > list = new ArrayList< Troll >();
		list = greetServer("");
		
		
		for( Iterator< Troll > it = list.iterator(); it.hasNext() ; ) {
			Troll troll = it.next();
			if (troll.getNom().compareTo(name) == 0) {
				it.remove();
			}
		}
	
		XStream xstream = new XStream();

        try{   
        	FileOutputStream outputStream = new FileOutputStream(this.getServletContext().getRealPath("trolls.xml"));
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
        	xstream.toXML(list, writer);
        }catch (Exception e){
            System.err.println("Error in XML Write: " + e.getMessage());
        }
        
		return null;

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
