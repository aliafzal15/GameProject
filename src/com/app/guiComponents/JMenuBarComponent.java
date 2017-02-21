package com.app.guiComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.app.controller.CharacterEditorMenuController;
import com.app.mapValidation.MapFinalValidation;
import com.app.menus.CampaignEditorMenu;
import com.app.menus.CharacterEditorMainMenu;
import com.app.menus.ItemEditorMainMenu;
import com.app.menus.MapEditor;
import com.app.models.MapModel;
import com.app.staticEngine.AppEnums.E_JFileChooserrMode;
import com.app.staticEngine.AppEnums.E_MapEditorMode;

import com.app.staticEngine.AppStatics;
import com.app.utilities.FileStorage;

import com.app.menus.ItemEditorMainMenu;

import javax.swing.*;

/*import com.app.models.MapModel;
import com.app.staticEngine.*;
import com.app.staticEngine.AppEnums.*;
import com.app.bL.Map;
import com.app.guiComponents.JFileChooserComponent;
import com.app.guiEngine.MapEditor;
import com.app.staticEngine.AppStatics;
import com.app.utilities.FileStorage;
import com.app.staticEngine.AppEnums.E_JFileChooserrMode;
//import com.app.towerDefense.utilities.FileStorage;
*/
/************
import com.app.towerDefense.guiComponents.BottomGamePanelView;
import com.app.towerDefense.guiComponents.JFileChooserComponent;
import com.app.towerDefense.guiComponents.JPanelComponent;
import com.app.towerDefense.guisystem.MapEditor;
import com.app.towerDefense.models.MapModel;

import com.app.towerDefense.staticContent.AppilicationEnums.E_JFileChooserrMode;
import com.app.towerDefense.staticContent.AppilicationEnums.E_MapEditorMode;
*********/

/**
 *  @author Ali Afzal
 * This class allows us to select an option on the main game window.
 * Options Available in File Menu: Item Editor , Character Editor , Map Editor , Campaign Editor and Exit
 * Options Available in Help Menu: About
 */
public class JMenuBarComponent {


	public JMenuBar getGameJMenuBar(final JFrame new_jframe) {
		
		/**
		 * This JLabel Constructor will set the main background image on the window
		 * 
		 * @author AliAfzal
		 *
		 */		

		final JLabel backGround = new JLabel(
				new ImageIcon(((new ImageIcon("images/homebk.jpg").getImage().getScaledInstance(new_jframe.getSize().width,
						(int) ((int) new_jframe.getSize().height - 30), java.awt.Image.SCALE_SMOOTH)))));
		new_jframe.add(backGround);

		/**
		 * End of JLabel Constructor
		 * 
		 * @author AliAfzal
		 *
		 */		
		
		
		/**
		 * Creating Menu Bar on the Main Window [Starts]
		 * 
		 * @author AliAfzal
		 *
		 */	
		

	JMenuBar menuBar = new JMenuBar();
		
	
	/**
	 * Creating Parent Menu on the Menu Bar [Start]
	 * 
	 * @author AliAfzal
	 *
	 */	
	
	JMenu menuFile = new JMenu(AppStatics.MENU_FILE);
	menuBar.add(menuFile);
	
	
	/**
	 * Creating Sub Menus in the File Parent Menu [Starts]
	 * 
	 * @author AliAfzal
	 *
	 */	
	

		final JMenuItem menuItemPlay = new JMenuItem(AppStatics.MENU_ITEM_PLAY);
		menuFile.add(menuItemPlay);
		final JMenuItem menuItemCreateMap = new JMenuItem(AppStatics.MENU_ITEM_CREATE_MAP);
		menuFile.add(menuItemCreateMap);
		final JMenuItem menuItemOpenMap = new JMenuItem(AppStatics.MENU_ITEM_OPEN_MAP);
		menuFile.add(menuItemOpenMap);
		final JMenuItem menuItemEditor=new JMenuItem(AppStatics.MENU_ITEM_EDITOR);
		menuFile.add(menuItemEditor);
		final JMenuItem menuItemCharacterEditor=new JMenuItem(AppStatics.MENU_Character_EDITOR);
		menuFile.add(menuItemCharacterEditor);	
		final JMenuItem menuItemCampaignEditor = new JMenuItem(AppStatics.MENU_Campaign_EDITOR);
		menuFile.add(menuItemCampaignEditor);
		final JMenuItem menuItemExit = new JMenuItem(AppStatics.MENU_ITEM_EXIT);
		menuFile.add(menuItemExit);
		
		/**
		 * Creating Help Parent Menu [Starts]
		 * 
		 * @author AliAfzal
		 *
		 */		
		
		
		JMenu menuHelp = new JMenu(AppStatics.MENU_HELP);
		menuBar.add(menuHelp);
		
		/**
		 * Creating SubMenu in Help Parent Menu [Starts]
		 * 
		 * @author AliAfzal
		 *
		 */	
		
		final JMenuItem menuItemAbout = new JMenuItem(AppStatics.MENU_ITEM_ABOUT);
		menuHelp.add(menuItemAbout);

		/**
		 * This class handle the menu Item action
		 * 
		 * @author AliAfzal
		 *
		 */

 	
	class menuItemAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource().equals(menuItemCreateMap)){
					
