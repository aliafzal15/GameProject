package gdxtesting.Deliverable3;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * the class is Junit test for Game interaction
 * @author chao wang
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CampaignEditorTest.class,
        CharacterEditorTest.class,
        GameScreenTest.class,
        ItemEditorTest.class,
        ItemExchangeTest.class,
        MapEditorTest.class,
        WeaponModelTest.class,
        BattleTest.class
})
public class DDgameTestSuite {

}
