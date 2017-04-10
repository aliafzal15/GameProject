
/**
 * This Class is for the Driver for WeaponDecorator
 * 
 * 	@author Ali Afzal
 */
public class WeaponDecoratorDriver {

	public WeaponSpecialEnchantment newDecoratedWeapon;
	
	/**
	 * This is the parameterized constructor
	 * @param FreezingDecorator
	 * @param BurningDecorator
	 * @param SlayingDecorator
	 * @param FrighteningDecorator
	 * @param PacifyingDecorator
	 * @param newItem
	 */	
	
	
	public static void main(String [] args){
		
		Item itm=new Item();
		WeaponDecoratorDriver dr=new WeaponDecoratorDriver("FreezingDecorator", "BurningDecorator", 
						"SlayingDecorator", "FrighteningDecorator", "PacifyingDecorator", itm);
		
		for(int i=0;i<dr.newDecoratedWeapon.getEnchantment().size();i++){
			System.out.println(dr.newDecoratedWeapon.getEnchantment().get(i));
		}
			
	}
	
/**
 * This is the parameterized constructor
 * @param FreezingDecorator
 * @param BurningDecorator
 * @param SlayingDecorator
 * @param FrighteningDecorator
 * @param PacifyingDecorator
 * @param newItem
 */	
	
	public WeaponDecoratorDriver (String FreezingDecorator,String BurningDecorator,String SlayingDecorator,String FrighteningDecorator,
			String PacifyingDecorator,Item newItem){
		
		WeaponSpecialEnchantment decoratedWeapon;
				decoratedWeapon =new PlainWeapon(newItem);
				System.out.println(decoratedWeapon.item.getLevel());
				
		if(FreezingDecorator!=null){
			decoratedWeapon=new Freezing(decoratedWeapon);
			

		};
			if(BurningDecorator!=null){
			decoratedWeapon=new Burning(decoratedWeapon);
		}
			if(SlayingDecorator!=null){
			decoratedWeapon=new Slaying(decoratedWeapon);
		}
		
		if(FrighteningDecorator!=null){
			decoratedWeapon=new Frightening(decoratedWeapon);
		}
		if(PacifyingDecorator!=null){
			decoratedWeapon=new Pacifying(decoratedWeapon);
		}

		this.newDecoratedWeapon=decoratedWeapon;
		
		System.out.println(newDecoratedWeapon.getEnchantment().size());
		//StackModel temp=(StackModel) newDecoratedWeapon.getEnchantment().get(0);
			
	}
	
	
	
}
