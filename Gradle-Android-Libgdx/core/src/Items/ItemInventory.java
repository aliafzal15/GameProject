package Items;


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
/**
 * inventory for storing items
 * @author chao wang
 * @version 1.0
 */
public class ItemInventory {
    private Array<Item> itemPack;
    /**
     * constructor
     */
    public ItemInventory() {
        this.itemPack = new Array<Item>();
    }
    /**
     * get item pack
     * @return a array as item pack
     */
    public Array<Item> getItemPack() {
        return itemPack;
    }
    /**
     * get information for item pack
     * @return the information
     */
    public Array<String> getItemPackInfo(){
        Array<String> itemPackInfo = new Array<String>();
        itemPackInfo.add("");
        for (int i = 0; i < itemPack.size; i++){
            itemPackInfo.add(i +"-" + itemPack.get(i).getName() + "-"+
                    itemPack.get(i).getItemType().toString()+"-"+itemPack.get(i).getLevel());
        }
        return itemPackInfo;
    }
    /**
     * set item pack
     * @param itemPack
     */
    public void setItemPack(Array<Item> itemPack) {
        this.itemPack = itemPack;
    }
    /**
     * add new created item to inventory
     * @param item new created item
     */
    public void addToInventory(Item item){
        itemPack.add(item);
    }
    /**
     * read files for itemInventory information
     * @throws IOException
     */
    public  void readFile() throws IOException {
        File file = new File("itemInventory.json");
        file.createNewFile(); // if file already exists will do nothing

        Scanner scanner = new Scanner(file);
        Json json = new Json();
        String context;
        Item item;
        while (scanner.hasNext()){
            context = scanner.nextLine();
            item = json.fromJson(Item.class, context);
            addToInventory(item);
        }
        scanner.close();
    }
    /**
     * write files for itemInventory information
     */
    public void saveToFile(){
        FileHandle file = Gdx.files.local("itemInventory.json");
        file.write(false);
        Json json = new Json();
        String context;
        for (Item i : this.itemPack){
            context = json.toJson(i) + System.getProperty("line.separator");
            file.writeString(context,true);
        }

    }

}
