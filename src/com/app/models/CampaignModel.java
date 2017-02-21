package com.app.models;

import java.util.ArrayList;


/**
 * This Class is for the Campaign Model
 * 
 * 	
 */
public class CampaignModel {

private ArrayList<MapModel> campaignMaps;
private boolean isValidated;
private String campaignFileName;
	
/**
 * This Constructor initializes the class members.
 * 
 * 	
 */
	public CampaignModel(){
		
		this.campaignMaps=new ArrayList();
		this.isValidated=false;				
	}
	 
	/**
	 * This method adds the map in a campaign
	 * 
	 * @param map
	 * 			map of type MapModel
	 * 	
	 */	
	public void setCampaignMap(MapModel map){
		this.campaignMaps.add(map);
	}
	
	/**
	 * This method adds the map in a campaign
	 * 
	 * @return campaignMaps: ArrayList of all maps in the campaign
	 * 			
	 * 	
	 */	
	public ArrayList<MapModel> getCampaignMaps(){
		
		return this.campaignMaps;
	}
	
	/**
	 * This method sets the isValidated member
	 * 
	 * @param status
	 * 			boolean type value indicating if the map is validated or not.True for validation and False for no validation.
	 * 	
	 */
	public void setIsValidated(boolean status){
		this.isValidated=status;
		
	}
	
	/**
	 * This method gets the isValidated value.
	 * 
	 * @return isValidated
	 * 	
	 */	
	public boolean getIsValidated(){
		return this.isValidated;
	}

	/**
	 * This method sets the Campaign File Name as stored on Hard Drive.
	 * 
	 * @param filename
	 * 			Name of the file
	 * 	
	 */	
	public void setCampaignFileName(String filename){
		this.campaignFileName=filename;
	}
	
	/**
	 * This method gets the Campaign File Name as stored on Hard Drive.
	 * 
	 * @return filename: String type Name of the file.
	 * 			
	 * 	
	 */		
	public String getCampaignFileName(){
		return this.campaignFileName;
	}
	
	
}
