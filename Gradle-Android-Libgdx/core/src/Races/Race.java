package Races;

import Items.Item;

public class Race {
	public enum RaceType {
		HUMAN(0), DWARF(1), ELF(2), ORC(3), TAUREN(4), TROLL(5), UNDEAD(6);
		private int index;

		RaceType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

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
