package gdxtesting.Deliverable3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.DDGame;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.MenuModel.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.MapModel.EntryDoor;
import com.chaowang.ddgame.MenuModel.MapModel.ExitDoor;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.MenuController.MapController;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.GameView.GameScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Iterator;

import gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * the class is Junit test for Game interaction
 * @author chao wang
 * @version 1.0
 */
@RunWith(GdxTestRunner.class)
public class GameScreenTest {
    private Map map;
    private Item item;
    private Character character1, character2;
    private int[][] mapMatrix;
    private MapController controller;
    private Player player;
    private Campaign campaign;

    @Before
    public void beforeGameTest(){
        // create item
        item = new Item(Item.ItemType.ARMOR, "unitTest", 1, EnchantedAbility.ARMORCLASS, null);
        // create character
        character1 = new Character();
        character1.setAbilities(new int[] {5,5,5,5,5,5});
        character1.changeLevel(1);
        character1.addToBackpack(item);
        character1.loadEquipment(item);
        character2 = new Character();
        character2.setAbilities(new int[] {5,5,5,5,5,5});
        character2.changeLevel(1);
        // create map
        map = new Map(1,4,"name",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
        mapMatrix = new int[map.getSize()][map.getSize()];
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
        map.addFriendLocationList(3,3,character1);
        map.addEnemyLocationList(2,3,character2);
        map.addItemLocationList(1,3, item);
        //create campaign;
        campaign = new Campaign();
        campaign.addToCampaign(map);
        campaign.addToCampaign(map);
        // create player
        player  = new Player(new Vector2(1f, 1f),character1);
    }

    /**
     * Test if player can move on the map
     */
    @Test
    public void testPlayerMove(){
        Vector2 prePosition = new Vector2(player.getPosition());
        player.move(10,10);
        assertTrue("player does not move to positive x, positive y", prePosition.x<player.getPosition().x
                && prePosition.y <player.getPosition().y);
    }

    /**
     * Test if item , player are overlaps, there is a detection system
     */
    @Test
    public void testCollisionDetection(){
        player.setPosition(new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 1 * PublicParameter.MAP_PIXEL_SIZE));
        player.getBound().set(player.getPosition().x, player.getPosition().y, player.getCurrentFrame().getRegionWidth(), player.getCurrentFrame().getRegionHeight());
        Iterator<Vector2> keySetIterator = map.getItemLocationList().keySet().iterator();
        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            map.getItemLocationList().get(cur).updateBound(cur);
            assertTrue("Player does not hit treasure", player.getBound().overlaps(map.getItemLocationList().get(cur)));
        }
    }

    /**
     * Test if enemy loose HP if he is under attack
     */
    @Test
    public void testEnemyUnderAttack(){
        Vector2 enemyPosition =  new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 2 * PublicParameter.MAP_PIXEL_SIZE);
        map.getEnemyLocationList().get(enemyPosition).setHitPoints(40);
        int preHP = map.getEnemyLocationList().get(enemyPosition).getHitPoints();
        character1.setAttackBonus(100);
        character1.setDamageBonus(20);
        map.getEnemyLocationList().get(enemyPosition).underAttack(character1);
        assertTrue("Hp does not reduce while under attack",  map.getEnemyLocationList().get(enemyPosition).getHitPoints() < preHP );
    }

    /**
     * Test if enemy 's status could be dead
     */
    @Test
    public void testEnemyDead(){
        Vector2 enemyPosition =  new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 2 * PublicParameter.MAP_PIXEL_SIZE);
        map.getEnemyLocationList().get(enemyPosition).makeDead();
        assertTrue(map.getEnemyLocationList().get(enemyPosition).isDead()
                    && map.getEnemyLocationList().get(enemyPosition).getEquipment().size()==0);
    }

    /**
     * Test if player can level up, and its ability also level up
     */
    @Test
    public void testPlayerLevelUp(){
        int prevHP = player.getCharacter().getHitPoints();
        player.getCharacter().promoteUp();
        player.getCharacter().promoteUp();
        assertTrue(player.getCharacter().getLevel() == 3
                        && prevHP < player.getCharacter().getHitPoints());
    }
    /**
     * Test if player can level up, and its equipment is not leveling up
     */
    @Test
    public void testPlayerItemNoLevelUp(){
        for (int i = 0 ; i < 4 ; i++){
            player.getCharacter().promoteUp();
        }
        assertTrue(player.getCharacter().getLevel() == 5
                && player.getCharacter().getEquipment().get(Item.ItemType.ARMOR).getLevel() == 1);
    }
    /**
     * Test if enemy can level up, and its equipment is leveling up
     */
    @Test
    public void testEnemyLevelUp(){
        Vector2 enemyPosition =  new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 2 * PublicParameter.MAP_PIXEL_SIZE);
        map.getEnemyLocationList().get(enemyPosition).loadEquipment(item);
        map.getEnemyLocationList().get(enemyPosition).changeLevel(5);
        assertTrue(map.getEnemyLocationList().get(enemyPosition).getLevel() == 5
                && map.getEnemyLocationList().get(enemyPosition).getEquipment().get(Item.ItemType.ARMOR).getLevel() == 2);
    }


    /**
     * Test if player can loot a chest, and put chest item is in backpack
     */
    @Test
    public void testPickUpItem(){
        PlayerController controller = new PlayerController(player);
        player.setPosition(new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 1 * PublicParameter.MAP_PIXEL_SIZE));
        player.getBound().set(player.getPosition().x, player.getPosition().y, player.getCurrentFrame().getRegionWidth(), player.getCurrentFrame().getRegionHeight());
        Iterator<Vector2> keySetIterator = map.getItemLocationList().keySet().iterator();
        int backpackSize = player.getCharacter().getBackpack().size();
        while(keySetIterator.hasNext()){
            Vector2 cur = keySetIterator.next();
            map.getItemLocationList().get(cur).updateBound(cur);
            if(player.getBound().overlaps(map.getItemLocationList().get(cur)) ){
                controller.pickupItem(map.getItemLocationList().get(cur));
                keySetIterator.remove();
            }
        }
        assertTrue("player does not pick up item", backpackSize < player.getCharacter().getBackpack().size());
    }

    /**
     * Test if can load a new map, and map character level matching to player level
     */
    @Test
    public void testLoadNewMap(){
        player.getCharacter().promoteUp();
        Game game = new DDGame();
        GameScreen screen = new GameScreen(game, player, map, campaign, new HashMap<Vector2, NPC>(), false);
        Vector2 enemyPosition =  new Vector2(3 * PublicParameter.MAP_PIXEL_SIZE, 2 * PublicParameter.MAP_PIXEL_SIZE);
        System.out.println(player.getCharacter().getLevel());
        System.out.println(screen.getMapModel().getEnemyLocationList().get(enemyPosition).getLevel());
        assertEquals(player.getCharacter().getLevel(), screen.getMapModel().getEnemyLocationList().get(enemyPosition).getLevel());
    }

    /**
     * Test if strategy is changing accoridng to good npc or bad npc
     */
    @Test
    public void testNPCStrategy(){
        HashMap<Vector2,NPC> npcList = new HashMap<Vector2,NPC>();
        NPC  npc = new NPC(new Vector2(1,1), character2, false);
        npcList.put(npc.getPosition(),npc);
        Game game = new DDGame();
        GameScreen screen = new GameScreen(game, player, map, campaign, npcList, false);
        screen.setNPCStrategy(npc, screen);
        assertTrue(npc.getStrategy().getClass().toString().contains("AggressiveNPCStrategy"));
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
