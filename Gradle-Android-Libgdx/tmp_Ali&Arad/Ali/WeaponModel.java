

/**
 * This Class is for the Weapon Models with respect to Melee and Ranged weapons
 */
public class WeaponModel {

	private String weaponType;
	private Item weapon;
	private int weaponRangeCells;
	
	/**
	 * This is parameterized constructor of the class
	 * 
	 * @param newWeapon
	 * 			This is the item model of type weapon for which the type is to be set
	 * @param newType
	 * 			This is the Type to be set either Melee or Ranged
	 */
	public WeaponModel (Item newWeapon, String newType) {
		
		this.weapon=newWeapon;
		this.weaponType=newType;	
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
	 * This function sets the weapon
	 * 
	 * @param newWeapon
	 * 			This is the item model of weapon type
	 */
	public void setWeapon(Item newWeapon){
		this.weapon=newWeapon;
	}
	
	
	/**
	 * This function gets the weapon
	 * 
	 * @return item model object of type weapon
	 */
	public Item getWeapon(){
		return this.weapon;
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
	}
}
