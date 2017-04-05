import java.util.Stack;

public class Frightening extends WeaponDecorator{

	/**
	 * This Class is for the FrighteningModel in WeaponDecorator
	 * 
	 * 	@author Ali Afzal
	 */
	private WeaponSpecialEnchantment weaponSpecialEnchantment;
	
	/**
	 * This is the parameterized constructor
	 * @param weaponSpecialEnchantment
	 */
	public Frightening(WeaponSpecialEnchantment weaponSpecialEnchantment){
		
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
		this.enchantmentStack.add("Frightening");
		this.enchantmentValueStack.add(this.weaponSpecialEnchantment.item.getLevel());	
	}
}
