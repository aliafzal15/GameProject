package gdxtesting.Deliverable3;

/**
 * Created by Chao on 11/04/2017.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.DDGame;
import com.chaowang.ddgame.GameController.NPCcontroller;
import com.chaowang.ddgame.GameController.PlayerController;
import com.chaowang.ddgame.GameModel.NPC;
import com.chaowang.ddgame.GameModel.Player;
import com.chaowang.ddgame.GameView.GameScreen;
import com.chaowang.ddgame.MenuController.MapController;
import com.chaowang.ddgame.MenuModel.CampaignModel.Campaign;
import com.chaowang.ddgame.MenuModel.CharacterModel.Character;
import com.chaowang.ddgame.MenuModel.ItemModel.EnchantedAbility;
import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;
import com.chaowang.ddgame.MenuModel.MapModel.EntryDoor;
import com.chaowang.ddgame.MenuModel.MapModel.ExitDoor;
import com.chaowang.ddgame.MenuModel.MapModel.Map;
import com.chaowang.ddgame.PublicParameter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * the class is Junit test for Game  battle
 * @author chao wang
 * @version 1.0
 */
@RunWith(GdxTestRunner.class)
public class BattleTest {
    private Item melee, range;
    private Character character1, character2;
    private NPC npc;
    private int[][] mapMatrix;
    private MapController controller;
    private Player player;
    private Map map;
    private Campaign campaign;

    @Before
    public void beforeGameTest(){
        // create item
        melee = new Item(Item.ItemType.WEAPON, "Melee", 1, EnchantedAbility.DAMAGEBONUS, new WeaponModel());
        range = new Item(Item.ItemType.WEAPON, "Range", 1, EnchantedAbility.ATTACKBONUS, new WeaponModel(WeaponModel.WeaponType.RANGE));
        // create character
        character1 = new Character();
        character1.setAbilities(new int[] {10,10,10,10,10,10});
        character1.changeLevel(1);
        character1.addToBackpack(melee);
        character1.loadEquipment(melee);
        character2 = new Character();
        character2.setAbilities(new int[] {10,10,10,10,10,10});
        character2.changeLevel(1);
        character2.addToBackpack(range);
        character2.loadEquipment(range);
        // create map
        map = new Map(1,4,"name",new EntryDoor(new Vector2(0,0)), new ExitDoor(new Vector2(2,2)));
        mapMatrix = new int[map.getSize()][map.getSize()];
        buildLocationMatrix();
        map.setLocationMatrix(mapMatrix);
        map.addFriendLocationList(3,3,character1);
        map.addEnemyLocationList(2,3,character2);
        map.addItemLocationList(1,3, melee);
        //create campaign;
        campaign = new Campaign();
        campaign.addToCampaign(map);
        campaign.addToCampaign(map);
        // create player
        player  = new Player(new Vector2(1f, 1f),character1);
        npc = new NPC(new Vector2(PublicParameter.MELEE_WEAPON_ATTACK_CELL* player.getBound().width / 2,5f), character2, false);
    }


    /**
     * Test if player of high armor class loose HP if he is under attack
     */
    @Test
    public void testAttackModifier(){
        player.getCharacter().setArmorClass(100);
        player.getCharacter().setHitPoints(100);
        int prevHp = player.getCharacter().getHitPoints();
        player.getCharacter().underAttack(npc.getCharacter());
        assertEquals(player.getCharacter().getHitPoints(), prevHp);
    }

    /**
     * Test if damage is correctly calcuated, if attacker's attack > armor class
     */
    @Test
    public void testDamageModifier(){
        player.getCharacter().setHitPoints(50);
        player.getCharacter().setArmorClass(5);
        npc.getCharacter().setAttackBonus(5);
        npc.getCharacter().setDamageBonus(5);
        int prevHp = player.getCharacter().getHitPoints();
        player.getCharacter().underAttack(npc.getCharacter());
        assertTrue(prevHp - player.getCharacter().getHitPoints() >5 &&
                prevHp - player.getCharacter().getHitPoints() <=13);
    }

    /**
     * Test if weapons are usable within their effective range
     */
    @Test
    public void testMeleeWeaponEffectiveRange(){
        HashMap<Vector2,NPC> npcList = new HashMap<Vector2,NPC>();
        npcList.put(npc.getPosition(),npc);
        Game game = new DDGame();
        GameScreen screen = new GameScreen(game, player, map, campaign, npcList, false);
        PlayerController playerController = new PlayerController(player, screen);
        Vector2 result = playerController.findEnemyInAttackRange();
        assertTrue(result.x == npc.getPosition().x && result.y == npc.getPosition().y);
    }

    /**
     * Test if weapons are usable within their effective range
     */
    @Test
    public void testRangeWeaponEffectiveRange(){
        HashMap<Vector2,NPC> npcList = new HashMap<Vector2,NPC>();
        npc.setPosition(new Vector2(PublicParameter.RANGE_WEAPON_ATTACK_CELL* PublicParameter.MAP_PIXEL_SIZE * 0.9f, 5f));
        player.setPosition(new Vector2(player.getBound().width, 5f));
        Game game = new DDGame();
        GameScreen screen = new GameScreen(game, player, map, campaign, npcList, false);
        NPCcontroller npCcontroller = new NPCcontroller(npc, screen);
        assertEquals(true,npCcontroller.findPlayerToAttack());
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
