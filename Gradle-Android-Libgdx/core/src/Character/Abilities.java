package Character;

import Items.EnchantedAbility;

public class Abilities  {
	public static final int ABILITYSIZE = 6;

	private int[] abilityArr;

	public enum AbilityType {
		//STRENGTH, DEXTERITY, CONSTITUTION, WISDOM, INTELLIGENCE, CHARISMA;

		STRENGTH(0), DEXTERITY(1), CONSTITUTION(2), WISDOM(3), INTELLIGENCE(4), CHARISMA(5);
		int index;

		private AbilityType(int value) {
			this.index = value;
		}

		public int getIndex() {
			return index;
		}

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
	

	public Abilities (int defaultValue){
		abilityArr = new int[ABILITYSIZE];
		for (Integer i : abilityArr){
			abilityArr[i] = defaultValue;
		}
	}

	public Abilities (int[] arr){
		abilityArr = arr;
	}
	
	public Abilities (){
		this(0);
	}

	public String toString(){
		return 	Integer.toString(this.abilityArr[0]) + " | " +Integer.toString(this.abilityArr[1]) + " | " + Integer.toString(this.abilityArr[2]) + " | "
				+ Integer.toString(this.abilityArr[3]) + " | " +Integer.toString(this.abilityArr[4]) + " | " +Integer.toString(this.abilityArr[5]);
	}


	public int[] getAbilityArr() {
		return abilityArr;
	}

	public void setAbilityArr(int[] abilityArr) {
		this.abilityArr = abilityArr;
	}

	public void setAbility(int index, int value) {
		this.abilityArr[index] = value;
	}
}
