import java.util.Stack;

/**
 * This Class is for the WeaponDecorator
 * 
 * 	@author Ali Afzal
 */

public abstract class WeaponDecorator extends WeaponSpecialEnchantment {

	private WeaponSpecialEnchantment weaponSpecialEnchantment;
	
	/**
	 * This is the parameterized constructor
	 * @param weaponSpecialEnchantment
	 */
	public WeaponDecorator(WeaponSpecialEnchantment weaponSpecialEnchantment){
		
		this.weaponSpecialEnchantment=weaponSpecialEnchantment;
	}
	
	/**
	 * This function set the stacks of enchantment values and enchantment type
	 * 
	 */
	public Stack getEnchantment() {		
		return this.weaponSpecialEnchantment.getEnchantment();
	}
	
	/**
	 * This function set the stacks of enchantment values and enchantment type
	 * 
	 */
	@Override
	public void setEnchantmentStacks() {
		this.weaponSpecialEnchantment.getEnchantment();
	}
	
}
