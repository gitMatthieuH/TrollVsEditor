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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.matthieuhostache.trollvseditor.shared.Troll;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TableView extends Composite {

	private static TableViewUiBinder uiBinder = GWT
			.create(TableViewUiBinder.class);
	@UiField(provided=true) CellTable<Troll> cellTable = new CellTable<Troll>();

	interface TableViewUiBinder extends UiBinder<Widget, TableView> {
	}

	public TableView() {
		cellTable.addColumn(nameColumn, "Name");
		ListDataProvider<Troll> dataProvider = new ListDataProvider<Troll>();
		dataProvider.addDataDisplay(cellTable);
		
		List<Troll> list = dataProvider.getList();
	    for (Troll troll : TROLLS) {
	      list.add(troll);
	    }
	}
	
	TextColumn<Troll> nameColumn = new TextColumn<Troll>() {
      @Override
      public String getValue(Troll troll) {
        return troll.nom;
      }
	};
	
	
}

