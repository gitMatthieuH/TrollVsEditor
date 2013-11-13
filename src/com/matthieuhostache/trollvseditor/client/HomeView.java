package com.matthieuhostache.trollvseditor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.matthieuhostache.trollvseditor.shared.Troll;

public class HomeView extends Composite{

	private static HomeViewUiBinder uiBinder = GWT
			.create(HomeViewUiBinder.class);
	
	private EditorView mainEditor = new EditorView();
	private ListView mainList = new ListView();
	private TableView mainTable = new TableView();
	
	
	@UiField Button edittroll;
	@UiField Button trolllist;
	@UiField Button trolltable;

	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {
	}

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("edittroll")
	void onEditTrollClick(ClickEvent event) {
		RootPanel.get("content").remove(mainList);
		RootPanel.get("content").remove(mainTable);
		RootPanel.get("content").add(mainEditor);
	}
	
	@UiHandler("trolllist")
	void onTrollListClick(ClickEvent event) {
		RootPanel.get("content").remove(mainEditor);
		RootPanel.get("content").remove(mainTable);
		RootPanel.get("content").add(mainList);
	}
	
	@UiHandler("trolltable")
	void onTrollTableClick(ClickEvent event) {
		RootPanel.get("content").remove(mainList);
		RootPanel.get("content").remove(mainEditor);
		RootPanel.get("content").add(mainTable);
	}


}
