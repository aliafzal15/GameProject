package com.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.app.menus.RunTimeFriendlyExchangeItems;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;

public class RunTimeFriendlyExchangeController {
	
	RunTimeFriendlyExchangeItems exchangeMenu;
	
	public RunTimeFriendlyExchangeController(String playerText,ArrayList<ItemsModel> plyrBagItems,
																		final ArrayList <ItemsModel> friendBagItems,final CharacterModel gameChar){
			
		exchangeMenu=new RunTimeFriendlyExchangeItems();
		
		exchangeMenu.lblPlyrItems.setText(playerText);
		
		populatePlayerItems(plyrBagItems);
		
		exchangeMenu.btnExchange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(friendBagItems.size()>0){
					exchangePlayerItems(gameChar,friendBagItems);
				}
				
				else{
					JOptionPane.showMessageDialog (null, "No Friendly Items to Exchange");
							
				}
				
				exchangeMenu.frameExchange.dispose();
			}
		});
		
		
		
	}
	
	
	
	public void populatePlayerItems(ArrayList<ItemsModel> plyrBag){
		
		for(int i=0;i<plyrBag.size();i++){
			exchangeMenu.comboBox.addItem(plyrBag.get(i).itemType+";"+plyrBag.get(i).itemBonus);
					
		}		
	}
	
	public void exchangePlayerItems(CharacterModel gamePlyr,ArrayList<ItemsModel> friendBag){
		
			int comboInx=exchangeMenu.comboBox.getSelectedIndex();
			friendBag.add(gamePlyr.getBagItems().get(comboInx));
			gamePlyr.getBagItems().remove(comboInx);
			gamePlyr.setBagItem(friendBag.get(0));
			friendBag.remove(0);
			
			populatePlayerItems(gamePlyr.getBagItems());
				
		
	}

}
