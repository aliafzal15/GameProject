package Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.chaowang.ddgame.PublicParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import Classes.Class.ClassType;
import Items.*;
import Races.Race.RaceType;
import util.AbilityModifier;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
/**
 * create characters
 * @author chao Wang
 * @version 1.0
 */
public class Character implements Json.Serializable{
    public static final int FIGHTATTRUBUTESIZE = 4;

	private ClassType classType;
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
	boolean isFriendly = true;

    private HashMap<Item.ItemType,Item> equipment;
    private ArrayList<Item> backpack;
    
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
		this(name, 1, RaceType.HUMAN);
	}
	/**
	 * 
	 * constructor for the class
	 * @param name the name of the character
	 * @param level the level of the character
	 * @param raceType the raceType of the character
	 */
	public Character(String name, int level, RaceType raceType) {
		this.setName(name);
		this.classType = ClassType.FIGHTER;
		this.raceType  = raceType;
		this.hitPoints = 0;
		this.attackBonus = 0;
		this.damageBonus = 0;
		this.armorClass = 0;
		this.abilities = new Abilities(0);
		this.abilityBonusArr = new int[Abilities.ABILITYSIZE];
		this.level = level;
		this.promotionPoint = level - 1;
		this.backpack = new ArrayList<Item>();
        this.equipment = new HashMap<Item.ItemType, Item>();
        updateTexture(raceType);
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
    public Character(String name, int level,int promotionPoint, RaceType raceType, int[] abilityArr, int[] abilityBonus) {
        this.setName(name);
        this.classType = ClassType.FIGHTER;
        this.raceType  = raceType;
		this.level = level;
		this.promotionPoint = promotionPoint;
		this.backpack = new ArrayList<Item>(PublicParameter.itemBackpackColumn * PublicParameter.itemBackpackRow);
        this.equipment = new HashMap<Item.ItemType, Item>();
        updateTexture(raceType);
        int[] subAbilityArr = new int[Abilities.ABILITYSIZE];
        System.arraycopy(abilityArr, 0 , subAbilityArr , 0, Abilities.ABILITYSIZE);
        this.abilities = new Abilities(subAbilityArr);
        this.setArmorClass(abilityArr[Abilities.ABILITYSIZE+0]);
        this.setAttackBonus(abilityArr[Abilities.ABILITYSIZE+1]);
        this.setDamageBonus(abilityArr[Abilities.ABILITYSIZE+2]);
        this.setHitPoints(abilityArr[Abilities.ABILITYSIZE+3]);
		abilityBonusArr = new int[Abilities.ABILITYSIZE];
		System.arraycopy(abilityBonus, 0, abilityBonusArr, 0, abilityBonus.length );
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
    public Character(String name, int level,int promotionPoint, RaceType raceType, int[] abilityArr, int[] abilityBonusArr, ArrayList<Item> backpack, HashMap<Item.ItemType,Item> equipment) {
        this(name,level,promotionPoint,raceType,abilityArr, abilityBonusArr);
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
                texture = new Texture(Gdx.files.internal("android/assets/races/human.jpg"));
                break;
            case DWARF:
                texture = new Texture(Gdx.files.internal("android/assets/races/dwarf.png"));
                break;
            case ELF:
                texture = new Texture(Gdx.files.internal("android/assets/races/elf.png"));
                break;
            case ORC:
                texture = new Texture(Gdx.files.internal("android/assets/races/orc.png"));
                break;
            case TAUREN:
                texture = new Texture(Gdx.files.internal("android/assets/races/tauren.png"));
                break;
            case TROLL:
                texture = new Texture(Gdx.files.internal("android/assets/races/troll.png"));
                break;
            case UNDEAD:
                texture = new Texture(Gdx.files.internal("android/assets/races/undead.png"));
                break;
        }
    }
    /**
     * decide the character is friendly or hostile 
     * @return true or false
     */
	public boolean getFriendly() {
		return isFriendly;
	}
	/**
	 * set the character is friendly or hostile 
	 * @param friend friendly or hostile 
	 */
	public void setFriendly(boolean friend) {
		isFriendly = friend;
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
	 * reset promote point for the character
	 */
	public void resetPromotePoint(){
		promotionPoint = level -1;
		for (int i : abilityBonusArr){
			i = 0;
		}
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
	 * increase the level of the character
	 */
	public void levelUp(){
		if (level < 10 ){
			level ++;
			if(promotionPoint < 9){
				promotionPoint++;
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
     * get promotion point 
     * @return promotion point 
     */
	public int getPromotionPoint() {
		return promotionPoint;
	}
	/**
	 * set promotion point
	 * @param promotion point
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
        this.abilities.setAbilityArr(abilities);;
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
    		int addArmorClass = armorClass - AbilityModifier.armorClassModifier(getDexterity());
    		int addAttackBonus = attackBonus - AbilityModifier.attachBonusModifier(getStrength(),getDexterity(), getLevel());
    		int addDamageBonus = damageBonus - AbilityModifier.damageBonusModifier(getStrength());
    		
        	abilities.setAbility(index, abilities.getAbilityArr()[index] + item.getLevel());
			setArmorClass(AbilityModifier.armorClassModifier(getDexterity()) + addArmorClass);
			setAttackBonus(AbilityModifier.attachBonusModifier(getStrength(),getDexterity(), getLevel()) + addAttackBonus);
			setDamageBonus(AbilityModifier.damageBonusModifier(getStrength()) + addDamageBonus);
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
    		int addArmorClass = armorClass - AbilityModifier.armorClassModifier(getDexterity());
    		int addAttackBonus = attackBonus - AbilityModifier.attachBonusModifier(getStrength(),getDexterity(), getLevel());
    		int addDamageBonus = damageBonus - AbilityModifier.damageBonusModifier(getStrength());
    		
        	abilities.setAbility(index, abilities.getAbilityArr()[index] - item.getLevel());
			setArmorClass(AbilityModifier.armorClassModifier(getDexterity()) + addArmorClass);
			setAttackBonus(AbilityModifier.attachBonusModifier(getStrength(),getDexterity(), getLevel()) + addAttackBonus);
			setDamageBonus(AbilityModifier.damageBonusModifier(getStrength()) + addDamageBonus);
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
	 * set level
	 * @param level the level for the character
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * get level
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * set hitPoints
	 * @param hitPoints the hit points 
	 */
	public void setHP(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	/**
	 * get hitPoints
	 * @return the hit points 
	 */
	public int getHP() {
		return hitPoints ;
	}
	/**
	 * switch to string type
	 */
    public  String toString(){
		int[] tmp = new int[Abilities.ABILITYSIZE];
		for (int i = 0 ; i < tmp.length; i++){
			tmp[i] = abilities.getAbilityArr()[i] + abilityBonusArr[i];
		}
        return "Name: "+this.name + "| Race Type: " + this.raceType.toString()+ "| Level: "+this.level+"| Ability: "+ Arrays.toString(tmp);
    }

    /**
     * 
     * decide if the character is dead or not 
     * @return true or false
     */
    public boolean isDead() {
		return getHP() <= 0;
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
    public ClassType getClassType() {
		return classType;
	}
    /**
     * set the type of the class
     * @param classType  the type of the class
     */
	public void setClassType(ClassType classType) {
		this.classType = classType;
	}
	/**
	 * get all attributes of item
	 * @return a array as attribute
	 */
	public int[] getAllAttributes(){
        int[] attributeArr = new int[Abilities.ABILITYSIZE + Character.FIGHTATTRUBUTESIZE];
        System.arraycopy(abilities.getAbilityArr(), 0 , attributeArr , 0, Abilities.ABILITYSIZE);
        attributeArr[Abilities.ABILITYSIZE ] = armorClass;
        attributeArr[Abilities.ABILITYSIZE + 1] = attackBonus;
        attributeArr[Abilities.ABILITYSIZE + 2] = damageBonus;
        attributeArr[Abilities.ABILITYSIZE + 3] = hitPoints;
        return attributeArr;
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
		json.writeValue("ClassType", classType);
		json.writeValue("RaceType", raceType);
		json.writeValue("Name", name);
		json.writeValue("Level", level);
		json.writeValue("abilities", abilities);
		json.writeValue("attackBonus", attackBonus);
		json.writeValue("damageBonus", damageBonus);
		json.writeValue("hitPoints", hitPoints);
		json.writeValue("armorClass", armorClass);
		json.writeValue("isFriendly", isFriendly);
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
		classType = ClassType.valueOf(jsonData.child.asString());
		raceType = RaceType.valueOf(jsonData.child.next.asString());
		updateTexture(raceType);
		name = jsonData.child.next.next.asString();
		level = jsonData.child.next.next.next.asInt();
		setAbilities(jsonData.child.next.next.next.next.child.asIntArray());
		attackBonus = jsonData.child.next.next.next.next.next.asInt();
		damageBonus = jsonData.child.next.next.next.next.next.next.asInt();
		hitPoints = jsonData.child.next.next.next.next.next.next.next.asInt();
		armorClass = jsonData.child.next.next.next.next.next.next.next.next.asInt();
		isFriendly = jsonData.child.next.next.next.next.next.next.next.next.next.asBoolean();
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


}
