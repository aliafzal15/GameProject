package com.app.menus;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.app.guiComponents.JMenuBarComponent;
import com.app.guiEngine.Game;

public class GameWindow extends JFrame {

	private JMenuBar gameJMenuBar;
	protected JMenuBarComponent gameJMenuBarComponent;
	
	/**
	 * WINDOW Constructor
	 * 
	 * @param new_width
	 *            the width of the frame
	 * @param new_height
	 *            the height of the frame
	 * @param new_title
	 *            the title of the game
	 * @param new_game
	 *            the game main class
	 */
	public GameWindow(int new_width, int new_height, String new_title, Game new_game) {
		
		this.setTitle(new_title);
		this.setPreferredSize(new Dimension(new_width,new_height));
		this.setMaximumSize(new Dimension(new_width, new_height));
		this.setMinimumSize(new Dimension(new_width, new_height));
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		initGui();
		this.setVisible(true);
		
		
		
		
	}

	/**
	 * This Method Initialized the GUI elements on the main game window.
	 * 
	 *
	 */
	private void initGui() {
		
		this.getContentPane().removeAll();
		this.setLayout(new BorderLayout());
		
		//gameJmenubar= (new JMenuBarComponent());
		
		
		gameJMenuBarComponent=new JMenuBarComponent();
		gameJMenuBar = gameJMenuBarComponent.getGameJMenuBar(this);
		this.setJMenuBar(gameJMenuBar);
	}
	
	
	
	
	
	
	
	
	
	
}
