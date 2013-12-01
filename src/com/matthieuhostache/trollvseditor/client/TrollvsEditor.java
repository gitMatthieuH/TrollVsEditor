package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;

import com.matthieuhostache.trollvseditor.shared.Troll;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TrollvsEditor implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final TrollServiceAsync greetingService = GWT
			.create(TrollService.class);
	
	private EditorView mainEditor = new EditorView();
	
	private static TrollvsEditor SINGLETON;
	
	public static TrollvsEditor get() {
		return SINGLETON;
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		SINGLETON = this;
		//RootPanel.get().add(mainEditor);
		RootPanel.get("navigation").add(mainEditor);
		mainEditor.addStyleName("table-center");
	
	}
	
	public void sendTrollsInfosToServer(ArrayList<Troll> trollList) {
		greetingService.saveTrolls(trollList,new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				Window.alert("Remote Procedure Call - Failure");
				Window.alert(SERVER_ERROR);
				/*dialogBox.setText("Remote Procedure Call - Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				
				dialogBox.center();
				closeButton.setFocus(true);*/
			}

			public void onSuccess(String result) {
				Window.alert(result);
			}
		});
	}

}