					final JTextField txtX = new JTextField();
					final JTextField txtY = new JTextField();
					
					/**
					 * Creating Logic for Map Size in text editor [Starts]
					 * 
					 * @author AliAfzal
					 *
					 */		
					
					txtX.addKeyListener(new KeyAdapter() {
						
						public void keyTyped(KeyEvent e) {
								char ch = e.getKeyChar();
								System.out.println("in text box");
									if (!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE)
												|| (ch == KeyEvent.VK_DELETE))) {
													e.consume();
													}
						if (txtX.getText().length() > 2)
							e.consume();
					}//KeyTyped
				});//txtX.addKeyListener(new KeyAdapter() 
					
					
					txtY.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent e) {
							char ch = e.getKeyChar();
							if (!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE)
									|| (ch == KeyEvent.VK_DELETE))) {
								e.consume();
							}
							if (txtY.getText().length() > 2)
								e.consume();
						}//KeyTyped
					});//txtY.addKeyListener(new KeyAdapter() 
								
					Object[] message = { "Size of X in Map:[1-30]", txtX, "Size of Y in Map:[1-30]", txtY };
		
					int option = JOptionPane.showConfirmDialog(null, message, "SET SIZE OF MAP",
							JOptionPane.OK_CANCEL_OPTION);			
								
							if(option==JOptionPane.OK_OPTION){
								String x= txtX.getText().trim();
								String y = txtY.getText().trim();			
							
								if (x.length() == 0)
									JOptionPane.showMessageDialog(null, "Size of X May not Be Empty");

								else if (y.length() == 0)
									JOptionPane.showMessageDialog(null, "Size of Y May not Be Empty");

								else if (Integer.parseInt(x) < 1 || Integer.parseInt(x) > 30)
									JOptionPane.showMessageDialog(null, "Size of X must Lie between 1 - 30");

								else if (Integer.parseInt(y) < 1 || Integer.parseInt(y) > 30)
									JOptionPane.showMessageDialog(null, "Size of Y must Lie between 1 - 30");

								else {
									System.out.println("This is Testing");
									MapModel mapModel = new MapModel();

									mapModel.setMapWidth(Integer.parseInt(x));
									mapModel.setMapHeight(Integer.parseInt(y));
									new MapEditor(new_jframe, AppStatics.TITLE_MAP_EDITOR,
											AppStatics.CHILD_POPUP_WINDOW_WIDTH,
											AppStatics.CHILD_POPUP_WINDOW_HEIGHT, mapModel, E_MapEditorMode.Create);
										
								}
						
							}//if(option==JOptionPane.OK_OPTION
																				
					
					}//if(e.getSource().equals(menuItemCreateMap))
				
				else if(e.getSource().equals(menuItemEditor)){
					
					ItemEditorMainMenu temp=new ItemEditorMainMenu();
							
					
				}//else if(e.getSource().equals(menuItemEditor))
				
				else if(e.getSource().equals(menuItemCharacterEditor)){
					
					CharacterEditorMenuController temp=new CharacterEditorMenuController();
							
					
				}//else if(e.getSource().equals(menuItemEditor))
				
				else if (e.getSource().equals(menuItemOpenMap)) {
					JFileChooser fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserrMode.Open);
					int result = fileChooser.showOpenDialog(new_jframe);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						MapModel mapModel = (new com.app.utilities.FileStorage()).openMapFile(file);
						if (mapModel != null) {
							
								new MapEditor(new_jframe, AppStatics.TITLE_MAP_EDITOR,
										AppStatics.CHILD_POPUP_WINDOW_WIDTH,
										AppStatics.CHILD_POPUP_WINDOW_HEIGHT, mapModel, E_MapEditorMode.Open);
							

						} else
							JOptionPane.showMessageDialog(null, "Unable to .dd open File");
					} else {
						JOptionPane.showMessageDialog(null, "No File Selected");
					}
				}//else if e.getSource().equals(menuItemOpenMap)
				
				else if(e.getSource().equals(menuItemCampaignEditor)){
					
					CampaignEditorMenu temp=new CampaignEditorMenu();
							
					
				}//else if(e.getSource().equals(menuItemEditor))
				
				
				
				
			}//public void actionPerformed
		

	}//class menuItemAction implements ActionListener

		
	menuItemCreateMap.addActionListener(new menuItemAction());	
	menuItemEditor.addActionListener(new menuItemAction());
	menuItemCharacterEditor.addActionListener(new menuItemAction());
	menuItemOpenMap.addActionListener(new menuItemAction());
	menuItemCampaignEditor.addActionListener(new menuItemAction());
