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
		int enchantmentPower=this.getItem().getLevel();		
		StackModel temp= new StackModel(enchantmentPower,"Frightening");
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

