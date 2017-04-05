import java.util.Stack;

/**
 * This Class is for the SlayingModel in WeaponDecorator
 * 
 * 	@author Ali Afzal
 */

public class Slaying extends WeaponDecorator{

	private WeaponSpecialEnchantment weaponSpecialEnchantment;
	
	/**
	 * This is the parameterized constructor
	 * @param weaponSpecialEnchantment
	 */
	public Slaying(WeaponSpecialEnchantment weaponSpecialEnchantment){
		
		super(weaponSpecialEnchantment);
		this.weaponSpecialEnchantment=weaponSpecialEnchantment;
		setEnchantmentStacks();
	}
	
	/**
	 * This function returns the stacks of enchantment values
	 * @return enchantmentValueStack
	 */
	@Override
	public Stack getEnchantment() {		
		return this.enchantmentValueStack;
	}
	
	
	

	/**
	 * This function set the stacks of enchantment values and enchantment type
	 * 
	 */
	@Override
	public void setEnchantmentStacks() {
		this.enchantmentStack.add("Slaying");
		this.enchantmentValueStack.add(this.weaponSpecialEnchantment.item.getLevel()*0);		
	}
}
