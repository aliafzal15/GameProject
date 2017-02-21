package com.app.models;


/**
 * This class is for Item Model
 * 
 * @author Ali Afzal
 *
 */
public class ItemsModel {
	
	public String itemType;
	public String itemBonus;
	
	
	/**
	 * This Constructor is for creating a new item with the given item type and item bonus.
	 * 
	 * @param itemType 
	 * 			Name of the item
	 * 
	 * @param itemBonus
	 * 			Bonus value that item possesses
	 * 
	 */	
	public ItemsModel(String itemType,String itemBonus){
		this.itemType=itemType;
		this.itemBonus=itemBonus;
			
	}
	
	/**
	 * Default Constructor
	 */	
	public ItemsModel() {
		
		}


	/**
	 * This Method sets the item type
	 * 
	 * @param itemType 
	 * 			Name of the item
	 * 
	 */	
	private void setItemType(String itemType){
		this.itemType=itemType;		
	}
	
	/**
	 * This Method gets the item type
	 * 
	 * @return itemType 
	 * 			
	 */	
	private String getItemType(String itemType){
		return this.itemType;
	}

	/**
	 * This Method sets the item bonus
	 * 
	 * @param itemBonus
	 * 			Bonus value of the item
	 * 
	 */	
	private void setItemBonus(String itemBonus){
		this.itemBonus=itemBonus;
	}
	
	/**
	 * This Method gets the item Bonus
	 * 
	 * @return itemBonus
	 * 			
	 */		
	private String getItemBonus(String itemBonus){
		return this.itemBonus;	
	}	
		
}
