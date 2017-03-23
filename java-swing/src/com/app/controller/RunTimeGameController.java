package com.app.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.app.guiComponents.JPanelComponent;
import com.app.guiEngine.Game;
import com.app.menus.GameWindow;
import com.app.menus.RunTimeGameMenu;
import com.app.models.CampaignModel;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.models.MapModel;
import com.app.staticEngine.AppStatics;
import com.app.staticEngine.AppEnums.E_MapEditorMode;



/**
 * This class is for  the play mode on the game map
 * 
 * @author Ali Afzal
 *
 */
public class RunTimeGameController implements ActionListener, KeyListener {
	
	private RunTimeGameMenu mainGameWindow;
	public StartGameController tempStartController;
	private boolean isHostileAlive;
	private boolean isPlyrAlive;
	private int numOfHostile;
	private int numOfFriendly;
	private boolean isExitDone;
	private int rowPlyr;
	private int colPlyr;
	private MapModel gameMap;
	private int prevState;
	private int prevXLoc;
	private int prevYLoc;
	private CampaignModel runCampaign;
	private int runMapIndex;
	private ArrayList<MapModel> runMaps;
	private int btnX;
	private int btnY;
	public boolean patternController;
	
	/**
	 * This is the default constructor
	 * 
	 *
	 */
	public RunTimeGameController(){
		
	}

	/**
	 * This is the parameterized constructor
	 * 
	 * @param camp
	 * 			Type CampaignModel
	 * @param maps
	 * 			Type ArrayList<MapModel>
	 * @param currMap
	 * 			Type MapModel
	 * @param gameChar
	 * 			Type CharacterModel
	 * @param playWindow
	 * 			Type RunTimeGameMenu
	 * @param startController
	 * 			Type StartGameController
	 * @param mapInx
	 * 			Type int
	 * 
	 */	
	public RunTimeGameController(CampaignModel camp,ArrayList<MapModel> maps, final MapModel currMap,
					CharacterModel gameChar,RunTimeGameMenu playWindow,StartGameController startController,int mapInx){
		
		
		this.isHostileAlive=true;
		this.isPlyrAlive=true;
		this.isExitDone=false;
		this.numOfFriendly=startController.friendlyCharacters.size();
		this.numOfHostile=startController.hostileCharacters.size();
		this.rowPlyr=startController._i;
		this.colPlyr=startController._j;
		
		this.mainGameWindow=playWindow;
		this.tempStartController=startController;
		this.gameMap=currMap;
		this.runCampaign=camp;
		this.runMapIndex=mapInx;
		this.runMaps=maps;

		
		
		playWindow.runGameButtons[rowPlyr][colPlyr].addKeyListener(this);
		playWindow.runGameButtons[rowPlyr][colPlyr].setFocusable(true);
		playWindow.runGameButtons[rowPlyr][colPlyr].setFocusable(true);
		playWindow.runGameButtons[rowPlyr][colPlyr].setFocusTraversalKeysEnabled(false);
		
	
				
		for(int i=0;i<playWindow.runGameButtons.length;i++){
			for(int j=0;j<playWindow.runGameButtons.length;j++){				
				playWindow.runGameButtons[i][j].addActionListener(this);
			}			
		}

			/*do{			
				
			}while (numOfHostile > 0 && isExitDone==false);*/		
		
		System.out.println("in constructor Run Time");
		
		patternController=false;
		new AbilityViewDriver(gameChar,this);
		new AbilityViewDriver(tempStartController.hostileCharacters.get(0),this);
		new AbilityViewDriver(tempStartController.friendlyCharacters.get(0),this);
		new PlayerInventoryViewDriver(gameChar,this);	
		new PlayerInventoryViewDriver(tempStartController.hostileCharacters.get(0),this);
		new PlayerInventoryViewDriver(tempStartController.friendlyCharacters.get(0),this);			
	}




