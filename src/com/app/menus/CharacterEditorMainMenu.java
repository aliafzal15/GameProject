package com.app.menus;



import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.app.models.CharacterModel;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTable;


/**
 *
 * This Class initiates the creation of GUI elements for the Character Editor Main Menu.
 * @author Ali Afzal
 */
public class CharacterEditorMainMenu extends JFrame  {

	private JFrame newFrame;
	public JTable table;
	public JLabel lblNewLabel_1;
	public JButton btnCreateFighter;
	public JButton btnEditFighter;
	public JButton btnCreateZombie;
	public JButton btnEditZombie;
	public JLabel lblStrength;
	public JLabel lblDexterity;
	public JLabel lblConstitution;
	public JLabel lblfStrengthVal;
	public JLabel lblfDexterityVal;
	public JLabel lblfConstitutionVal;
	public JLabel lblfHitPoints;
	public JLabel lblfAttackBonus;
	public JLabel lblfDamageBonus;
	public JLabel lblfArmorClass;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel lblfHitPointsVal;
	public JLabel lblfAttackBonusVal;
	public JLabel lblfDamageBonusVal;
	public JLabel lblfArmorClassVal;
	public JLabel lblzStrengthVal;
	public JLabel lblzDexterityVal;
	public JLabel lblzConstitutionVal;
	public JLabel lblzHitPointsVal;
	public JLabel lblzAttackBonusVal;
	public JLabel lblzDamageBonusVal;
	public JLabel lblzArmorClassVal;
	public JLabel lblHeader;
	public JLabel lblfLevel;
	public JLabel lblfLevelVal;
	public JLabel lblzLevel;
    public JLabel lblzLevelVal;
   	public JLabel lblfStregthModfier;
	public JLabel lblfStrModVal;
	public JLabel lblfDexterityModifier;
	public JLabel lblfDexModVal;
	public JLabel lblfConstitutionModifier;
	public JLabel lblfConstModVal;
	public JLabel lblzStregthModfier;
	public JLabel lblzStrModVal;
	public JLabel lblzDexterityModifier;
	public JLabel lblzDexModVal;
	public JLabel lblzConstitutionModifier;
	public JLabel lblzConstVal;
	public JButton btnResetdelete;
	public JButton fItemInventory;
	public JButton zItemInventroy;



	

	/**
	 * Launch the application.
	 */

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Learning window = new Learning();
					window.newFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Default Constructor.
	 */
	public CharacterEditorMainMenu() {
		//initialize();
		
	}

