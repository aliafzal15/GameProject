package util;

public class AbilityModifier {
    public static int hitPointModifier(int constitution, int level){
            return constitution * 2 + level * 10;
    }

    public static int armorClassModifier(int dexterity){
        return dexterity / 2 ;
    }

    public static int attachBonusModifier(int strength, int dexterity, int level){
        return strength / dexterity * 10 + level * 2 ;
    }

    public static int damageBonusModifier(int strength){
        return strength / 2;
    }




}
