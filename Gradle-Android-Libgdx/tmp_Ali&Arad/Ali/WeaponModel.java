import java.util.Random;

/**
 * This Class is for the Weapon Models with respect to Melee and Ranged weapons
 */
public class WeaponModel {

	private String weaponType;
	private int weaponRangeCells;
	private EnchantedAbility weaponEnchantabilityType;
	private int newAttackBonus;
	private int newDamageBouns;
	private int attackPower;
	private int damagePower;
	
	/**
	 * This is parameterized constructor of the class
	 * 
	 * @param newWeapon
	 * 			This is the item model of type weapon for which the type is to be set
	 * @param newType
	 * 			This is the Type to be set either Melee or Ranged
	 */
	public WeaponModel (String newType, EnchantedAbility enchantabilityType) {
		
		this.weaponType=newType;	
		this.weaponEnchantabilityType=enchantabilityType;
		this.setAttackRange(this.weaponType);		
	}
	
	/**
	 * This function sets the weapon type
	 * 
	 * @param type
	 * 			This is the type to be set either Melee or Ranged
	 */
	public void setWeaponType(String type){		
		this.weaponType=type;
	}
	
	/**
	 * This function gets the weapon type
	 * 
	 * @return type of the weapon that is either Melee or Ranged
	 */
	public String getWeaponType(){		
		return this.weaponType;
	}
	
	
	/**
	 * This function sets the weapon attack range
	 * 
	 * @param type 
	 * 			This is the type of the weapon
	 */
	public void setAttackRange(String type){
		
		if(type.equals("Melee")){
			this.weaponRangeCells=1;
		}
		else if (type.equals("Ranged")){
			this.weaponRangeCells=3;
		}
		else 
		{
			this.weaponRangeCells=0;	
		}
	}
	

	
	/**
	 * This function new Attack or Damage Bonus as per the type
	 * 
	 * @param gameChar
	 * 			This is the type of the gameCharacter
	 */
	
public void calculateNewAttackDamageBonus(Character gameChar){
		
		
		if(this.weaponType.equals("Melee") 
				&& (this.weaponEnchantabilityType==weaponEnchantabilityType.ATTACKBONUS)){
			
			int strMod= gameChar.getStrengthBonus();
			int prevAttackBonus= gameChar.getAttackBonus();			
			this.newAttackBonus=strMod+prevAttackBonus;
			
			int d20=rollOneD20();
			
			this.attackPower=d20+this.newAttackBonus;
			

		}
		
		else if(this.weaponType.equals("Ranged") 
				&& (this.weaponEnchantabilityType==weaponEnchantabilityType.ATTACKBONUS)){
			
			int dexMod= gameChar.getDexterityBonus();
			int prevAttackBonus= gameChar.getAttackBonus();		
			this.newAttackBonus=dexMod+prevAttackBonus;
			
			int d20=rollOneD20();
			
			this.attackPower=d20+this.newAttackBonus;
		}
		else if(this.weaponType.equals("Melee") 
				&& (this.weaponEnchantabilityType==weaponEnchantabilityType.DAMAGEBONUS)){
			
			int strMod= gameChar.getStrengthBonus();
			int prevDamageBonus=gameChar.getDamageBonus();
			this.newDamageBouns=strMod+prevDamageBonus;
			
			int d8=rollOneD8();
			
			this.damagePower=d8+this.newDamageBouns;
		}

	
	
	}
	
/**
 * This function generates one D20
 * 
 */
	private int rollOneD20(){
		Random rand = new Random();
		int d1= rand.nextInt(20) + 1;
		return d1;
	}
	
	/**
	 * This function generates one D8
	 * 
	 */
	private int rollOneD8(){
		Random rand = new Random();
		int d1= rand.nextInt(8) + 1;
		return d1;
	}
	
	
	
	
	
}
