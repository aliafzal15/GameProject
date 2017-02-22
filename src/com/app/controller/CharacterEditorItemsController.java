package com.app.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.menus.CharacterEditorItemsInventory;
import com.app.menus.CharacterEditorMainMenu;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.utilities.FileStorage;




/**
 * 
 *This Class holds all the logic for actions available on the Character Inventory Menu.
 *@author Ali Afzal
 * 
 *
 */
public class CharacterEditorItemsController {
	
	public CharacterEditorItemsInventory itemInv;
	private ArrayList <CharacterModel> characterList;
	private ArrayList<ItemsModel> allItems;
	private FileStorage itemStorage;
	private ItemsModel objItem;
	private ArrayList<ItemsModel> objWornItemsFighter;
	private ArrayList<ItemsModel> objBagItemsFighter;
	private ArrayList<ItemsModel> objWornItemsZombie;
	private ArrayList<ItemsModel> objBagItemsZombie;
	private String plyrType;
	private int removeIndex;
	private int removeBagIndex;
	private int plyrIndex;
	private CharacterEditorMainMenu tempMainMenu;
	private int armorClassEnchanment;
	private int strengthClassEnchanment;
	private int attackBonusClassEnchanment;
	
	/**
	 * 
	 *This Constructor holds all the action listeners for Item Inventory Menu
	 *
	 *@param characterLists
	 *			ArrayList of all characters in the file and their properties
	 *@param objIndex
	 *			index in the ArrayList which holds the character properties 
	 *@param plyrType
	 *			Type of the character
	 *@param mainMenu
	 *			CharacterEditorMainMenu object to manipulate GUI elements
	 */
	public CharacterEditorItemsController(final ArrayList <CharacterModel> characterLists,final int objIndex,String plyrType,CharacterEditorMainMenu mainMenu){
		
		this.characterList=characterLists;
		this.characterList.get(objIndex).setArrayWorn();
		this.characterList.get(objIndex).setArrayBag();
		this.objBagItemsFighter=new ArrayList();
		this.objWornItemsFighter=new ArrayList();
		this.objWornItemsZombie=new ArrayList();
		this.objBagItemsZombie=new ArrayList();
		this.plyrType=plyrType;
		this.plyrIndex=objIndex;
		itemInv= new CharacterEditorItemsInventory(new JFrame());
		this.tempMainMenu=mainMenu;		
		getItemsFromFile();	
		populateAllItemsCombo();
		populateWornItemsCombo(objIndex);
		populateBagItemsCombo(objIndex);
		//this.characterList.get(objIndex).setWornItemList(objWornItemsFighter);
		
		this.armorClassEnchanment=initializeEnchanementClass("ArmorClass");
		this.strengthClassEnchanment=initializeEnchanementClass("StrengthClass");
		this.attackBonusClassEnchanment=initializeEnchanementClass("AttackBonusClass");
		
		
		
		itemInv.btnAddItemWorn.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					
				if(checkIfItemInWorn(objWornItemsFighter) && "Fighter".equals(getPlayerType())){
					
					String temp=(String) itemInv.comboItemsAll.getSelectedItem();
					
					String[] item=temp.split(":");
					
					objItem=new ItemsModel();
					objItem.itemType=item[0];
					objItem.itemBonus=item[1];
					
				
					
					
				    //characterList.get(objIndex).setWornItemList(objWornItemsFighter);
				    //objWornItemsFighter.add(objItem);
					//characterList.get(objIndex).setArrayWorn();
					
					
					if(characterList.get(objIndex).setWornItem(objItem)){
						
						setEnchanementValues(item[0],item[1],"Add");									
						}
					
					objWornItemsFighter=characterList.get(objIndex).getWornItems();
					
					
					
					String charType=characterList.get(objIndex).getCharType();
					
					itemStorage=new FileStorage();
					
					try {
						if("Fighter".equals(getPlayerType())){
						  itemStorage.saveWornItemDetailsFighter(characterList.get(objIndex).getWornItems());
						  }
						
						
						
						} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					//11 feb itemInv.comboItemsWorn.addItem(objItem.itemType+":"+objItem.itemBonus);
					render();
					
					objItem=null;
					
										
					
				}//if(checkIfItemInWorn(objWornItemsFighter))
				
				else if(checkIfItemInWorn(objWornItemsZombie) && "Zombie".equals(getPlayerType())){
					
					String temp=(String) itemInv.comboItemsAll.getSelectedItem();
					
					String[] item=temp.split(":");
					
					objItem=new ItemsModel();
					objItem.itemType=item[0];
					objItem.itemBonus=item[1];
					
					
				    //characterList.get(objIndex).setWornItemList(objWornItemsFighter);
				    //objWornItemsFighter.add(objItem);
					//characterList.get(objIndex).setArrayWorn();
					
					
					if(characterList.get(objIndex).setWornItem(objItem)){
						
						setEnchanementValues(item[0],item[1],"Add");		
						
											
					}
					
					objWornItemsFighter=characterList.get(objIndex).getWornItems();
					
					
					
					String charType=characterList.get(objIndex).getCharType();
					
					itemStorage=new FileStorage();
					
					try {
						if("Zombie".equals(getPlayerType())){
						  itemStorage.saveWornItemDetailsZombie(characterList.get(objIndex).getWornItems());
						  }
						
						
						
						} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					//11 feb itemInv.comboItemsWorn.addItem(objItem.itemType+":"+objItem.itemBonus);
					render();
					
					objItem=null;
					
										
					
				}
			
				
			  }
								
	        });	
		
		itemInv.btnRmvWornItem.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				removeItemWorn();
				//characterList.get(objIndex).setWornItemList(objWornItemsFighter);	
				
				render();
								
			}
        });	
		
		itemInv.btnAddItemBag.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				
					
					String temp=(String) itemInv.comboItemsAll.getSelectedItem();
					
					String[] item=temp.split(":");
					
					objItem=new ItemsModel();
					objItem.itemType=item[0];
					objItem.itemBonus=item[1];
					
					
					
					//characterList.get(objIndex).setArrayBag();
					
					//characterList.get(objIndex).setBagItemList(objBagItemsFighter);
				   
					//objWornItemsFighter.add(objItem);
					//characterList.get(objIndex).setArrayBag();
					boolean addInComboBag=characterList.get(objIndex).setBagItem(objItem);
					
					if("Fighter".equals(getPlayerType())){
					objBagItemsFighter=characterList.get(objIndex).getBagItems();
					}
					
					else{
						
						objBagItemsZombie=characterList.get(objIndex).getBagItems();
						
					}
					
					
					//String charType=characterList.get(objIndex).getCharType();
					
					itemStorage=new FileStorage();
					
					try {
						if("Fighter".equals(getPlayerType())){
						  itemStorage.saveBagItemDetailsFighter(characterList.get(objIndex).getBagItems());
						  }
						
						else if("Zombie".equals(getPlayerType())) {
							
							itemStorage.saveBagItemDetailsZombie(characterList.get(objIndex).getBagItems());
						}
						
						
						
						} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
					
					render();
					objItem=null;
												
			}
        });	
		
		itemInv.btnRmvItemBag.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				removeItemBag();
				//characterList.get(objIndex).setBagItemList(objBagItemsFighter);
				
				render();			
			}
        });	
		
		itemInv.btnAddWornToBag.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				addWornToBag(objIndex,characterList.get(objIndex).getCharType());							
				render();			
			}
        });	
		
		itemInv.btnAddBagToWorn.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
			if(checkIfBagItemInWorn(objWornItemsFighter) && "Fighter".equals(getPlayerType())){
				
				String temp=(String) itemInv.comboBagPack.getSelectedItem();
				
				String[] item=temp.split(":");
				
				objItem=new ItemsModel();
				objItem.itemType=item[0];
				objItem.itemBonus=item[1];
				
				
			    // characterList.get(objIndex).setWornItemList(objWornItemsFighter);
			    //objWornItemsFighter.add(objItem);
				//characterList.get(objIndex).setArrayWorn();
				
				
				
				if(characterList.get(objIndex).setWornItem(objItem)){
					
					setEnchanementValues(item[0],item[1],"Add");		
													
				}
				
				objWornItemsFighter=characterList.get(objIndex).getWornItems();
				
				objBagItemsFighter.remove(removeBagIndex);
				
				String charType=characterList.get(objIndex).getCharType();
				
				itemStorage=new FileStorage();
				
				try {
					if("Fighter".equals(getPlayerType())){
					  itemStorage.saveWornItemDetailsFighter(characterList.get(objIndex).getWornItems());
					  itemStorage.saveBagItemDetailsFighter(characterList.get(objIndex).getBagItems());
					}
					
					
					
					} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				//11 feb itemInv.comboItemsWorn.addItem(objItem.itemType+":"+objItem.itemBonus);
				render();
				
				objItem=null;
				
									
				
			}//if(checkIfItemInWorn(objWornItemsFighter))
			else if(checkIfBagItemInWorn(objWornItemsZombie) && "Zombie".equals(getPlayerType())){
				
				String temp=(String) itemInv.comboBagPack.getSelectedItem();
				
				String[] item=temp.split(":");
				
				objItem=new ItemsModel();
				objItem.itemType=item[0];
				objItem.itemBonus=item[1];
				
				
			    // characterList.get(objIndex).setWornItemList(objWornItemsFighter);
			    //objWornItemsFighter.add(objItem);
				//characterList.get(objIndex).setArrayWorn();
				
				
				if(characterList.get(objIndex).setWornItem(objItem)){
					
					setEnchanementValues(item[0],item[1],"Add");		
											
				}
				
				
				objWornItemsZombie=characterList.get(objIndex).getWornItems();
				
				objBagItemsZombie.remove(removeBagIndex);
				
				String charType=characterList.get(objIndex).getCharType();
				
				itemStorage=new FileStorage();
				
				try {
					if("Zombie".equals(getPlayerType())){
					  itemStorage.saveWornItemDetailsZombie(characterList.get(objIndex).getWornItems());
					  itemStorage.saveBagItemDetailsZombie(characterList.get(objIndex).getBagItems());
					}
					
										
					} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
								
				//11 feb itemInv.comboItemsWorn.addItem(objItem.itemType+":"+objItem.itemBonus);
				render();
				
				objItem=null;			
		
			}
			
		  }
						
        });	
		
				
	}
	
	
	
	
	
	
	
