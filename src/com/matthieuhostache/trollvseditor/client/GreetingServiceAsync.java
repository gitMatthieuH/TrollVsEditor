package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.matthieuhostache.trollvseditor.shared.Troll;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(ArrayList<Troll> trollList, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void greetServer(String input, AsyncCallback<ArrayList<Troll>> callback)
			throws IllegalArgumentException;
}
