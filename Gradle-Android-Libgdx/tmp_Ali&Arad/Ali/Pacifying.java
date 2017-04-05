import java.util.Stack;

/**
 * This Class is for the PacifyingModel in WeaponDecorator
 * 
 * 	@author Ali Afzal
 */
public class Pacifying extends WeaponDecorator{

	private WeaponSpecialEnchantment weaponSpecialEnchantment;
	


	/**
	 * This is the parameterized constructor
	 * @param weaponSpecialEnchantment
	 */
	public Pacifying(WeaponSpecialEnchantment weaponSpecialEnchantment){
		
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
		this.enchantmentStack.add("Pacifying");
		this.enchantmentValueStack.add(this.weaponSpecialEnchantment.item.getLevel()*1000);	
	}
}
