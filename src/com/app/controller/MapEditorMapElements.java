package com.app.controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.codec.language.MatchRatingApproachEncoder;

import com.app.models.MapModel;

public class MapEditorMapElements {

	private JFrame frame;
	public JLabel lblElements;
	public JComboBox comboElements;
	public JButton btnSaveElement;
	public String elementMapValue;
	private MapModel newMapModel;
	private int exit_i;
	private int exit_j;
	private int entry_i;
	private int entry_j;

	public MapEditorMapElements(final JButton btn,MapModel mapMdl,final int i,final int j) {
		initialize();
		newMapModel=mapMdl;
		
		btnSaveElement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
											
				setElementValue();
				 					
					if(elementMapValue=="Wall"){
						
						if(((i!=0) && (i!=newMapModel.getMapHeight()-1)) || ((j!=0) && (j != newMapModel.getMapWidth()-1))){    
							
							setIsFighterZombie(newMapModel,i, j);
							btn.setBackground(Color.red);
							newMapModel.mapGridSelection[i][j] = 1;	
							btn.setText("");
							System.out.println("SettingWall>> "+entry_i+" "+entry_j+" "+exit_i+" "+exit_j+">> "+i+" "+j);
							setEntryExit(newMapModel,i,j);
					   }
						
						else{
							JOptionPane.showMessageDialog(null,"Wall Can not be Placed To Avoid Invalid Map");
						}
					}
					else if(elementMapValue=="Fighter" && checkIfZombieInMap(newMapModel)==false){
					
						btn.setBackground(Color.yellow);
						btn.setText("F");
						newMapModel.mapGridSelection[i][j] = 2;	
						newMapModel.isFighterPlaced=true;
						newMapModel.isZombiePlaced=false;
						setEntryExit(newMapModel,i,j);
					}
					else if(elementMapValue=="Zombie" && checkIfFighterInMap(newMapModel)==false){
						
						btn.setBackground(Color.orange);
						btn.setText("Z");
						newMapModel.mapGridSelection[i][j] = 3;	
						newMapModel.isFighterPlaced=false;
						newMapModel.isZombiePlaced=true;
						setEntryExit(newMapModel,i,j);
					}
					else if((elementMapValue=="Entry") && (checkIfEntryDone(newMapModel)!=true)){
						
						if( ((i>=0 && j==0) || (i<=newMapModel.getMapWidth()-1 && j==newMapModel.getMapHeight()-1))   &&  (checkIfExitInSameColumn(newMapModel,j)==false) ){    
								
								setIsFighterZombie(newMapModel,i, j);
								btn.setBackground(Color.white);
								btn.setText("O");
								newMapModel.mapGridSelection[i][j] = 4;	
								newMapModel.isEntryDone=true;
								newMapModel.setEntryPoint(new Point(i, j));
								entry_i=i;
								entry_j=j;
						
								System.out.println("SettingEntry>> "+entry_i+" "+entry_j+" "+exit_i+" "+exit_j+">> "+i+" "+j);
						
							}
						
							else{
							JOptionPane.showMessageDialog(null,"Entry Can not be Placed To Avoid Invalid Map");
							}
					}
					else if((elementMapValue=="Exit")&& (checkIfExitDone(newMapModel)!=true)){
						System.out.println(i+" "+j);
							if( ((i>=0 && j==0) || (i<=newMapModel.getMapWidth()-1 && j==newMapModel.getMapHeight()-1))   &&  (checkIfEntryInSameColumn(newMapModel,j)==false) ){ 
								
								setIsFighterZombie(newMapModel,i, j);
								btn.setBackground(Color.gray);
								btn.setText("E");
								newMapModel.mapGridSelection[i][j] = 5;
								newMapModel.isExitDone=true;
								newMapModel.setExitPoint(new Point(i,j));
								exit_i=i;
								exit_j=j;
							}
							
							else{
								JOptionPane.showMessageDialog(null,"Exit Can not be Placed To Avoid Invalid Map");
							}
							
					}
							
					else if(elementMapValue=="None"){
						setIsFighterZombie(newMapModel,i, j);
						btn.setBackground(Color.green);
						btn.setText("");
						newMapModel.mapGridSelection[i][j] = 0;	
						setEntryExit(newMapModel,i,j);
					}
								
				frame.dispose();
			}

		  });
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 348, 187);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setVisible(true);
		
		lblElements= new JLabel("Elements");
		lblElements.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblElements.setBounds(22, 61, 80, 20);
		frame.getContentPane().add(lblElements);
		
		btnSaveElement = new JButton("Save");
		btnSaveElement.setFont(new Font("Arial Black", Font.BOLD, 12));
		btnSaveElement.setBounds(204, 93, 89, 23);
		frame.getContentPane().add(btnSaveElement);
		
		comboElements = new JComboBox();
		comboElements.setBounds(121, 62, 92, 20);
		frame.getContentPane().add(comboElements);
		
		comboElements.addItem("Wall");
		comboElements.addItem("Fighter");
		comboElements.addItem("Zombie");
		comboElements.addItem("Entry");
		comboElements.addItem("Exit");
		comboElements.addItem("None");
		
		
		System.out.println("Save button Not pressed");
		
		
		
			
	}
	
	public void setElementValue(){
		this.elementMapValue=(String) comboElements.getSelectedItem();
	}
	
	
	public void setEntryExit(MapModel mdl,int i,int j){
		
		
		Point tempPointEntry=mdl.getEntryPoint();
		Point tempPointExit=mdl.getExitPoint();
		
	if(tempPointEntry!=null){
			
		if((tempPointEntry.x==i) && (tempPointEntry.y==j)){
			mdl.isEntryDone=false;
			mdl.setEntryPoint(null);
			System.out.print("in Entry box");
		}			
   	}

	 
	if(tempPointExit!=null){
		if((tempPointExit.x==i) && (tempPointExit.y==j)){
				mdl.isExitDone=false;
				mdl.setExitPoint(null);
				System.out.print("in Exit box");
			}
	}
}
	
	
	public boolean checkIfExitDone(MapModel map){
		if(map.isExitDone){
			JOptionPane.showMessageDialog(null,"Exit Already Exits");
			return true;

		}
		else{
			return false;
		}
	}
	
	public boolean checkIfEntryDone(MapModel map){
		if(map.isEntryDone){
			JOptionPane.showMessageDialog(null,"Entry Already Exits");
			return true;

		}
		else{
			return false;
		}
	}
	
	public boolean checkIfEntryInSameColumn(MapModel mapmdl,int y){
	
		Point tempPointEntry=mapmdl.getEntryPoint();
			if(tempPointEntry!=null){
					if(tempPointEntry.y==y){
						return true;
					}
					else{
						return false;
					}
			}
			else{
				   return false;		
				}				
	   }
	
	public boolean checkIfExitInSameColumn(MapModel mapmdl,int y){
		
		Point tempPointExit=mapmdl.getExitPoint();
			if(tempPointExit!=null){
					if(tempPointExit.y==y){
						return true;
					}
					else{
						return false;
					}
			}
			else{
				   return false;		
				}				
	   }
	
	
	public boolean checkIfZombieInMap(MapModel mpMdl){
		
			if(mpMdl.isFighterPlaced==true){
				
				JOptionPane.showMessageDialog(null,"You can Place one Zombie/Fighter in the Map");
				return true;
			}
			
			else{
				return false;
			 }
		
	}
	
	public boolean checkIfFighterInMap(MapModel mpMdl){
		
		if(mpMdl.isFighterPlaced==true){			
			JOptionPane.showMessageDialog(null,"You can Place one Zombie/Fighter in the Map");
			return true;
		}
		
		else{
			return false;
		 }
	
}
	
	public void setIsFighterZombie(MapModel mdl,int i, int j){
			
		if(mdl.mapGridSelection[i][j]==2){
			mdl.isFighterPlaced=false;
		}
			
		else if(mdl.mapGridSelection[i][j]==3){
			mdl.isZombiePlaced=false;
		}
		
   	}
	
				
}

