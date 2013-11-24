package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;
import java.util.Comparator;

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
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.matthieuhostache.trollvseditor.shared.Troll;

import java.util.List;

public class TableView extends Composite {

	private static TableViewUiBinder uiBinder = GWT
			.create(TableViewUiBinder.class);
	@UiField(provided=true) CellTable<Troll> cellTable = new CellTable<Troll>();
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	interface TableViewUiBinder extends UiBinder<Widget, TableView> {
	}

	public TableView() {
		getTrollsInfosFromServer(); 
	}
	
	public void getTrollsInfosFromServer() {
		greetingService.greetServer("salut", new AsyncCallback<ArrayList<Troll>>() {
			@Override
			public void onSuccess(ArrayList<Troll> result) {
				
				TextColumn<Troll> nameColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return troll.getNom();
			      }
			    };
			    
			    TextColumn<Troll> raceColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getRace());
			      }
			    };
			    
			    TextColumn<Troll> attaqueColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getAttaque());
			      }
			    };
			    
			    TextColumn<Troll> deguatColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getDeguat());
			      }
			    };
			    
			    TextColumn<Troll> esquiveColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getEsquive());
			      }
			    };
			    
			    TextColumn<Troll> regenerationColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getRegeneration());
			      }
			    };
					    			    
			    TextColumn<Troll> pointdevieColumn = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getPointdevie());
			      }
			    };
					        
			    TextColumn<Troll> competence1Column = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getCompetence1());
			      }
			    };
					    
			    TextColumn<Troll> competence2Column = new TextColumn<Troll>() {
			      @Override
			      public String getValue(Troll troll) {
			        return Integer.toString(troll.getCompetence2());
			      }
			    };
			    
			    			    
			    cellTable.addColumn(nameColumn, "Nom");
			    cellTable.addColumn(raceColumn, "Race");
			    cellTable.addColumn(attaqueColumn, "Attaque");
			    cellTable.addColumn(deguatColumn, "Déguat");
			    cellTable.addColumn(esquiveColumn, "Esquive");
			    cellTable.addColumn(regenerationColumn, "Regénération");
			    cellTable.addColumn(pointdevieColumn, "Point de vie");
			    cellTable.addColumn(competence1Column, "Compétence 1");
			    cellTable.addColumn(competence2Column, "Compétence 2");
			    
			    
			    ListDataProvider<Troll> dataProvider = new ListDataProvider<Troll>();
			    
			    dataProvider.addDataDisplay(cellTable);
			    
			    List<Troll> list = dataProvider.getList();
			    
				for (Troll troll : result) {
			      list.add(troll);
			    }
				
				initWidget(cellTable);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
}