	/**
	 * Initialize the contents of the newFrame.
	 * @param newFrame
	 * 				JFrame Object passed as an argument to open a new window
	 * 				for character editor where new GUI elements can be displayed.
	 */
	public CharacterEditorMainMenu initialize(JFrame newFrame) {
		newFrame = new JFrame();
		newFrame.setTitle("Character Editor");
		newFrame.getContentPane().setBackground(Color.YELLOW);
		//newFrame.setBounds(100, 100, 450, 370);
		newFrame.setBounds(100, 100, 800, 650);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newFrame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fighter");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\User\\workspace\\SOEN 6441 Build 1\\images\\fighter2.jpg"));
		lblNewLabel.setBounds(135, 38, 144, 75);
		newFrame.getContentPane().add(lblNewLabel);
		

		
		lblNewLabel_1 = new JLabel("Zombie");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 11));
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\User\\workspace\\SOEN 6441 Build 1\\images\\zombie.jpg"));
		lblNewLabel_1.setBounds(543, 38, 144, 75);
		newFrame.getContentPane().add(lblNewLabel_1);
		
		btnCreateFighter = new JButton("Create Fighter");
		btnCreateFighter.setBounds(10, 38, 120, 23);
		newFrame.getContentPane().add(btnCreateFighter);
		
		btnEditFighter = new JButton("Edit Fighter");
		btnEditFighter.setBounds(10, 65, 120, 23);
		newFrame.getContentPane().add(btnEditFighter);
		
		btnCreateZombie = new JButton("Create Zombie");
		btnCreateZombie.setBounds(413, 38, 120, 23);
		newFrame.getContentPane().add(btnCreateZombie);
		
		btnEditZombie = new JButton("Edit Zombie");
		btnEditZombie.setBounds(413, 65, 120, 23);
		newFrame.getContentPane().add(btnEditZombie);
		
		lblStrength = new JLabel("Strength: ");
		lblStrength.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblStrength.setBounds(10, 121, 76, 14);
		newFrame.getContentPane().add(lblStrength);
		
		lblDexterity = new JLabel("Dexterity: ");
		lblDexterity.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblDexterity.setBounds(10, 146, 84, 14);
		newFrame.getContentPane().add(lblDexterity);
		
		lblConstitution = new JLabel("Constitution: ");
		lblConstitution.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblConstitution.setBounds(10, 170, 101, 14);
		newFrame.getContentPane().add(lblConstitution);
		
		lblfStrengthVal = new JLabel("New lable");
		lblfStrengthVal.setBackground(Color.RED);
		lblfStrengthVal.setBounds(84, 121, 46, 14);
		newFrame.getContentPane().add(lblfStrengthVal);
		
		lblfDexterityVal = new JLabel("New label");
		lblfDexterityVal.setBackground(Color.RED);
		lblfDexterityVal.setBounds(84, 146, 46, 14);
		newFrame.getContentPane().add(lblfDexterityVal);
		
		lblfConstitutionVal = new JLabel("New label");
		lblfConstitutionVal.setBackground(Color.RED);
		lblfConstitutionVal.setBounds(109, 170, 46, 14);
		newFrame.getContentPane().add(lblfConstitutionVal);
		
		lblfHitPoints = new JLabel("Hit Points: ");
		lblfHitPoints.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfHitPoints.setBounds(10, 195, 84, 14);
		newFrame.getContentPane().add(lblfHitPoints);
		
		lblfAttackBonus = new JLabel("Attack Bonus: ");
		lblfAttackBonus.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfAttackBonus.setBounds(10, 221, 109, 14);
		newFrame.getContentPane().add(lblfAttackBonus);
		
		lblfDamageBonus = new JLabel("Damage Bonus: ");
		lblfDamageBonus.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfDamageBonus.setBounds(10, 246, 120, 14);
		newFrame.getContentPane().add(lblfDamageBonus);
		
		lblfArmorClass = new JLabel("Armor Class: ");
		lblfArmorClass.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfArmorClass.setBounds(10, 271, 101, 14);
		newFrame.getContentPane().add(lblfArmorClass);
		
		label = new JLabel("Strength: ");
		label.setFont(new Font("Arial Black", Font.BOLD, 12));
		label.setBounds(413, 122, 76, 14);
		newFrame.getContentPane().add(label);
		
		label_1 = new JLabel("Dexterity: ");
		label_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_1.setBounds(413, 146, 84, 14);
		newFrame.getContentPane().add(label_1);
		
		label_2 = new JLabel("Constitution: ");
		label_2.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_2.setBounds(413, 171, 101, 14);
		newFrame.getContentPane().add(label_2);
		
		label_3 = new JLabel("Hit Points: ");
		label_3.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_3.setBounds(413, 196, 84, 14);
		newFrame.getContentPane().add(label_3);
		
		label_4 = new JLabel("Attack Bonus: ");
		label_4.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_4.setBounds(413, 222, 109, 14);
		newFrame.getContentPane().add(label_4);
		
		label_5 = new JLabel("Damage Bonus: ");
		label_5.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_5.setBounds(413, 247, 120, 14);
		newFrame.getContentPane().add(label_5);
		
		label_6 = new JLabel("Armor Class: ");
		label_6.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_6.setBounds(413, 272, 120, 14);
		newFrame.getContentPane().add(label_6);
		
		lblfHitPointsVal = new JLabel("New label");
		lblfHitPointsVal.setBounds(94, 195, 46, 14);
		newFrame.getContentPane().add(lblfHitPointsVal);
		
		lblfAttackBonusVal = new JLabel("New label");
		lblfAttackBonusVal.setBounds(120, 222, 46, 14);
		newFrame.getContentPane().add(lblfAttackBonusVal);
		
		lblfDamageBonusVal = new JLabel("New label");
		lblfDamageBonusVal.setBounds(130, 247, 46, 14);
		newFrame.getContentPane().add(lblfDamageBonusVal);
		
		lblfArmorClassVal = new JLabel("New label");
		lblfArmorClassVal.setBounds(109, 271, 46, 14);
		newFrame.getContentPane().add(lblfArmorClassVal);
		
		lblzStrengthVal = new JLabel("New label");
		lblzStrengthVal.setBounds(486, 122, 46, 14);
		newFrame.getContentPane().add(lblzStrengthVal);
		
		lblzDexterityVal = new JLabel("New label");
		lblzDexterityVal.setBounds(487, 147, 46, 14);
		newFrame.getContentPane().add(lblzDexterityVal);
		
		lblzConstitutionVal = new JLabel("New label");
		lblzConstitutionVal.setBounds(509, 171, 46, 14);
		newFrame.getContentPane().add(lblzConstitutionVal);
		
		lblzHitPointsVal = new JLabel("New label");
		lblzHitPointsVal.setBounds(497, 196, 46, 14);
		newFrame.getContentPane().add(lblzHitPointsVal);
		
		lblzAttackBonusVal = new JLabel("New label");
		lblzAttackBonusVal.setBounds(519, 222, 46, 14);
		newFrame.getContentPane().add(lblzAttackBonusVal);
		
		lblzDamageBonusVal = new JLabel("New label");
		lblzDamageBonusVal.setBounds(529, 247, 46, 14);
		newFrame.getContentPane().add(lblzDamageBonusVal);
		
		lblzArmorClassVal = new JLabel("New label");
		lblzArmorClassVal.setBounds(509, 272, 46, 14);
		newFrame.getContentPane().add(lblzArmorClassVal);
		
		lblHeader = new JLabel("Character Editor");
		lblHeader.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblHeader.setForeground(new Color(0, 128, 0));
		lblHeader.setBounds(284, 0, 176, 14);
		newFrame.getContentPane().add(lblHeader);
		
		lblfLevel = new JLabel("Level: ");
		lblfLevel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfLevel.setBounds(10, 296, 50, 14);
		newFrame.getContentPane().add(lblfLevel);
		
		lblfLevelVal = new JLabel("New label");
		lblfLevelVal.setBounds(65, 296, 46, 14);
		newFrame.getContentPane().add(lblfLevelVal);
		
		lblzLevel = new JLabel("Level: ");
		lblzLevel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblzLevel.setBounds(413, 297, 50, 14);
		newFrame.getContentPane().add(lblzLevel);
		
		lblzLevelVal = new JLabel("New label");
		lblzLevelVal.setBounds(461, 297, 46, 14);
		newFrame.getContentPane().add(lblzLevelVal);
		


		lblfStregthModfier = new JLabel("Strength Modfier: ");
		lblfStregthModfier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfStregthModfier.setBounds(10, 321, 137, 14);
		newFrame.getContentPane().add(lblfStregthModfier);
		
		lblfStrModVal = new JLabel("New label");
		lblfStrModVal.setBounds(142, 322, 46, 14);
		newFrame.getContentPane().add(lblfStrModVal);
		
		lblfDexterityModifier = new JLabel("Dexterity Modifier: ");
		lblfDexterityModifier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfDexterityModifier.setBounds(10, 346, 145, 14);
		newFrame.getContentPane().add(lblfDexterityModifier);
		
		lblfDexModVal = new JLabel("New label");
		lblfDexModVal.setBounds(151, 347, 46, 14);
		newFrame.getContentPane().add(lblfDexModVal);
		
		lblfConstitutionModifier = new JLabel("Constitution Modifier: ");
		lblfConstitutionModifier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblfConstitutionModifier.setBounds(10, 371, 167, 14);
		newFrame.getContentPane().add(lblfConstitutionModifier);
		
		lblfConstModVal = new JLabel("New label");
		lblfConstModVal.setBounds(175, 372, 46, 14);
		newFrame.getContentPane().add(lblfConstModVal);
		
		lblzStregthModfier = new JLabel("Strength Modfier: ");
		lblzStregthModfier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblzStregthModfier.setBounds(413, 321, 137, 14);
		newFrame.getContentPane().add(lblzStregthModfier);
		
		lblzStrModVal = new JLabel("New label");
		lblzStrModVal.setBounds(545, 322, 50, 14);
		newFrame.getContentPane().add(lblzStrModVal);
		
		lblzDexterityModifier = new JLabel("Dexterity Modifier: ");
		lblzDexterityModifier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblzDexterityModifier.setBounds(413, 347, 145, 14);
		newFrame.getContentPane().add(lblzDexterityModifier);

		lblzDexModVal = new JLabel("New label");
		lblzDexModVal.setBounds(560, 347, 46, 14);
		newFrame.getContentPane().add(lblzDexModVal);
		
		lblzConstitutionModifier = new JLabel("Constitution Modifier: ");
		lblzConstitutionModifier.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblzConstitutionModifier.setBounds(413, 372, 167, 14);
		newFrame.getContentPane().add(lblzConstitutionModifier);
		
		lblzConstVal = new JLabel("New label");
		lblzConstVal.setBounds(580, 372, 46, 14);
		newFrame.getContentPane().add(lblzConstVal);
		
		btnResetdelete = new JButton("Reset/Delete");
		btnResetdelete.setBounds(648, 11, 126, 23);
		newFrame.getContentPane().add(btnResetdelete);
		
		
		fItemInventory = new JButton("Item Inventory");
		fItemInventory.setBounds(10, 90, 120, 23);
		newFrame.getContentPane().add(fItemInventory);
		
		zItemInventroy = new JButton("Item Inventory");
		zItemInventroy.setBounds(413, 90, 120, 23);
		newFrame.getContentPane().add(zItemInventroy);
		
		 lblfStrengthVal.setText("0");
		 lblfDexterityVal.setText("0");
		 lblfConstitutionVal.setText("0");
		 lblfLevelVal.setText("0");
		 lblfStrModVal.setText("0");
		 lblfDexModVal.setText("0");
		 lblfConstModVal.setText("0");
		 lblfHitPointsVal.setText("0");
		 lblfAttackBonusVal.setText("0");
		 lblfDamageBonusVal.setText("0");
		 lblfArmorClassVal.setText("0");
		 lblzStrengthVal.setText("0");
		 lblzDexterityVal.setText("0");
		 lblzConstitutionVal.setText("0");
		 lblzLevelVal.setText("0");
		 lblzStrModVal.setText("0");
		 lblzDexModVal.setText("0");
		 lblzConstVal.setText("0");
		 lblzHitPointsVal.setText("0");
		 lblzAttackBonusVal.setText("0");
		 lblzDamageBonusVal.setText("0");
		 lblzArmorClassVal.setText("0");
		
		
		newFrame.setVisible(true);
	
		
		
		
		return this;
		
	}
}
