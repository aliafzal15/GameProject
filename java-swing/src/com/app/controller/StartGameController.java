package com.app.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import com.app.menus.PlayGameMenu;
import com.app.menus.RunTimeGameMenu;
import com.app.models.CampaignModel;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.models.MapModel;
import com.app.utilities.FileStorage;

public class StartGameController {
	
	public ArrayList<CharacterModel> gameCharacters;
	public ArrayList<ItemsModel> gameFighterWornItems;
	public ArrayList<ItemsModel> gameFighterBagItems;
	public ArrayList<ItemsModel> gameZombieWornItems;
	public ArrayList<ItemsModel> gameZombieBagItems;
	public ArrayList<ItemsModel> gameMapItems;
	public ArrayList<CharacterModel> hostileCharacters;
	public ArrayList<CharacterModel> friendlyCharacters;
	public int gamePlayGridSelection [][];
	public String plyrBtnTxt;
	public String enemyBtnTxt;
	public int friendlyBtnId;
	public int enemyBtnId;
	public String hostilePlyrType;
	public String friendlyPlyrType;
	public int _i;
	public int _j;
	public JButton initialButtonsState [][];
	public ArrayList<ItemsModel> gameCharWornItems;
	public ArrayList<ItemsModel> gameCharBagItems;
	public CharacterModel gamecharacter;
	public int mapIndex;

	public	StartGameController(CampaignModel camp,ArrayList<MapModel> maps, MapModel currMap,CharacterModel gameChar,RunTimeGameMenu playWindow,int mapInx) throws IOException{
	
		FileStorage objFSO=new FileStorage();
		
		gameCharacters=new ArrayList();
		gameFighterBagItems=new ArrayList();
		gameFighterWornItems=new ArrayList();
		gameZombieWornItems=new ArrayList();
		gameZombieBagItems=new ArrayList();
		gameMapItems=new ArrayList();
		hostileCharacters=new ArrayList();
		friendlyCharacters=new ArrayList();
		gamePlayGridSelection=currMap.getMapGridSelection();
		gameCharWornItems=new ArrayList();
		gameCharBagItems=new ArrayList<>();
		this.gamecharacter=gameChar;
		this.mapIndex=mapInx;
		
		gameCharacters=objFSO.readCharacterInFile();
		gameFighterWornItems=objFSO.readWornItemsFighter();
		gameZombieWornItems=objFSO.readWornItemsZombie();
		gameFighterBagItems=objFSO.readBagItemsFighter();
		gameZombieBagItems=objFSO.readBagItemsZombie();
		gameMapItems=objFSO.ReadItemInFile();
		
		this.initialButtonsState=playWindow.runGameButtons;
		
		setHostileFriendlyChars(currMap.getMapCharacters(),gamecharacter);
		setLevelAdaptability(friendlyCharacters, hostileCharacters,gamecharacter);
		setItemsArraysOfHostileFriendly(friendlyCharacters, hostileCharacters);	
		setPlayerLocTxt(gamecharacter);
		setPlayerOnEntryPoint(gamecharacter,currMap,gamePlayGridSelection,playWindow.runGameButtons);
		
		gamecharacter.setWornItemList(gameCharWornItems);
		gamecharacter.setBagItemList(gameCharBagItems);
		
		 
		new RunTimeGameController(camp,maps,currMap,gamecharacter,playWindow,this,mapIndex);
	}

	public void setHostileFriendlyChars(ArrayList<CharacterModel> mapChars,CharacterModel mainPlyr){
		
			for(int i=0;i<mapChars.size();i++){
				
				if(mapChars.get(i).getCharType().equals(mainPlyr.getCharType())){
					this.friendlyCharacters.add(mapChars.get(i));
					
				}
				else{
					this.hostileCharacters.add(mapChars.get(i));
					 
				}
			}		
	}
	
	
	public void setLevelAdaptability(ArrayList<CharacterModel> friendlyChars,
												ArrayList<CharacterModel> hostileChars, CharacterModel mainPlyr){
		
				if(friendlyChars.size()>0){
					 for(int i=0;i<friendlyChars.size();i++){
						 	friendlyChars.get(i).setCharLevel(mainPlyr.getCharLevel());
						 	friendlyChars.get(i).setArrayWorn();
						 	friendlyChars.get(i).setArrayBag();
					 }
				}
				
				if(hostileChars.size()>0){
					 for(int i=0;i<hostileChars.size();i++){
						 hostileChars.get(i).setCharLevel(mainPlyr.getCharLevel());
						 hostileChars.get(i).setArrayWorn();
						 hostileChars.get(i).setArrayBag();
					 }
				}				
				
	}
	
	
	public void setHostFriendEnchanements(CharacterModel mainPlyr, ArrayList<ItemsModel> plyrItems){
		
			if(mainPlyr.getCharLevel()<5){			
				setEnchanement(plyrItems, 1);			
			} else if (mainPlyr.getCharLevel()<9){
				setEnchanement(plyrItems, 2);
			} else if (mainPlyr.getCharLevel()<13){
				setEnchanement(plyrItems, 3);
			} else if (mainPlyr.getCharLevel()<17){
				setEnchanement(plyrItems, 4);
			}else{
				setEnchanement(plyrItems, 5);
			}
	}
	
