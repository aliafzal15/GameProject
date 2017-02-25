package Campaign;


import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import Map.Map;


public class Campaign implements Json.Serializable{


    private Array<Map> mapPack;
    private String name;

    public Campaign() {
    	name = "default";
        this.mapPack = new Array<Map>();
    }

    public Array<Map> getMapPack() {
		return mapPack;
	}

	public void setMapPack(Array<Map> mapPack) {
		this.mapPack = mapPack;
	}

	public void addToCampaign(Map map){
    	mapPack.add(map);
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize(){
		return mapPack.size;
	}
	
	
	
	@Override
	public String toString() {
		return name + " [" + mapPack.size + "] ";
	}

	public Array<String> getMapListInfo(){
        Array<String> mapPackInfo = new Array<String>();
        for (int i = 0; i < mapPack.size; i++){
        	mapPackInfo.add(i +"-" + mapPack.get(i).getName() + "-"+
            		mapPack.get(i).getSize()+"-"+mapPack.get(i).getLevel());
        }
        return mapPackInfo;
    }

	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
        json.writeValue("Name", name);
        json.writeValue("MapPack", mapPack, ArrayList.class, Map.class);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
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
