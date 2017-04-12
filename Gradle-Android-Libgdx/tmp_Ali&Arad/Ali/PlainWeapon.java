import java.util.Stack;
/**
 * This Class is for the PlainWeaponModel in WeaponDecorator
 * 
 * 	@author Ali Afzal
 */
public class PlainWeapon extends WeaponSpecialEnchantment {

	/**
	 * This is the parameterized constructor
	 * @param newItem
	 */
	public PlainWeapon(Item newItem){
		this.item=newItem;
		setEnchantmentStacks();
	}
	

	/**
	 * This function returns the stacks of enchantment values
	 * @return enchantmentValueStack
	 */
	@Override
	public Stack getEnchantment() {		
		return this.enchantmentStack;
	}
	

	/**
	 * This function set the stacks of enchantment values and enchantment type
	 * 
	 */
	@Override
	public void setEnchantmentStacks() {
		this.enchantmentStack=new Stack();
	}

	/**
	 * This function returns the item model object
	 * 
	 */
	@Override
	public Item getItem() {
		return this.item;	
	}
	
}
