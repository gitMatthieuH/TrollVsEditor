package com.matthieuhostache.trollvseditor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Troll implements IsSerializable {
	
	private String nom;
	private int race;
	private int attaque;
	private int deguat;
	private int esquive;
	private int regeneration;
	private int pointdevie;
	private int competence1;
	private int competence2;
	
	public Troll() {
		
	}
	
	public Troll(String nom, int race, int attaque, int deguat, int esquive,
			int regeneration, int pointdevie, int comp1, int comp2) {
		super();
		this.nom = nom;
		this.race = race;
		this.attaque = attaque;
		this.deguat = deguat;
		this.esquive = esquive;
		this.regeneration = regeneration;
		this.pointdevie = pointdevie;
		this.competence1 = comp1;
		this.competence2 = comp2;
	}


	public int getCompetence1() {
		return competence1;
	}

	public void setCompetence1(int competence1) {
		this.competence1 = competence1;
	}

	public int getCompetence2() {
		return competence2;
	}

	public void setCompetence2(int competence2) {
		this.competence2 = competence2;
	}

	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public int getRace() {
		return race;
	}


	public void setRace(int race) {
		this.race = race;
	}


	public int getAttaque() {
		return attaque;
	}


	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}


	public int getDeguat() {
		return deguat;
	}


	public void setDeguat(int deguat) {
		this.deguat = deguat;
	}


	public int getEsquive() {
		return esquive;
	}


	public void setEsquive(int esquive) {
		this.esquive = esquive;
	}


	public int getRegeneration() {
		return regeneration;
	}


	public void setRegeneration(int regeneration) {
		this.regeneration = regeneration;
	}


	public int getPointdevie() {
		return pointdevie;
	}


	public void setPointdevie(int pointdevie) {
		this.pointdevie = pointdevie;
	}
}
