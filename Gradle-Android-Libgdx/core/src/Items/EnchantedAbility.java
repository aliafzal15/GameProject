package Items;

/**
 * Created by Chao on 09/02/2017.
 */

public enum EnchantedAbility {

    STRENGTH(0), DEXTERITY(1), CONSTITUTION(2), WISDOM(3), INTELLIGENCE(4), CHARISMA(5), ARMORCLASS(6), ATTACKBONUS(7), DAMAGEBONUS(8);
    int index;

    private EnchantedAbility(int value) {
        this.index = value;
    }

    public int getIndex() {
        return index;
    }

    public EnchantedAbility getAbility (int index) {
        switch (index) {
            case 0:
                return STRENGTH;
            case 1:
                return DEXTERITY;
            case 2:
                return CONSTITUTION;
            case 3:
                return WISDOM;
            case 4:
                return INTELLIGENCE;
            case 5:
                return CHARISMA;
            case 6:
                return ARMORCLASS;
            case 7:
                return ATTACKBONUS;
            case 8:
                return DAMAGEBONUS;
        }
        return WISDOM;
    }
}
