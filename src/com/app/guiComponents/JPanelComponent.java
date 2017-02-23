package com.app.guiComponents;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.app.staticEngine.AppStatics;
import com.app.utilities.MiscellaneousHelper;
import com.app.staticEngine.AppEnums;
import com.app.staticEngine.AppEnums.E_MapEditorMode;
import com.app.controller.MapEditorMapElements;
import com.app.models.MapModel;


/**
 * Main Class that initializes the panel for Map Editor
 * @author AliAfzal
 *
 */
public class JPanelComponent {

	//public BottomGamePanelView bottomGamePanel;
	public JButton[][] buttons;
	public String elementaVal;

	// -- Map Editor window Create and Open map mode
	
	/**
	 * This method initialized the grid layout for the Map Editor and places elements if map to be edited else
	 * plain grid layout with buttons on it.
	 *
	 */
	public JPanel getMapEditorGridPanel(final MapModel mapModel, E_MapEditorMode mapEditorMode) {
		if (E_MapEditorMode.Create == mapEditorMode) {
			mapModel.mapGridSelection = new int[mapModel.getMapHeight()][mapModel.getMapWidth()];
		}

		
		JPanel panel = new JPanel();
		GridLayout gridLayout = new GridLayout(mapModel.getMapHeight(), mapModel.getMapWidth(), 3, 3);
		panel.setLayout(gridLayout);
		
		
		JButton b[][] = new JButton[mapModel.getMapHeight()][mapModel.getMapWidth()];

		for (int i = 0; i < mapModel.getMapHeight(); i++) {
			for (int j = 0; j < mapModel.getMapWidth(); j++) {
				b[i][j] = new JButton();
				int value = 0;
				int multiple = 0;

				multiple = mapModel.getMapWidth();

				if (i == 0 && j == 0){
					value = 0;
				}
				else{
					value = 1 + j + (i * multiple);
				   }
				b[i][j].setName(value + ":" + i + ":" + j);
				
				System.out.println(b[i][j].getName()+">>>"+multiple+" "+1+"+" + j+ "+"+ (i * multiple));
				
	//0 is for no assignment //1 for wall //2 for Fighter //3 for Zombie //4 for Entry //5 for Exit

				if (E_MapEditorMode.Create == mapEditorMode) {
					mapModel.mapGridSelection[i][j] = 0;
					b[i][j].setBackground(Color.green);
				} 
				
				else {
					if (mapModel.mapGridSelection[i][j] == 1) {
						b[i][j].setBackground(Color.red);
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
					} else {
						b[i][j].setBackground(Color.green);
					}
				}
				
				
				b[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JButton btn = ((JButton) e.getSource());
						String[] nameArry = btn.getName().split(":");
						System.out.println("Button Name:" +Integer.parseInt(nameArry[1]) + Integer.parseInt(nameArry[2]));
						int _i = Integer.parseInt(nameArry[1]);
						int _j = Integer.parseInt(nameArry[2]);
						
						
				
				       try {
						MapEditorMapElements mapElementObj=new MapEditorMapElements(btn,mapModel,_i,_j);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
						
					   System.out.println(" Btn Name : " + btn.getName());
					}

				});
				
				panel.add(b[i][j]);
			}
		}//for
		// this.setContentPane(panel);
		
		return panel;
	}
	

}

