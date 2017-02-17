package com.app.menus;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.app.controller.ItemEditorMainMenuController;
import com.app.models.ItemsModel;
import com.app.utilities.*;


/**
 * 

 *
 * This Class initiates the creation of GUI elements in the Create/Edit Item Menu
 * @author Ali Afzal
 */
public class ItemEditorCreateMenu {

	private final static int  windowWidth=800;
	private final static int  windowHeight=250;
	private String menuTitle ="Create Item";
	private ItemsModel itemObject;
	public ArrayList <ItemsModel> items;
	private static String comboVal;
	private FileStorage itemStorage;
	private boolean toBeSaved;
	
	public JButton saveItemBtn;
	
	public JComboBox itemHelmet;
	public JComboBox itemArmor;
	public JComboBox itemShield;
	public JComboBox itemRing;
	public JComboBox itemBelt;
	public JComboBox itemBoots;
	public JComboBox itemWeapon;
	
	
	public JLabel labelHelmet;
	public JLabel labelArmor;
	public JLabel labelShield;
	public JLabel labelRing;
	public JLabel labelBelt;
	public JLabel labelBoots;
	public JLabel labelWeapon;
	public JLabel emptyLabel;
	
	public JPanel itemsJPanel;
	
	public ItemEditorCreateMenu(ItemEditorMainMenu mainMenuFrame){
		
		
		initCreateMenuWindow(mainMenuFrame);
		
	}

	/**
	 * Constructor contains all the logic for the creation of GUI:-nCombos and Save button
	 * 
	 * @param mainMenuFrame
	 *            Takes ItemEditorMainMenu object as an argument so as to render new GUI
	 */
	private void initCreateMenuWindow(final ItemEditorMainMenu mainMenuFrame) {
		
		mainMenuFrame.getContentPane().removeAll();
		mainMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainMenuFrame.setTitle(menuTitle);
		mainMenuFrame.setPreferredSize(new Dimension(windowWidth,windowHeight));
		mainMenuFrame.setResizable(false);
		mainMenuFrame.pack();
		mainMenuFrame.setLocationRelativeTo(null);
		
	
		
		
		itemHelmet=new JComboBox();
		createComboBox(itemHelmet);
		
		itemArmor=new JComboBox();
		createComboBox(itemArmor);
		
		itemShield=new JComboBox();
		createComboBox(itemShield);
		
		itemRing=new JComboBox();
		createComboBox(itemRing);
		
		itemBelt=new JComboBox();
		createComboBox(itemBelt);
		
		itemBoots=new JComboBox();
		createComboBox(itemBoots);
		
		itemWeapon=new JComboBox();
		createComboBox(itemWeapon);
		
		itemHelmet=new JComboBox();
		createComboBox(itemHelmet);
		
		 labelHelmet = new JLabel();
		 labelArmor = new JLabel();
		 labelShield = new JLabel();
		 labelRing = new JLabel();
		 labelBelt = new JLabel();
		 labelBoots = new JLabel();
		 labelWeapon = new JLabel();
		 emptyLabel= new JLabel();
		 
		 labelHelmet.setText("Helmet");
		 labelArmor.setText("Armor");
		 labelShield.setText("Sheild");
		 labelRing.setText("Ring");
		 labelBelt.setText("Belt");
		 labelBoots.setText("Boots");
		 labelWeapon.setText("Weapon");
		 emptyLabel.setText("  ");
		 
		 saveItemBtn=new JButton();
		 saveItemBtn.setText("Save");
		 
		
		
	
        Container c = mainMenuFrame.getContentPane();
        //c.setLayout(new GridLayout(1,1));
        
		
       
        
      
        itemsJPanel=new JPanel(new GridLayout(0,8));
        
      

     
    	itemsJPanel.add(labelHelmet);
    	itemsJPanel.add(itemHelmet);
    		
   
    	itemsJPanel.add(labelArmor);
    	itemsJPanel.add(itemArmor);
    	

 
    	itemsJPanel.add(labelShield);
    	itemsJPanel.add(itemShield);
    	
    	itemsJPanel.add(labelRing);
    	itemsJPanel.add(itemRing);
    	
    	itemsJPanel.add(labelBelt);
    	itemsJPanel.add(itemBelt);
    	
    	itemsJPanel.add(labelBoots);
    	itemsJPanel.add(itemBoots);
    	
    	itemsJPanel.add(labelWeapon);
    	itemsJPanel.add(itemWeapon);
    	
    	itemsJPanel.add(emptyLabel);
    	itemsJPanel.add(saveItemBtn);
       
        c.add(itemsJPanel);
       
        
       
       mainMenuFrame.setVisible(true);
        
       
       items=new ArrayList();
       //ArrayList <ItemsModel> tempItems=new ArrayList();
       
       getItemsFromFile(items);
       
       updateGuiDropDowns(items);
       
       System.out.println("size of file items "+items.size());
       
       System.out.println("****size of file items "+items.size());
       
       
   	/**
   	 * This class implements the ActionListener.
   	 * Holds all the logic for saving a created or edited item in file.
   	 * Item is created/edited with the selected value in the combo of the respective element
   	 * when save button is pressed.
   	 *           
   	 */           
   class menuItemEditorAction implements ActionListener {

		
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(itemHelmet)) {
                itemObject=null;
            	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Helmet");
                	if(Integer.parseInt(comboVal)>0){
                		itemObject=new ItemsModel("Helmet",comboVal);
                		items.add(itemObject);
                		
                	}
                System.out.println("I AM IN COMBO BOX Helmet with val= " + comboVal );
                
        
            }//if (e.getSource().equals(itemHelmet
            
            else if(e.getSource().equals(itemArmor)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Armor");
                 
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Armor",comboVal);
                 		
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Armor with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemArmor))
            
