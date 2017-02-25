package Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class MapInventory {

    private Array<Map> mapPack;

	public MapInventory() {
        this.mapPack = new Array<Map>();
    }

    public void setMapPack(Array<Map> mapPack) {
		this.mapPack = mapPack;
	}
    
    public Array<Map> getMapPack() {
        return mapPack;
    }

    public void addToInventory(Map Map){
    	mapPack.add(Map);
    }

    public Array<String> getMapListInfo(){
        Array<String> mapPackInfo = new Array<String>();
        mapPackInfo.add("");
        for (int i = 0; i < mapPack.size; i++){
        	mapPackInfo.add(i+"-"+ mapPack.get(i).getName()
                    +"-item:"+mapPack.get(i).getItemLocationList().size()
                    +"-NPC:"+mapPack.get(i).getFriendLocationList().size()
                    +"-enemy:"+mapPack.get(i).getEnemyLocationList().size());
        }
        return mapPackInfo;
    }

    public  void readFile() throws IOException {
        File file = new File("mapInventory.json");
        file.createNewFile(); // if file already exists will do nothing

        Scanner scanner = new Scanner(file);
        Json json = new Json();
        String context;
        Map map;
        while (scanner.hasNext()){
            context = scanner.nextLine();
            map = json.fromJson(Map.class, context);
            addToInventory(map);
        }
        scanner.close();

    }

    public void saveToFile(){

        FileHandle file = Gdx.files.local("mapInventory.json");
        file.write(false);
        Json json = new Json();
        String context;
        for (Map i : this.mapPack){
            context = json.toJson(i) + System.getProperty("line.separator");
            file.writeString(context,true);
        }
    }

}
