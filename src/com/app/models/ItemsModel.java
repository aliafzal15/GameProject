package com.app.models;

public class ItemsModel {
	
	public String itemType;
	public String itemBonus;
	
	public ItemsModel(String itemType,String itemBonus){
		this.itemType=itemType;
		this.itemBonus=itemBonus;
			
	}
	
	
public ItemsModel() {
		// TODO Auto-generated constructor stub
	}




private void setItemType(String itemType){
	this.itemType=itemType;
		
}

private String getItemType(String itemType){
	return this.itemType;
		
}

private void setItemBonus(String itemBonus){
	this.itemBonus=itemBonus;
		
}
	
private String getItemBonus(String itemBonus){
	return this.itemBonus;
		
}	
		

}
