package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.matthieuhostache.trollvseditor.shared.Troll;

public class ListView extends Composite{

	private static ListViewUiBinder uiBinder = GWT
			.create(ListViewUiBinder.class);

	interface ListViewUiBinder extends UiBinder<Widget, ListView> {
	}

	public ListView() {
		initWidget(uiBinder.createAndBindUi(this));
		getTrollsInfosFromServer();
	}
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	
	@UiField(provided=true) CellList<Troll> cellList = new CellList<Troll>(new AbstractCell<Troll>(){
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				Troll value, SafeHtmlBuilder sb) {
			// TODO Auto-generated method stub
			if (value != null) {
	            sb.appendEscaped(value.getNom());
	         }
			
		}
	});
	
	public void getTrollsInfosFromServer() {
		greetingService.greetServer("salut", new AsyncCallback<ArrayList<Troll>>() {
			@Override
			public void onSuccess(ArrayList<Troll> result) {
				// TODO Auto-generated method stub
				cellList.setRowCount(result.size(), true);
			    cellList.setRowData(0, result);
			    //Window.alert(result.get(0).getNom());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


}
