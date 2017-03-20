package com.app.utilities;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.app.models.CampaignModel;
import com.app.models.CharacterModel;
import com.app.models.ItemsModel;
import com.app.models.MapModel;
import com.google.gson.Gson;

/**
 * This class is for saving a map, campaign, saving items and saving characters
 * 
 * @author AliAfzal
 *
 */
public class FileStorage {
	
	/**
	 * Default Constructor
	 */
	public FileStorage(){
		
	}

	/**
	 * this method saves the map file
	 * 
	 * @param new_file
	 *            creates a new file object
	 * @param new_mapModel
	 *            creates a new map model
	 * @return string message
	 */
	public String saveMapFile(File new_file, MapModel new_mapModel) {
		String fileContent = getJsonFromObject(new_mapModel);
		fileContent = (new MiscellaneousHelper()).EncodeBase64(fileContent);
		String filePath = new_file.getPath();
		if (!filePath.endsWith(".dd"))
			filePath += ".dd";
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(fileContent);
			fileWriter.flush();
			fileWriter.close();
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR : " + e.getMessage();
		}
	}

	/**
	 * this method opens up a map file
	 * 
	 * @param new_file
	 *            new file object
	 * @return mapModel model of the map
	 */
	public MapModel openMapFile(File new_file) {

		String fileContent = "";
		try {
			fileContent = new String(Files.readAllBytes(Paths.get(new_file.getPath())));
			fileContent = (new MiscellaneousHelper()).DecodeBase64(fileContent);
			return (MapModel) getObjectFromJson(fileContent, MapModel.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method gets json from object
	 * 
	 * @param new_object
	 *            new object
	 * @return json converts gson to json and returns it
	 */
	public String getJsonFromObject(Object new_object) {
		Gson gson = new Gson();
		return gson.toJson(new_object);
	}

	/**
	 * this methods gets object from a json
	 * 
	 * @param new_jsonString
	 *            json string object
	 * @param new_class
	 *            new clas
	 * @return object object from json
	 */
	public Object getObjectFromJson(String new_jsonString, Class<?> new_class) {
		Gson gson = new Gson();
		return gson.fromJson(new_jsonString, new_class);
	}
	
	/**
	 * this method save the item in a file
	 * 
	 * @param items: which is an ArrayList of Type ItemsModel
	 */
	public void SaveItemInFile(ArrayList <ItemsModel> items) throws IOException{
		

	
		
		 File filename=new File("Items.txt");
		 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
		 FileWriter fw=new FileWriter(filename);
		 Writer out=new BufferedWriter(fw);	
		 
	
				 		 
		for(int i=0; i<items.size();i++){
		 
		 
			 
		   out.write(items.get(i).itemType);
		   out.write(";");
		   out.write(items.get(i).itemBonus);
		   out.write(System.lineSeparator());
		   
		 }
		
		out.close();
		 
		
		
	}
	
	/**
	 * this method reads items from a file
	 * 
	 * @return items: which is an ArrayList of Type ItemsModel
	 */	
	public ArrayList <ItemsModel> ReadItemInFile() throws IOException{
	
		
		String line;
		
		ArrayList reading=new ArrayList();
		
		 ArrayList <ItemsModel> newItems=new ArrayList();
		
		File filename=new File("Items.txt");
		
		
	if(filename.exists()){
		
			FileReader fr=new FileReader(filename);
		
			BufferedReader in=new BufferedReader(new FileReader(filename));
		
			while ((line=in.readLine())!=null){
				reading.add(line);		
			}
		 
		  in.close();
		  
		  
		  int sz2=reading.size();
		  
		  for (int j=0;j<sz2;j++){
			  
			  System.out.println("Object in file was "+reading.get(j).toString());
			  
		  }
		 
		 
		  //rebuilding the items arraylist after reading from file
		  String tempArray;
		  String[] tempItems;
		 
		  
		  
		  	for (int k=0;k < sz2;k++){
		  		tempArray=reading.get(k).toString();
		  		tempItems=tempArray.split(";");
		  		ItemsModel tempIt =new ItemsModel();
		  
		  		int i=0;
		  			tempIt.itemType=tempItems[i];
		  			tempIt.itemBonus=tempItems[i+1];
		  			
		  			
		  		newItems.add(tempIt);
		  		tempItems=null;
		  		tempIt=null;
		  
		  }
		  	
	}//if block
	
   	
  return newItems;
		  	 		
		
	}
	
	/**
	 * this method saves the Character in a file
	 * 
	 * @param chars: which is an ArrayList of type CharacterModel
	 */	
public void SaveCharInFile(ArrayList <CharacterModel> chars) throws IOException{
		


		 File filename=new File("Characters.txt");
		 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
		 FileWriter fw=new FileWriter(filename);
		 Writer out=new BufferedWriter(fw);	
		 
	
				 		 
		for(int i=0; i<chars.size();i++){
		 
		 
			 
		   out.write(chars.get(i).getCharType().toString());
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getStrength()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getDexterity()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getConstitution()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getCharLevel()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getStrMod()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getDexMod()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getConstMod()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getHitPoints()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getAttackBonus()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getDamageBonus()));
		   out.write(";");
		   out.write(Integer.toString(chars.get(i).getArmorClass()));
		   out.write(";");
		   out.write(chars.get(i).getFightingType());
		   out.write(System.lineSeparator());
		   
			
		   
		 }
		
		out.close();
		 
		
		
	}
	
/**
 * this method reads the character from a file
 * 
 * @return newChars: which is an ArrayList of type CharacterModel
 */
public ArrayList <CharacterModel> readCharacterInFile() throws IOException{
		
	String line;
	
	ArrayList reading=new ArrayList();
	
	 ArrayList <CharacterModel> newChars=new ArrayList();
	
	File filename=new File("Characters.txt");
	
	
if(filename.exists()){
	
		FileReader fr=new FileReader(filename);
	
		BufferedReader in=new BufferedReader(new FileReader(filename));
	
		while ((line=in.readLine())!=null){
			reading.add(line);		
		}
	 
	  in.close();
	  
	  
	  int sz2=reading.size();
	  
	  for (int j=0;j<sz2;j++){
		  
		  System.out.println("Object in file was "+reading.get(j).toString());
		  
	  }
	 
	 
	  //rebuilding the items arraylist after reading from file
	  String tempArray;
	  String[] tempChars;
	 
	  
	  
	  	for (int k=0;k < sz2;k++){
	  		tempArray=reading.get(k).toString();
	  		tempChars=tempArray.split(";");
	  		CharacterModel tempChar =new CharacterModel();
	  		 System.out.println("Size of Characters file array>>>>"+tempChars.length);
	  
	  		int i=0;
	  			tempChar.setCharType(tempChars[i]);
	  			tempChar.setStrength(Integer.parseInt(tempChars[i+1]));
	  			tempChar.setDexterity(Integer.parseInt(tempChars[i+2]));
	  			tempChar.setConstitution(Integer.parseInt(tempChars[i+3]));
	  			tempChar.setCharLevel(Integer.parseInt(tempChars[i+4]));
	  			tempChar.setStrMod(Integer.parseInt(tempChars[i+5]));
	  			tempChar.setDexMod(Integer.parseInt(tempChars[i+6]));
	  			tempChar.setConsMod(Integer.parseInt(tempChars[i+7]));
	  			tempChar.setHitPoints(Integer.parseInt(tempChars[i+8]));
	  			tempChar.setAttackBonus(Integer.parseInt(tempChars[i+9]));
	  			tempChar.setDamageBonus(Integer.parseInt(tempChars[i+10]));
	  			tempChar.setArmorClass(Integer.parseInt(tempChars[i+11]));
	  			tempChar.setFightingType(tempChars[i+12]);  						
	  			newChars.add(tempChar);
	  			tempChars=null;
	  			tempChar=null;
	  
	  }
	    in.close();
	  	
}//if block


return newChars;
	  	 		
	
}

/**
 * this method deletes all characters in File
 * 
 * @param items: which is an ArrayList of Type ItemsModel
 */
public void deleteCharacter()throws IOException{
	

	 File filename=new File("Characters.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 FileWriter fw=new FileWriter(filename);
	 Writer out=new BufferedWriter(fw);	
	 out.close();
	 
	 filename=new File("FighterBagItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 fw=new FileWriter(filename);
	 out=new BufferedWriter(fw);	
	 out.close();
	 
	 filename=new File("FighterWornItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 fw=new FileWriter(filename);
	 out=new BufferedWriter(fw);	
	 out.close();
	
	 filename=new File("ZombieBagItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 fw=new FileWriter(filename);
	 out=new BufferedWriter(fw);	
	 out.close();
	 
	 filename=new File("ZombieWornItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 fw=new FileWriter(filename);
	 out=new BufferedWriter(fw);	
	 out.close();
	
}

/**
 * this method saves the worn items by the Fighter character in the file
 * 
 * @param items, which is an ArrayList of Type ItemsModel
 */
public void saveWornItemDetailsFighter(ArrayList<ItemsModel> items) throws IOException{
	
	 File filename=new File("FighterWornItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 FileWriter fw=new FileWriter(filename);
	 Writer out=new BufferedWriter(fw);	
	 
if(items.size()>0){
			 		 
	for(int i=0; i<items.size();i++){
	 		 
	   out.write(items.get(i).itemType.toString());
	   out.write(":");
	   out.write(items.get(i).itemBonus.toString());
	   out.write(System.lineSeparator());	   
	 }
	
}
	
	out.close();
	 	
}

/**
 * this method reads the worn items by the Fighter character from the file
 * 
 * @param items: which is an ArrayList of Type ItemsModel
 */
public ArrayList <ItemsModel> readWornItemsFighter() throws IOException{
		
	String line;
	
	ArrayList reading=new ArrayList();
	
	ArrayList<ItemsModel> newItms=new ArrayList();
	
	File filename=new File("FighterWornItems.txt");
	
	
if(filename.exists()){
	
		FileReader fr=new FileReader(filename);
	
		BufferedReader in=new BufferedReader(new FileReader(filename));
	
		while ((line=in.readLine())!=null){
			reading.add(line);		
		}
	 
	  in.close();
	  
	  
	  int sz2=reading.size();
	  
	  for (int j=0;j<sz2;j++){
		  
		  System.out.println("Object in file was "+reading.get(j).toString());
		  
	  }
	 
	 
	  //rebuilding the items arraylist after reading from file
	  String tempArr;
	  String[] tempItms;
	 
	  
	  
	  	for (int k=0;k < sz2;k++){
	  		tempArr=reading.get(k).toString();
	  		tempItms=tempArr.split(":");
	  		ItemsModel tempItem =new ItemsModel();
	  		
	  		
	  
	  		int i=0;
	  			tempItem.itemType=tempItms[i];
	  			tempItem.itemBonus=tempItms[i+1];
	  		  			  						
	  			newItms.add(tempItem);
	  			//tempItems=null;
	  			//tempItem=null;
	  
	  }
	    in.close();
	  	
}//if block

System.out.println("Size of ItemsWorn>>>>"+newItms.size());
return newItms;
	  	 		
	
}

/**
 * this method saves the Bag Pack items of the Fighter in file
 * 
 * @param items: which is an ArrayList of Type ItemsModel
 */
public void saveBagItemDetailsFighter(ArrayList<ItemsModel> items) throws IOException{
	
	 File filename=new File("FighterBagItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 FileWriter fw=new FileWriter(filename);
	 Writer out=new BufferedWriter(fw);	
	 
if(items.size()>0){
			 		 
	for(int i=0; i<items.size();i++){
	 		 
	   out.write(items.get(i).itemType.toString());
	   out.write(":");
	   out.write(items.get(i).itemBonus.toString());
	   out.write(System.lineSeparator());	   
	 }
	
	out.close();
 } 	
}

/**
 * this method reads the Bag Pack items of the Fighter in file
 * 
 * @return items: which is an ArrayList of Type ItemsModel
 */
public ArrayList <ItemsModel> readBagItemsFighter() throws IOException{
	
	String line;
	
	ArrayList reading=new ArrayList();
	
	ArrayList<ItemsModel> newItms=new ArrayList();
	
	File filename=new File("FighterBagItems.txt");
	
	
if(filename.exists()){
	
		FileReader fr=new FileReader(filename);
	
		BufferedReader in=new BufferedReader(new FileReader(filename));
	
		while ((line=in.readLine())!=null){
			reading.add(line);		
		}
	 
	  in.close();
	  
	  
	  int sz2=reading.size();
	  
	  for (int j=0;j<sz2;j++){
		  
		  System.out.println("Object in file was "+reading.get(j).toString());
		  
	  }
	 
	 
	  //rebuilding the items arraylist after reading from file
	  String tempArr;
	  String[] tempItms;
	 
	  
	  
	  	for (int k=0;k < sz2;k++){
	  		tempArr=reading.get(k).toString();
	  		tempItms=tempArr.split(":");
	  		ItemsModel tempItem =new ItemsModel();
	  		
	  		
	  
	  		int i=0;
	  			tempItem.itemType=tempItms[i];
	  			tempItem.itemBonus=tempItms[i+1];
	  		  			  						
	  			newItms.add(tempItem);
	  			//tempItems=null;
	  			//tempItem=null;
	  
	  }
	    in.close();
	  	
}//if block

System.out.println("Size of ItemsBag>>>>"+newItms.size());
return newItms;
	  	 		
	
}

/**
 * this method reads the worn items of the Zombie in file
 * 
 * @return items: which is an ArrayList of Type ItemsModel
 */
public ArrayList <ItemsModel> readWornItemsZombie() throws IOException{
	
	

	
	String line;
	
	ArrayList reading=new ArrayList();
	
	ArrayList<ItemsModel> newItms=new ArrayList();
	
	File filename=new File("ZombieWornItems.txt");
	
	
if(filename.exists()){
	
		FileReader fr=new FileReader(filename);
	
		BufferedReader in=new BufferedReader(new FileReader(filename));
	
		while ((line=in.readLine())!=null){
			reading.add(line);		
		}
	 
	  in.close();
	  
	  
	  int sz2=reading.size();
	  
	  for (int j=0;j<sz2;j++){
		  
		  System.out.println("Object in file was "+reading.get(j).toString());
		  
	  }
	 
	 
	  //rebuilding the items arraylist after reading from file
	  String tempArr;
	  String[] tempItms;
	 
	  
	  
	  	for (int k=0;k < sz2;k++){
	  		tempArr=reading.get(k).toString();
	  		tempItms=tempArr.split(":");
	  		ItemsModel tempItem =new ItemsModel();
	  		
	  		
	  
	  		int i=0;
	  			tempItem.itemType=tempItms[i];
	  			tempItem.itemBonus=tempItms[i+1];
	  		  			  						
	  			newItms.add(tempItem);
	  			//tempItems=null;
	  			//tempItem=null;
	  
	  }
	    in.close();
	  	
}//if block

System.out.println("Size of ItemsWornZombie>>>>"+newItms.size());
return newItms;
	  	 		
	
}
/**
 * this method reads the Bag Pack items of the Zombie in file
 * 
 * @return items: which is an ArrayList of Type ItemsModel
 */
public ArrayList <ItemsModel> readBagItemsZombie() throws IOException{
	
	

	
	String line;
	
	ArrayList reading=new ArrayList();
	
	ArrayList<ItemsModel> newItms=new ArrayList();
	
	File filename=new File("ZombieBagItems.txt");
	
	
if(filename.exists()){
	
		FileReader fr=new FileReader(filename);
	
		BufferedReader in=new BufferedReader(new FileReader(filename));
	
		while ((line=in.readLine())!=null){
			reading.add(line);		
		}
	 
	  in.close();
	  
	  
	  int sz2=reading.size();
	  
	  for (int j=0;j<sz2;j++){
		  
		  System.out.println("Object in file was "+reading.get(j).toString());
		  
	  }
	 
	 
	  //rebuilding the items arraylist after reading from file
	  String tempArr;
	  String[] tempItms;
	 
	  
	  
	  	for (int k=0;k < sz2;k++){
	  		tempArr=reading.get(k).toString();
	  		tempItms=tempArr.split(":");
	  		ItemsModel tempItem =new ItemsModel();
	  		
	  		
	  
	  		int i=0;
	  			tempItem.itemType=tempItms[i];
	  			tempItem.itemBonus=tempItms[i+1];
	  		  			  						
	  			newItms.add(tempItem);
	  			//tempItems=null;
	  			//tempItem=null;
	  
	  }
	    in.close();
	  	
}//if block

System.out.println("Size of ZombieItemsBag>>>>"+newItms.size());
return newItms;
	  	 		
	
}

/**
 * this method saves the Worn Items of the Zombie in file
 * 
 * @param items: which is an ArrayList of Type ItemsModel
 */
public void saveWornItemDetailsZombie(ArrayList<ItemsModel> items) throws IOException{
	
	 File filename=new File("ZombieWornItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 FileWriter fw=new FileWriter(filename);
	 Writer out=new BufferedWriter(fw);	
	 
if(items.size()>0){
			 		 
	for(int i=0; i<items.size();i++){
	 		 
	   out.write(items.get(i).itemType.toString());
	   out.write(":");
	   out.write(items.get(i).itemBonus.toString());
	   out.write(System.lineSeparator());	   
	 }
	
}
	
	out.close();
	 	
}

/**
 * this method saves the Bag Pack items of the Zombie in file
 * 
 * @param items: which is an ArrayList of Type ItemsModel
 */
public void saveBagItemDetailsZombie(ArrayList<ItemsModel> items) throws IOException{
	
	 File filename=new File("ZombieBagItems.txt");
	 //FileWriter fw=new FileWriter(filename,true);//true will ensure appending of file
	 FileWriter fw=new FileWriter(filename);
	 Writer out=new BufferedWriter(fw);	
	 
if(items.size()>0){
			 		 
	for(int i=0; i<items.size();i++){
	 		 
	   out.write(items.get(i).itemType.toString());
	   out.write(":");
	   out.write(items.get(i).itemBonus.toString());
	   out.write(System.lineSeparator());	   
	 }
	
	out.close();
} 	
}	

/**
 * this method saves the campaign in file
 * 
 * @param camp: which is an object of CampaignModel
 */
public String saveCampaignFile(File new_file, CampaignModel camp){
	

		   //write converted json data to a file named "CountryGSON.json"
		   String fileContent = getJsonFromObject(camp);
		   //fileContent = (new MiscellaneousHelper()).EncodeBase64(fileContent);
		   
			String filePath = new_file.getPath();
				if (!filePath.endsWith(".ddc")){
						filePath += ".ddc";
				}
						try {
							FileWriter fileWriter = new FileWriter(filePath);
							fileWriter.write(fileContent);
							fileWriter.write(System.lineSeparator());
							fileWriter.flush();
							fileWriter.close();
							return "SUCCESS";
						} catch (Exception e) {
							e.printStackTrace();
							return "ERROR : " + e.getMessage();
						}
		   
	}

/**
 * this method reads the campaign details in file
 * 
 * @return camp: which is an object of CampaignModel
 */
public CampaignModel openCampaignFile(File new_file) throws IOException {

	String line;
	
	CampaignModel tempCampiagnModel = null;
 			
	File filename=new File(new_file.getPath());			
	FileReader fr=new FileReader(filename);
	BufferedReader in=new BufferedReader(fr);

	
	while ((line=in.readLine())!=null){
		
		//line = EncodeBase64(line);			
		tempCampiagnModel= (CampaignModel) getObjectFromJson(line, CampaignModel.class);		

	}
  in.close();
		
		return tempCampiagnModel;

}




}
