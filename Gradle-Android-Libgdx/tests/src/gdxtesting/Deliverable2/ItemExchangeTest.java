package gdxtesting.Deliverable2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.DDGame;
import com.chaowang.ddgame.GameController.ItemExchangeController;
import com.chaowang.ddgame.MenuModel.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.MapModel.EntryDoor;
import com.chaowang.ddgame.MenuModel.MapModel.ExitDoor;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.MenuController.MapController;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameView.GameItemExchangeScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertTrue;


/**
 * the class is Junit test for Game interaction
 * @author chao wang
 * @version 1.0
 */
@RunWith(GdxTestRunner.class)
public class ItemExchangeTest {

    private Map map;
    private Item item;
    private Character character;
    private int[][] mapMatrix;
    private MapController controller;
    private Player player;
    private Campaign campaign;

    @Before
    public void beforeGameTest(){
        // create item
        item = new Item(Item.ItemType.ARMOR, "unitTest", 1, EnchantedAbility.ARMORCLASS, null);
        // create character
        character = new Character();
        character.setAbilities(new int[] {5,5,5,5,5,5});
        character.changeLevel(1);
        character.addToBackpack(item);
        character.loadEquipment(item);
        // create map
        map = new Map(1,4,"name",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
        mapMatrix = new int[map.getSize()][map.getSize()];
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
        map.addFriendLocationList(3,3,character);
        map.addEnemyLocationList(2,3,character);
        map.addItemLocationList(1,3, item);
        //create campaign;
        campaign = new Campaign();
        campaign.addToCampaign(map);
        campaign.addToCampaign(map);
        // create player
        player  = new Player(new Vector2(1f, 1f),character);
    }


    /**
     * Test if player can echange item with NPC, since it is random exchange
     * we exchange player & NPC sharing the same character, and same backpack item
     * assert criteria, is either exchange a different item, or exchange the same item,
     * NPC's same item remain at the same position
     */
    @Test
    public void testExchangeItem(){
        Game game = new DDGame();
        Vector2 enemyPosition =  new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 2 * PublicParameter.MAP_PIXEL_SIZE);
        GameItemExchangeScreen screen = new GameItemExchangeScreen(game, player, map, campaign, enemyPosition, false);
        ItemExchangeController controller = new ItemExchangeController(screen, player, map.getEnemyLocationList().get(enemyPosition));
        Item.ItemType type = player.getCharacter().getBackpack().get( player.getCharacter().getBackpack().size()-1).getItemType();
        controller.exchangeItem(player.getCharacter().getBackpack().size()-1);
        assertTrue(player.getCharacter().getBackpack().get(player.getCharacter().getBackpack().size()-1).getItemType() != type
                || map.getEnemyLocationList().get(enemyPosition).getBackpack().get(map.getEnemyLocationList().get(enemyPosition).getBackpack().size() -1 ).getItemType() == type);
    }

    /**
     * internal use to build location matrix, instead of read from view
     */
    private void buildLocationMatrix() {
        // create external matrix for map object, wall is 1, entrance is 2, exit is 3
        for (int i =0 ; i < map.getSize(); i++){
            for(int j = 0; j < map.getSize(); j++){
                if(i == map.getEntryDoor().y && j == map.getEntryDoor().x){
                    mapMatrix[i][j] = 2;
                }
                else if (i == map.getExitDoor().y && j == map.getExitDoor().x){
                    mapMatrix[i][j] = 3;
                }
                else if (i == 1 ){
                    mapMatrix[i][j] = 1;  //block the maze at middle, cannot go to exit
                }
                else{
                    mapMatrix[i][j] = 0;
                }
            }
        }
    }


}