/**
 * Default Constructor
 */	
public CharacterEditorItemsController() {
		// TODO Auto-generated constructor stub
	}





/**
 * This Method Sets all Items Combo
 */
private void setAllItemsCombo(){
		
	
}

/**
 * 
 *This Method reads items from the items file
 *
 */	
public void getItemsFromFile(){
	
	itemStorage=new FileStorage();
	try {
		
		
		this.allItems=itemStorage.ReadItemInFile();
		System.out.println("XXXXXXXXsize of file all items in inventory: "+allItems.size());
			
    	itemStorage=null;
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
}
	
/**
 * 
 *This Method populates All Items Combo 
 *
 *				
 */
public void populateAllItemsCombo(){
	
	for(int i=0; i <this.allItems.size();i++){
		
		itemInv.comboItemsAll.addItem(allItems.get(i).itemType+":"+allItems.get(i).itemBonus);
		
	}
}

/**
 * 
 *This Method populates Worn Items Combo
 *@param objInx
 *			index of the character object in characterLists ArrayList
 *				
 */
public void populateWornItemsCombo(int objInx) {
	

	itemStorage=new FileStorage();
	
if(this.plyrType.equals("Fighter")){
	
	//objWornItemsFighter=new ArrayList();
	
	try {
		objWornItemsFighter=itemStorage.readWornItemsFighter();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			if(objWornItemsFighter.size()>0){
	
					for(int i=0; i <this.objWornItemsFighter.size();i++){
		
					  itemInv.comboItemsWorn.addItem(objWornItemsFighter.get(i).itemType+":"+objWornItemsFighter.get(i).itemBonus);
		
				}
					
					this.characterList.get(objInx).setWornItemList(objWornItemsFighter);
	
			}//if(objWornItemsFighter.size()>0)
 
		}//if(this.plyrType.equals("Fighter"))

else if(this.plyrType.equals("Zombie")){
	
	
	try {
		objWornItemsZombie=itemStorage.readWornItemsZombie();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			if(objWornItemsZombie.size()>0){
	
					for(int i=0; i <this.objWornItemsZombie.size();i++){
		
					  itemInv.comboItemsWorn.addItem(objWornItemsZombie.get(i).itemType+":"+objWornItemsZombie.get(i).itemBonus);
		
				}
					
					this.characterList.get(objInx).setWornItemList(objWornItemsZombie);
	
			}//if(objWornItemsFighter.size()>0)
 
		}//if(this.plyrType.equals("Fighter"))

	}

/**
 * 
 *This Method checks if the item already in worn list or not
 *@param items
 *			list of items worn
 *				
 */
public boolean checkIfItemInWorn(ArrayList<ItemsModel> items){
	
	if(items.size()>0){
		
		String temp=(String) itemInv.comboItemsAll.getSelectedItem();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeIndex=i;
			  		 //JOptionPane.showMessageDialog (null, "Item Already in the Worn List!!!","Error", 
					//			JOptionPane.ERROR_MESSAGE);
			  		return false;
			  	}
			  	
			  		
			  
		  }
				
	}
	return true;
}

/**
 * 
 *This Method populates Worn Items Combo
 *@return plyrType: Type of the character
 *			
 *				
 */
public String getPlayerType(){
	return this.plyrType;
}


/**
 * 
 *This Method remove an item in worn combo and list
 *						
 */
public void removeItemWorn(){
	
	if(this.plyrType.equals("Fighter")){
		
				if(objWornItemsFighter.size()>0){
		
						for(int i=0; i <this.objWornItemsFighter.size();i++){
							
							if((checkIfItemInWornRmv(objWornItemsFighter)==false)){
								
							//11febitemInv.comboItemsWorn.removeItem(objWornItemsFighter.get(removeIndex).itemType+":"+objWornItemsFighter.get(removeIndex).itemBonus);	
							
							setEnchanementValues(objWornItemsFighter.get(removeIndex).itemType,objWornItemsFighter.get(removeIndex).itemBonus,"Sub");
							objWornItemsFighter.remove(removeIndex);
							characterList.get(this.plyrIndex).setWornItemList(objWornItemsFighter);
							
							try {
								itemStorage.saveWornItemDetailsFighter(characterList.get(this.plyrIndex).getWornItems());
								itemStorage.saveBagItemDetailsFighter(characterList.get(this.plyrIndex).getBagItems());
							
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							}
					}
		
				}//if(objWornItemsFighter.size()>0)
				
				else {
					
					 JOptionPane.showMessageDialog (null, "No Items to remove!!!","Error", 
								JOptionPane.ERROR_MESSAGE);
				}
	 
			}//if(this.plyrType.equals("Fighter"))
	
	else if(this.plyrType.equals("Zombie")){
		
		if(objWornItemsZombie.size()>0){

				for(int i=0; i <this.objWornItemsZombie.size();i++){
					
					if((checkIfItemInWornRmv(objWornItemsZombie)==false)){
						
					//11febitemInv.comboItemsWorn.removeItem(objWornItemsFighter.get(removeIndex).itemType+":"+objWornItemsFighter.get(removeIndex).itemBonus);	
						setEnchanementValues(objWornItemsZombie.get(removeIndex).itemType,objWornItemsZombie.get(removeIndex).itemBonus,"Sub");
						objWornItemsZombie.remove(removeIndex);
						characterList.get(this.plyrIndex).setWornItemList(objWornItemsZombie);
						
						try {
							itemStorage.saveWornItemDetailsZombie(characterList.get(this.plyrIndex).getWornItems());
							itemStorage.saveBagItemDetailsZombie(characterList.get(this.plyrIndex).getBagItems());
						
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
			}

		}//if(objWornItemsFighter.size()>0)
		
		else {
			
			 JOptionPane.showMessageDialog (null, "No Items to remove!!!","Error", 
						JOptionPane.ERROR_MESSAGE);
		}

	}
	
}

/**
 * 
 *This Method checks if item already in worn then remove the older entry and adds new entry
 *@param items
 *			list of items worn in items ArrayList
 *			
 *				
 */
public boolean checkIfItemInWornRmv(ArrayList<ItemsModel> items){
	
	if(items.size()>0){
		
		String temp=(String) itemInv.comboItemsWorn.getSelectedItem();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeIndex=i;
			  			  		
			  		
			  		
			  		return false;
			  	}
			  	
			  		
			  
		  }
				
	}
	return true;
}

/**
 * 
 *This Method populates Bag Pack Items Combo
 *@param objInx
 *			index of the character in the characterLists
 *						
 */
public void populateBagItemsCombo(int objInx) {
	

	itemStorage=new FileStorage();
	
if(this.plyrType.equals("Fighter")){
	
		//objBagItemsFighter=new ArrayList();
	
	try {
		objBagItemsFighter=itemStorage.readBagItemsFighter();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			if(objBagItemsFighter.size()>0){
	
					for(int i=0; i <this.objBagItemsFighter.size();i++){
		
					itemInv.comboBagPack.addItem(objBagItemsFighter.get(i).itemType+":"+objBagItemsFighter.get(i).itemBonus);
		
				}
					
				this.characterList.get(objInx).setBagItemList(objBagItemsFighter);	
	
			}//if(objWornItemsFighter.size()>0)
 
		}//if(this.plyrType.equals("Fighter"))

else if(this.plyrType.equals("Zombie")){
	
	//objBagItemsFighter=new ArrayList();

	try {
		objBagItemsZombie=itemStorage.readBagItemsZombie();
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}

		if(objBagItemsZombie.size()>0){

				for(int i=0; i <this.objBagItemsZombie.size();i++){
	
				itemInv.comboBagPack.addItem(objBagItemsZombie.get(i).itemType+":"+objBagItemsZombie.get(i).itemBonus);
	
			}
				
			this.characterList.get(objInx).setBagItemList(objBagItemsZombie);	

		}//if(objWornItemsFighter.size()>0)

	}//if(this.plyrType.equals("Fighter"))

	}

/**
 * 
 *This Method removes item in the Bag Pack
 *			
 *				
 */
public void removeItemBag(){
	
	if(this.plyrType.equals("Fighter")){
		
				if(objBagItemsFighter.size()>0){
		
	
							
							if((checkIfItemInBagRmv(objBagItemsFighter)==false)){
								
								//11febitemInv.comboBagPack.removeItemAt(removeBagIndex);
							
							objBagItemsFighter.remove(removeBagIndex);
							characterList.get(this.plyrIndex).setBagItemList(objBagItemsFighter);
							
							try {
								itemStorage.saveBagItemDetailsFighter(characterList.get(this.plyrIndex).getBagItems());
							
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							}
					
		
				}//if(objWornItemsFighter.size()>0)
				
				else {
					
					 JOptionPane.showMessageDialog (null, "No Items to remove!!!","Error", 
								JOptionPane.ERROR_MESSAGE);
				}
	 
			}//if(this.plyrType.equals("Fighter"))
	
	else if(this.plyrType.equals("Zombie")){
		
		if(objBagItemsZombie.size()>0){


					
					if((checkIfItemInBagRmv(objBagItemsZombie)==false)){
						
						//11febitemInv.comboBagPack.removeItemAt(removeBagIndex);
					objBagItemsZombie.remove(removeBagIndex);
					characterList.get(this.plyrIndex).setBagItemList(objBagItemsZombie);
					
					try {
						itemStorage.saveBagItemDetailsZombie(characterList.get(this.plyrIndex).getBagItems());
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					}
			

		}//if(objWornItemsFighter.size()>0)
		
		else {
			
			 JOptionPane.showMessageDialog (null, "No Items to remove!!!","Error", 
						JOptionPane.ERROR_MESSAGE);
		}

	}//if(this.plyrType.equals("Fighter"))
	
}


/**
 * 
 *This Method populates Worn Items Combo
 *@return plyrType: Type of the character
 *			
 *				
 */
public boolean checkIfItemInBagRmv(ArrayList<ItemsModel> items){
	
	if(items.size()>0){
		
		String temp=(String) itemInv.comboBagPack.getSelectedItem();
		//this.removeBagIndex= itemInv.comboBagPack.getSelectedIndex();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeBagIndex= i;
			  		return false;
			  	}
			  	
			  		
			  
		  }
				
	}
	return true;
}

/**
 * 
 *This Method adds item to worn from bag list
 *@param objInx
 *			index of the character in the characterLists
 *			
 *				
 */
public void addWornToBag(int objInx,String plyrType){
	
	
	
	String tempItem=(String) itemInv.comboItemsWorn.getSelectedItem();
	int comboInx=itemInv.comboItemsWorn.getSelectedIndex();
	
	String [] itmArr=tempItem.split(":");
	
	ItemsModel objItm=new ItemsModel();
	
	objItm.itemType=itmArr[0];
	objItm.itemBonus=itmArr[1];
	
	System.out.println("<><><><>Size of BagItems<><><><> "+this.characterList.get(objInx).getBagItems().size());
	
		if(plyrType.equals("Fighter")){
			
			if((this.characterList.get(objInx).setBagItem(objItm)==true)){
		
					//11feb itemInv.comboBagPack.addItem(tempItem);
					//11feb itemInv.comboItemsWorn.removeItemAt(comboInx);
					System.out.println("<><><><>Size of WornItems BeforeRemoval<><><><> "+this.objWornItemsFighter.size());
						if (checkIfItemInWornRmv(objWornItemsFighter)==false){
								setEnchanementValues(objWornItemsFighter.get(removeIndex).itemType,objWornItemsFighter.get(removeIndex).itemBonus,"Sub");
								this.objWornItemsFighter.remove(removeIndex);
							}
							System.out.println("<><><><>Size of WornItems AfterRemoval<><><><> "+ this.objWornItemsFighter.size());

							this.characterList.get(objInx).setWornItemList(this.objWornItemsFighter);
							this.objBagItemsFighter=this.characterList.get(objInx).getBagItems();
		
							try {
								itemStorage.saveWornItemDetailsFighter(characterList.get(objInx).getWornItems());
								itemStorage.saveBagItemDetailsFighter(characterList.get(objInx).getBagItems());
		
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				}	
		}
		
	
	
	else if(plyrType.equals("Zombie")){
			
			if((this.characterList.get(objInx).setBagItem(objItm)==true)){
		
		//11feb itemInv.comboBagPack.addItem(tempItem);
		//11feb itemInv.comboItemsWorn.removeItemAt(comboInx);
		System.out.println("<><><><>Size of WornItems BeforeRemoval<><><><> "+this.objWornItemsZombie.size());
		if (checkIfItemInWornRmv(objWornItemsZombie)==false){
		setEnchanementValues(objWornItemsZombie.get(removeIndex).itemType,objWornItemsZombie.get(removeIndex).itemBonus,"Sub");
		this.objWornItemsZombie.remove(removeIndex);
		}
		System.out.println("<><><><>Size of WornItems AfterRemoval<><><><> "+ this.objWornItemsZombie.size());

		this.characterList.get(objInx).setWornItemList(this.objWornItemsZombie);
		this.objBagItemsZombie=this.characterList.get(objInx).getBagItems();
		
		try {
			itemStorage.saveWornItemDetailsZombie(characterList.get(objInx).getWornItems());
			itemStorage.saveBagItemDetailsZombie(characterList.get(objInx).getBagItems());
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	  }
			
	}	

}


/**
 * 
 *This Method renders the Main Character Editor Menu
 *			
 *				
 */
public void render(){
	setAllCombosNull();
	populateAllItemsCombo();
	populateWornItemsCombo(getPlyrIndex());
	populateBagItemsCombo(getPlyrIndex());
	updateAblities();
}


/**
 * 
 *This Method returns the player index
 *@return plyrIndex			
 *				
 */
private int getPlyrIndex(){
	return this.plyrIndex;
}

/**
 * 
 *This Method sets all combos to Null
 *			
 *				
 */
public void setAllCombosNull(){
	
	this.itemInv.comboBagPack.removeAllItems();
	this.itemInv.comboItemsAll.removeAllItems();
	this.itemInv.comboItemsWorn.removeAllItems();
}

/**
 * 
 *This Method checks if bag item already in worn list
 *@param items
 *		   All items ArrayList				
 *				
 */
public boolean checkIfBagItemInWorn(ArrayList<ItemsModel> items){
	
if(items.size()>0){
		
		String temp=(String) itemInv.comboBagPack.getSelectedItem();
		
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeBagIndex=i;
			  		//JOptionPane.showMessageDialog (null, "Item Already in the Worn List!!!","Error", 
					//		JOptionPane.ERROR_MESSAGE);
			  		return false;
			  	}			  				  
		  }
				
	}
	return true;
		
}

/**
 * 
 *This Method updates all ability scores
 *			
 *				
 */
public void updateAblities(){
	
	try {
		  FileStorage objFSO=new FileStorage();
		  objFSO.SaveCharInFile(characterList);
		  objFSO=null;
		  
	  } catch (IOException e1) {
	
		e1.printStackTrace();
	
	  }	 
	
	if(plyrType.equals("Fighter")){
	
	tempMainMenu.lblfStrengthVal.setText(Integer.toString(this.characterList.get(plyrIndex).getStrength()));
	tempMainMenu.lblfArmorClassVal.setText(Integer.toString(this.characterList.get(plyrIndex).getArmorClass()));
	tempMainMenu.lblfAttackBonusVal.setText(Integer.toString(this.characterList.get(plyrIndex).getAttackBonus()));
	}
	
	else if(plyrType.equals("Zombie")){
		
		tempMainMenu.lblzStrengthVal.setText(Integer.toString(this.characterList.get(plyrIndex).getStrength()));
		tempMainMenu.lblzArmorClassVal.setText(Integer.toString(this.characterList.get(plyrIndex).getArmorClass()));
		tempMainMenu.lblzAttackBonusVal.setText(Integer.toString(this.characterList.get(plyrIndex).getAttackBonus()));
		
	}
}

public int getItemEnchanement(String itmType){
	
	if(plyrType.equals("Zombie")){
	
		for(int i=0;i<objWornItemsZombie.size();i++){
			if(objWornItemsZombie.get(i).itemType.equals(itmType)){
				return Integer.parseInt(objWornItemsZombie.get(i).itemBonus);
			}
		}
	}
	
	if(plyrType.equals("Fighter")){
		
		for(int i=0;i<objWornItemsFighter.size();i++){
			if(objWornItemsFighter.get(i).itemType.equals(itmType)){
				return Integer.parseInt(objWornItemsFighter.get(i).itemBonus);
			}
		}
	}
	
	return 0;

}

/**
 * 
 *This Method gets the enchantment class of the item
 *@param Type
 *			Item Type			
 *@return the class to be increased
 */
public String getEnchanementType(String Type){
	
	String classType;
	
	
	 switch(Type) {
     case "Helmet" :
    	 classType= "ArmorClass"; 
        break;
     case "Armor" :
    	 classType= "ArmorClass";
     case "Shield":
    	 classType= "ArmorClass";
        break;
     case "Ring" :
    	 classType= "ArmorClass";
     case "Boots" :
    	 classType= "ArmorClass";
        break;
        
     case "Weapon":
    	 classType= "AttackBonusClass";
    	 break;
    	 
     case "Belt":
    	 classType="StrengthClass";
    	 break;
         
     default :
    	 classType="";
  }
	
	 	return classType;
	
	}

/**
 * 
 *This Method initializes the enchantment class
 *@param Type
 *			Item Type			
 *@return enchm: the enchantment value
 */
private int initializeEnchanementClass(String Type){
	
	int enchm=0;
	
	
	 switch(Type) {
	 
     case "ArmorClass" :
    		int enchHelmet=getItemEnchanement("Helmet");
    		int enchArmor=getItemEnchanement("Armor");
    		int enchShield=getItemEnchanement("Shield");
    		int enchRing=getItemEnchanement("Ring");
    		int enchBoots=getItemEnchanement("Boots");
    		
    		int totalArmorModifier=enchHelmet+enchArmor+enchShield+enchRing+enchBoots;
    		enchm=totalArmorModifier; 
    		break;
        
        
     case "AttackBonusClass" :
    	  enchm=characterList.get(this.plyrIndex).getAttackBonus();
    	  break;
    	 
    	 
     case "StrengthClass":
    	 enchm=characterList.get(this.plyrIndex).getStrength();
        break;
     
         
     default :
    	 return enchm;
  }
	
	 	return enchm;
	
	}

/**
 * 
 *This Method sets the enchantment value of the object Armor,Strength or Attack Bonus according to enchantment class
 *@param type
 *			Item type	
 *@param Opr		
 *		 Operation to be performed either add or subtract
 */
public void setEnchanementValues(String type,String bonus,String Opr){
	
	
	switch(Opr){
	
	case "Add":
	
	if(getEnchanementType(type).equals("ArmorClass")){
		
		armorClassEnchanment=armorClassEnchanment+Integer.parseInt(bonus);
		this.characterList.get(this.plyrIndex).increaseArmorClass(armorClassEnchanment);	
		
		
		 
		
	
	}
	
	else if(getEnchanementType(type).equals("StrengthClass")){
		
		strengthClassEnchanment=strengthClassEnchanment+Integer.parseInt(bonus);		
		this.characterList.get(this.plyrIndex).increaseStrength(strengthClassEnchanment);
		
		
	}
	else if(getEnchanementType(type).equals("AttackBonusClass")){
		
		attackBonusClassEnchanment=attackBonusClassEnchanment+Integer.parseInt(bonus);
		this.characterList.get(this.plyrIndex).increaseAttackBonus(attackBonusClassEnchanment);
		
		
	}
	
	break;
	
	
	case "Sub":
		
		if(getEnchanementType(type).equals("ArmorClass")){
			
			armorClassEnchanment=armorClassEnchanment-Integer.parseInt(bonus);
			this.characterList.get(this.plyrIndex).increaseArmorClass(armorClassEnchanment);	
		
		}
		
		else if(getEnchanementType(type).equals("StrengthClass")){
			
			strengthClassEnchanment=strengthClassEnchanment-Integer.parseInt(bonus);
			this.characterList.get(this.plyrIndex).increaseStrength(strengthClassEnchanment);
			
		}
		else if(getEnchanementType(type).equals("AttackBonusClass")){
			
			attackBonusClassEnchanment=attackBonusClassEnchanment-Integer.parseInt(bonus);
			this.characterList.get(this.plyrIndex).increaseAttackBonus(attackBonusClassEnchanment);
			
		}
		
		break;
		
	
	}
	
}

/**
 * 
 *This Method checks if item in worn list		
 *@param items
 *		   items worn list.
 *@param temp
 *			combo item to be compared
 *@return boolean : False, if found. True, if not found.
 */
public boolean checkIfItemInWornList(ArrayList<ItemsModel> items,String temp){
			
		  for(int i=0;i<items.size();i++){
			  		String tempVal=items.get(i).itemType+":"+items.get(i).itemBonus;
			  	if(tempVal.equals(temp)){
			  		this.removeIndex=i;
			  		 //JOptionPane.showMessageDialog (null, "Item Already in the Worn List!!!","Error", 
					//			JOptionPane.ERROR_MESSAGE);
			  		return false;
			  	}
			  			  
		  }
				

	return true;
}

/**
 * 
 *This Method sets the frame possible
 *@param val
 *		True to set visible.
 */
public void setFrameVisbile(boolean val){
	itemInv.frame.setVisible(val);
	
}



}
