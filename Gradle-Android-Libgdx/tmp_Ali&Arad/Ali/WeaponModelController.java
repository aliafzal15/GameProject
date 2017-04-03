import java.util.Random;

public class WeaponModelController {

	private WeaponModel weaponModel;
	private Character gameCharacter;
	private int newAttackBonus;
	private int newDamageBouns;
	
	public WeaponModelController(WeaponModel newWeaponModel,Player newPlayer){
		
		this.weaponModel=newWeaponModel;	
		this.gameCharacter=newPlayer.getCharacter();
		calculateNewAttackDamageBonus(this.gameCharacter);
		this.gameCharacter.setAttackBonus(this.newAttackBonus);
		this.gameCharacter.setDamageBonus(this.newDamageBouns);
	}
	
	
	//Melee attack bonus is calculated with respect to strength modifier
	//Ranged attack bonus is calculated with respect to dexterity modifier
	public void calculateNewAttackDamageBonus(Character gameChar){
		
		
		if(weaponModel.getWeaponType().equals("Melee")){
			
			int strMod= gameChar.getStrengthBonus();
			int prevAttackBonus= gameChar.getAttackBonus();			
			this.newAttackBonus=strMod+prevAttackBonus;
			
			int d20=rollOneD20();
			
			this.newDamageBouns=d20+this.newAttackBonus;
			

		}
		
		else if (weaponModel.getWeaponType().equals("Ranged")){
			
			int dexMod= gameChar.getDexterityBonus();
			int prevAttackBonus= gameChar.getAttackBonus();
			
			this.newAttackBonus=dexMod+prevAttackBonus;
			
			int d20=rollOneD20();
			
			this.newDamageBouns=d20+this.newAttackBonus;
		}
	
	
	}
	
	private int rollOneD20(){
		Random rand = new Random();
		int d1= rand.nextInt(20) + 1;
		return d1;
	}
		
}
