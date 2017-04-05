import java.util.Stack;

public abstract class WeaponSpecialEnchantment{	
	
	public Item item;
	public Stack enchantmentStack;
	public Stack enchantmentValueStack;
	public abstract Stack getEnchantment();
	public abstract void setEnchantmentStacks();
	
}