	public void setItemsArraysOfHostileFriendly(ArrayList<CharacterModel> friendChars, ArrayList<CharacterModel> hostChars){
		
			if(friendChars.size()>0){
				 
					 for(int i=0;i<friendChars.size();i++){
						  if(friendChars.get(0).getCharType().equals("Fighter")){
						  	friendChars.get(i).setWornItemList(gameFighterWornItems);
						  	friendChars.get(i).setBagItemList(gameFighterBagItems);
						  	setHostFriendEnchanements(friendChars.get(i),friendChars.get(i).getWornItems());
						  	
						  }	else{
								 friendChars.get(i).setWornItemList(gameZombieWornItems);
								 friendChars.get(i).setBagItemList(gameZombieBagItems);
						  }		 setHostFriendEnchanements(friendChars.get(i),friendChars.get(i).getWornItems());		 	
				     } 
	     	}
			
			if(hostChars.size()>0){
				 
				 for(int i=0;i<hostChars.size();i++){
					  if(hostChars.get(0).getCharType().equals("Fighter")){
					  	hostChars.get(i).setWornItemList(gameFighterWornItems);
					  	hostChars.get(i).setBagItemList(gameFighterBagItems);
					  	setHostFriendEnchanements(hostChars.get(i),hostChars.get(i).getWornItems());
					  }	else{
							 hostChars.get(i).setWornItemList(gameZombieWornItems);
							 hostChars.get(i).setBagItemList(gameZombieBagItems);
							 setHostFriendEnchanements(hostChars.get(i),hostChars.get(i).getWornItems());
					  }				 	
			     } 
    	}
					
  }

 public void setEnchanement(ArrayList<ItemsModel> plyrItems,int newEnch){
	 
	 if(plyrItems.size()>0){
		 	for(int i=0;i<plyrItems.size();i++){
		 			plyrItems.get(i).itemBonus=Integer.toString(newEnch);
		 	}
	 }
	 
 }
 
public void setPlayerOnEntryPoint(CharacterModel mainPlyr,MapModel curMap, int gameMapGrid [][],JButton gameBtns[][]){
	 
	
	
	 for (int i = 0; i < gameMapGrid.length; i++) {
			for (int j = 0; j < gameMapGrid.length; j++) {
				
					if (gameMapGrid[i][j]==4){
						
						gameBtns[i][j].setText(plyrBtnTxt);
						gameBtns[i][j].setBackground(Color.PINK);
						_i=i;
						_j=j;
					}
				
			}
	 }
	 
 }

public void setPlayerLocTxt(CharacterModel mainPlyr) throws IOException{
	
	FileStorage objFSO=new FileStorage();
	
	if(mainPlyr.getCharType().equals("Fighter")){
			plyrBtnTxt="FP";
			enemyBtnId=3;
			friendlyBtnId=2;
			enemyBtnTxt="Z";
			hostilePlyrType="Zombie";
			friendlyPlyrType="Fighter";
			
		
			gameCharWornItems=objFSO.readWornItemsFighter();
			gameCharBagItems=objFSO.readBagItemsFighter();
			
		
			
	}
	else{
			plyrBtnTxt="ZP";
			enemyBtnId=2;
			enemyBtnTxt="F";
			friendlyBtnId=3;
			hostilePlyrType="Fighter";
			friendlyPlyrType="Zombie";
			
			gameCharWornItems=objFSO.readWornItemsZombie();
			gameCharBagItems=objFSO.readBagItemsZombie();
	}
	
	
}


 
	
}
