package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.matthieuhostache.trollvseditor.shared.Troll;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.event.logical.shared.SelectionEvent;

public class EditorView extends Composite {

	private static EditorViewUiBinder uiBinder = GWT
			.create(EditorViewUiBinder.class);
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private ArrayList<Troll> trollList = new ArrayList<Troll>();
	
	private int caracPoints;
	private int caracSpePoints;
	private ArrayList<Integer> currentTrollsId = new ArrayList<Integer>();
	private int elemOnEdit = -1;
	
	private ListDataProvider<Troll> dataProvider = new ListDataProvider<Troll>();

	interface EditorViewUiBinder extends UiBinder<Widget, EditorView> {
	}
	
	
	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));
		tabPanel.selectTab(0);
		initTable();
	}

	@UiField FormPanel formPanel;
	@UiField TextBox name;
	@UiField IntegerBox attaque;
	@UiField IntegerBox degats;
	@UiField IntegerBox esquive;
	@UiField IntegerBox regeneration;
	@UiField IntegerBox pointdevie;
	@UiField ListBox race;
	@UiField Button attaquem;
	@UiField Button attaquep;
	@UiField Image pic;
	@UiField IntegerBox compet1;
	@UiField Button compet1m;
	@UiField Button compet1p;
	@UiField Button compet2m;
	@UiField IntegerBox compet2;
	@UiField Button compet2p;
	@UiField Label compet2name;
	@UiField Label compet1name;
	@UiField Label ptsCompet;
	@UiField Label caracPointsRestants;
	
	@UiField(provided=true) CellTable<Troll> cellTable = new CellTable<Troll>();
	@UiField TabPanel tabPanel;
	@UiField ListBox listTrolls;
	@UiField ListBox trollsToAdd;
	@UiField Button editBn;
	@UiField Button delBtn;

	public EditorView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		//savetrolls.setText(firstName);
		this.caracPoints = 50;
		this.caracSpePoints = 20;
		
	}
	
	@UiHandler("addtroll")
	void onAddtrollsClick(ClickEvent event) {
		Troll trollToS = new Troll(name.getValue(),race.getSelectedIndex(),attaque.getValue(),degats.getValue(),esquive.getValue(),regeneration.getValue(),pointdevie.getValue(),compet1.getValue(),compet2.getValue());
		trollList.add(trollToS);
		trollsToAdd.addItem(trollToS.getNom());
	}

	@UiHandler("savetrolls")
	void onSavetrollsClick(ClickEvent event) {
		System.out.println("onSavetrollsClick ");
		System.out.println("elemOnEdit "+this.elemOnEdit);
		if (elemOnEdit != -1) {
			System.out.println("if ");
			System.out.println("esquive " + esquive.getValue());
			Troll trollToSave = new Troll(name.getValue(),race.getSelectedIndex(),attaque.getValue(),degats.getValue(),esquive.getValue(),regeneration.getValue(),pointdevie.getValue(),compet1.getValue(),compet2.getValue());
			System.out.println("esquive 2 " + trollToSave.getEsquive());
			this.trollList.set(elemOnEdit, trollToSave);
			
		}
		formPanel.submit();
	}
	
	@UiHandler("formPanel")
	void onFormPanelSubmit(SubmitEvent event) {
		TrollvsEditor.get().sendTrollsInfosToServer(this.trollList);
		
	}

	@UiHandler("attaquem")
	void onAttaquemClick(ClickEvent event) {
		if(this.caracPoints>0 && attaque.getValue()>0) {
			attaque.setValue(attaque.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("attaquep")
	void onAttaquepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			attaque.setValue(attaque.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("degatsm")
	void onDegatsClick(ClickEvent event) {
		if(this.caracPoints>0 && degats.getValue()>0) {
			degats.setValue(degats.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("degatsp")
	void onDegatspClick(ClickEvent event) {
		if(this.caracPoints<50) {
			degats.setValue(degats.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("esquivem")
	void onEsquivemClick(ClickEvent event) {
		if(this.caracPoints>0 && esquive.getValue()>0) {
			esquive.setValue(esquive.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("esquivep")
	void onEsquivepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			esquive.setValue(esquive.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("regenerationm")
	void onRegenerationmClick(ClickEvent event) {
		if(this.caracPoints>0 && regeneration.getValue()>0) {
			regeneration.setValue(regeneration.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("regenerationp")
	void onRegenerationpClick(ClickEvent event) {
		if(this.caracPoints<50) {
			regeneration.setValue(regeneration.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("pointdeviem")
	void onPointdeviemClick(ClickEvent event) {
		if(this.caracPoints>0 && pointdevie.getValue()>0) {
			pointdevie.setValue(pointdevie.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("pointdeviep")
	void onPointdeviepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			pointdevie.setValue(pointdevie.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	@UiHandler("compet1m")
	void onCompet1mClick(ClickEvent event) {
		if(this.caracSpePoints>0 && compet1.getValue()>0) {
			compet1.setValue(compet1.getValue()-1);
			this.caracSpePoints--;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	@UiHandler("compet1p")
	void onCompet1pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet1.setValue(compet1.getValue()+1);
			this.caracSpePoints++;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	@UiHandler("compet2m")
	void onCompet2mClick(ClickEvent event) {
		if(this.caracSpePoints>0 && compet2.getValue()>0) {
			compet2.setValue(compet2.getValue()-1);
			this.caracSpePoints--;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	@UiHandler("compet2p")
	void onCompet2pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet2.setValue(compet2.getValue()+1);
			this.caracSpePoints++;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	@UiHandler("race")
	void onRaceChange(ChangeEvent event) {
		String picUrl;
		
		switch (race.getSelectedIndex())
		{
			
		  case 0:
			  picUrl = "img/hebus.jpg";
			  compet1name.setText("Botte Secrète");
			  compet2name.setText("Hypnotisme");
		    break;
		  case 1:
			  picUrl = "img/tinette.jpg";
			  compet1name.setText("Accélération Métabolique");
			  compet2name.setText("Vampirisme");
		    break;
		  case 2:
			  picUrl = "img/trollf.jpg";
			  compet1name.setText("Régénération Accrue");
			  compet2name.setText("Rafale Psychique");
		    break;
		  case 3:
			  picUrl = "img/waha.jpg";
			  compet1name.setText("Camouflage");
			  compet2name.setText("Projectile Magique");
			  break;
		  case 5:
			  picUrl = "img/tetram.jpg";
			  compet1name.setText("Balayage");
			  compet2name.setText("Siphon des âmes");
			  break;
		  default:
			  picUrl = "img/tetram.jpg";
			  compet1name.setText("Botte Secrète");
			  compet2name.setText("Hypnotisme");
		}
		
		pic.setUrl(picUrl);
	}
	@UiHandler("addtroll")
	void onAddtrollClick(ClickEvent event) {
	}
	
	public void initTable() {
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
	}
	
	public void getTrollsInfosFromServer() {
		greetingService.greetServer("", new AsyncCallback<ArrayList<Troll>>() {
			@Override
			public void onSuccess(ArrayList<Troll> result) {
				//listTrolls.clear();
				
				trollList = result;
				
				for(Troll troll: result) {
					listTrolls.addItem(troll.getNom());
				}
						
				
			    dataProvider = new ListDataProvider<Troll>();
			    
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
	
	@UiHandler("tabPanel")
	void onTabSelection(SelectionEvent<Integer> event) {
		System.out.println("tab "+event.getSelectedItem());
	  if (event.getSelectedItem() == 1) {
		  getTrollsInfosFromServer();
		  listTrolls.clear();
		  dataProvider.refresh();
		  
	  }
	}
	
	
	@UiHandler("editBn")
	void onEditBnClick(ClickEvent event) {
		if (listTrolls.getSelectedIndex() != -1) {
			int trollIndex = listTrolls.getSelectedIndex();
			tabPanel.selectTab(0);
			ArrayList<Troll> currentsTrolls = new ArrayList<Troll>();
			currentsTrolls.add(trollList.get(trollIndex));
			currentTrollsId.clear();
			currentTrollsId.add(trollIndex);
			trollsToAdd.clear();
			for(Troll troll : currentsTrolls) {
				trollsToAdd.addItem(troll.getNom());
			}
		}
	}
	
	@UiHandler("delBtn")
	void onDelBtnClick(ClickEvent event) {
		if (listTrolls.getSelectedIndex() != -1) {
			int trollIndex = listTrolls.getSelectedIndex();
			String name = trollList.get(trollIndex).getNom();
			greetingService.delTroll(name, new AsyncCallback<String>(){
				@Override
				public void onSuccess(String result) {
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
		}
	}
	
	
	
	void initEditor(int trollId) {
		Troll currentTroll = trollList.get(trollId);
		name.setValue(currentTroll.getNom());
		race.setSelectedIndex(currentTroll.getRace());
		attaque.setValue(currentTroll.getAttaque());
		degats.setValue(currentTroll.getDeguat());
		esquive.setValue(currentTroll.getEsquive());
		regeneration.setValue(currentTroll.getRegeneration());
	}
	@UiHandler("trollsToAdd")
	void onTrollsToAddClick(ClickEvent event) {
		int idInTrollList = currentTrollsId.get(trollsToAdd.getSelectedIndex());
		initEditor(idInTrollList);
		elemOnEdit = trollsToAdd.getSelectedIndex();
	}
	
}
