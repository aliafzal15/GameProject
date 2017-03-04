package com.app.mapValidation;


import java.awt.Point;

import com.app.models.MapModel;
import com.app.staticEngine.AppEnums;
import com.app.staticEngine.AppEnums.E_MapValidationDirecton;

/**
 * This class validates map features(Entry,Exit,Scenery) and placement of character.
 * 
 * @author Ali Afzal
 *
 */
public class MapFinalValidation {
	
	
	
	/**
	 * Default Constructor
	 * 
	 */	
	public MapFinalValidation(){
		
	}

	/**
	 * This method validates the map
	 * 
	 * @param newMapModel
	 *            as a map
	 * @return the routeStatus
	 */
	public String mapValidations(MapModel newMapModel) {
		String routeStatus = "";
		
		if (!newMapModel.isEntryDone || newMapModel.getEntryPoint() == null) {
			routeStatus = routeStatus+"Please First Select an Entry Point.";
			newMapModel.setMapDescriptionOnSave(routeStatus);
		}

		if (!newMapModel.isExitDone || newMapModel.getExitPoint() == null) {
			routeStatus =routeStatus+"Please First Select an Exit Point.";
			newMapModel.setMapDescriptionOnSave(routeStatus);
		}

		if(!newMapModel.isFighterPlaced && !newMapModel.isZombiePlaced){
			routeStatus =routeStatus+"Please Place either one Fighter/Zombie on Map.";
			newMapModel.setMapDescriptionOnSave(routeStatus);
			
		}

		return routeStatus;

	}
	
}
