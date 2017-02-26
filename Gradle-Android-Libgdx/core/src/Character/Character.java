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

	public Character(){
		this("Default");
	};
	
	public Character(String name) {
		this(name, 1, RaceType.HUMAN);
	}

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

    public Character(String name, int level,int promotionPoint, RaceType raceType, int[] abilityArr, int[] abilityBonusArr, ArrayList<Item> backpack, HashMap<Item.ItemType,Item> equipment) {
        this(name,level,promotionPoint,raceType,abilityArr, abilityBonusArr);
        this.backpack = backpack;
		this.equipment = equipment;
    }

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

	public boolean getFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean friend) {
		isFriendly = friend;
	}

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

	public void resetPromotePoint(){
		promotionPoint = level -1;
		for (int i : abilityBonusArr){
			i = 0;
		}
	}

    public void levelDown(){
		if (level > 1 ){
			level --;
			if(promotionPoint > 0){
				promotionPoint --;
			}
		}
	}

	public void levelUp(){
		if (level < 10 ){
			level ++;
			if(promotionPoint < 9){
				promotionPoint++;
			}
		}
	}

	public void setStrengthBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.STRENGTH.getIndex()] = bonus; }
	public void setDexterityBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.DEXTERITY.getIndex()] = bonus; }
	public void setConstitutionBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.CONSTITUTION.getIndex()] = bonus; }
	public void setWisdomBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.WISDOM.getIndex()] = bonus; }
	public void setIntellegenceBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.INTELLIGENCE.getIndex()] = bonus; }
	public void setCharismaBonus (int bonus){ abilityBonusArr[Abilities.AbilityType.CHARISMA.getIndex()] = bonus; }

	public int getStrengthBonus (){ return abilityBonusArr[Abilities.AbilityType.STRENGTH.getIndex()] ; }
	public int getDexterityBonus (){ return abilityBonusArr[Abilities.AbilityType.DEXTERITY.getIndex()] ; }
	public int getConstitutionBonus (){ return abilityBonusArr[Abilities.AbilityType.CONSTITUTION.getIndex()] ; }
	public int getWisdomBonus (){ return abilityBonusArr[Abilities.AbilityType.WISDOM.getIndex()] ; }
	public int getIntellegenceBonus (){ return abilityBonusArr[Abilities.AbilityType.INTELLIGENCE.getIndex()] ; }
	public int getCharismaBonus (){ return abilityBonusArr[Abilities.AbilityType.CHARISMA.getIndex()] ; }

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

	public int getPromotionPoint() {
		return promotionPoint;
	}

	public void setPromotionPoint(int promotionPoint) {
		this.promotionPoint = promotionPoint;
	}

	public int[] getAbilityBonusArr() {
		return abilityBonusArr;
	}

	public void setAbilityBonusArr(int[] abilityBonusArr) {
		this.abilityBonusArr = abilityBonusArr;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

    public Abilities getAbilities() {
        return abilities;
    }

    public void setAbilities(int[] abilities) {
        this.abilities.setAbilityArr(abilities);;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public ArrayList<Item> getBackpack() {
        return backpack;
    }

    public void setBackpack(ArrayList<Item> backpack) {
        this.backpack = backpack;
    }

    public HashMap<Item.ItemType, Item> getEquipment() {
        return equipment;
    }

    public void setEquipment(HashMap<Item.ItemType, Item> equipment) {
        this.equipment = equipment;
    }
    
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

    public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getArmorClass() {
		return armorClass;
	}

	public void setArmorClass(int defense) {
		this.armorClass = defense;
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	public int getDamageBonus() {
		return damageBonus;
	}

	public void setDamageBonus(int damageBonus) {
		this.damageBonus = damageBonus;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}


	public void setHP(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getHP() {
		return hitPoints ;
	}

    public  String toString(){
		int[] tmp = new int[Abilities.ABILITYSIZE];
		for (int i = 0 ; i < tmp.length; i++){
			tmp[i] = abilities.getAbilityArr()[i] + abilityBonusArr[i];
		}
        return "Name: "+this.name + "| Race Type: " + this.raceType.toString()+ "| Level: "+this.level+"| Ability: "+ Arrays.toString(tmp);
    }


    public boolean isDead() {
		return getHP() <= 0;
	}

	public int getStrength() {
        return abilities.getAbilityArr()[Abilities.AbilityType.STRENGTH.getIndex()];
	}

	public int getDexterity() {
		return abilities.getAbilityArr()[Abilities.AbilityType.DEXTERITY.getIndex()];
	}

	public int getConstitution() {
        return abilities.getAbilityArr()[Abilities.AbilityType.CONSTITUTION.getIndex()];
	}

	public int getWisdom() {
        return abilities.getAbilityArr()[Abilities.AbilityType.WISDOM.getIndex()];

    }

	public int getIntelligence() {
        return abilities.getAbilityArr()[Abilities.AbilityType.INTELLIGENCE.getIndex()];

    }

	public int getCharisma() {
        return abilities.getAbilityArr()[Abilities.AbilityType.CHARISMA.getIndex()];

    }

	public void setStrength(int strength) {
        abilities.setAbility(Abilities.AbilityType.STRENGTH.getIndex(), strength);
	}

	public void setDexterity(int dexterity) {
		abilities.setAbility(Abilities.AbilityType.DEXTERITY.getIndex(), dexterity);
	}

	public void setConstitution(int constitution) {
        abilities.setAbility(Abilities.AbilityType.CONSTITUTION.getIndex(), constitution);
    }

    public void setWisdom(int wisdom) {
        abilities.setAbility(Abilities.AbilityType.WISDOM.getIndex(), wisdom);
    }

    public void setIntelligence(int intelligence) {
        abilities.setAbility(Abilities.AbilityType.INTELLIGENCE.getIndex(), intelligence);

    }

    public void setCharisma(int charisma){
        abilities.setAbility(Abilities.AbilityType.CHARISMA.getIndex(), charisma);
    }


    public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public int[] getAllAttributes(){
        int[] attributeArr = new int[Abilities.ABILITYSIZE + Character.FIGHTATTRUBUTESIZE];
        System.arraycopy(abilities.getAbilityArr(), 0 , attributeArr , 0, Abilities.ABILITYSIZE);
        attributeArr[Abilities.ABILITYSIZE ] = armorClass;
        attributeArr[Abilities.ABILITYSIZE + 1] = attackBonus;
        attributeArr[Abilities.ABILITYSIZE + 2] = damageBonus;
        attributeArr[Abilities.ABILITYSIZE + 3] = hitPoints;
        return attributeArr;
    }


	public RaceType getRaceType() {
		return raceType;
	}


	public void setRaceType(RaceType raceType) {
		this.raceType = raceType;
	}

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
