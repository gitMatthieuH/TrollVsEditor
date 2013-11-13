package com.matthieuhostache.trollvseditor.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.matthieuhostache.trollvseditor.shared.Troll;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.ChangeEvent;

public class EditorView extends Composite {

	private static EditorViewUiBinder uiBinder = GWT
			.create(EditorViewUiBinder.class);
	
	private ArrayList<Troll> trollList = new ArrayList<Troll>();
	
	private int caracPoints;
	private int caracSpePoints;

	interface EditorViewUiBinder extends UiBinder<Widget, EditorView> {
	}

	public EditorView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField FormPanel formPanel;
	@UiField TextBox name;
	@UiField IntegerBox attaque;
	@UiField IntegerBox degats;
	@UiField IntegerBox esquive;
	@UiField IntegerBox regeneration;
	@UiField IntegerBox pointdevie;
	@UiField ListBox race;
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

	public EditorView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		//savetrolls.setText(firstName);
		this.caracPoints = 50;
		this.caracSpePoints = 20;
	}
	
	@UiHandler("addtroll")
	void onAddtrollsClick(ClickEvent event) {
		Troll trollToSave = new Troll(name.getValue(),race.getSelectedIndex(),attaque.getValue(),degats.getValue(),esquive.getValue(),regeneration.getValue(),pointdevie.getValue(),compet1.getValue(),compet2.getValue());
		trollList.add(trollToSave);
		cellList.setRowCount(trollList.size(), true);
	    cellList.setRowData(0, trollList);
	}

	@UiHandler("savetrolls")
	void onSavetrollsClick(ClickEvent event) {
		formPanel.submit();
	}
	
	@UiHandler("formPanel")
	void onFormPanelSubmit(SubmitEvent event) {
		
		TrollvsEditor.get().sendTrollsInfosToServer(this.trollList);
		Window.alert("send !");
		
	}

	@UiHandler("attaquem")
	void onAttaquemClick(ClickEvent event) {
		if(this.caracPoints>0) {
			attaque.setValue(attaque.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("attaquep")
	void onAttaquepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			attaque.setValue(attaque.getValue()+1);
			this.caracPoints++;
		}
	}
	
	@UiHandler("degatsm")
	void onDegatsClick(ClickEvent event) {
		if(this.caracPoints>0) {
			degats.setValue(degats.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("degatsp")
	void onDegatspClick(ClickEvent event) {
		if(this.caracPoints<50) {
			degats.setValue(degats.getValue()+1);
			this.caracPoints++;
		}
	}
	
	@UiHandler("esquivem")
	void onEsquivemClick(ClickEvent event) {
		if(this.caracPoints>0) {
			esquive.setValue(esquive.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("esquivep")
	void onEsquivepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			esquive.setValue(esquive.getValue()+1);
			this.caracPoints++;
		}
	}
	
	@UiHandler("regenerationm")
	void onRegenerationmClick(ClickEvent event) {
		if(this.caracPoints>0) {
			regeneration.setValue(regeneration.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("regenerationp")
	void onRegenerationpClick(ClickEvent event) {
		if(this.caracPoints<50) {
			regeneration.setValue(regeneration.getValue()+1);
			this.caracPoints++;
		}
	}
	
	@UiHandler("pointdeviem")
	void onPointdeviemClick(ClickEvent event) {
		if(this.caracPoints>0) {
			pointdevie.setValue(pointdevie.getValue()-1);
			this.caracPoints--;
		}
	}
	
	@UiHandler("pointdeviep")
	void onPointdeviepClick(ClickEvent event) {
		if(this.caracPoints<50) {
			pointdevie.setValue(pointdevie.getValue()+1);
			this.caracPoints++;
		}
	}
	
	@UiHandler("compet1m")
	void onCompet1mClick(ClickEvent event) {
		if(this.caracSpePoints>0) {
			compet1.setValue(compet1.getValue()-1);
			this.caracSpePoints--;
		}
	}
	
	@UiHandler("compet1p")
	void onCompet1pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet1.setValue(compet1.getValue()+1);
			this.caracSpePoints++;
		}
	}
	
	@UiHandler("compet2m")
	void onCompet2mClick(ClickEvent event) {
		if(this.caracSpePoints>0) {
			compet2.setValue(compet2.getValue()-1);
			this.caracSpePoints--;
		}
	}
	
	@UiHandler("compet2p")
	void onCompet2pClick(ClickEvent event) {
		if(this.caracSpePoints<20) {
			compet2.setValue(compet2.getValue()+1);
			this.caracSpePoints++;
		}
	}
	
	@UiHandler("race")
	void onRaceChange(ChangeEvent event) {
		String picUrl;
		
		switch (race.getSelectedIndex())
		{
			
		  case 1:
			  picUrl = "img/hebus.jpg";
			  compet1name.setText("Botte Secrète");
			  compet2name.setText("Hypnotisme");
		    break;
		  case 2:
			  picUrl = "img/tinette.jpg";
			  compet1name.setText("Accélération Métabolique");
			  compet2name.setText("Accélération Psychique");
		    break;
		  case 3:
			  picUrl = "img/trollf.jpg";
			  compet1name.setText("Régénération Accrue");
			  compet2name.setText("Rafale Psychique");
		    break;
		  case 4:
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
}
