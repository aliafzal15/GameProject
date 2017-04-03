package Weapon;
/** Decorator that mixes Freezing with WeaponEnchantment.
 *  It is a subclass of WeaponEnchantmentDecorator, and thus a subclass of WeaponEnchantment. 
 * @author Arad Masoumabady
 */
public class Freezing extends WeaponEnchantmentDecorator {
	/**
	 * When creating a decorated WeaponEnchantment, pass a WeaponEnchantment to be decorated
	 * as a parameter. Note that this can be an already-decorated WeaponEnchantment.
	 */
	public Freezing(WeaponEnchantment decoratedWeaponEnchantment){
		super(decoratedWeaponEnchantment);
	}
    /**
     * Overriding methods defined in the abstract superclass. 
     * Enables to provide different behavior for decorated WeaponEnchantment methods
     */
	public String getEnchantment(){
		return super.getEnchantment()+", Target cannot move for a number of turns equal to the enchantment bonus of the weapon";
	}
}
