package com.chaowang.ddgame.MenuModel.MapModel;

import com.chaowang.ddgame.PublicParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
/**
 * class for map editor
 * @author chao wang
 * @version 1.0
 */
public class Map implements Json.Serializable{

    private int level = 1;
    private int size;
    private String name;
    private EntryDoor entryDoor;
    private ExitDoor exitDoor;
    private ArrayList<Wall> wallLocationList;
    private HashMap<Vector2,Item> itemLocationList;
    private HashMap<Vector2,Character> friendLocationList;
    private HashMap<Vector2,Character> enemyLocationList;

    private int[][]  locationMatrix;
    /**
     * constructor
     */
    public  Map(){
        this.level = 0 ;
        this.name = "";
        this.size = 0;
        locationMatrix = new int[1][1];
        locationMatrix[0][0] = 0;
        itemLocationList = new HashMap<Vector2, Item>();
        friendLocationList = new HashMap<Vector2, Character>();
        enemyLocationList = new HashMap<Vector2, Character>();
        wallLocationList = new ArrayList<Wall>();
    }
    /**
     * 
     * constructor
     * @param level the level of the map
     * @param size the size of the map
     * @param name the name
     */
    public Map(int level, int size, String name) {
        this.level = level;
        this.size = size;
        this.name = name;
        locationMatrix = new int[size][size];
        for (int i = 0; i < size ;i++){
            for (int j =0; j < size; j++)
                locationMatrix[i][j] = 0;
        }
        wallLocationList = new ArrayList<Wall>(size);
        itemLocationList = new HashMap<Vector2, Item>();
        friendLocationList = new HashMap<Vector2, Character>();
        enemyLocationList = new HashMap<Vector2, Character>();
    }
    /**
     * constructor
     * @param level the level of the map
     * @param size the size of the map
     * @param name the name
     * @param entryEntryDoor the information about entry door
     * @param exitEntryDoor the information about exit door
     */
    public Map(int level, int size, String name, EntryDoor entryEntryDoor, ExitDoor exitEntryDoor) {
        this(level, size, name);
        this.entryDoor = entryEntryDoor;
        this.exitDoor = exitEntryDoor;
    }

    /**
     * LocationMatrix setter
     * @param locationMatrix
     */
    public void setLocationMatrix(int[][] locationMatrix) {
        this.locationMatrix = locationMatrix;
    }