///******************************************************************************		
	
	return menuBar;
		
		
		
	}//getGameJMenuBar(final JFrame new_jframe)
	

	/**
	 * This method gets the Map editor menu bar
	 * 
	 * @param new_mapModel MapModel object is passed to the getMapEditorJmenuBar
	 * @param new_jframe
	 *            the frame of the application
	 * @return the menu bar
	 */
	public JMenuBar getMapEditorJMenuBar(final MapModel new_mapModel, final JFrame new_jframe) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu(AppStatics.MENU_FILE);
		final JMenuItem menuItemSave = new JMenuItem(AppStatics.MENU_ITEM_SAVE);
		menuFile.add(menuItemSave);
		final JMenuItem menuItemExit = new JMenuItem(AppStatics.MENU_ITEM_EXIT);
		menuFile.add(menuItemExit);

		class menuItemAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(menuItemSave)) {
					String MapValidationStatus = (new MapFinalValidation()).mapValidations(new_mapModel);

					if (MapValidationStatus != "")
						JOptionPane.showMessageDialog(null, MapValidationStatus);
					else {
						JFileChooser fileChooser = new JFileChooserComponent()
								.getJFileChooser(E_JFileChooserrMode.Save);
						int result = fileChooser.showSaveDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							new_mapModel.setMapFileName(file.getName());
							String msg = new FileStorage().saveMapFile(file, new_mapModel);
							if (msg.contains("SUCCESS")) {
								JOptionPane.showMessageDialog(null, "File Saved Successfuly.");
								closeFrame(new_jframe);
							} else
								JOptionPane.showMessageDialog(null, msg);
						}
					}
				}

				else if (e.getSource().equals(menuItemExit)) {
					closeFrame(new_jframe);
				}
				
				
				

			}

		}
		menuItemSave.addActionListener(new menuItemAction());
		menuItemExit.addActionListener(new menuItemAction());
		menuBar.add(menuFile);
		
		return menuBar;
	}	
	
	
	
	
	
	
	public void closeFrame(JFrame new_jframe) {
		new_jframe.dispose();
	}	
	
	
	
	
	
}//public class JMenuBarComponent
