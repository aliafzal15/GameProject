import java.util.Stack;

public abstract class WeaponSpecialEnchantment{	
	
	public Item item;
	public Stack <StackModel>enchantmentStack;
	public abstract Stack getEnchantment();
	public abstract void setEnchantmentStacks();
	public abstract Item getItem();
	
}
