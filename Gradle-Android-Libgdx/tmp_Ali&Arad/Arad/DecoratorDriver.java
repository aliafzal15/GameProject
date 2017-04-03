package Weapon;

public class DecoratorDriver {
	public static final void main(String[] args){
		WeaponEnchantment c = new NoEnchantment();
		System.out.println(c.getEnchantment());
		c= new Frightening(c);
		System.out.println(", " + c.getEnchantment());
		c= new Pacifying(c);
		System.out.println(", " + c.getEnchantment());
		c= new Slaying(c);
		System.out.println(", " + c.getEnchantment());
	}
}
