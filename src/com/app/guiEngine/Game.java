package com.app.guiEngine;

import com.app.menus.GameWindow;
import com.app.staticEngine.*;

/**
 * 
 * @author AliAfzal
 *
 * Main Class that initiates the creation of the project
 *
 */


public class Game {

	/**
	 * Constructor of the Game Class
	 */
	public Game() {

		new GameWindow(AppStatics.WINDOW_WIDTH, AppStatics.WINDOW_HEIGHT,
				"D&D Game by \"Ali Afzal\".", this);

	}

	/**
	 * method that will start the game process and threads
	 */
	public void start() {

	}

	/**
	 * Main Method of the class that creates the Game Instance.
	 * 
	 * @param args
	 *            contains the supplied command-line arguments as an array of
	 *            String objects
	 */
	public static void main(String args[]) {
		new Game();
	}

	// END
}

