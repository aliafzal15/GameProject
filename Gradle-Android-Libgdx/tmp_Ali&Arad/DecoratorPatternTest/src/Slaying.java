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
		int enchantmentPower=this.getItem().getLevel()*0;//multiplied by 0 so that during combat we can playing with slaying strategy by checking this power =0	
		StackModel temp= new StackModel(enchantmentPower,"Slaying");
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
