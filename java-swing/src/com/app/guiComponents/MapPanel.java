package com.app.guiComponents;

import javax.swing.JPanel;

import com.app.models.MapModel;

public class MapPanel extends JPanel{

	private MapModel gameMap;
	
	
	public MapPanel(MapModel newMapModel){	
		gameMap=newMapModel;		
	}
	
}
