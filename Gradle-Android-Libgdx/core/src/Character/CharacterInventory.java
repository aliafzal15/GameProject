package Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Races.Race;

public class CharacterInventory {

    private Array<Character> characterPack;

    public CharacterInventory() {
        this.characterPack = new Array<Character>();
    }

    public Array<Character> getChatacterPack() {
        return characterPack;
    }

    public void addToInventory(Character character){
    	characterPack.add(character);
    }

    public Array<String> getCharacterListInfo(){
        Array<String> characterPackInfo = new Array<String>();
        for (int i = 0; i < characterPack.size; i++){
            characterPackInfo.add(i +"-" + characterPack.get(i).getName() + "-"+
            		characterPack.get(i).getRaceType().toString()+"-"+characterPack.get(i).getLevel());
        }
        return characterPackInfo;
    }

    public  void readFile() throws IOException {
        File file = new File("characterInventory.json");
        file.createNewFile(); // if file already exists will do nothing

        Scanner scanner = new Scanner(file);
        Json json = new Json();
        String context;
        Character character;
        while (scanner.hasNext()){
            context = scanner.nextLine();
            character = json.fromJson(Character.class, context);
            addToInventory(character);
        }
        scanner.close();

    }

    public void saveToFile(){

        FileHandle file = Gdx.files.local("characterInventory.json");
        file.write(false);
        Json json = new Json();
        String context;
        for (Character i : this.characterPack){
            context = json.toJson(i) + System.getProperty("line.separator");
            file.writeString(context,true);
        }

    }

}
