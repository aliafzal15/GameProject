package com.app.staticEngine;



/**
 * This class contains all constant variables such as windows width, height of window for main window and child window, 
 * titles, menu items names and image paths
 * 
 * @author Ali Afzal
 *
 */
public class AppStatics {

	// Game Statistics
	public static final int WINDOW_WIDTH = 840;
	public static final int WINDOW_HEIGHT = (int) WINDOW_WIDTH / 12 * 9;

	public static final int CHILD_POPUP_WINDOW_WIDTH = (int) WINDOW_WIDTH - 100;
	public static final int CHILD_POPUP_WINDOW_HEIGHT = (int) WINDOW_HEIGHT - 100;

	// Window Titles
	public static final String TITLE_MAP_EDITOR = "MAP EDITOR";
	public static final String MAP_MODE_CREATE = "(CREATE)";
	public static final String MAP_MODE_OPEN = "(OPEN)";

	// MENU Related Items
	public static final String MENU_FILE = "FILE";
	public static final String MENU_HELP = "HELP";
	public static final String MENU_ITEM_PLAY = "PLAY";
	public static final String MENU_ITEM_CREATE_MAP = "CREATE MAP";
	public static final String MENU_ITEM_OPEN_MAP = "OPEN_MAP";
	public static final String MENU_ITEM_ABOUT = "ABOUT";
	public static final String MENU_ITEM_SAVE = "SAVE";
	public static final String MENU_ITEM_EXIT = "EXIT";
	public static final String MENU_ITEM_EDITOR = "ITEM EDITOR";
	public static final String MENU_Character_EDITOR = "CHARACTER EDITOR";
	public static final String MENU_Campaign_EDITOR = "CAMPAIGN EDITOR";

	// -- Images Paths
	public static final String IMAGE_PATH_GAME_BK = "images/gameBk.png";


	//
	public static  String MAP_PATH_BOUNDARY_BUTTONS_NAME = "";
}
