package LibgdxExample;

import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.ClassesModel.DDgameClass;
/**
 * model to fighter class 
 * @author chao wang
 * @version 2.0
 */
public class Fighter {
	/**
	 * enum type to set fighters' initial values
	 */
    public enum FighterType {
        BULLY(0, new int[]{Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() }),

        NIMBLE(1, new int[]{Abilities.AbilityType.DEXTERITY.getIndex(), Abilities.AbilityType.CONSTITUTION.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() }),

        TANK(2, new int[]{Abilities.AbilityType.CONSTITUTION.getIndex(), Abilities.AbilityType.DEXTERITY.getIndex(),
                Abilities.AbilityType.STRENGTH.getIndex(), Abilities.AbilityType.INTELLIGENCE.getIndex(),
                Abilities.AbilityType.CHARISMA.getIndex(), Abilities.AbilityType.WISDOM.getIndex() });

    private int[] abilityImportance ;
    private int index;

    /**
     * constructor
     * @param index the index of the certain type
     */
    FighterType(int index, int[] arr) {
        this.index = index;
        this.abilityImportance = arr;
    }
    /**
     * get the index of the type
     * @return index
     */
    public int getIndex() {
        return index;
    }
    /**
     * get the index of the type
     * @return index
     */
    public int[] getAbilityImportance() {
        return abilityImportance;
    }
    /**
     * set the index of certain race type
     * @param index certain race type's index
     */
    public void setIndex(int index) {
        this.index = index;
    }
    /**
     * get the certain race type
     * @param index race type's index
     * @return certain race type
     */
    public FighterType getFighterType(int index){
        switch (index){
            case 0:
                return BULLY;
            case 1:
                return NIMBLE;
            case 2:
                return TANK;
        }
        return BULLY;
    }
};

}
