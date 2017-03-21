package com.app.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.app.guiComponents.JFileChooserComponent;
import com.app.guiComponents.JPanelComponent;
import com.app.menus.PlayCampaignMenu;
import com.app.menus.PlayGameMenu;
import com.app.menus.RunTimeGameMenu;
import com.app.models.CampaignModel;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.models.MapModel;
import com.app.models.PlayGameModel;
import com.app.staticEngine.AppEnums.E_JFileChooserrMode;
import com.app.staticEngine.AppEnums.E_MapEditorMode;
import com.app.utilities.FileStorage;



/**
 * This class is for the initialization of game.
 * 
 * @author Ali Afzal
 *
 */
public class PlayGameController {
	
	private MapModel playMap;
	public JPanel gameMapPanel;
	private CampaignModel playCampaign;
	private PlayGameModel gameModel;
	
	private ArrayList<MapModel> gameMaps;
	private ArrayList<CharacterModel> fileCharacters;
	private CharacterModel mainPlayer;
	
	/**
	 * This is parameterized constructor.
	 * 
	 * @param playGameCamp
	 * 			Type CampaignModel
	 *
	 */
	public PlayGameController(final PlayCampaignMenu playGameCamp) throws IOException{
		
		
		FileStorage objFSO=new FileStorage();
				
		fileCharacters=new ArrayList();
		fileCharacters=objFSO.readCharacterInFile();
		
		playGameCamp.btnLoadCamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserrMode.Open);
				int result = fileChooser.showOpenDialog(playGameCamp.framePlayCamp);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					try {
						playCampaign = (new com.app.utilities.FileStorage()).openCampaignFile(file);
						gameMaps=playCampaign.getCampaignMaps();
					} 
					
					catch (IOException e1) {
					
						e1.printStackTrace();
					}
											
						if (playCampaign!= null) {	
							
							playGameCamp.comboPlayCamp.addItem(playCampaign.getCampaignFileName());
							
							
					  } else{
						JOptionPane.showMessageDialog(null, "Unable to open .ddc open File");
						playCampaign=null;
					   }				
				
				}

			}
 });	
		
		playGameCamp.btnStartGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
					if(playCampaign==null){
						JOptionPane.showMessageDialog(null, "First Load a Campaign");						
					} else{
						playGameCamp.framePlayCamp.dispose();
						ArrayList<MapModel> campaignMaps=playCampaign.getCampaignMaps();
						final PlayGameMenu gameMenu=new PlayGameMenu();
						
							if(fileCharacters.size()>0){							
								for(int i=0;i<fileCharacters.size();i++){									
									gameMenu.comboPlayer.addItem(fileCharacters.get(i).getCharType());	
								}
							}else{
								gameMenu.btnNext.setEnabled(false);
							}
														
									gameMenu.btnNext.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
																				
												for(int i=0;i<fileCharacters.size();i++){
												   if(gameMenu.comboPlayer.getSelectedItem().equals(fileCharacters.get(i).getCharType())){
													   mainPlayer=fileCharacters.get(i);
													   break;
							       					}
												}
												
											gameMenu.framePlayGame.setSize(1000, 600);
											gameMenu.framePlayGame.getContentPane().removeAll();
											gameMenu.framePlayGame.setLayout(new BorderLayout());
										
											JPanelComponent panelComponent =new JPanelComponent();
											gameMapPanel = (panelComponent).getMapEditorGridPanel(gameMaps.get(0),
													E_MapEditorMode.Play,new Dimension(gameMaps.get(0).getMapWidth(),gameMaps.get(0).getMapHeight()));
											
											
											System.out.println("Width>>"+gameMapPanel.getWidth()+" ,Height>>"+gameMapPanel.getHeight());
							
											gameMenu.framePlayGame.getContentPane().add(gameMapPanel,BorderLayout.CENTER);
														
											
											RunTimeGameMenu gameWindow= new RunTimeGameMenu();
											
											gameWindow.frameRunGame=gameMenu.framePlayGame;
											gameWindow.runGamePanel=gameMapPanel;
											gameWindow.frameRunGame.getContentPane().add(gameWindow.runGamePanel,BorderLayout.CENTER);
											gameWindow.runGameButtons=(panelComponent).gamePlayButtons;
											
											gameMenu.framePlayGame.setVisible(false);
											gameWindow.frameRunGame.setVisible(true);
											
											//PlayGameMenu playGameWindow=gameMenu;
											
											try {
												
												StartGameController mainGameController=new StartGameController(playCampaign,gameMaps,gameMaps.get(0),mainPlayer,gameWindow,0);
											
											} catch (IOException e1) {
											
												e1.printStackTrace();
											}
											
											//gameMenu.framePlayGame.getContentPane().add(gameMapPanel, BorderLayout.NORTH);
																																		
										}
									});		
							
					}//else if		

			}
 });	
		
		
		
	}

}