    /**
     * add to a list about wall locations 
     * @param i
     * @param j
     */
    public void addWallLocationList(int i, int j){
        Wall wall = new Wall(new Vector2(j* PublicParameter.MAP_PIXEL_SIZE, i*PublicParameter.MAP_PIXEL_SIZE));
        wallLocationList.add(wall);
    }
    /**
     * remove from a list about wall locations 
     * @param i
     * @param j
     */
    public void removeWallLocationList(int i, int j){
        for (int k = 0; k< wallLocationList.size() ; k++) {
            if(wallLocationList.get(k).getPosition().x == j*PublicParameter.MAP_PIXEL_SIZE
                    && wallLocationList.get(k).getPosition().y == i*PublicParameter.MAP_PIXEL_SIZE ){
                wallLocationList.remove(k);
            }
        }
    }
    /**
     * add to a list about item locations 
     * @param i
     * @param j
     * @param item
     */
    public void addItemLocationList(int i, int j, Item item ){
        itemLocationList.put(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE), item);
    }
    /**
     * remove from a list about item locations 
     * @param i
     * @param j
     * @return
     */
    public Item removeItemLocationList(int i, int j){
       return itemLocationList.remove(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE));
    }
    /**
     * add to a list about friends locations 
     * @param i
     * @param j
     * @param character
     */
    public void addFriendLocationList(int i, int j, Character character ){
//    	character.setFriendly(true);
        friendLocationList.put(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE), character);
    }
    /**
     * remove from a list about friends locations 
     * @param i
     * @param j
     * @return
     */
    public Character removeFriendLocationList(int i, int j){
        return friendLocationList.remove(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE));
    }
    /**
     * add to a list about enemy locations 
     * @param i
     * @param j
     * @param character
     */
    public void addEnemyLocationList(int i, int j, Character character ){
//    	character.setFriendly(false);
        enemyLocationList.put(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE), character);
    }
    /**
     * remove from a list about enemy locations 
     * @param i
     * @param j
     * @return
     */
    public Character removeEnemyLocationList(int i, int j ){
        return enemyLocationList.remove(new Vector2(j * PublicParameter.MAP_PIXEL_SIZE, i * PublicParameter.MAP_PIXEL_SIZE));
    }
    /**
     * get the matrix of map location
     * @return the matrix of map location
     */
    public int[][] getLocationMatrix() {
        return locationMatrix;
    }
    /**
     * set the size of the map
     * @param size the size of the map
     */
    public void setSize(int size) {
        this.size = size;
        locationMatrix = new int[size][size];
        for (int i = 0; i < size ;i++){
            for (int j =0; j < size; j++)
                locationMatrix[i][j] = 0;
        }
        wallLocationList = new ArrayList<Wall>(size);
    }
    /**
     * get the name
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * set the names
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * get the map level
     * @return map level
     */
    public int getLevel() {
        return level;
    }
    /**
     * set the map level
     * @param level map level
     */
    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * get the entry door information
     * @return entry door information
     */
    public EntryDoor getEntryDoor() {
        return entryDoor;
    }
    /**
     * set the entry door information
     * @param entryDoor the entry door
     */
    public void setEntryDoor(EntryDoor entryDoor) {
        this.entryDoor = entryDoor;
    }
    /**
     * get the exit door information
     * @return exit door information
     */
    public ExitDoor getExitDoor() {
        return exitDoor;
    }
    /**
     * set the exit door information
     * @param exitDoor exit door information
     */
    public void setExitDoor( ExitDoor exitDoor) {
        this.exitDoor = exitDoor;
    }
    /**
     * get wall locations
     * @return wall locations
     */
    public ArrayList<Wall> getWallLocationList() {
        return wallLocationList;
    }
    /**
     * set wall locations
     * @param wallLocationList wall locations information
     */
    public void setWallLocationList(ArrayList<Wall> wallLocationList) {
        this.wallLocationList = wallLocationList;
    }
    /**
     * get item locations
     * @return item locations
     */
    public HashMap<Vector2, Item> getItemLocationList() {
        return itemLocationList;
    }
    /**
     * set item locations
     * @param itemLocationList item locations
     */
    public void setItemLocationList(HashMap<Vector2, Item> itemLocationList) {
        this.itemLocationList = itemLocationList;
    }
    /**
     * get enemy locations list
     * @return enemy locations
     */
    public HashMap<Vector2, Character> getEnemyLocationList() {
		return enemyLocationList;
	}
    /**
     * set enemy locations
     * @param enemyLocationList enemy locations
     */
	public void setEnemyLocationList(HashMap<Vector2, Character> enemyLocationList) {
		this.enemyLocationList = enemyLocationList;
	}
	/**
	 * get friend locations list
	 * @return friend locations
	 */
	public HashMap<Vector2, Character> getFriendLocationList() {
        return friendLocationList;
    }
	/**
	 * set friend locations 
	 * @param friendLocationList
	 */
    public void setFriendLocationList(HashMap<Vector2, Character> friendLocationList) {
        this.friendLocationList = friendLocationList;
    }
    /**
     * get the size of map
     * @return size
     */
    public int getSize() {
        return size;
    }
    /**
     * validate entry door
     * @return the amount of entry doors
     */
    public int validateEntryDoor() {
        int x = 0, y = 0, count = 0;
        for (int i = 0 ; i< locationMatrix.length; i ++){
            for (int j = 0 ; j < locationMatrix[0].length; j++ ){
                if(locationMatrix[i][j] == 2 ){
                    count ++;
                    x = j; y = i;
                }
            }
        }
        if (count == 1){
            entryDoor = new EntryDoor(new Vector2(x * PublicParameter.MAP_PIXEL_SIZE, (size -1 - y) * PublicParameter.MAP_PIXEL_SIZE));
        }
        return  count;
    }
    /**
     * map validation for doors
     * @return the amount of doors
     */
    public int validateExitDoor() {
        int x = 0, y = 0, count = 0;
        for (int i = 0 ; i< locationMatrix.length; i ++){
            for (int j = 0 ; j < locationMatrix[0].length; j++ ){
                if(locationMatrix[i][j] == 3 ){
                    count ++;
                    x = j; y = i;
                }
            }
        }
        if(count == 1){
            exitDoor = new ExitDoor(new Vector2( x * PublicParameter.MAP_PIXEL_SIZE, (size -1 - y) * PublicParameter.MAP_PIXEL_SIZE));
        }
        if( y == (size - 1)){
            count = -1;
        }
        return count;
    }
    /**
     * add wall
     */
    public void addWall(){
        for (int i =locationMatrix.length-1 ; i>=0 ; i--){
            for (int j = 0; j < locationMatrix[0].length; j++){
                if(locationMatrix[i][j] == 1 ){
                    wallLocationList.add(new Wall(new Vector2( j * PublicParameter.MAP_PIXEL_SIZE, (size - 1 - i)* PublicParameter.MAP_PIXEL_SIZE)));
                }
            }
        }
    }
    /**
     * get the distance between entry door and exit door
     * @return distance
     */
    public Vector2 getDistanceOfEntryExit(){
        return new Vector2( Math.abs(entryDoor.x - exitDoor.x), Math.abs(entryDoor.y - exitDoor.y));
    }
    /**
     * switch to string type
     */
    @Override
	public String toString() {
		return name + " [" + size + " x " + size +"]";
	}
    /**
     * @return  string detail information in the map
     */
    public String getMapInfo() {
        return "Name: "+this.name + "| items : " + this.itemLocationList.size() + "| NPC : " + this.friendLocationList.size() +
                "| enemies: "+this.enemyLocationList.size();
    }

    public void adjustLevel(int level){
    	this.level = level;
        Iterator<Vector2> keySetIterator = itemLocationList.keySet().iterator();
        Vector2 cur;
        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            itemLocationList.get(cur).setLevel((int) Math.ceil( level / 4.0 ));
        }

        keySetIterator = enemyLocationList.keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(enemyLocationList.get(cur).getLevel() != level){
                enemyLocationList.get(cur).setLevel(level);
            }
        }

        keySetIterator = friendLocationList.keySet().iterator();

        while(keySetIterator.hasNext()){
            cur = keySetIterator.next();
            if(friendLocationList.get(cur).getLevel() != level){
                friendLocationList.get(cur).setLevel(level);
            }
        }
    }
	/**
	 * write files for map information
	 */
	@Override
    public void write(Json json) {
        json.writeValue("Name", name);
        json.writeValue("Level", level);
        json.writeValue("Size", size);
        json.writeValue("EntryDoor", entryDoor, EntryDoor.class);
        json.writeValue("ExitDoor", exitDoor, ExitDoor.class);
        json.writeValue("LocationMatrix", locationMatrix);
        json.writeValue("wallLocationList", wallLocationList, ArrayList.class, Wall.class);
        json.writeValue("itemLocationList", itemLocationList, HashMap.class, Item.class);
        json.writeValue("friendLocationList", friendLocationList, HashMap.class, Character.class);
        json.writeValue("enemyLocationList", enemyLocationList, HashMap.class, Character.class);

    }
	/**
	 * read files for map information
	 */
    @Override
    public void read(Json json, JsonValue jsonData) {
        String context;
        name = jsonData.child.asString();
        level = jsonData.child.next.asInt();
        size = jsonData.child.next.next.asInt();
        context = jsonData.child.next.next.next.toString();
        context = context.substring(context.indexOf("{")-1);
        entryDoor = json.fromJson(EntryDoor.class, context);
        context = jsonData.child.next.next.next.next.toString();
        context = context.substring(context.indexOf("{")-1);
        exitDoor = json.fromJson(ExitDoor.class, context);

        Iterator<JsonValue> dataIterator;
        JsonValue pointer = jsonData.child.next.next.next.next.next;
        if(pointer != null) {
            dataIterator = pointer.iterator();
            locationMatrix = new int[size][size];
            for( int i = 0 ; i < size; i++){
                locationMatrix[i] = dataIterator.next().asIntArray();
            }
        }

        pointer = jsonData.child.next.next.next.next.next.next;
        if(pointer != null){
            dataIterator = pointer.iterator();
            Wall wall;
            while(dataIterator.hasNext()){
                context = dataIterator.next().toString();
                wall = json.fromJson(Wall.class, context);
                wallLocationList.add(wall);
            }
        }

        Vector2 location;
        pointer = jsonData.child.next.next.next.next.next.next.next;
        if(pointer != null){
            dataIterator = pointer.iterator();
            Item item;
            JsonValue dataValue;
            while(dataIterator.hasNext()){
                dataValue= dataIterator.next();
                context = dataValue.name();
                location = new Vector2(Float.parseFloat(context.substring(context.indexOf("(")+1,dataValue.name.indexOf(",")))
                        , Float.parseFloat(context.substring(context.indexOf(",")+1,dataValue.name.indexOf(")"))));
                context = dataValue.toString();
                context = context.substring(context.indexOf("{")-1);
                item = json.fromJson(Item.class, context);
                itemLocationList.put(location,item);
            }
        }

        pointer = jsonData.child.next.next.next.next.next.next.next.next;
        if(pointer != null){
            dataIterator = pointer.iterator();
            Character friend;
            JsonValue dataValue;
            while(dataIterator.hasNext()){
                dataValue= dataIterator.next();
                context = dataValue.name();
                location = new Vector2(Float.parseFloat(context.substring(context.indexOf("(")+1,dataValue.name.indexOf(",")))
                        , Float.parseFloat(context.substring(context.indexOf(",")+1,dataValue.name.indexOf(")"))));
                context = dataValue.toString();
                context = context.substring(context.indexOf("{")-1);
                friend = json.fromJson(Character.class, context);
                friendLocationList.put(location,friend);
            }
        }
        
        pointer = jsonData.child.next.next.next.next.next.next.next.next.next;
        if(pointer != null){
            dataIterator = pointer.iterator();
            Character enemy;
            JsonValue dataValue;
            while(dataIterator.hasNext()){
                dataValue= dataIterator.next();
                context = dataValue.name();
                location = new Vector2(Float.parseFloat(context.substring(context.indexOf("(")+1,dataValue.name.indexOf(",")))
                        , Float.parseFloat(context.substring(context.indexOf(",")+1,dataValue.name.indexOf(")"))));
                context = dataValue.toString();
                context = context.substring(context.indexOf("{")-1);
                enemy = json.fromJson(Character.class, context);
                enemyLocationList.put(location,enemy);
            }
        }

    }
}
