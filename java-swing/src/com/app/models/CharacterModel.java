package com.app.models;

import java.math.RoundingMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * This class is for the Character Model
 * 
 * @author Ali Afzal
 *
 */
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

/**
 * This Constructor creates a new character with the given character type.
 * 
 * @param charType
 * 			  Character Type
 *
 */
	public CharacterModel(String charType){
		
		this.isFirstCreation=true;
		this.charType=charType;
		this.plyrLevel=1;
		generateAbilityScores();
		generateModifiers();
		calculateHitPoints();
		calculateAttackBonus();
		calculateDamageBonus();
		this.setCharLevel(1);
		this.isEditable=true;
		this.setArrayWorn();
		this.setArrayBag();
		this.plyrArmorClass=0;
		
	}

	/**
	 * Default Constructor
	 */
	public CharacterModel() {
		
	}
	
	/**
	 * This Method calculates a value of 3d6 and return the total value.
	 * 
	 * @return total
	 * 			  sum of of 3d6
	 *
	 */
	private int generate3d6(){
	
	Random rand = new Random();
	int total=0;
	int d1= rand.nextInt(6) + 1;
	int d2=rand.nextInt(6) + 1;
	int d3=rand.nextInt(6) + 1;
	
	total=d1+d2+d3;
	
	return total;
	
	
}
	
	/**
	 * This Method calculates the value for attack bonus.
	 *
	 */
	public void calculateAttackBonus(){			
			this.attackBonus=this.plyrLevel;		
	}
	
	
	/**
	 * This Method calculates the damage bonus.
	 *
	 */
public void calculateDamageBonus(){
	
	this.damageBonus=this.getStrMod();
		
}
	
/**
 * This Method generates the ability scores by calling the generate3d6 function.
 *
 */
public void generateAbilityScores(){
	
	this.plyrStrength=generate3d6();
	this.plyrDexterity=generate3d6();
	this.plyrConstitution=generate3d6();
	
}


/**
 * This Method the value of the Armor Class
 *
 */
public void calculateArmorClass(){
			
}

/**
 * This Method sets the value for the armor class.
 * 
 * @param armr
 * 			value of armor
 *
 */
public void setArmorClass(int armr){
	this.plyrArmorClass=armr;
}


/**
 * This Method gets the value for the armor class.
 * 
 * @return armr
 * 			
 *
 */
public int getArmorClass(){
	
return this.plyrArmorClass;
		
}

/**
 * This Method generates the ability modifiers by calling the calculate modifier funtion.
 * 			
 *
 */
public void generateModifiers(){
	
	this.strModifier=calculateModifier(this.plyrStrength);
	this.dexModifier=calculateModifier(this.plyrDexterity);
	this.constModifier=calculateModifier(this.plyrConstitution);
	
}


/**
 * This Method gets the value of character's strength.
 * 	
 * @return plyrStrength		
 *
 */
public int getStrength(){
	
	return this.plyrStrength;
	
}


/**
 * This Method gets the value of character's Dexterity.
 * 	
 * @return plyrDexterity	
 *
 */
public int getDexterity(){
	
	return this.plyrDexterity;
	
}

/**
 * This Method gets the value of character's Constitution.
 * 	
 * @return plyrConstitution
 *
 */
public int getConstitution(){
	
	return this.plyrConstitution;
	
}

/**
 * This Method gets the value of character's type.
 * 	
 * @return charType
 *
 */
public String getCharType(){
	
	return this.charType;
	
}

/**
 * This Method sets the value of character's level.
 * 	
 * @param level
 * 			level of the character
 *
 */
public void setCharLevel(int level){
	
	this.plyrLevel=level;
	
}


/**
 * This Method sets the value of Strength Modifier.
 * 	
 * @param mod
 * 			Strength Modifier Value
 */
public void setStrMod(int mod){
	
	this.strModifier=mod;
	
}

/**
 * This Method sets the value of Strength.
 * 	
 * @param str
 * 			Strength Value
 */
public void setStrength(int str){
	
	this.plyrStrength=str;
	
}

/**
 * This Method sets the value of Dexterity.
 * 	
 * @param dex
 * 			Dexterity Value
 */
public void setDexterity(int dex){
	
	this.plyrDexterity=dex;
	
}

/**
 * This Method sets the value of Dexterity Modifier.
 * 	
 * @param mod
 * 			Dexterity Modifier Value
 */
public void setDexMod(int mod){
	
	this.dexModifier=mod;
	
}

/**
 * This Method sets the value of Constitution.
 * 	
 * @param cons
 * 			Constitution Value
 */
public void setConstitution(int cons){
	
	this.plyrConstitution=cons;
	
}


/**
 * This Method sets the value of Constitution Modifier.
 * 	
 * @param mod
 * 			Dexterity Modifier Value
 */
public void setConsMod(int mod){
	
	this.constModifier=mod;
	
}


/**
 * This Method sets the Hit Points.
 * 	
 * @param points
 * 			Hit Points Value
 */
public void setHitPoints(int points){
	
	this.plyrHitPoints=points;
	
}


/**
 * This Method sets the attack bonus.
 * 	
 * @param points
 * 			Attack Bonus Value
 */
