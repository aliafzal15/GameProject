package com.chaowang.ddgame.CampaignModel;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * inventory for storing campaigns
 * @author chao wang
 * @version 1.0
 */
public class CampaignInventory {

	
    private Array<Campaign> campaignPack;
    /**
     * constructor
     */
    public CampaignInventory() {
        this.campaignPack = new Array<Campaign>();
    }
    /**
     * get campaign pack
     * @return campaign pack
     */
    public Array<Campaign> getCampaignPack() {
		return campaignPack;
	}
    /**
     * set campaign pack
     * @param campaignPack
     */
	public void setCampaignPackPack(Array<Campaign> campaignPack) {
		this.campaignPack = campaignPack;
	}
	/**
	 * add new campaign to inventory
	 * @param campaign
	 */
	public void addToCampaignPack(Campaign campaign){
        Campaign tmp = new Campaign(campaign);
		campaignPack.add(tmp);
    }
	/**
	 * get campaign pack information
	 * @return
	 */
	public Array<String> getCampaignPackInfo(){
        Array<String> itemPackInfo = new Array<String>();
        for (int i = 0; i < campaignPack.size; i++){
            itemPackInfo.add(i +"-" + campaignPack.get(i).getName() + "-"+
            		campaignPack.get(i).getSize());
        }
        return itemPackInfo;
    }
	/**
	 * read files about campaign inventory
	 * @throws IOException
	 */
    public  void readFile() throws IOException {
        File file = new File("data" + File.separator + "CampaignInventory.json");
        file.createNewFile(); // if file already exists will do nothing

        Scanner scanner = new Scanner(file);
        Json json = new Json();
        String context;
        Campaign campaign;
        while (scanner.hasNext()){
            context = scanner.nextLine();
            campaign = json.fromJson(Campaign.class, context);
            addToCampaignPack(campaign);
        }
        scanner.close();

    }
    /**
     * write files about campaign inventory
     */
    public void saveToFile(){

        FileHandle file = Gdx.files.local("data" + File.separator + "CampaignInventory.json");
        file.write(false);
        Json json = new Json();
        String context;
        for (Campaign i : this.campaignPack){
            context = json.toJson(i) + System.getProperty("line.separator");
            file.writeString(context,true);
        }
    }

}
