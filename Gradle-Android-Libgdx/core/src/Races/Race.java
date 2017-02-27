package Races;

import Items.Item;
/**
 * class for races
 * @author chao wang
 * @version 1.0
 */
public class Race {
	/**
	 *set different types of races
	 */
	public enum RaceType {
		HUMAN(0), DWARF(1), ELF(2), ORC(3), TAUREN(4), TROLL(5), UNDEAD(6);
		private int index;
		/**
		 * constructor
		 * @param index the index of the certain type
		 */
		RaceType(int index) {
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
		public RaceType getRaceType(int index){
			switch (index){
				case 0:
					return HUMAN;
				case 1:
					return DWARF;
				case 2:
					return ELF;
				case 3:
					return ORC;
				case 4:
					return TAUREN;
				case 5:
					return TROLL;
				case 6:
					return UNDEAD;
			}
			return HUMAN;
		}
	};
}
