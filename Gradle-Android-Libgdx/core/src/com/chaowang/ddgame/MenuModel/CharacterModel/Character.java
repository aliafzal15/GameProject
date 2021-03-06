package com.chaowang.ddgame.MenuModel.CharacterModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chaowang.ddgame.MenuModel.ClassesModel.Fighter;
import com.chaowang.ddgame.MenuModel.ClassesModel.FighterBuilder;
import com.chaowang.ddgame.MenuModel.ClassesModel.FighterBuilderDirector;
import com.chaowang.ddgame.MenuModel.ClassesModel.FighterBullyBuilder;
import com.chaowang.ddgame.MenuModel.ClassesModel.FighterNimbleBuilder;
import com.chaowang.ddgame.MenuModel.ClassesModel.FighterTankBuilder;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.Buring;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponDecoratorPattern.WeaponSpecialEnchantment;
import com.chaowang.ddgame.MenuModel.ItemModel.WeaponModel;
import com.chaowang.ddgame.MenuView.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;
import com.chaowang.ddgame.MenuModel.ClassesModel.Fighter.FighterType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import com.chaowang.ddgame.MenuModel.ItemModel.Item;
import com.chaowang.ddgame.MenuModel.RacesModel.Race.RaceType;
import com.chaowang.ddgame.util.Blinker;
import com.chaowang.ddgame.util.CharacterScoreModifier;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.util.Dice;

/**
 * create characters
 * @author chao Wang
 * @version 1.0
 */
public class Character extends Observable implements Json.Serializable{
    public static final int FIGHTATTRUBUTESIZE = 4;

	private RaceType raceType;
	private String name;
	private int level;
	
	private Abilities abilities;
	private int attackBonus;
	private int damageBonus;
	private int hitPoints;
	private int armorClass;
	private int promotionPoint;
	private int[] abilityBonusArr;
    private Texture texture;
    private Texture friendlyTexture;
	boolean isDead;

    private HashMap<Item.ItemType, Item> equipment;
    private ArrayList<Item> backpack;

	private Fighter fighter;
	private FighterBuilder fighterBuilder;
	private FighterBuilderDirector director;

	private int[] weaponEnchantmentInfection;

	private Blinker blinker;
    /**
     * constructor for the class
     */
	public Character(){
		this("Default");
	};
	/**
	 * constructor for the class
	 * @param name the name of the character
	 */
	public Character(String name) {
		this(name, 1, RaceType.HUMAN, FighterType.BULLY);
	}
	/**
	 * 
	 * constructor for the class
	 * @param name the name of the character
	 * @param level the level of the character
	 * @param raceType the raceType of the character
	 */
	public Character(String name, int level, RaceType raceType, FighterType fighterType) {
		createFighterBasedOnType(fighterType);
		this.fighter = director.getFighter();
		this.setName(name);
		this.raceType  = raceType;
		this.hitPoints = 0;
		this.attackBonus = 0;
		this.damageBonus = 0;
		this.armorClass = 0;
		this.abilities = new Abilities(0);
		this.abilityBonusArr = new int[Abilities.ABILITYSIZE];
		this.weaponEnchantmentInfection = new int[WeaponSpecialEnchantment.WeaponEnchantement.values().length];
		this.level = level;
		this.promotionPoint = level - 1;
		this.backpack = new ArrayList<Item>(PublicParameter.ITEM_BACKPACK_SIZE);
        this.equipment = new HashMap<Item.ItemType, Item>();
        updateTexture(raceType);
        friendlyTexture = new Texture(Gdx.files.internal("map/friend1.png"));
		this.isDead = false;
		blinker = new Blinker();
    }

