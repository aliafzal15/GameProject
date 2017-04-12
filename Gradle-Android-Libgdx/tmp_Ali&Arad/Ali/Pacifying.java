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
		int enchantmentPower=this.getItem().getLevel()*1000; //multiplied by 1000 so during combat we can check if its greater than 1000 then we can do pacifying strategy		
		StackModel temp= new StackModel(enchantmentPower,"Pacifying");
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
