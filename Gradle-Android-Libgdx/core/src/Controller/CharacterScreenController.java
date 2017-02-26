package Controller;
import Character.Character;

public class CharacterScreenController {
	Character player;
	
	public CharacterScreenController(Character player){
		this.player = player;
	}
	
	public void levelUp(){
		player.setLevel(player.getLevel()+1);
	}
	

//	public int attack(int attackRoll, Character opposingCharacter) {
//		return new CombatSimulator().fight(this, opposingCharacter, attackRoll);
//	}
//	
//	public void setDefense(int defense) {
//		armor.setArmor(defense);
//	}
//	
//	public void setArmor(Defense armor) {
//		this.defense = armor;
//	}
}