            else if(e.getSource().equals(itemShield)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Shield"); 
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Shield",comboVal);
                 		
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Shield with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemShield))  
            else if(e.getSource().equals(itemRing)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Ring"); 
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Ring",comboVal);
                 		
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Ring with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemRing))
            else if(e.getSource().equals(itemBelt)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Belt");
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Belt",comboVal);
                 		
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Belt with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemBelt))
            else if(e.getSource().equals(itemBoots)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Boots"); 
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Boots",comboVal);
                 		
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Boots with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemBoots))
            
            else if(e.getSource().equals(itemWeapon)){
            	itemObject=null;
             	JComboBox cb = (JComboBox)(e.getSource());
                comboVal = cb.getSelectedItem().toString();
                removeExtraAddition(items,"Weapon");
                 	if(Integer.parseInt(comboVal)>0){
                 		itemObject=new ItemsModel("Weapon",comboVal);
                 		items.add(itemObject);
                 	}
                 System.out.println("I AM IN COMBO BOX Weapon with val= " + comboVal );
                   	
            }//if(e.getSource().equals(itemWeapon))
            
            else if(e.getSource().equals(saveItemBtn)){
            	itemStorage=new FileStorage();
            	try {
            		
            		
					itemStorage.SaveItemInFile(items);
					mainMenuFrame.dispose();
					JOptionPane.showMessageDialog (null, "Items Saved Successfully", "Record Saved", 
																		JOptionPane.INFORMATION_MESSAGE);
					itemStorage=null;
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                   	
            }//if(e.getSource().equals(saveItemBtn))
           
        }
        
   }  
        
   itemHelmet.addActionListener(new menuItemEditorAction());  
   itemArmor.addActionListener(new menuItemEditorAction());
   itemShield.addActionListener(new menuItemEditorAction());
   itemRing.addActionListener(new menuItemEditorAction());
   itemBelt.addActionListener(new menuItemEditorAction());
   itemBoots.addActionListener(new menuItemEditorAction());
   itemWeapon.addActionListener(new menuItemEditorAction());
   saveItemBtn.addActionListener(new menuItemEditorAction());
	}
	
	
  	/**
   	 * This function creates 7 combo boxes.
   	 * @param combo
   	 * 			Takes the JComboBox object as an argument.
   	 *           
   	 */
	private void createComboBox(JComboBox combo){
		
		for (int i=0;i<6;i++){
			
			combo.addItem(i);
			
		
		}		
		
	}
	
	
   	/**
   	 * This function prevents duplicate creation of an item when save button is pressed.
   	 * @param items
   	 * 			Takes the ArrayList of type ItemsModel "items" holding all the items.
   	 *  @param itemType			
   	 * 			Item Type for which the removal is to be done.
   	 *           
   	 */  	
	private void removeExtraAddition(ArrayList <ItemsModel> items,String itemType){
		
		for (int i=0;i<items.size();i++){
			String tempType =items.get(i).itemType;
			
				if(tempType.equals(itemType)){
					items.remove(i);           					
				}
	}
		
			
	}
	
	
   	/**
   	 * This function gets the items from the file. initializes the ArrayList.
   	 * @param tempItems
   	 * 			Takes the arraylist of ItemsModel Type "tempItems" holding all the items.
   	 *           
   	 */ 
	private void getItemsFromFile(ArrayList <ItemsModel> tempItems){
		
		itemStorage=new FileStorage();
    	try {
    		
    		
			items=itemStorage.ReadItemInFile();
			System.out.println("XXXXXXXXsize of file Tempitems "+tempItems.size());
				
	    	itemStorage=null;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	
    
    	//return items;
    	
		
	}
	
   	/**
   	 * This function renders the GUI. Updates the items when edited or created.
   	 * Combo Value represent the new value of an item
   	 *           
   	 */  	
 private void updateGuiDropDowns(ArrayList <ItemsModel>items){
	 
	 	for (int i=0;i<items.size();i++){
	 		
	 			if(items.get(i).itemType.equals("Helmet")){
	 				itemHelmet.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 			if(items.get(i).itemType.equals("Armor")){
	 				itemArmor.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 			
	 			if(items.get(i).itemType.equals("Shield")){
	 				itemShield.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 			if(items.get(i).itemType.equals("Ring")){
	 				itemRing.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 			if(items.get(i).itemType.equals("Belt")){
	 				itemBelt.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 			if(items.get(i).itemType.equals("Boots")){
	 				itemBoots.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			if(items.get(i).itemType.equals("Weapon")){
	 				itemWeapon.setSelectedItem(Integer.parseInt(items.get(i).itemBonus));	 					 				
	 			}
	 			
	 		
	 	}
	 	 
 }
	
	
}