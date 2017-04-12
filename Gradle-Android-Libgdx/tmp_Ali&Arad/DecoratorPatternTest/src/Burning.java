import java.util.Stack;

/**
 * This Class is for the BurningModel in WeaponDecorator
 * 
 * 	@author Ali Afzal
 */

public class Burning extends WeaponDecorator{

	private WeaponSpecialEnchantment weaponSpecialEnchantment;
	
	/**
	 * This is the parameterized constructor
	 * @param weaponSpecialEnchantment
	 */
	public Burning(WeaponSpecialEnchantment weaponSpecialEnchantment){
		
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
		
		return this.weaponSpecialEnchantment.getEnchantment();
	}
	
	
	
	/**
	 * This function set the stacks of enchantment values and enchantment type
	 * 
	 */
	@Override
	public void setEnchantmentStacks() {
		int enchantmentPower=this.getItem().getLevel()*5;		
		StackModel temp= new StackModel(enchantmentPower,"Burning");
		this.weaponSpecialEnchantment.getEnchantment().push(temp);
	}
	
	/**
	 * This function returns the item model object
	 * 
	 */
	@Override
	public Item getItem() {
		return this.weaponSpecialEnchantment.getItem();
	}
}
