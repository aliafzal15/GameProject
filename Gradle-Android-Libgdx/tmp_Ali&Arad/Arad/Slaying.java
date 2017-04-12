package Weapon;
/** Decorator that mixes Slaying with WeaponEnchantment.
 *  It is a subclass of WeaponEnchantmentDecorator, and thus a subclass of WeaponEnchantment. 
 * @author Arad Masoumabady
 */
public class Slaying extends WeaponEnchantmentDecorator {
	/**
	 * When creating a decorated WeaponEnchantment, pass a WeaponEnchantment to be decorated
	 * as a parameter. Note that this can be an already-decorated WeaponEnchantment.
	 */
	public Slaying(WeaponEnchantment decoratedWeaponEnchantment){
		super(decoratedWeaponEnchantment);
	}
    /**
     * Overriding methods defined in the abstract superclass. 
     * Enables to provide different behavior for decorated WeaponEnchantment methods
     */
	public String getEnchantment(){
		return super.getEnchantment()+", Target dies instantly";
	}
}
