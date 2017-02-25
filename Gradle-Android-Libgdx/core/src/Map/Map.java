package Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.chaowang.ddgame.PublicParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Items.Item;
import Character.Character;

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

    public Map(int level, int size, String name, EntryDoor entryEntryDoor, ExitDoor exitEntryDoor) {
        this(level, size, name);
        this.entryDoor = entryEntryDoor;
        this.exitDoor = exitEntryDoor;
    }

    public void addWallLocationList(int i, int j){
        Wall wall = new Wall(new Vector2(j* PublicParameter.mapPixelSize, i*PublicParameter.mapPixelSize));
        wallLocationList.add(wall);
    }

    public void removeWallLocationList(int i, int j){
        for (int k = 0; k< wallLocationList.size() ; k++) {
            if(wallLocationList.get(k).getPosition().x == j*PublicParameter.mapPixelSize
                    && wallLocationList.get(k).getPosition().y == i*PublicParameter.mapPixelSize ){
                wallLocationList.remove(k);
            }
        }
    }

    public void addItemLocationList(int i, int j, Item item ){
        itemLocationList.put(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize), item);
    }

    public Item removeItemLocationList(int i, int j){
       return itemLocationList.remove(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize));
    }

    public void addFriendLocationList(int i, int j, Character character ){
        friendLocationList.put(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize), character);
    }

    public Character removeFriendLocationList(int i, int j){
        return friendLocationList.remove(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize));
    }

    public void addEnemyLocationList(int i, int j, Character character ){
    	character.setFriendly(false);
        enemyLocationList.put(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize), character);
    }

    public Character removeEnemyLocationList(int i, int j ){
        return enemyLocationList.remove(new Vector2(j * PublicParameter.mapPixelSize, i * PublicParameter.mapPixelSize));
    }

    public int[][] getLocationMatrix() {
        return locationMatrix;
    }

    public void setSize(int size) {
        this.size = size;
        locationMatrix = new int[size][size];
        for (int i = 0; i < size ;i++){
            for (int j =0; j < size; j++)
                locationMatrix[i][j] = 0;
        }
        wallLocationList = new ArrayList<Wall>(size);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public EntryDoor getEntryDoor() {
        return entryDoor;
    }

    public void setEntryDoor(EntryDoor entryDoor) {
        this.entryDoor = entryDoor;
    }

    public ExitDoor getExitDoor() {
        return exitDoor;
    }

    public void setExitDoor( ExitDoor exitDoor) {
        this.exitDoor = exitDoor;
    }

    public ArrayList<Wall> getWallLocationList() {
        return wallLocationList;
    }

    public void setWallLocationList(ArrayList<Wall> wallLocationList) {
        this.wallLocationList = wallLocationList;
    }

    public HashMap<Vector2, Item> getItemLocationList() {
        return itemLocationList;
    }

    public void setItemLocationList(HashMap<Vector2, Item> itemLocationList) {
        this.itemLocationList = itemLocationList;
    }

    
    
    public HashMap<Vector2, Character> getEnemyLocationList() {
		return enemyLocationList;
	}

	public void setEnemyLocationList(HashMap<Vector2, Character> enemyLocationList) {
		this.enemyLocationList = enemyLocationList;
	}

	public HashMap<Vector2, Character> getFriendLocationList() {
        return friendLocationList;
    }

    public void setFriendLocationList(HashMap<Vector2, Character> enemyLocationList) {
        this.friendLocationList = enemyLocationList;
    }

    public int getSize() {
        return size;
    }

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
            entryDoor = new EntryDoor(new Vector2(x * PublicParameter.mapPixelSize, y * PublicParameter.mapPixelSize));
        }
        return  count;
    }

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
            exitDoor = new ExitDoor(new Vector2( x * PublicParameter.mapPixelSize, y * PublicParameter.mapPixelSize));
        }
        return count;
    }

    public void addWall(){
        for (int i=0; i< locationMatrix.length ; i++){
            for (int j = 0; j < locationMatrix[0].length; j++){
                if(locationMatrix[i][j] == 1 ){
                    wallLocationList.add(new Wall(new Vector2( j * PublicParameter.mapPixelSize, i* PublicParameter.mapPixelSize)));
                }
            }
        }
    }

    public Vector2 getDistanceOfEntryExit(){
        return new Vector2( Math.abs(entryDoor.getPosition().x - exitDoor.getPosition().x), Math.abs(entryDoor.getPosition().y - exitDoor.getPosition().y));
    }
    
    

    @Override
	public String toString() {
		return name + "[ " + size + " x " + size +"]";
	}

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


    }
}