	/**
	 * 
	 * constructor for the class
	 * @param name the name of the character
	 * @param level the level of the character
	 * @param raceType the raceType of the character
	 * @param abilityArr the abilityArr of the character
	 * @param abilityBonus the abilityBonus of the character
	 */
    public Character(String name, int level,int promotionPoint, RaceType raceType, FighterType fighterType, int[] abilityArr, int[] abilityBonus) {
    	this.setName(name);
		createFighterBasedOnType(fighterType);
		this.fighter = director.getFighter();
		this.raceType  = raceType;
		this.level = level;
		this.promotionPoint = promotionPoint;
		this.backpack = new ArrayList<Item>(PublicParameter.ITEM_BACKPACK_SIZE);
        this.equipment = new HashMap<Item.ItemType, Item>();
		this.isDead = false;
		updateTexture(raceType);
        int[] subAbilityArr = new int[Abilities.ABILITYSIZE];
		this.weaponEnchantmentInfection = new int[WeaponSpecialEnchantment.WeaponEnchantement.values().length];
		System.arraycopy(abilityArr, 0 , subAbilityArr , 0, Abilities.ABILITYSIZE);
        this.abilities = new Abilities(subAbilityArr);
        this.setHitPoints(abilityArr[Abilities.ABILITYSIZE]);
        this.setAttackBonus(abilityArr[Abilities.ABILITYSIZE+1]);
        this.setDamageBonus(abilityArr[Abilities.ABILITYSIZE+2]);
        this.setArmorClass(abilityArr[Abilities.ABILITYSIZE+3]);
		abilityBonusArr = new int[Abilities.ABILITYSIZE];
		System.arraycopy(abilityBonus, 0, abilityBonusArr, 0, abilityBonus.length );
		blinker = new Blinker();
	}
    /**
     * 
     * @param name the name of the character
     * @param level the level of the character
     * @param promotionPoint the promotionPoint of the character
     * @param raceType the raceType of the character
     * @param abilityArr the abilityArr of the character
     * @param abilityBonusArr the abilityBonusArr of the character
     * @param backpack the backpack of the character
     * @param equipment the equipment of the character
     */
    public Character(String name, int level, int promotionPoint, RaceType raceType, FighterType fighterType, int[] abilityArr, int[] abilityBonusArr, ArrayList<Item> backpack, HashMap<Item.ItemType, Item> equipment) {
        this(name,level,promotionPoint,raceType, fighterType,abilityArr, abilityBonusArr);
        this.backpack = backpack;
		this.equipment = equipment;
    }
    /**
     * update the image of the character
     * @param raceType the type of the race of the character
     */
    private void updateTexture(RaceType raceType) {
        switch (raceType){
            case HUMAN:
                texture = new Texture(Gdx.files.internal("races/human.png"));
                break;
            case DWARF:
                texture = new Texture(Gdx.files.internal("races/dwarf.png"));
                break;
            case ELF:
                texture = new Texture(Gdx.files.internal("races/elf.png"));
                break;
            case ORC:
                texture = new Texture(Gdx.files.internal("races/orc.png"));
                break;
            case TAUREN:
                texture = new Texture(Gdx.files.internal("races/tauren.png"));
                break;
            case TROLL:
                texture = new Texture(Gdx.files.internal("races/troll.png"));
                break;
            case UNDEAD:
                texture = new Texture(Gdx.files.internal("races/undead.png"));
                break;
        }
    }
    /**
     * under Attack
     * @param attacker
     */
	public void underAttack(Character attacker){
		if(!isDead()){
			int d20 = Dice.roll(1,20);
			if(MainMenuScreen.logArea !=null){
				MainMenuScreen.logArea.appendText(this.getName() + "'s AC " + this.armorClass + " VS "+ (d20 + attacker.getAttackBonus()) +" attack\n");
			}
			if( d20 + attacker.getAttackBonus() > armorClass){
				int damage;
				// FIST DAMAGE MODIFER
				if(attacker.getEquipment().get(Item.ItemType.WEAPON)== null){
					damage = Math.max(Dice.roll(1,3) + attacker.getDamageBonus(), 0);
					// RANGE MELEE MODIFER
				} else if(attacker.getEquipment().get(Item.ItemType.WEAPON).getWeaponType()== WeaponModel.WeaponType.MELEE){
					damage = Math.max(Dice.roll(1,6) + attacker.getDamageBonus(), 0);
				} else{
					// RANGE DAMAGE MODIFER
					damage = Math.max(Dice.roll(1,8) + attacker.getDamageBonus(), 0);
				}
				this.hitPoints -= damage;
				// display which weapon gives damage
				if(MainMenuScreen.logArea !=null){
					if(attacker.getEquipment().get(Item.ItemType.WEAPON)!=null){
						MainMenuScreen.logArea.appendText(this.getName() +" get " + attacker.getEquipment().get(Item.ItemType.WEAPON).getWeaponType().toString()
								+damage+ " damage, Hp:"+this.getHitPoints()+"\n");
					} else{
						MainMenuScreen.logArea.appendText(this.getName() +" get fist"
								+damage+ " damage, Hp:"+this.getHitPoints()+"\n");
					}
				}
			} else{
				MainMenuScreen.logArea.appendText(this.getName() + "defend " + attacker.getName()+ "'s attack\n");
			}
			if(attacker.getEquipment().get(Item.ItemType.WEAPON) != null){
				boolean[] tmp = attacker.getEquipment().get(Item.ItemType.WEAPON).getWeaponModel().getWeaponEnhantmentEqu();
				if(tmp[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()]){
					weaponEnchantmentInfection[WeaponSpecialEnchantment.WeaponEnchantement.BURNING.getIndex()]= PublicParameter.BURING_TURNS*10 +attacker.getEquipment().get(Item.ItemType.WEAPON).getLevel();
				}
				if(tmp[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()]){
					weaponEnchantmentInfection[WeaponSpecialEnchantment.WeaponEnchantement.FREEZING.getIndex()]= attacker.getEquipment().get(Item.ItemType.WEAPON).getLevel();
				}
				if(tmp[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()]){
					weaponEnchantmentInfection[WeaponSpecialEnchantment.WeaponEnchantement.FRIGHTENING.getIndex()]= attacker.getEquipment().get(Item.ItemType.WEAPON).getLevel();
				}
				if(tmp[WeaponSpecialEnchantment.WeaponEnchantement.PACIFYING.getIndex()]){
					weaponEnchantmentInfection[WeaponSpecialEnchantment.WeaponEnchantement.PACIFYING.getIndex()]= 1;
				}
				if(tmp[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()]){
					weaponEnchantmentInfection[WeaponSpecialEnchantment.WeaponEnchantement.SLAYING.getIndex()]= 1;
				}
			}
			// VISUALIZE THE DAMAGE
			blinker.setBlinking(true);
		} else{
			makeDead();
		}
	}
	/**
	 * set dead status
	 */
	public void makeDead() {
		this.isDead = true;
		this.texture = new Texture(Gdx.files.internal("races/dead.png"));
		setAbilities(new int[]{-1,-1,-1,-1,-1,-1});
		
		for(Iterator<Map.Entry<Item.ItemType, Item>> it = equipment.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Item.ItemType, Item> entry = it.next();
			if (backPackisFull()) {
				backpack.remove(0);
			}
			backpack.add(entry.getValue());
			it.remove();
		}
		
		this.hitPoints = -1;
		this.armorClass = 0;
		this.attackBonus = 0 ;
		this.damageBonus = 0;
		if(MainMenuScreen.logArea!= null){
			MainMenuScreen.logArea.appendText(this.getName() + " is dead \n");
		}
	}

