package com.chaowang.ddgame.ClassesModel;

/**
 * model to fighter class
 * fighter class has no constructor since it depends on Figther builder subclass to set Attributes
 * @author chao wang
 * @version 2.0
 */
public class Fighter implements Class{
	/**
	 * enum type to set fighters' initial values
	 */
    public enum FighterType { BULLY(0), NIMBLE(1), TANK(2);

        private int index;
        /**
         * constructor
         * @param index the index of the certain type
         */
        FighterType(int index) {
            this.index = index;
        }
        /**
         * get the index of the type
         * @return index
         */
        public int getIndex() {
            return index;
        }

        /**
         * get the certain Fighter type
         * @param index Fighter type's index
         * @return certain Fighter type
         */
        public FighterType getFighterTypeFromIndex(int index){
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

    private FighterType fighterType;
    private int[] abilityImportance ;

    @Override
    public ClassType getClassType() {
        return ClassType.FIGHTER;
    }

    /**
     * sett for ability Importance, no constructor
     * @param abilityImportance
     */
    public void setAbilityImportance(int[] abilityImportance) {
        this.abilityImportance = abilityImportance;
    }
    /**
     * get the index of the type
     * @return index
     */
    public int[] getAbilityImportance() {
        return abilityImportance;
    }

    public void setFighterType (FighterType type){
        fighterType = type;
    }

    /**
     * getter for figther type.
     * @return
     */
    public FighterType getFighterType() {
        return fighterType;
    }

    /**
     * gett for fighter type index
     * @return
     */
    public int getFighterTypeIndex(){
        return fighterType.getIndex();
    }
}
