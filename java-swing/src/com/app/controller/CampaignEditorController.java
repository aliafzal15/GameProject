package com.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.app.guiComponents.JFileChooserComponent;
import com.app.menus.CampaignEditorMenu;
import com.app.menus.MapEditor;
import com.app.models.CampaignModel;
import com.app.models.MapModel;
import com.app.staticEngine.AppStatics;
import com.app.staticEngine.AppEnums.E_JFileChooserrMode;
import com.app.staticEngine.AppEnums.E_MapEditorMode;
import com.app.utilities.FileStorage;

/**
 * This class holds the logic for campaign editor controller.
 */
public class CampaignEditorController {
	
	private MapModel map;
	private CampaignModel campaign;


	/**
	 * This Constructor holds the logic for action listeners on the Campaign Editor Menu
	 * @param objCmpEditMenu
	 * 				View object of the Campaign Editor Menu
	 */
	public CampaignEditorController(final CampaignEditorMenu objCmpEditMenu){
		
		campaign=new CampaignModel();
		
		
		objCmpEditMenu.btnAddMap.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					
					
					
					
     				JFileChooser fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserrMode.Open);
					int result = fileChooser.showOpenDialog(objCmpEditMenu.frameCamp);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();												
						map = (new com.app.utilities.FileStorage()).openMapFile(file);
												
							if (map!= null) {
								campaign.setCampaignMap(map);
								objCmpEditMenu.comboMaps.addItem(file.getName());
								
								
						  } else{
							JOptionPane.showMessageDialog(null, "Unable to .dd open File");
							campaign=null;
						   }				
					
					}
					System.out.println("Campaign Size>>"+campaign.getCampaignMaps().size());
					
				}//action performed
	        });
		
		
		objCmpEditMenu.btnSaveCamp.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				if(campaign!=null){						
					boolean validationStatus=getCampaignValidation(campaign);
					
						if(validationStatus==true){
							
							JFileChooser fileChooser = new JFileChooserComponent()
									.getJFileChooser(E_JFileChooserrMode.Save);
							int result = fileChooser.showSaveDialog(null);
							if (result == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								campaign.setCampaignFileName(file.getName());
								//new_mapModel.setMapSecret();
								String msg = new FileStorage().saveCampaignFile(file,campaign);
								if (msg.contains("SUCCESS")) {
									JOptionPane.showMessageDialog(null, "File Saved Successfuly.");									
									//closeFrame(new_jframe);
								} else
									JOptionPane.showMessageDialog(null, msg);
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "Invalid Camapign. Make sure that exit point of one map is the entry point of the second map and so on.");
						}
										
					
				} else{
					
				}
							
			}//action performed
        });
		
		
		objCmpEditMenu.btnRmvMap.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if (objCmpEditMenu.comboMaps.getItemCount()>0){
					int tempMapVal=objCmpEditMenu.comboMaps.getSelectedIndex();			
					campaign.getCampaignMaps().remove(tempMapVal);
					objCmpEditMenu.comboMaps.removeItemAt(tempMapVal);
					System.out.println("Campaign Size>>"+campaign.getCampaignMaps().size());
			  }else{
				  JOptionPane.showMessageDialog(null,"No Maps to Remove.");
			  }
				
			}//action performed
        });
			
		objCmpEditMenu.btnLoadCamp.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				
				objCmpEditMenu.comboMaps.removeAllItems();
				
 				JFileChooser fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserrMode.Open);
				int result = fileChooser.showOpenDialog(objCmpEditMenu.frameCamp);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					try {
						campaign = (new com.app.utilities.FileStorage()).openCampaignFile(file);
					} 
					
					catch (IOException e1) {
					
						e1.printStackTrace();
					}
											
						if (campaign!= null) {
								objCmpEditMenu.comboLoadCamp.addItem(campaign.getCampaignFileName());
								for(int i=0;i<campaign.getCampaignMaps().size();i++){
									objCmpEditMenu.comboMaps.addItem(campaign.getCampaignMaps().get(i).getMapFileName());
								}
								
							
							
					  } else{
						JOptionPane.showMessageDialog(null, "Unable to .ddc open File");
						campaign=null;
					   }				
				
				}
				System.out.println("Campaign Size>>"+campaign.getCampaignMaps().size());
				
			}//action performed
        });
		
	}//class constructor
	
	/**
	 * This Method validates if the campaign is valid or not
	 * @param camp
	 * 			 object of type Campaign Model
	 * @return boolean: returns True if validated otherwise False.
	 */
	public boolean getCampaignValidation(CampaignModel camp){
		
		if(camp.getCampaignMaps().size()>1){
			
			for(int i=1;i<camp.getCampaignMaps().size();i++){
				
					
				
				if((camp.getCampaignMaps().get(i).getEntryPoint().x==camp.getCampaignMaps().get(i-1).getExitPoint().x) && 
													(camp.getCampaignMaps().get(i).getEntryPoint().y==camp.getCampaignMaps().get(i-1).getExitPoint().y - (camp.getCampaignMaps().get(i-1).getMapWidth()-1))){
					
					camp.setIsValidated(true);
					
				}
				else{
					camp.setIsValidated(false);
					
				}
			}
			
			return camp.getIsValidated();
		}
		else{
			JOptionPane.showMessageDialog(null, "Add more than One Map to Create a Campaign");
			return false;
		}
	}
	


}
