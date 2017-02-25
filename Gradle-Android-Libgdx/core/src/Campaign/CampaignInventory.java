package Campaign;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;


public class CampaignInventory {


    private Array<Campaign> campaignPack;

    public CampaignInventory() {
        this.campaignPack = new Array<Campaign>();
    }

    public Array<Campaign> getCampaignPack() {
		return campaignPack;
	}

	public void setCampaignPackPack(Array<Campaign> campaignPack) {
		this.campaignPack = campaignPack;
	}

	public void addToCampaignPack(Campaign campaign){
		campaignPack.add(campaign);
    }

	public Array<String> getCampaignPackInfo(){
        Array<String> itemPackInfo = new Array<String>();
        for (int i = 0; i < campaignPack.size; i++){
            itemPackInfo.add(i +"-" + campaignPack.get(i).getName() + "-"+
            		campaignPack.get(i).getSize());
        }
        return itemPackInfo;
    }

    public  void readFile() throws IOException {
        File file = new File("CampaignInventory.json");
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

    public void saveToFile(){

        FileHandle file = Gdx.files.local("CampaignInventory.json");
        file.write(false);
        Json json = new Json();
        String context;
        for (Campaign i : this.campaignPack){
            context = json.toJson(i) + System.getProperty("line.separator");
            file.writeString(context,true);
        }
    }

}