public void setAttackBonus(int points){
	
	this.attackBonus=points;
	
}

/**
 * This Method sets the damage bonus.
 * 	
 * @param points
 * 			Damage Bonus Value
 */
public void setDamageBonus(int points){
	
	this.damageBonus=points;
	
}

/**
 * This Method sets the type of the character.
 * 	
 * @param type
 * 			Type of the character
 */
public void setCharType(String type){
	
	this.charType=type;
	
}


/**
 * This Method g0ets the level of the character.
 * 	
 * @return plyrLevel
 * 		
 */
public int getCharLevel(){
	
	return this.plyrLevel;
	
}

/**
 * This Method gets the level of the character.
 * 	
 * @return plyrLevel
 * 		
 */
public int getDexMod(){
	
	return this.dexModifier;
	
}

/**
 * This Method gets the strength modifier.
 * 	
 * @return Stregth Modifier
 * 		
 */
public int getStrMod(){
	
	return this.strModifier;
	
}

/**
 * This Method gets the Constitution modifier.
 * 	
 * @return Constitution Modifier
 * 		
 */
public int getConstMod(){
	
	return this.constModifier;
	
}

/**
 * This Method gets the Hit Points.
 * 	
 * @return  Hit Points
 * 		
 */
public int getHitPoints(){
	
	return this.plyrHitPoints;
	
}

/**
 * This Method gets the attack bonus.
 * 	
 * @return Attack Bonus
 * 		
 */
public int getAttackBonus(){
	
	return this.attackBonus;
	
}

/**
 * This Method gets the damage bonus.
 * 	
 * @return Damage Bonus
 * 		
 */
public int getDamageBonus(){
	
	return this.damageBonus;
}

/**
 * This Method calculates Hit Points.
 * 		
 */
public void calculateHitPoints(){
	
	int level=this.getCharLevel();
	int oneD10=generate1d10();
	int consMod=this.getConstMod();
	int total=(level*oneD10)+consMod;
	this.plyrHitPoints=total;
	
}

/**
 * This Method calculates a value of a modifier
 * 
 * @return Ability Modifier
 * 		
 */
public int calculateModifier(int score){
	
	DecimalFormat df = new DecimalFormat("###");
	df.setRoundingMode(RoundingMode.DOWN);
	double a=((score/2)-5);
	df.format(a);
	int abilityModifier=Integer.parseInt(df.format(a));
	return 	abilityModifier;
	
}

/**
 * This Method calculates a value for 1d10
 * 
 * @return 1d10 value
 * 		
 */
private int generate1d10(){
	
Random rand = new Random();

int d1= rand.nextInt(10) + 1;

return d1;


}

/**
 * This Method sets the worn item for the character
 * 
 * @return true if added otherwise false if not added.
 * 		
 */
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
	
/**
 * This Method sets the worn item ArrayList with all items worn by the character
 * 
 * @param items
 * 			list of all items worn by the character.
 * 		
 */
public void setWornItemList(ArrayList<ItemsModel> items){
	
	this.plyrWornItems=items;
	
}

/**
 * This Method gets the worn item ArrayList with all items worn by the character
 * 
 * @return plyrWornItems: list of all items worn by the character.
 * 		
 */
public ArrayList<ItemsModel> getWornItems(){
	
	return this.plyrWornItems;
	
	
}

/**
 * This Method initializes the Worn Items ArrayList
 * 	
 */
public void  setArrayWorn(){
	
	if(this.plyrWornItems==null){
		this.plyrWornItems=new ArrayList();
	}	
}

/**
 * This Method initializes the Bag Pack Items ArrayList
 * 	
 */
public void  setArrayBag(){
	
	if(this.plyrItemsBagPack==null){
		this.plyrItemsBagPack=new ArrayList();
	}	
}

/**
 * This Method adds an item in the Bag Pack Items ArrayList
 * 
 * @param  obj
 * 			 item of data type ItemsModel
 * 	
 */
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

/**
 * This Method sets Bag Items ArrayList
 * 
 * @param  items
 * 			 ArrayList of type Items Model
 * 	
 */
public void setBagItemList(ArrayList<ItemsModel> items){
	
	this.plyrItemsBagPack=items;	
}


/**
 * This Method get Bag Items ArrayList
 * 
 * @return plyrItemsBagPack
 * 				ArrayList of items in the Bag Pack	
 */
public ArrayList<ItemsModel> getBagItems(){
	
	return this.plyrItemsBagPack;	
}


/**
 * This Method sets the armor class equal to new increased value.
 * 
 * @param  ench
 * 			 New Increased Value;
 * 	
 */
public void increaseArmorClass(int ench){
	this.plyrArmorClass=ench;
}

/**
 * This Method sets the Strength equal to new increased value.
 * 
 * @param  ench
 * 			 New Increased Value;
 * 	
 */
public void increaseStrength(int ench){
	this.plyrStrength=ench;
}

/**
 * This Method sets the Attack Bonus equal to new increased value.
 * 
 * @param  ench
 * 			 New Increased Value;
 * 	
 */
public void increaseAttackBonus(int ench){
	this.attackBonus=ench;
}



}
