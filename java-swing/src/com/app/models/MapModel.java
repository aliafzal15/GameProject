package com.app.models;

import java.awt.Point;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.app.utilities.FileStorage;

/**
 * This class is for the Map Model
 * 
 * @author Ali Afzal
 *
 */
public class MapModel {
	
	//public String mapSecret;
	//public String mapAutoGeneratedDescription;
	Point entryPoint;
	Point exitPoint;
	public boolean isEntryDone;
	public boolean isExitDone;
	public boolean isFighterPlaced;
	public boolean isZombiePlaced;
	public int mapGridSelection[][];
	public ArrayList<CharacterModel> mapCharacters;
	public ArrayList<ItemsModel> mapCharactersWornItems;
	public ArrayList<ItemsModel> mapCharactersBagItems;
	public ArrayList<ItemsModel> mapItems;
	public String mapDescriptionOnSave;
	int mapWidth;
	int mapHeight;
	public String mapFileName;

	/**
	 * The constructor for MapModel
	 * 
	 * @param new_entryPoint
	 *            entry point on the map
	 * @param new_exitPoint
	 *            exit point on the map
	 * @param new_isEntryDone
	 *            has an entry point
	 * @param new_isExitDone
	 *            has an exit point
	 * @param new_mapGridSelection
	 *            map grid selections
	 * @param new_mapWidth
	 *            map width
	 * @param new_mapHeight
	 *            map height
	 * @param mapDesc
	 * 			 Description generated at the time of saving the map regarding the validation
	 * @param fileNam
	 * 			  File Name of the Map as saved on the Hard drive.          
	 */
	public MapModel(Point new_entryPoint,
			Point new_exitPoint, boolean new_isEntryDone, boolean new_isExitDone, int[][] new_mapGridSelection,
			int new_mapWidth, int new_mapHeight,boolean isFighter,boolean isZombie,String mapDesc,String fileNam,
			ArrayList<CharacterModel> charsAll,ArrayList<ItemsModel>mpItems,ArrayList<ItemsModel> mapCharWorn,ArrayList<ItemsModel> mapCharBag){
		super();

		this.entryPoint = new_entryPoint;
		this.exitPoint = new_exitPoint;
		this.isEntryDone = new_isEntryDone;
		this.isExitDone = new_isExitDone;
		this.mapGridSelection = new_mapGridSelection;
		this.mapWidth = new_mapWidth;
		this.mapHeight = new_mapHeight;
		this.isFighterPlaced=isFighter;
		this.isZombiePlaced=isZombie;
		this.mapDescriptionOnSave=mapDesc;
		this.mapFileName=fileNam;
		this.mapCharacters=charsAll;
		this.mapItems=mpItems;
		this.mapCharactersWornItems=mapCharWorn;
		this.mapCharactersBagItems=mapCharBag;
	}

	/**
	 * Default Constructor
	 */ 
	public MapModel() {
		
		this.mapCharacters=new ArrayList();		
		this.mapCharactersWornItems=new ArrayList();
		this.mapCharactersBagItems=new ArrayList();
		this.mapItems=new ArrayList();

	}


	/**
	 * This method gets the isEntry done
	 * 
	 * @return entryDone Check if entry point is selected
	 */
	public boolean isEntryDone() {
		return isEntryDone;
	}

	/**
	 * This method sets the isEntryDone
	 * 
	 * @param new_isEntryDone
	 *            check if entry point selected
	 */
	public void setEntryDone(boolean new_isEntryDone) {
		this.isEntryDone = new_isEntryDone;
	}

	/**
	 * this method checks if the exit point is chosen
	 * 
	 * @return isExitDone boolean value
	 */
	public boolean isExitDone() {
		return isExitDone;
	}

	/**
	 * this method sets the exit point
	 * 
	 * @param new_isExitDone checks if exit point is selected
	 */
	public void setExitDone(boolean new_isExitDone) {
		this.isExitDone = new_isExitDone;
	}

	/**
	 * this method gets map grid selection
	 * 
	 * @return mapGridSelection map grid selection
	 */
	public int[][] getMapGridSelection() {
		return mapGridSelection;
	}

	/**
	 * this method sets the map grid selection
	 * 
	 * @param new_mapGridSelection
	 *            map grid selection
	 */
	public void setMapGridSelection(int[][] new_mapGridSelection) {
		this.mapGridSelection = new_mapGridSelection;
	}

	/**
	 * this method gets map width
	 * 
	 * @return mapwidth width of the map
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * this method sets map width
	 * 
	 * @param mapWidth
	 *            width of the map
	 */
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * this method gets the height of the map
	 * 
	 * @return mapHeight height of the map
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * this method sets the map height
	 * 
	 * @param mapHeight
	 *            height of the map
	 */
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	/**
	 * this returns the entry point on the map
	 * 
	 * @return entryPoint entry point on the map
	 */
	public Point getEntryPoint() {
		return entryPoint;
	}

	/**
	 * this method sets the entry point on the map
	 * 
	 * @param entryPoint
	 *            entry point on the map
	 */
	public void setEntryPoint(Point entryPoint) {
		this.entryPoint = entryPoint;
	}

	/**
	 * this method returns the exit point
	 * 
	 * @return exitPoint exit point on the map
	 */
	public Point getExitPoint() {
		return exitPoint;
	}

	/**
	 * this method sets the exit point on the map
	 * 
	 * @param new_exitPoint
	 *            exit point on the map
	 */
	public void setExitPoint(Point new_exitPoint) {
		this.exitPoint = new_exitPoint;
	}
	
	/**
	 * this method sets the map description when attempt to save
	 * 
	 * @param desc
	 *            description generated at the time of final validation
	 */
	public void setMapDescriptionOnSave(String desc) {
		this.mapDescriptionOnSave=desc;
	}
	
	/**
	 * this method sets the name of the file map as stored on hard drive
	 * 
	 * @param filename
	 *            name of the map file
	 */
	public void setMapFileName(String filename){
		this.mapFileName=filename;
	}
	
	/**
	 * this method returns the name of the file map as stored on hard drive
	 * 
	 * @return filename object of type String
	 *           
	 */
	public String getMapFileName(){
		return this.mapFileName;
	}
	
	/**
	 * this method returns the character in map
	 * 
	 * @return mapCharacters: character in the map
	 *           
	 */
	public ArrayList<CharacterModel> getMapCharacters(){
		
		return this.mapCharacters;
		
	}
	
	
	/**
	 * this method set the Items in Map
	 * 
	 * @param tempItems 
	 * 			Items List 
	 *           
	 */
	public void setMapItemsList(ArrayList<ItemsModel> tempItems){
		this.mapItems=tempItems;		
	}
	
	/**
	 * this method adds the Item in Map
	 * 
	 * @param item 
	 * 			Item to be added
	 *           
	 */
	public void addItemMAp(ItemsModel item){
		this.mapItems.add(item);		
	}
	
	
	/**
	 * this method returns the items in map
	 * 
	 * @return mapItems: items in the map
	 *           
	 */
	public ArrayList<ItemsModel> getMapItems(){
		
			return this.mapItems;
	}

}
