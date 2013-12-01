package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;

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
	@UiField VerticalPanel facesContener;

	/**
	 * Constructeur du binder
	 */
	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));
		tabPanel.selectTab(0);
		initTable();
	}
	
	public EditorView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		//savetrolls.setText(firstName);
		this.caracPoints = 50;
		this.caracSpePoints = 20;
		
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur le bouton ajouter de l'onglet editeur de troll
	 * @param event
	 */
	@UiHandler("addtroll")
	void onAddtrollsClick(ClickEvent event) {
		Troll trollToS = new Troll(name.getValue(),race.getSelectedIndex(),attaque.getValue(),degats.getValue(),esquive.getValue(),regeneration.getValue(),pointdevie.getValue(),compet1.getValue(),compet2.getValue());
		trollList.add(trollToS);
		trollsToAdd.addItem(trollToS.getNom());
	}
	
	
	/**
	 * Déclencheur de l'évenement au clique sur le bouton sauvegarder de l'onglet editeur de troll
	 * @param event
	 */
	@UiHandler("savetrolls")
	void onSavetrollsClick(ClickEvent event) {
		System.out.println("onSavetrollsClick ");
		System.out.println("elemOnEdit "+this.elemOnEdit);
		if (elemOnEdit != -1) {
			Troll trollToSave = new Troll(name.getValue(),race.getSelectedIndex(),attaque.getValue(),degats.getValue(),esquive.getValue(),regeneration.getValue(),pointdevie.getValue(),compet1.getValue(),compet2.getValue());
			this.trollList.set(elemOnEdit, trollToSave);
		}
		formPanel.submit();
	}
	
	
	/**
	 * Envoi du formulaire édition/ajout d'une liste de trolls de l'onglet editeur de troll
	 * @param event
	 */
	@UiHandler("formPanel")
	void onFormPanelSubmit(SubmitEvent event) {
		TrollvsEditor.get().sendTrollsInfosToServer(this.trollList);
		
	}

	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut attaque
	 * @param event
	 */
	@UiHandler("attaquem")
	void onAttaquemClick(ClickEvent event) {
		if(this.caracPoints>0 && attaque.getValue()>0) {
			attaque.setValue(attaque.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut attaque
	 * @param event
	 */
	@UiHandler("attaquep")
	void onAttaquepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			attaque.setValue(attaque.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut degats
	 * @param event
	 */
	@UiHandler("degatsm")
	void onDegatsClick(ClickEvent event) {
		if(this.caracPoints>0 && degats.getValue()>0) {
			degats.setValue(degats.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut degats
	 * @param event
	 */
	@UiHandler("degatsp")
	void onDegatspClick(ClickEvent event) {
		if(this.caracPoints<50) {
			degats.setValue(degats.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut esquive
	 * @param event
	 */
	@UiHandler("esquivem")
	void onEsquivemClick(ClickEvent event) {
		if(this.caracPoints>0 && esquive.getValue()>0) {
			esquive.setValue(esquive.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut esquive
	 * @param event
	 */
	@UiHandler("esquivep")
	void onEsquivepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			esquive.setValue(esquive.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut regeneration
	 * @param event
	 */
	@UiHandler("regenerationm")
	void onRegenerationmClick(ClickEvent event) {
		if(this.caracPoints>0 && regeneration.getValue()>0) {
			regeneration.setValue(regeneration.getValue()-1);
			this.caracPoints--;
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut regeneration
	 * @param event
	 */
	@UiHandler("regenerationp")
	void onRegenerationpClick(ClickEvent event) {
		if(this.caracPoints<50) {
			regeneration.setValue(regeneration.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut point de vie
	 * @param event
	 */
	@UiHandler("pointdeviem")
	void onPointdeviemClick(ClickEvent event) {
		if(this.caracPoints>0 && pointdevie.getValue()>0) {
			pointdevie.setValue(pointdevie.getValue()-1);
			this.caracPoints--;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut point de vie
	 * @param event
	 */
	@UiHandler("pointdeviep")
	void onPointdeviepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			pointdevie.setValue(pointdevie.getValue()+1);
			this.caracPoints++;
			caracPointsRestants.setText( Integer.toString(50 - this.caracPoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut competence 1
	 * @param event
	 */
	@UiHandler("compet1m")
	void onCompet1mClick(ClickEvent event) {
		if(this.caracSpePoints>0 && compet1.getValue()>0) {
			compet1.setValue(compet1.getValue()-1);
			this.caracSpePoints--;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut competence 1
	 * @param event
	 */
	@UiHandler("compet1p")
	void onCompet1pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet1.setValue(compet1.getValue()+1);
			this.caracSpePoints++;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur - de l'attribut competence 2
	 * @param event
	 */
	@UiHandler("compet2m")
	void onCompet2mClick(ClickEvent event) {
		if(this.caracSpePoints>0 && compet2.getValue()>0) {
			compet2.setValue(compet2.getValue()-1);
			this.caracSpePoints--;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur + de l'attribut competence 2
	 * @param event
	 */
	@UiHandler("compet2p")
	void onCompet2pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet2.setValue(compet2.getValue()+1);
			this.caracSpePoints++;
			ptsCompet.setText( Integer.toString(20 - this.caracSpePoints));
		}
	}
	
	/**
	 * Déclencheur de l'évenement au changement de l'attribut race
	 * @param event
	 */
	@UiHandler("race")
	void onRaceChange(ChangeEvent event) {
		String picUrl;
		pic.setUrl(getUrlRace(race.getSelectedIndex()));
	}
	
	/**
	 * Fonction qui retourne le chemin de l'image d'une race en foncion de l'id de la race
	 * @param event
	 */
	public String getUrlRace(int index) {
		String picUrl;
		
		switch (index)
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
		return GWT.getHostPageBaseURL() + picUrl;
	}
	
	
	/**
	 * Fonction qui retourne le nom de la race d'une race en foncion de l'id de la race
	 * @param event
	 */
	public String getNameRace(int index) {
		String name;
		
		switch (index)
		{
			
		  case 0:
			  name = "Skrim";
		    break;
		  case 1:
			  name = "Kastar";
		    break;
		  case 2:
			  name = "Durakuir";
		    break;
		  case 3:
			  name = "Tomawak";
			  break;
		  case 5:
			  name = "Darkling";
			  break;
		  default:
			  name = "Skrim";
		}
		return name;
	}
	
	
	/**
	 * Fonction de création des entêtes des colonnes du tableau présent dans l'onglet listing des trolls
	 */
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
	
	
	
	/**
	 * Fonction qui récupère les trolls sauvegardés côté serveur et met
	 * en forme en fonction de l'onglet fournit en paramètre
	 * @param tabIndex
	 */
	public void getTrollsInfosFromServer(final int tabIndex) {
		greetingService.greetServer("", new AsyncCallback<ArrayList<Troll>>() {
			@Override
			public void onSuccess(final ArrayList<Troll> result) {
				//listTrolls.clear();
				
				trollList = result;
				
				for(Troll troll: result) {
					listTrolls.addItem(troll.getNom());
				}
				
				switch (tabIndex)
				{
					// Onglet liste des trolls
				  case 1:
					dataProvider = new ListDataProvider<Troll>();    
					dataProvider.addDataDisplay(cellTable);

					List<Troll> list = dataProvider.getList();
					list.add(trollList.get(0));
					break;
					// Onglet chasseur de tête
				  case 2:
					  double nbColumns = 6;
					  int nbRows = (int) Math.ceil(result.size()/nbColumns);
					  System.out.print("nbRows :" + nbRows);
				      Grid grid = new Grid(nbRows, (int)nbColumns);
				      facesContener.clear();
					  facesContener.add(grid);
					  int idTroll = 0;
					  for (int row = 0; row < nbRows; row++) {
				         for (int col = 0; col < nbColumns; col++) {
				        	final Troll troll = result.get(idTroll);
				        	final int savedIdTroll = idTroll;
				        	Image image = new Image();
							image.setUrl(getUrlRace(troll.getRace()));
							image.setWidth("150px");
							image.setAltText("Nom : "+ result.get(idTroll).getNom());
							image.addClickHandler(new ClickHandler() {
							  @Override
							  /* Au clique sur l'image */
							  public void onClick(ClickEvent event) {
								  PopupPanel popup = new PopupPanel(true);
								  VerticalPanel vp = new VerticalPanel();
								  popup.add(vp);
								  HorizontalPanel hp = new HorizontalPanel();
								  vp.add(new HTML(displayTroll(troll)));
								  vp.add(hp);
								  Button editBtn = new Button("Editer");
								  /* Au clique  sur bouton edition du popup */
								  editBtn.addClickHandler(new ClickHandler() {
									 @Override
									 public void onClick(ClickEvent event) {
										tabPanel.selectTab(0);
										ArrayList<Troll> currentsTrolls = new ArrayList<Troll>();
										currentsTrolls.clear();
										currentsTrolls.add(troll);
										currentTrollsId.clear();
										currentTrollsId.add(savedIdTroll);
										trollsToAdd.clear();
										for(Troll troll : currentsTrolls) {
											trollsToAdd.addItem(troll.getNom());
										}
										trollsToAdd.setSelectedIndex(0);
										int idInTrollList = currentTrollsId.get(trollsToAdd.getSelectedIndex());
										initEditor(idInTrollList);
										elemOnEdit = listTrolls.getSelectedIndex();
									 }
								  });
								  Button delBtn = new Button("Supprimer");
								  /* Au clique  sur bouton suppression du popup */
								  delBtn.addClickHandler(new ClickHandler() {
									 @Override
									 public void onClick(ClickEvent event) {	 
										 String name = troll.getNom();
										 greetingService.delTroll(name, new AsyncCallback<String>(){
											@Override
											public void onSuccess(String result) {
												getTrollsInfosFromServer(2);
											}
											
											@Override
											public void onFailure(Throwable caught) {
												
											}
										 });
									 }
								  });
								  hp.add(editBtn);
								  hp.add(delBtn);
								  popup.center();
								  popup.show();
							  }
							});
				            grid.setWidget(row, col, image);
				            idTroll++;
				         }
				      }
				  default:
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	/**
	 * Déclencheur de l'évenement au changement d'onglet
	 * @param event
	 */
	@UiHandler("tabPanel")
	void onTabSelection(SelectionEvent<Integer> event) {
		System.out.println("tab "+event.getSelectedItem());
		if (event.getSelectedItem() == 0) {
			trollsToAdd.clear();
		}
		if (event.getSelectedItem() == 1) {
		  getTrollsInfosFromServer(1);
		  listTrolls.clear();
		  dataProvider.refresh();
		  initEditor();  
		}
		if (event.getSelectedItem() == 2) {
		  getTrollsInfosFromServer(2);
		}
	}
	
	
	/**
	 * Déclencheur de l'évenement au clique sur le bouton editer de l'onglet listings des trolls
	 * @param event
	 */
	@UiHandler("editBn")
	void onEditBnClick(ClickEvent event) {
		if (listTrolls.getSelectedIndex() != -1) {
			int trollIndex = listTrolls.getSelectedIndex();
			System.out.println("trollIndex : " + trollIndex);
			tabPanel.selectTab(0);
			ArrayList<Troll> currentsTrolls = new ArrayList<Troll>();
			currentsTrolls.clear();
			currentsTrolls.add(trollList.get(trollIndex));
			System.out.println("trollList.get(trollIndex) : " + trollList.get(trollIndex));
			currentTrollsId.clear();
			currentTrollsId.add(trollIndex);
			trollsToAdd.clear();
			for(Troll troll : currentsTrolls) {
				trollsToAdd.addItem(troll.getNom());
			}
			trollsToAdd.setSelectedIndex(0);
			int idInTrollList = currentTrollsId.get(trollsToAdd.getSelectedIndex());
			initEditor(idInTrollList);
			elemOnEdit = listTrolls.getSelectedIndex();
		}
	}
	
	
	/**
	 * Déclencheur de l'évenement au clique sur le bouton supprimer de l'onglet listings des trolls
	 * @param event
	 */
	@UiHandler("delBtn")
	void onDelBtnClick(ClickEvent event) {
		if (listTrolls.getSelectedIndex() != -1) {
			int trollIndex = listTrolls.getSelectedIndex();
			System.out.println("trollIndex : " + trollIndex);
			String name = trollList.get(trollIndex).getNom();
			System.out.println("name client : " + name);
			greetingService.delTroll(name, new AsyncCallback<String>(){
				@Override
				public void onSuccess(String result) {
					getTrollsInfosFromServer(1);
					  listTrolls.clear();
					  dataProvider.refresh();
					  initEditor(); 
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
		}
	}
	
	/**
	 * Initaliser l'éditeur de trol
	 */
	void initEditor() {
		name.setValue("");
		race.setSelectedIndex(0);
		attaque.setValue(0);
		degats.setValue(0);
		esquive.setValue(0);
		regeneration.setValue(0);
		compet1.setValue(0);
		compet2.setValue(0);
	}
	
	/**
	 * Initaliser l'éditeur de troll avec les infos du troll selectionné
	 * @param trollId
	 */
	void initEditor(int trollId) {
		Troll currentTroll = trollList.get(trollId);
		name.setValue(currentTroll.getNom());
		race.setSelectedIndex(currentTroll.getRace());
		attaque.setValue(currentTroll.getAttaque());
		degats.setValue(currentTroll.getDeguat());
		esquive.setValue(currentTroll.getEsquive());
		regeneration.setValue(currentTroll.getRegeneration());
		compet1.setValue(currentTroll.getCompetence1());
		compet2.setValue(currentTroll.getCompetence2());
	}
	
	/**
	 * Déclencheur de l'évenement au clique sur troll a éditer de l'onglet editeur de troll
	 * @param event
	 */
	@UiHandler("trollsToAdd")
	void onTrollsToAddClick(ClickEvent event) {
		int idInTrollList = currentTrollsId.get(trollsToAdd.getSelectedIndex());
		initEditor(idInTrollList);
		elemOnEdit = trollsToAdd.getSelectedIndex();
	}
	
	
	/**
	 * Déclencheur de l'évenement à la selection d'un troll de l'onglet listing des trolls
	 * @param event
	 */
	@UiHandler("listTrolls")
	void onListTrollsClick(ClickEvent event) {
		int trollIndex = listTrolls.getSelectedIndex();
		/* Modification des données du tableau en fonction du troll selectionné */
		dataProvider.refresh();
		dataProvider = new ListDataProvider<Troll>();    
		dataProvider.addDataDisplay(cellTable);

		List<Troll> list = dataProvider.getList();
		list.add(trollList.get(trollIndex));
	}
	
	/**
	 * Fonction qui retourne les information au format html 
	 * en fonction du troll passé en paramètre
	 * @param troll
	 * @return
	 */
	String displayTroll(Troll troll) {
		String nom = troll.getNom();
		String race = getNameRace(troll.getRace());
		int attaque = troll.getAttaque();
		int degats = troll.getDeguat();
		int esquive = troll.getEsquive();
		int regen = troll.getRegeneration();
		int pointdevie = troll.getPointdevie();
		int compet1 = troll.getCompetence1();
		int compet2 = troll.getCompetence1();
		String infos = "Nom : "+nom+"<br>";
		infos += "Race : "+race+"<br>";
		infos += "Attaque : "+attaque+"<br>";
		infos += "Degats : "+degats+"<br>";
		infos += "Esquive : "+esquive+"<br>";
		infos += "Regeneration : "+regen+"<br>";
		infos += "Points de vie : "+pointdevie+"<br>";
		infos += "Compétence 1 : "+compet1+"<br>";
		infos += "Compétence 2 : "+compet2+"<br>";
		return infos;
	}
}
