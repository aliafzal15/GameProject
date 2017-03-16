package com.app.menus;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;


/**
 *
 * This Class initiates the creation of GUI elements in the Items Inventory Menu.
 * @author AliAfzal
 */
public class CharacterEditorItemsInventory {

	public JFrame frame;
	
	public JComboBox comboItemsWorn;
	public JComboBox comboItemsAll;
	public JButton btnRmvWornItem;
	public JButton btnAddItemWorn;
	public JButton btnAddItemBag;
	public JComboBox comboBagPack;
	public JButton btnRmvItemBag;
	public JButton btnAddBagToWorn;
	public JButton btnAddWornToBag;
	
	
	
	/**
	 * Initializes the GUI of Inventory Menu
	 * 
	 * @param frames
	 * 			JFrame object passed as an argument to open a new window where
	 * 			inventory related GUI elements can be displayed.
	 */
	public CharacterEditorItemsInventory(JFrame frames)
	{	this.frame=frames;
		initialize(this.frame);
	}

	/**
	 * Initialize the contents of the frame.
	 * All GUI objects are created and aligned on the window in this method.
	 */
	private void initialize(JFrame frame) {
		frame = new JFrame();
		frame.setTitle("Item Inventory");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		comboItemsWorn = new JComboBox();
		comboItemsWorn.setBounds(56, 98, 105, 20);
		frame.getContentPane().add(comboItemsWorn);
		
		JLabel lblItemsWorn = new JLabel("Items Worn");
		lblItemsWorn.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblItemsWorn.setBounds(67, 73, 94, 14);
		frame.getContentPane().add(lblItemsWorn);
		
		JLabel lblAllItems = new JLabel("All Items");
		lblAllItems.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblAllItems.setBounds(294, 73, 94, 14);
		frame.getContentPane().add(lblAllItems);
		
		comboItemsAll = new JComboBox();
		comboItemsAll.setBounds(281, 98, 105, 20);
		frame.getContentPane().add(comboItemsAll);
		
		btnRmvWornItem = new JButton("Remove Item");
		btnRmvWornItem.setBounds(56, 129, 123, 23);
		frame.getContentPane().add(btnRmvWornItem);
		
		btnAddItemWorn = new JButton("Add Item in Items Worn");
		btnAddItemWorn.setBounds(281, 129, 180, 23);
		frame.getContentPane().add(btnAddItemWorn);
		
		btnAddItemBag = new JButton("Add Item in Bag Pack");
		btnAddItemBag.setBounds(281, 161, 180, 23);
		frame.getContentPane().add(btnAddItemBag);
		
		comboBagPack = new JComboBox();
		comboBagPack.setBounds(56, 227, 105, 20);
		frame.getContentPane().add(comboBagPack);
		
		JLabel lblBagPack = new JLabel("Bag Pack");
		lblBagPack.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblBagPack.setBounds(67, 200, 94, 14);
		frame.getContentPane().add(lblBagPack);
		
		btnRmvItemBag = new JButton("Remove Item");
		btnRmvItemBag.setBounds(56, 259, 138, 23);
		frame.getContentPane().add(btnRmvItemBag);
		
		btnAddBagToWorn = new JButton("Add Item in Worn");
		btnAddBagToWorn.setBounds(56, 288, 138, 23);
		frame.getContentPane().add(btnAddBagToWorn);
		
		btnAddWornToBag = new JButton("Add Item in Bag");
		btnAddWornToBag.setBounds(56, 163, 123, 23);
		frame.getContentPane().add(btnAddWornToBag);
		
		frame.setVisible(true);
	}
}
