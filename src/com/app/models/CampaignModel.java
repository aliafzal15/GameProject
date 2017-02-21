package com.app.models;

import java.util.ArrayList;

public class CampaignModel {

private ArrayList<MapModel> campaignMaps;
private boolean isValidated;
private String campaignFileName;
	

	public CampaignModel(){
		
		this.campaignMaps=new ArrayList();
		this.isValidated=false;				
	}
	 
	
	public void setCampaignMap(MapModel map){
		this.campaignMaps.add(map);
	}
	
	public ArrayList<MapModel> getCampaignMaps(){
		
		return this.campaignMaps;
	}
	
	public void setIsValidated(boolean status){
		this.isValidated=status;
		
	}
	
	public boolean getIsValidated(){
		return this.isValidated;
	}

	public void setCampaignFileName(String filename){
		this.campaignFileName=filename;
	}
	
	public String getCampaignFileName(){
		return this.campaignFileName;
	}
	
	
}
