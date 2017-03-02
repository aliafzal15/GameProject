package Campaign;


import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import Map.Map;

/**
 * class for campaign editor
 * @author chao wang
 * @version 1.0
 */
public class Campaign implements Json.Serializable{


    private Array<Map> mapPack;
    private String name;
    /**
     * constructor
     */
    public Campaign() {
    	name = "default";
        this.mapPack = new Array<Map>();
    }
    /**
     * constructor
     * @param camp a certain campaign
     */
    public Campaign(Campaign camp){
		this.name = camp.getName();
		this.mapPack = new Array<Map>(camp.getMapPack());
	}
    /**
     * get map pack
     * @return array as map pack
     */
    public Array<Map> getMapPack() {
		return mapPack;
	}
    /**
     * set map pack
     * @param mapPack 
     */
	public void setMapPack(Array<Map> mapPack) {
		this.mapPack = mapPack;
	}
	/**
	 * add new map to campaign
	 * @param map 
	 */
	public void addToCampaign(Map map){
    	mapPack.add(map);
    }
    /**
     * get the campaign's name
     * @return a string as name
     */
    public String getName() {
		return name;
	}
    /**
     * set the campaign's name
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get the size of the campaign
	 * @return 
	 */
	public int getSize(){
		return mapPack.size;
	}
	
	
	/**
	 * switch to string type
	 */
	@Override
	public String toString() {
		return name + " [size:" + mapPack.size + "] ";
	}
	/**
	 * get the information about all maps in the campaign
	 * @return array as all maps' information 
	 */
	public Array<String> getMapListInfo(){
        Array<String> mapPackInfo = new Array<String>();
        for (int i = 0; i < mapPack.size; i++){
        	mapPackInfo.add(i +"-" + mapPack.get(i).getName() + "-"+
            		mapPack.get(i).getSize()+"-"+mapPack.get(i).getLevel());
        }
        return mapPackInfo;
    }
	/**
	 * write files about campaign's information
	 */
	@Override
	public void write(Json json) {
        json.writeValue("Name", name);
        json.writeValue("MapPack", mapPack, ArrayList.class, Map.class);
	}
	/**
	 * read files about campaign's information
	 */
	@Override
	public void read(Json json, JsonValue jsonData) {
        String context;
        name = jsonData.child.asString();
        JsonValue pointer = jsonData.child.next;
        Iterator<JsonValue> dataIterator;

        if(pointer != null){
            dataIterator = pointer.iterator();
            Map map;
            while(dataIterator.hasNext()){
                context = dataIterator.next().toString();
                map = json.fromJson(Map.class, context);
                mapPack.add(map);
            }
        }
	}

}