	/**
	 * This Method captures the keystroke event and adds and subtracts in the x or y of the player location.
	 * @param e
	 * 		 Type KeyEvent
	 *
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			
			
			
				if(rowPlyr+1 < gameMap.mapGridSelection.length){
					rowPlyr=rowPlyr+1;
					move(rowPlyr,colPlyr);
					System.out.println("Moving Down");
					
				}
	}
		
		if(e.getKeyCode()==KeyEvent.VK_UP){
			
			
				if(rowPlyr -1 >= 0){
					rowPlyr=rowPlyr-1;
					move(rowPlyr,colPlyr);
					System.out.println("Moving Up");
					
				}

		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					
			
			
				if((colPlyr +1) < gameMap.mapGridSelection.length){
					colPlyr=colPlyr+1;
					move(rowPlyr,colPlyr);
					System.out.println("Moving Right");
					
				}
					
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
					
			
			
			if((colPlyr-1)>= 0){
				colPlyr=colPlyr-1;
				move(rowPlyr,colPlyr);
				System.out.println("Moving Left");
				
			}

		}
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This Method captures the action on the map buttons and perform the operations to trigger observer pattern for
	 * ability scores view and inventory view.
	 *
	 * @param e
	 * 		 Type ActionEvent
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.patternController=false;
		
		JButton btn = ((JButton) e.getSource());		
		String[] nameArry = btn.getName().split(":");
		System.out.println("Button Name:" +Integer.parseInt(nameArry[1]) + Integer.parseInt(nameArry[2]));
		int _i = Integer.parseInt(nameArry[1]);
		int _j = Integer.parseInt(nameArry[2]);
		
		  if(_i==btnX && _j==btnY){				
			  tempStartController.gamecharacter.getAbilityScores();								
			}
		
		  else if(gameMap.mapGridSelection[_i][_j]==tempStartController.friendlyBtnId){
			  tempStartController.friendlyCharacters.get(0).getAbilityScores();
		  }
		  else if(gameMap.mapGridSelection[_i][_j]==tempStartController.enemyBtnId ||
				  															gameMap.mapGridSelection[_i][_j] == 14){
			  tempStartController.hostileCharacters.get(0).getAbilityScores();
		  }	
		
		
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		
	}

	/**
	 * This Method operates the movement of the player on the map
	 *
	 * @param x
	 * 		 Type int
	 * @param y
	 * 		 Type int
	 *
	 */
	public void move(int x,int y){
				
		setPrevState(x,y);
		gameMap.mapGridSelection[x][y]=13;
		btnX=x;
		btnY=y;
		setMapState(gameMap,mainGameWindow.runGameButtons);
		
		
			if(prevState==tempStartController.enemyBtnId){
				isHostileAlive=false;
				lootHostileItems();
				gameMap.mapGridSelection[x][y]=14;				
			}
			
			if (prevState==7 && getMapItem("Shield")!=null){				
					if(tempStartController.gamecharacter.setBagItem(getMapItem("Shield"))){						
						prevState=0;
						gameMap.mapGridSelection[x][y]=0;							
					}
						
			}
			
			if(prevState==6 && getMapItem("Weapon")!=null){				
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Weapon"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}
			
			if	(prevState==8 && getMapItem("Armor")!=null){			
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Armor"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}
			
			
			if	(prevState==12 && getMapItem("Helmet")!=null){			
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Helmet"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}
			
			if	(prevState==9 && getMapItem("Boots")!=null){			
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Boots"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}
			
			if	(prevState==10 && getMapItem("Belt")!=null){				
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Belt"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}	
			
			if	(prevState==11 && getMapItem("Ring")!=null){			
				if(tempStartController.gamecharacter.setBagItem(getMapItem("Ring"))){						
					prevState=0;
					gameMap.mapGridSelection[x][y]=0;							
				}					
		}	
			
			
				if(prevState==5 && isHostileAlive==false){				
						
					int tempMapIndex=runMapIndex+1;
							if(tempMapIndex<runCampaign.getCampaignMaps().size()){
								runMapIndex=runMapIndex+1;
								
								RunTimeGameMenu gameWindow= new RunTimeGameMenu();
								
								
								mainGameWindow.frameRunGame.getContentPane().removeAll();
								mainGameWindow.frameRunGame.setLayout(new BorderLayout());
								
								
								gameWindow.frameRunGame=mainGameWindow.frameRunGame;
								JPanelComponent panelComponent =new JPanelComponent();
								JPanel gameMapPanel = (panelComponent).getMapEditorGridPanel(runCampaign.getCampaignMaps().get(runMapIndex),
										E_MapEditorMode.Play,new Dimension(runCampaign.getCampaignMaps().get(runMapIndex).getMapWidth(),runCampaign.getCampaignMaps().get(runMapIndex).getMapHeight()));
								
								gameWindow.runGamePanel=gameMapPanel;
								gameWindow.runGameButtons=panelComponent.gamePlayButtons;
								gameWindow.frameRunGame.getContentPane().add(gameMapPanel,BorderLayout.CENTER);
	
								
								mainGameWindow=null;
								gameWindow.frameRunGame.setVisible(true);
								mainGameWindow=gameWindow;
								
								tempStartController.gamecharacter.setCharLevel(tempStartController.gamecharacter.getCharLevel()+1);
								
								
								try {
									new StartGameController(runCampaign,runMaps, runCampaign.getCampaignMaps().get(runMapIndex),tempStartController.gamecharacter,gameWindow,runMapIndex);
								
								} catch (IOException e) {
								
									e.printStackTrace();
								}
								
							  }else{
									
									JOptionPane.showMessageDialog (null, "Game Over. All Zombies Dead. Campaign Finished !!!");
									mainGameWindow.frameRunGame.dispose();
									//Game newGame=new Game();
																		
								}
					
					
				}//if(prevState==5 && isHostileAlive==false) 
				
				
				
				
				
				if(prevState==tempStartController.friendlyBtnId){
					
					new RunTimeFriendlyExchangeController(tempStartController.plyrBtnTxt,tempStartController.gamecharacter.getBagItems(),
												tempStartController.friendlyCharacters.get(0).getBagItems(),tempStartController.gamecharacter,tempStartController.friendlyCharacters);
									
						
				  }
				
	}
	
	
	/**
	 * This Method sets the map state when a character is moved
	 *
	 * @param mapModel
	 * 		 Type MapModel
	 * @param b[][]
	 * 		 Type JButton
	 *
	 */
	public void setMapState(MapModel mapModel,JButton[][] b){
		
		for(int i=0;i<b.length;i++){
			for(int j=0;j<b.length;j++){
		
		
		
			if (mapModel.mapGridSelection[i][j] == 1) {
				b[i][j].setBackground(Color.red);
				b[i][j].setText(null);
				
			} else if (mapModel.mapGridSelection[i][j] == 2) {
				b[i][j].setBackground(Color.yellow);
				b[i][j].setText("F");
			
			} else if (mapModel.mapGridSelection[i][j] == 3) {
				b[i][j].setBackground(Color.orange);
				b[i][j].setText("Z");
			
			} else if (mapModel.mapGridSelection[i][j] == 4) {
				b[i][j].setBackground(Color.white);
				b[i][j].setText("O");
				
			} else if (mapModel.mapGridSelection[i][j] == 5) {
				b[i][j].setBackground(Color.gray);
				b[i][j].setText("E");
				
			} else if (mapModel.mapGridSelection[i][j] == 6) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IW");
			
			} else if (mapModel.mapGridSelection[i][j] == 7) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IS");
				
			} else if (mapModel.mapGridSelection[i][j] == 8) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IA");
				
			} else if (mapModel.mapGridSelection[i][j] == 9) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IBO");
				
			} else if (mapModel.mapGridSelection[i][j] == 10) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IBL");
			
			} else if (mapModel.mapGridSelection[i][j] == 11) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IR");
				
			} else if (mapModel.mapGridSelection[i][j] == 12) {
				b[i][j].setBackground(Color.blue);
				b[i][j].setText("IH");
				
			} else if (mapModel.mapGridSelection[i][j] == 13) {
				
					b[i][j].setBackground(Color.pink);
					b[i][j].setText(tempStartController.plyrBtnTxt);
					mapModel.mapGridSelection[i][j]=prevState;
					
					
			} else if (mapModel.mapGridSelection[i][j] == 14) {
				
				b[i][j].setBackground(Color.CYAN);
				b[i][j].setText(tempStartController.enemyBtnTxt+"-Dead");
				
			} 
			
			
			else {
				b[i][j].setBackground(Color.green);
				b[i][j].setText(null);
			
			}				
	
		  }
	    }
	}
	
	
	/**
	 * This Method sets the previous state of the cell on the map
	 *
	 * @param x
	 * 		 Type int
	 * @param y
	 * 		 Type int
	 *
	 *
	 */
	public void setPrevState(int x, int y){
		
		prevState=gameMap.mapGridSelection[x][y];
		prevXLoc=x;
		prevYLoc=y;
		

		
	}
	
	/**
	 * This Method loots the hostile items
	 *
	 *
	 */
 public void  lootHostileItems(){
	 
	 		
		
		for (int i=0 ; i< tempStartController.hostileCharacters.get(0).getWornItems().size();i++){
			
				if(tempStartController.gamecharacter.getBagItems().size() <= 10){
					
					tempStartController.gamecharacter.setBagItem(tempStartController.hostileCharacters.get(0).getWornItems().get(i));
					tempStartController.hostileCharacters.get(0).getWornItems().remove(i);
						
					}				
		}
		
		
		for (int i=0 ; i< tempStartController.hostileCharacters.get(0).getBagItems().size();i++){
			
			if(tempStartController.gamecharacter.getBagItems().size() <= 10){
				
				tempStartController.gamecharacter.setBagItem(tempStartController.hostileCharacters.get(0).getBagItems().get(i));
				tempStartController.hostileCharacters.get(0).getBagItems().remove(i);
					
				}
						
	}
		
  }
 
	/**
	 * This Method captures the items on the map
	 *
	 * @param itmeType
	 * 		 Type String
	 *
	 */
 public ItemsModel getMapItem(String itemType){
	 
	 for(int i=0;i<tempStartController.runTimeMapItems.size();i++){
		 
		 	if(tempStartController.runTimeMapItems.get(i).itemType.equals(itemType)){
		 		
		 		return tempStartController.runTimeMapItems.get(i);
		 	}
		 
	 }
	 
	 return null;
 }
 
 
 
}