	/**
	 * change the type of the race
	 * @return if successfully changed
	 */
	public boolean nextRace(){
        if(raceType.getIndex() >= 6 ){
            return false;
        }
        else{
            this.raceType = raceType.getRaceType(this.raceType.getIndex()+1);
            updateTexture(this.raceType);
            return true;
        }
    }
	/**
	 * change the type of the fighter
	 * @return if successfully changed
	 */
	public boolean nextFighterType(){
		if(fighter.getFighterTypeIndex() >= 2 ){
			return false;
		}
		else{
			createFighterBasedOnType(fighter.getFighterType().getFighterTypeFromIndex(fighter.getFighterTypeIndex()+1));
			this.fighter = director.getFighter();
			return true;
		}
	}
	/**
	 * reset promote point for the character
	 */
	public void resetPromotePoint(){
		promotionPoint = level -1;
		for (int i : abilityBonusArr){
			i = 0;
		}
	}
	
	/**
	 * reset promote point for the character
	 */
	public int getPromotePointofLevel(){
		return level -1;
	}
	/**
	 * lower the level of the character
	 */
    public void levelDown(){
		if (level > 1 ){
			level --;
			if(promotionPoint > 0){
				promotionPoint --;
			}
		}
	}
	/**
	 * set different kind of extra bonus
	 * @param bonus
	 */
	public void setStrengthBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.STRENGTH.getIndex()] = bonus; }
	public void setDexterityBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.DEXTERITY.getIndex()] = bonus; }
	public void setConstitutionBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.CONSTITUTION.getIndex()] = bonus; }
	public void setWisdomBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.WISDOM.getIndex()] = bonus; }
	public void setIntellegenceBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.INTELLIGENCE.getIndex()] = bonus; }
	public void setCharismaBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.CHARISMA.getIndex()] = bonus; }
	/**
	 * get different kind of extra bonus
	 * @return specific bonus for the character
	 */
	public int getStrengthBonus (){ return abilityBonusArr[Abilities.AbilityType.STRENGTH.getIndex()] ; }
	public int getDexterityBonus (){ return abilityBonusArr[Abilities.AbilityType.DEXTERITY.getIndex()] ; }
	public int getConstitutionBonus (){ return abilityBonusArr[Abilities.AbilityType.CONSTITUTION.getIndex()] ; }
	public int getWisdomBonus (){ return abilityBonusArr[Abilities.AbilityType.WISDOM.getIndex()] ; }
	public int getIntellegenceBonus (){ return abilityBonusArr[Abilities.AbilityType.INTELLIGENCE.getIndex()] ; }
	public int getCharismaBonus (){ return abilityBonusArr[Abilities.AbilityType.CHARISMA.getIndex()] ; }
	/**
	 * change back to previous race
	 * @return if successfully changed to previous race
	 */
    public boolean previousRace(){
        if(raceType.getIndex() <=0 ){
            return false;
        }
        else{
            this.raceType = raceType.getRaceType(this.getRaceType().getIndex() -1);
            updateTexture(this.raceType);
            return true;
        }
    }
	/**
	 * change back to previous race
	 * @return if successfully changed to previous race
	 */
	public boolean previousFighterType(){
		if(fighter.getFighterTypeIndex() <=0 ){
			return false;
		}
		else{
			createFighterBasedOnType(this.fighter.getFighterType().getFighterTypeFromIndex(this.fighter.getFighterTypeIndex()-1));
			this.fighter = director.getFighter();
			return true;
		}
	}
    /**
     * get promotion point 
     * @return promotion point 
     */
	public int getPromotionPoint() {
		return promotionPoint;
	}
	/**
	 * set promotion point
	 * @param promotionPoint
	 */
	public void setPromotionPoint(int promotionPoint) {
		this.promotionPoint = promotionPoint;
	}
	/**
	 * get a series of bonus
	 * @return a array as series of bonus
	 */
	public int[] getAbilityBonusArr() {
		return abilityBonusArr;
	}
	/**
	 * set a series of bonus
	 */
	public void setAbilityBonusArr(int[] abilityBonusArr) {
		this.abilityBonusArr = abilityBonusArr;
	}

	public int[] getWeaponEnchantmentInfection() {
		return weaponEnchantmentInfection;
	}

	public void setWeaponEnchantmentInfection(int[] weaponEnchantmentInfection) {
		this.weaponEnchantmentInfection = weaponEnchantmentInfection;
	}

	/**
	 * get name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get the abilities
	 * @return abilities
	 */
    public Abilities getAbilities() {
        return abilities;
    }
    /**
     * set abilities
     * @param abilities a array as different abilities
     */
    public void setAbilities(int[] abilities) {
        this.abilities.setAbilityArr(abilities);
    }
	/**
	 * get image
	 * @return the image
	 */
	public Texture getFriendlyTexture() {
		return friendlyTexture;
	}
    /**
     * get image
     * @return the image
     */
    public Texture getTexture() {
        return texture;
    }
    /**
     * set image
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    /**
     * get backpack
     * @return backpack
     */
    public ArrayList<Item> getBackpack() {
        return backpack;
    }
    /**
     * set backpack
     */
    public void setBackpack(ArrayList<Item> backpack) {
        this.backpack = backpack;
    }
    /**
     * get equipment
     * @return equipment
     */
    public HashMap<Item.ItemType, Item> getEquipment() {
        return equipment;
    }
    /**
     * set equipment
     */
    public void setEquipment(HashMap<Item.ItemType, Item> equipment) {
        this.equipment = equipment;
    }
    /**
     * load equipment
     * @param item the specific item for the character
     */
    public void loadEquipment(Item item) {
    	equipment.put(item.getItemType(), item);
    	int index = item.getEnchantedAbility().getIndex();
    	if( index < Abilities.ABILITYSIZE){
			//calculate the attack bonus, damage bonus, armor class, bonused by equiped equipment
			int addArmorClass = armorClass - CharacterScoreModifier.armorClassCalculator(getDexterity());
			int addAttackBonus = attackBonus - CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(),getDexterity(), getLevel());
			if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
				addAttackBonus = attackBonus - CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(),getDexterity(), getLevel());
			}
			int addDamageBonus = damageBonus - CharacterScoreModifier.damageBonusCalculator(getStrength());
    		
        	abilities.setAbility(index, abilities.getAbilityArr()[index] + item.getLevel());
			// re - add the equipment bonus after redistribute the ability score
			setArmorClass(CharacterScoreModifier.armorClassCalculator(getDexterity()) + addArmorClass);
			setAttackBonus(CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(), getDexterity(), getLevel())+addAttackBonus);
			if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
				setAttackBonus(CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(), getDexterity(), getLevel())+addAttackBonus);
			}
			setDamageBonus(CharacterScoreModifier.damageBonusCalculator(getStrength()) + addDamageBonus);
    	}
    	if( index == Abilities.ABILITYSIZE){
        	this.setArmorClass(getArmorClass() + item.getLevel());
    	}
    	if( index == Abilities.ABILITYSIZE + 1){
        	this.setAttackBonus(getAttackBonus() + item.getLevel());
    	}
    	if( index == Abilities.ABILITYSIZE + 2){
        	this.setDamageBonus(getDamageBonus() + item.getLevel());
    	}
    }
    /**
     * remove equipment from the character
     * @param itemType the type if the equipment
     * @return the removed equipment
     */
    public Item removeEquipment(Item.ItemType itemType) {
    	Item item = equipment.remove(itemType);
    	int index = item.getEnchantedAbility().getIndex();
    	if( index < Abilities.ABILITYSIZE){
			//calculate the attack bonus, damage bonus, armor class, bonused by equiped equipment
    		int addArmorClass = armorClass - CharacterScoreModifier.armorClassCalculator(getDexterity());
    		int addAttackBonus = attackBonus - CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(),getDexterity(), getLevel());
			if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
				addAttackBonus = attackBonus - CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(),getDexterity(), getLevel());
			}
    		int addDamageBonus = damageBonus - CharacterScoreModifier.damageBonusCalculator(getStrength());
    		
        	abilities.setAbility(index, abilities.getAbilityArr()[index] - item.getLevel());
			// re - add the equipment bonus after redistribute the ability score
			setArmorClass(CharacterScoreModifier.armorClassCalculator(getDexterity()) + addArmorClass);
			setAttackBonus(CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()) + addAttackBonus);
			if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
				setAttackBonus(CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()) + addAttackBonus);
			}
			setDamageBonus(CharacterScoreModifier.damageBonusCalculator(getStrength()) + addDamageBonus);
    	}
    	if( index == Abilities.ABILITYSIZE){
        	this.setArmorClass(getArmorClass() - item.getLevel());
    	}
    	if( index == Abilities.ABILITYSIZE + 1){
        	this.setAttackBonus(getAttackBonus() - item.getLevel());
    	}
    	if( index == Abilities.ABILITYSIZE + 2){
        	this.setDamageBonus(getDamageBonus() - item.getLevel());
    	}
		return item;
    }
    /**
     * get hit points
     * @return hit points
     */
    public int getHitPoints() {
		return hitPoints;
	}
    /**
     * set hit points
     * @param hitPoints
     */
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	/**
	 * reduce hit points
	 */
	public void reduceHitPoints(int deduction) {
		this.hitPoints -= deduction;
        MainMenuScreen.logArea.appendText(name + " is burning "+ deduction + "hp\n");
		if(isDead()){
			makeDead();
		}
	}
	/**
	 * get the ArmorClass
	 * @return armorClass
	 */
	public int getArmorClass() {
		return armorClass;
	}
	/**
	 * set the ArmorClass
	 * @param defense
	 */
	public void setArmorClass(int defense) {
		this.armorClass = defense;
	}
	/**
	 * get attack bonus
	 * @return attack bonus
	 */
	public int getAttackBonus() {
		return attackBonus;
	}
	/**
	 * set attack bonus
	 * @param attackBonus
	 */
	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}
	/**
	 * get damage bonus
	 * @return damage bonus the bonus of the damage
	 */
	public int getDamageBonus() {
		return damageBonus;
	}
	/**
	 * set damage bonus
	 * @param damageBonus the bonus of the damage
	 */
	public void setDamageBonus(int damageBonus) {
		this.damageBonus = damageBonus;
	}

	/**
	 * increase the level of the character only for player
	 */
	public void promoteUp(){
		if (level < PublicParameter.CHARACTER_MAX_LEVEL ){
			level ++;
			if(promotionPoint < 9){
				promotionPoint++;
			}
			setHitPoints(CharacterScoreModifier.hitPointCalculator(getConstitution(), getLevel()));
			setArmorClass(CharacterScoreModifier.armorClassCalculator(getDexterity()));
			setAttackBonus(CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
			if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
				setAttackBonus(CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
			}			setDamageBonus(CharacterScoreModifier.damageBonusCalculator(getStrength()));
		}
	}
	/**
	 * set level for enemy and NPC
	 * @param level the level for the character
	 */
	public void changeLevel(int level) {
		this.level = level;
		resetPromotePoint();
		setHitPoints(CharacterScoreModifier.hitPointCalculator(getConstitution(), getLevel()));
		setAttackBonus(CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
		if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
			setAttackBonus(CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
		}
		setDamageBonus(CharacterScoreModifier.damageBonusCalculator(getStrength()));
		setArmorClass(CharacterScoreModifier.armorClassCalculator(getDexterity()));
		HashMap.Entry<Item.ItemType, Item> entry;
		// adjust NPC's equipment items level
		for(Iterator<HashMap.Entry<Item.ItemType, Item>> it = equipment.entrySet().iterator(); it.hasNext(); ) {
			entry = it.next();
			int difference  = entry.getValue().getLevel() - (int) Math.ceil( level / 4.0 );
			if(difference != 0){
				entry.getValue().setLevel((int) Math.ceil( level / 4.0 ));
				if(entry.getValue().getEnchantedAbility().getIndex() < Abilities.ABILITYSIZE){
					abilities.getAbilityArr()[entry.getValue().getEnchantedAbility().getIndex()] -= difference;
					setHitPoints(CharacterScoreModifier.hitPointCalculator(getConstitution(), getLevel()));
					setArmorClass(CharacterScoreModifier.armorClassCalculator(getDexterity()));
					setAttackBonus(CharacterScoreModifier.meleeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
					if(this.getEquipment().get(Item.ItemType.WEAPON)!= null && this.getEquipment().get(Item.ItemType.WEAPON).getWeaponType() == WeaponModel.WeaponType.RANGE){
						setAttackBonus(CharacterScoreModifier.rangeAttackBonusCalculator(getStrength(), getDexterity(), getLevel()));
					}
					setDamageBonus(CharacterScoreModifier.damageBonusCalculator(getStrength()));
				} else if (entry.getValue().getEnchantedAbility().getIndex() == Abilities.ABILITYSIZE){
					this.setArmorClass(getArmorClass() - difference);
				}else if (entry.getValue().getEnchantedAbility().getIndex() == Abilities.ABILITYSIZE+1){
					this.setAttackBonus(getAttackBonus() - difference);
				}else if (entry.getValue().getEnchantedAbility().getIndex() == Abilities.ABILITYSIZE+2){
					this.setDamageBonus(getDamageBonus() - difference);
				}
			}
		}
		// adjust NPC's backpack items level
		for(Item item : backpack) {
			int difference  = item.getLevel() - (int) Math.ceil( level / 4.0 );
			if(difference != 0) {
				item.setLevel((int) Math.ceil(level / 4.0));
			}
		}
	}


	/**
	 * add item to backpack
	 * @param item
	 * @return boolean
	 */
	public boolean addToBackpack(Item item){
		if(backPackisFull()){
			backpack.remove(0);
		}
		backpack.add(item);
		return true;
	}
	/**
	 * get level
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * switch to string type
	 */
    public  String toString(){
		int[] tmp = new int[Abilities.ABILITYSIZE];
		for (int i = 0 ; i < tmp.length; i++){
			tmp[i] = abilities.getAbilityArr()[i] + abilityBonusArr[i];
		}
        return "Name: "+this.name + "| Race: " + this.raceType.toString()+  "| " + this.fighter.getFighterType().toString() +
				"| Level: "+this.level+"| Ability: "+ Arrays.toString(tmp)+"\nHP: "+ hitPoints+" | armor: "+armorClass+
				" | attack: "+attackBonus+" | damage: "+damageBonus;
    }
    
	/**
	 * Display personal full abilities and attributes
	 */
    public  String displayAllAtributes(){
		int[] tmp = this.getBaseAttributes();
		for (int i = 0 ; i < tmp.length; i++){
			if(i < abilityBonusArr.length){
				tmp[i] = abilities.getAbilityArr()[i] + abilityBonusArr[i];
			}
		}
        return "Name: "+this.name + "\n" +
        		"Race Type: " + this.raceType.toString()+ "\n" +
				"Fighter Type: " + this.fighter.getFighterType().toString()+ "\n" +
				"Level: "+this.level+ "\n" +
        		"Strength: " + tmp[0] + "\n" +
        		"Dexterity: " + tmp[1] + "\n" +
        		"Constitution: " + tmp[2] + "\n" +
        		"Wisdom: " + tmp[3] + "\n" +
        		"Intelligence: " + tmp[4] + "\n" +
        		"Charisma: " + tmp[5] + "\n" +
        		"Hit Point : " + tmp[6] + "\n" +
				"Attach Bonus : " + tmp[7] + "\n" +
				"Damage Bonus : " + tmp[8] + "\n" +
				"Armor Class : " + tmp[9] ;
	}

    /**
     * 
     * decide if the character is dead or not 
     * @return true or false
     */
    public boolean isDead() {
		return getHitPoints() <= 1;
	}
    /**
     * get Strength
     * @return Strength
     */
	public int getStrength() {
        return abilities.getAbilityArr()[Abilities.AbilityType.STRENGTH.getIndex()];
	}
    /**
     * get Dexterity
     * @return Dexterity
     */
	public int getDexterity() {
		return abilities.getAbilityArr()[Abilities.AbilityType.DEXTERITY.getIndex()];
	}
    /**
     * get Constitution
     * @return Constitution
     */
	public int getConstitution() {
        return abilities.getAbilityArr()[Abilities.AbilityType.CONSTITUTION.getIndex()];
	}
    /**
     * get Wisdom
     * @return Wisdom
     */
	public int getWisdom() {
        return abilities.getAbilityArr()[Abilities.AbilityType.WISDOM.getIndex()];

    }
    /**
     * get Intelligence
     * @return Intelligence
     */
	public int getIntelligence() {
        return abilities.getAbilityArr()[Abilities.AbilityType.INTELLIGENCE.getIndex()];

    }
    /**
     * get Charisma
     * @return Charisma
     */
	public int getCharisma() {
        return abilities.getAbilityArr()[Abilities.AbilityType.CHARISMA.getIndex()];

    }
	/**
	 * set Strength
	 * @param strength 
	 */
	public void setStrength(int strength) {
        abilities.setAbility(Abilities.AbilityType.STRENGTH.getIndex(), strength);
	}
	/**
	 * set Dexterity
	 * @param dexterity 
	 */
	public void setDexterity(int dexterity) {
		abilities.setAbility(Abilities.AbilityType.DEXTERITY.getIndex(), dexterity);
	}
	/**
	 * set constitution
	 * @param constitution
	 */
	public void setConstitution(int constitution) {
        abilities.setAbility(Abilities.AbilityType.CONSTITUTION.getIndex(), constitution);
    }
	/**
	 * 
	 * set wisdom
	 * @param wisdom
	 */
    public void setWisdom(int wisdom) {
        abilities.setAbility(Abilities.AbilityType.WISDOM.getIndex(), wisdom);
    }
    /**
     * set intelligence
     * @param intelligence
     */
    public void setIntelligence(int intelligence) {
        abilities.setAbility(Abilities.AbilityType.INTELLIGENCE.getIndex(), intelligence);

    }
    /**
     * set charisma
     * @param charisma
     */
    public void setCharisma(int charisma){
        abilities.setAbility(Abilities.AbilityType.CHARISMA.getIndex(), charisma);
    }
    /**
     * get the type of the class
     * @return classType
     */
    public FighterType getFighterType() {
		return fighter.getFighterType();
	}
    /**
     * set the type of the class
     * @param fighterType  the type of the class
     */
	public void setFighterType(FighterType fighterType) {
		createFighterBasedOnType(fighterType);
		this.fighter = director.getFighter();
	}
	/**
	 * get all attributes of item
	 * @return a array as attribute
	 */
	public int[] getBaseAttributes(){
        int[] attributeArr = new int[Abilities.ABILITYSIZE + Character.FIGHTATTRUBUTESIZE];
        System.arraycopy(abilities.getAbilityArr(), 0 , attributeArr , 0, Abilities.ABILITYSIZE);
        attributeArr[Abilities.ABILITYSIZE ] = hitPoints;
        attributeArr[Abilities.ABILITYSIZE + 1] = attackBonus;
        attributeArr[Abilities.ABILITYSIZE + 2] = damageBonus;
        attributeArr[Abilities.ABILITYSIZE + 3] = armorClass;
        return attributeArr;
    }

	/**
	 * check if backpack max size reached
	 * @return
	 */
    public boolean backPackisFull(){
		return backpack.size() >= 10;
	}
	/**
	 * get the type of the race
	 * @return the race type
	 */
	public RaceType getRaceType() {
		return raceType;
	}
	/**
	 * set the type of the race
	 * @param raceType the race type
	 */
	public void setRaceType(RaceType raceType) {
		this.raceType = raceType;
	}
	/**
	 * write files for character information
	 */
	@Override
	public void write(Json json) {
		json.writeValue("FighterType", fighter.getFighterType());
		json.writeValue("RaceType", raceType);
		json.writeValue("Name", name);
		json.writeValue("Level", level);
		json.writeValue("abilities", abilities);
		json.writeValue("hitPoints", hitPoints);
		json.writeValue("attackBonus", attackBonus);
		json.writeValue("damageBonus", damageBonus);
		json.writeValue("armorClass", armorClass);
		json.writeValue("weaponEnchantmentInfection", weaponEnchantmentInfection);
		json.writeValue("equipment", equipment, HashMap.class, Item.class);
		json.writeValue("backPack", backpack, ArrayList.class, Item.class);
		json.writeValue("PromoPoint", promotionPoint);
		json.writeValue("BonusAbilities", abilityBonusArr);
	}
	/**
	 * read files for character information
	 */
	@Override
	public void read(Json json, JsonValue jsonData) {
		createFighterBasedOnType(FighterType.valueOf(jsonData.child.asString()));
		this.fighter = director.getFighter();
		raceType = RaceType.valueOf(jsonData.child.next.asString());
		updateTexture(raceType);
		name = jsonData.child.next.next.asString();
		level = jsonData.child.next.next.next.asInt();
		setAbilities(jsonData.child.next.next.next.next.child.asIntArray());
		hitPoints = jsonData.child.next.next.next.next.next.asInt();
		if(isDead()){
			this.texture = new Texture(Gdx.files.internal("races/dead.png"));
		}
		attackBonus = jsonData.child.next.next.next.next.next.next.asInt();
		damageBonus = jsonData.child.next.next.next.next.next.next.next.asInt();
		armorClass = jsonData.child.next.next.next.next.next.next.next.next.asInt();
		weaponEnchantmentInfection = jsonData.child.next.next.next.next.next.next.next.next.next.asIntArray();
		JsonValue equipmentPointer = jsonData.child.next.next.next.next.next.next.next.next.next.next;
		if(equipmentPointer != null){
			Iterator<JsonValue> dataIterator = equipmentPointer.iterator();
			Item item;
			JsonValue dataValue;
			String context;
			while(dataIterator.hasNext()){
				dataValue= dataIterator.next();
				context = dataValue.toString();
				context = context.substring(context.indexOf("{")-1);
				item = json.fromJson(Item.class, context);
				equipment.put(Item.ItemType.valueOf(dataValue.name) ,item);
			}
		}

		JsonValue backPackPointer = jsonData.child.next.next.next.next.next.next.next.next.next.next.next;
		if(backPackPointer != null){
			Iterator<JsonValue> dataIterator = backPackPointer.iterator();
			Item item;
			JsonValue dataValue;
			String context;
			while(dataIterator.hasNext()){
				dataValue= dataIterator.next();
				context = dataValue.toString();
				item = json.fromJson(Item.class, context);
				backpack.add(item);
			}
		}

		promotionPoint  = jsonData.child.next.next.next.next.next.next.next.next.next.next.next.next.asInt();
		abilityBonusArr = jsonData.child.next.next.next.next.next.next.next.next.next.next.next.next.next.asIntArray();

	}
	
	/**
	 * draw texture on the game screen for NPC, and player
	 * @param batch
	 * @param cur  NPC, or player location
	 * @param isFriendly
	 */
	public void draw(SpriteBatch batch, Vector2 cur, boolean isFriendly, float delta) {
		if(!blinker.shouldBlink(delta)){
			if(isFriendly == true){
				// is friendly NPC use unified image
				batch.draw(friendlyTexture, cur.x, cur.y, PublicParameter.MAP_PIXEL_SIZE  / 3, PublicParameter.MAP_PIXEL_SIZE  / 3 );
			} else{
				batch.draw(texture, cur.x, cur.y, PublicParameter.MAP_PIXEL_SIZE  / 2, PublicParameter.MAP_PIXEL_SIZE  / 2);
			}
		}
	}

	/**
	 * observerable method to preview ability points
	 */
	public void previewAllAttribute(){
		setChanged();
		notifyObservers(0);
	}
	/**
	 * observerable method to preview all items, and change equipment
	 */
	public void previewLoadEquipment(int index){
		if(index >= 0 && index < backpack.size() ){
			Item itemtmp = backpack.remove(index);
			loadEquipment(itemtmp);
		}
		setChanged();
		notifyObservers(1);
	}
	/**
	 * observerable method to preview all items, and change equipment
	 */
	public void previewUnloadEquipment(int index){
		if(index >= 0 ){
			getBackpack().add(removeEquipment(Item.ItemType.getItemType(index)));
		}
		setChanged();
		notifyObservers(1);
	}

	/**
	 * create Fighter Based On Type
	 * @param type
	 */
	private void createFighterBasedOnType(FighterType type) {
		director = new FighterBuilderDirector();
		switch (type){
			case BULLY:
				fighterBuilder = new FighterBullyBuilder();
				break;
			case NIMBLE:
				fighterBuilder = new FighterNimbleBuilder();
				break;
			case TANK:
				fighterBuilder = new FighterTankBuilder();
				break;
			default:
				fighterBuilder = new FighterBullyBuilder();
				break;
		}
		director.setBuilder(fighterBuilder);
		director.constructFighter();
	}
	/**
	 * get Fighter
	 * @return
	 */
	public Fighter getFighter() {
		return fighter;
	}
	/**
	 * set Fighter value
	 * @param fighter
	 */
	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
	}
}
