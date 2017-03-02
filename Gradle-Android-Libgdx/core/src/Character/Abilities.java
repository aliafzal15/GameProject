package Character;

import Items.EnchantedAbility;
/**
 * all ability types for characters
 * @author chao wang
 * @version 1.0
 */
public class Abilities  {
	public static final int ABILITYSIZE = 6;

	private int[] abilityArr;
	/**
	 * set all ability types
	 */
	public enum AbilityType {
		//STRENGTH, DEXTERITY, CONSTITUTION, WISDOM, INTELLIGENCE, CHARISMA;

		STRENGTH(0), DEXTERITY(1), CONSTITUTION(2), WISDOM(3), INTELLIGENCE(4), CHARISMA(5);
		int index;
		/**
		 * set specific ability type
		 * @param value which specific type
		 */
		private AbilityType(int value) {
			this.index = value;
		}
		/**
		 * get the index of the type
		 * @return the index of the type
		 */
		public int getIndex() {
			return index;
		}
		/**
		 * get the specific ability type for the character
		 * @param index which specific type
		 * @return the specific ability type
		 */
		public AbilityType getAbility (int index) {
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
			}
			return WISDOM;
		}

	};
	
	/**
	 * constructor
	 * @param defaultValue default value for Abilities
	 */
	public Abilities (int defaultValue){
		abilityArr = new int[ABILITYSIZE];
		for (Integer i : abilityArr){
			abilityArr[i] = defaultValue;
		}
	}
	/**
	 * constructor
	 * @param arr
	 */
	public Abilities (int[] arr){
		abilityArr = arr;
	}
	/**
	 * constructors
	 */
	public Abilities (){
		this(0);
	}
	/**
	 * switch to string type as output
	 */
	public String toString(){
		return 	Integer.toString(this.abilityArr[0]) + " | " +Integer.toString(this.abilityArr[1]) + " | " + Integer.toString(this.abilityArr[2]) + " | "
				+ Integer.toString(this.abilityArr[3]) + " | " +Integer.toString(this.abilityArr[4]) + " | " +Integer.toString(this.abilityArr[5]);
	}
	/**
	 * get ability array
	 * @return abilities
	 */
	public int[] getAbilityArr() {
		return abilityArr;
	}
	/**
	 * set ability array
	 * @param abilityArr a array as abilities
	 */
	public void setAbilityArr(int[] abilityArr) {
		this.abilityArr = abilityArr;
	}
	/**
	 * set ability level
	 * @param index
	 * @param value
	 */
	public void setAbility(int index, int value) {
		this.abilityArr[index] = value;
	}
}
