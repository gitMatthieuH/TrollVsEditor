package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.matthieuhostache.trollvseditor.shared.Troll;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(ArrayList<Troll> trollList) throws IllegalArgumentException;
	ArrayList<Troll> greetServer(String name) throws IllegalArgumentException;
}
