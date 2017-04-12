package Weapon;
/**
 * Abstract decorator class, it extends the WeaponEnchantment abstract class
 * @author Arad Masoumabady
 *
 */
public abstract class WeaponEnchantmentDecorator extends WeaponEnchantment{
	protected final WeaponEnchantment decoratedWeaponEnchantment;
    /**
     * Wraps a WeaponEnchantment object inside an object of one of 
     * WeaponEnchantmentDecorator's subclasses
     */
	public WeaponEnchantmentDecorator(WeaponEnchantment decoratedWeaponEnchantment){
		this.decoratedWeaponEnchantment= decoratedWeaponEnchantment;
	}
    /**
     * Provides the wrapper with the WeaponEnchantment interface and delegates 
     * its methods to the wrapped WeaponEnchantment object. 
     */
	public String getEnchantment(){
		return decoratedWeaponEnchantment.getEnchantment();
	}
}
