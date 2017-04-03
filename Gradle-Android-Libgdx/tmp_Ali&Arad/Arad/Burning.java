package Weapon;
/** Decorator that mixes Burning with WeaponEnchantment.
 *  It is a subclass of WeaponEnchantmentDecorator, and thus a subclass of WeaponEnchantment. 
 * @author Arad Masoumabady
 */
public class Burning extends WeaponEnchantmentDecorator {
	/**
	 * When creating a decorated WeaponEnchantment, pass a WeaponEnchantment to be decorated
	 * as a parameter. Note that this can be an already-decorated WeaponEnchantment.
	 */
	public Burning(WeaponEnchantment decoratedWeaponEnchantment){
		super(decoratedWeaponEnchantment);
	}
    /**
     * Overriding methods defined in the abstract superclass. 
     * Enables to provide different behavior for decorated WeaponEnchantment methods
     */
	public String getEnchantment(){
		return super.getEnchantment()+", Target takes (5x enchantment bonus) damage for the 3 next turns.";
	}

}
