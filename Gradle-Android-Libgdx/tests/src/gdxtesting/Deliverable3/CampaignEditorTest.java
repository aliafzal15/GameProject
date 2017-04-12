package gdxtesting.Deliverable3;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Vector2;

import gdxtesting.GdxTestRunner;

import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.MapModel.EntryDoor;
import com.chaowang.ddgame.MenuModel.MapModel.ExitDoor;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
/**
 * the class is Junit test for Campaign
 * @author chao wang
 * @version 1.0
 */
 
@RunWith(GdxTestRunner.class)
public class CampaignEditorTest {
    Map map1, map2;
    Campaign campaign;
    
    /**
     * build campaign, and its items:map1 and map2;
     */
    @Before public void createCampaignSet(){
        map1 = new Map(1,3,"map1",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
        map2 = new Map(1,3,"map2",new EntryDoor(new Vector2(3,3)), new ExitDoor(new Vector2(2,2)));
        campaign = new Campaign();
    }
    
    /**
     * test if more  than 1 map can load to campaign
     */
	@Test
	public void testCampaignSize() {
		campaign.addToCampaign(map1);
		campaign.addToCampaign(map2);
		assertEquals(campaign.getSize(), 2);
	}
	
	/**
	 * Test if campaign is loading map according to order
	 */
	@Test
	public void testCampaignOrder() {
		campaign.addToCampaign(map2);
		campaign.addToCampaign(map1);
		assertEquals(campaign.getMapPack().get(1).getName(), "map1");
	}

}
