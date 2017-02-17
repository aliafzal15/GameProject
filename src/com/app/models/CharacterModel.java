package com.app.models;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class CharacterModel {

private int plyrStrength;
private int plyrDexterity;
private int plyrConstitution;
private int plyrHitPoints;
private int plyrAttackBonus;
private int plyrDamageBonus;
private int plyrArmorClass;
private int plyrLevel;
private int strModifier;
private int dexModifier;
private int constModifier;
private int attackBonus;
private int damageBonus;
private ArrayList<ItemsModel> plyrWornItems;
private ArrayList<ItemsModel> plyrItemsBagPack;

private String charType;

private boolean isFirstCreation;
private boolean isEditable;



	public CharacterModel(String charType){
		
		this.isFirstCreation=true;
		this.charType=charType;
		generateAbilityScores();
		generateModifiers();
		calculateHitPoints();
		calculateAttackBonus();
		this.setCharLevel(1);
		this.isEditable=true;
		this.setArrayWorn();
		this.setArrayBag();
		this.plyrArmorClass=0;
		
	}



	public CharacterModel() {
		
	}



	private int generate3d6(){
	
	Random rand = new Random();
	int total=0;
	int d1= rand.nextInt(6) + 1;
	int d2=rand.nextInt(6) + 1;
	int d3=rand.nextInt(6) + 1;
	
	total=d1+d2+d3;
	
	return total;
	
	
}
	

public void calculateAttackBonus(){
		
		if (this.getCharLevel()==1){
			
			this.attackBonus=1;		
		}
		
		else {
			
			this.attackBonus=this.attackBonus+1;
		}
		
	}

public void calculateDamageBonus(){
	
	this.damageBonus=this.getStrMod();
		
}
	
public void generateAbilityScores(){
	
	this.plyrStrength=generate3d6();
	this.plyrDexterity=generate3d6();
	this.plyrConstitution=generate3d6();
	
}

public void calculateArmorClass(){
	

		
}


public void setArmorClass(int armr){
	this.plyrArmorClass=armr;
}

public int getArmorClass(){
	
return this.plyrArmorClass;
		
}


public void generateModifiers(){
	
	this.strModifier=calculateModifier(this.plyrStrength);
	this.dexModifier=calculateModifier(this.plyrDexterity);
	this.constModifier=calculateModifier(this.plyrConstitution);
	
}


public int getStrength(){
	
	return this.plyrStrength;
	
}

public int getDexterity(){
	
	return this.plyrDexterity;
	
}

public int getConstitution(){
	
	return this.plyrConstitution;
	
}

public String getCharType(){
	
	return this.charType;
	
}


public void setCharLevel(int level){
	
	this.plyrLevel=level;
	
}



public void setStrMod(int mod){
	
	this.strModifier=mod;
	
}

public void setStrength(int str){
	
	this.plyrStrength=str;
	
}

public void setDexterity(int dex){
	
	this.plyrDexterity=dex;
	
}


public void setDexMod(int mod){
	
	this.dexModifier=mod;
	
}

public void setConstitution(int cons){
	
	this.plyrConstitution=cons;
	
}

public void setConsMod(int mod){
	
	this.constModifier=mod;
	
}

public void setHitPoints(int points){
	
	this.plyrHitPoints=points;
	
}

public void setAttackBonus(int points){
	
	this.attackBonus=points;
	
}

public void setDamageBonus(int points){
	
	this.damageBonus=points;
	
}

public void setCharType(String type){
	
	this.charType=type;
	
}



public int getCharLevel(){
	
	return this.plyrLevel;
	
}

public int getDexMod(){
	
	return this.dexModifier;
	
}

public int getStrMod(){
	
	return this.strModifier;
	
}

public int getConstMod(){
	
	return this.constModifier;
	
}

public int getHitPoints(){
	
	return this.plyrHitPoints;
	
}
public int getAttackBonus(){
	
	return this.attackBonus;
	
}

public int getDamageBonus(){
	
	return this.damageBonus;
}


public void calculateHitPoints(){
	
	int level=this.getCharLevel();
	int oneD10=generate1d10();
	int consMod=this.getConstMod();
	int total=(level*oneD10)+consMod;
	this.plyrHitPoints=total;
	
}


public int calculateModifier(int score){
	
	DecimalFormat df = new DecimalFormat("###");
	df.setRoundingMode(RoundingMode.DOWN);
	double a=((score/2)-5);
	df.format(a);
	int abilityModifier=Integer.parseInt(df.format(a));
	return 	abilityModifier;
	
}

private int generate1d10(){
	
Random rand = new Random();

int d1= rand.nextInt(10) + 1;

return d1;


}


public boolean setWornItem(ItemsModel obj){
	
	if(this.plyrWornItems.size()<7){
	
			this.plyrWornItems.add(obj);
			return true;
	
	}
	
	else{
		
		JOptionPane.showMessageDialog (null,"Item could not be added.Worn Items Bag is Full.","Error !!!", 
				JOptionPane.ERROR_MESSAGE); 
		return false;
	}

}
	
public void setWornItemList(ArrayList<ItemsModel> items){
	
	this.plyrWornItems=items;
	
}



public ArrayList<ItemsModel> getWornItems(){
	
	return this.plyrWornItems;
	
	
}

public void  setArrayWorn(){
	
	if(this.plyrWornItems==null){
		this.plyrWornItems=new ArrayList();
	}
	
}

public void  setArrayBag(){
	
	if(this.plyrItemsBagPack==null){
		this.plyrItemsBagPack=new ArrayList();
	}
	
}

public boolean setBagItem(ItemsModel obj){
	
	if(this.plyrItemsBagPack.size()<10){
	
			this.plyrItemsBagPack.add(obj);
			return true;
	
	}
	
	else{
		
		JOptionPane.showMessageDialog (null,"Item could not be added.Items Bag Pack is Full.","Error !!!", 
				JOptionPane.ERROR_MESSAGE); 
		return false;
	}

}

public void setBagItemList(ArrayList<ItemsModel> items){
	
	this.plyrItemsBagPack=items;
	
}

public ArrayList<ItemsModel> getBagItems(){
	
	return this.plyrItemsBagPack;
	
	
}

public void increaseArmorClass(int ench){
	this.plyrArmorClass=ench;
}

public void increaseStrength(int ench){
	this.plyrStrength=ench;
}
public void increaseAttackBonus(int ench){
	this.attackBonus=ench;
}




}
