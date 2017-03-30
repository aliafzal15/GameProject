package com.app.menus;



import com.app.menus.RunTimeIneventory;
import com.app.models.CharacterModel;
import com.app.observer.CharacterView;
import com.app.observer.ItemInventoryView;

public class InventoryViewDriver {
	
	public CharacterModel charViewDriver;
	public CharacterView  viewCharacter;
	public ItemInventoryView viewInventory;
	
	
	public InventoryViewDriver(CharacterModel newChar){
		
		charViewDriver=newChar;
		viewCharacter=new CharacterView(newChar);
		viewInventory=new ItemInventoryView(newChar);	
		charViewDriver.addObserver(viewInventory);
	}
	

}
