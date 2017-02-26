package Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.PublicParameter;

import java.io.Serializable;

public class Item implements Json.Serializable{


    public enum ItemType {
        HELMET(0, new EnchantedAbility[]{EnchantedAbility.INTELLIGENCE, EnchantedAbility.ARMORCLASS, EnchantedAbility.WISDOM}),
        ARMOR(1, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS}),
        WEAPON(2, new EnchantedAbility[]{EnchantedAbility.ATTACKBONUS,EnchantedAbility.DAMAGEBONUS}),
        BELT(3, new EnchantedAbility[]{EnchantedAbility.CONSTITUTION,EnchantedAbility.STRENGTH}),
        SHIELD(4, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS}),
        BOOTS(5, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS,EnchantedAbility.DEXTERITY}),
        RING(6, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS, EnchantedAbility.STRENGTH, EnchantedAbility.WISDOM, EnchantedAbility.CONSTITUTION, EnchantedAbility.CHARISMA});

        EnchantedAbility[] abilityArr;
        private int index;

        private ItemType(int index, EnchantedAbility[] abilityArr) {
            this.index = index;
            this.abilityArr = abilityArr;
        }

        public EnchantedAbility[] getEnchantedAbility() {
            return abilityArr;
        }

        public int getIndex() {
            return index;
        }

        public static ItemType getItemType(int index){
            switch (index){
                case 0:
                    return HELMET;
                case 1:
                    return ARMOR;
                case 2:
                    return WEAPON;
                case 3:
                    return BELT;
                case 4:
                    return SHIELD;
                case 5:
                    return BOOTS;
                case 6:
                    return RING;
            }
            return HELMET;
        }

    };

    private int level;
    private String name;
    private ItemType itemType;
    private EnchantedAbility enchantedAbility;
    private Texture texture;
    private int abilityPointer = 0;

    public Item(ItemType type , String name, int level, EnchantedAbility enchantedAbility) {
        this.itemType = type;
        abilityPointer=0;
        while(abilityPointer < type.abilityArr.length){
            if(enchantedAbility == type.abilityArr[abilityPointer]){
                break;
            }
            abilityPointer ++;
        }
        this.level = level;
        this.name = name;
        this.enchantedAbility = enchantedAbility;
        updateTexture(type);
    }

    private void updateTexture(ItemType type) {
//        if (texture != null){
//            texture.dispose();
//        }
        switch (type){
            case ARMOR:
                texture = new Texture(Gdx.files.internal("android/assets/items/armor.png"));
                break;
            case BELT:
                texture = new Texture(Gdx.files.internal("android/assets/items/belt.png"));
                break;
            case BOOTS:
                texture = new Texture(Gdx.files.internal("android/assets/items/boots.png"));
                break;
            case HELMET:
                texture = new Texture(Gdx.files.internal("android/assets/items/helmet.png"));
                break;
            case RING:
                texture = new Texture(Gdx.files.internal("android/assets/items/ring.png"));
                break;
            case SHIELD:
                texture = new Texture(Gdx.files.internal("android/assets/items/shield.jpg"));
                break;
            case WEAPON:
                texture = new Texture(Gdx.files.internal("android/assets/items/sword.png"));
                break;
        }
    }

    public Item(){
        this(ItemType.HELMET, "HELMET", 0, ItemType.ARMOR.getEnchantedAbility()[0]);
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public EnchantedAbility getEnchantedAbility() {
        return enchantedAbility;
	}

	public  String toString(){
        return "Item Type: " + this.itemType.toString()+ "| Name: "+this.name+"| Bonus: "+this.level+"| Ability: "+this.enchantedAbility.toString();
    }

	public boolean nextAbility(){
        if(abilityPointer >= this.itemType.abilityArr.length - 1){
            return false;
        }
        else{
            abilityPointer ++;
            this.enchantedAbility = this.itemType.abilityArr[abilityPointer];
            return true;
        }
    }

    public boolean previousAbility(){
        if(abilityPointer <= 0 ){
            return false;
        }
        else{
            abilityPointer --;
            this.enchantedAbility = this.itemType.abilityArr[abilityPointer];
            return true;
        }
    }

    public boolean nextItem(){
        if(itemType.index >= 6 ){
            return false;
        }
        else{
            this.itemType = itemType.getItemType(this.itemType.index+1);
            updateTexture(this.itemType);
            this.enchantedAbility = this.itemType.abilityArr[0];
            abilityPointer = 0;
            return true;
        }
    }

    public boolean previousItem(){
        if(itemType.index <=0 ){
            return false;
        }
        else{
            this.itemType = itemType.getItemType(this.itemType.index-1);
            updateTexture(this.itemType);
            this.enchantedAbility = this.itemType.abilityArr[0];
            abilityPointer = 0;
            return true;
        }
    }


    @Override
    public void write(Json json) {
        json.writeValue("ItemType", itemType);
        json.writeValue("Name", name);
        json.writeValue("Level", level);
        json.writeValue("enchantedAbility", enchantedAbility);
        json.writeValue("abiltyPointer", abilityPointer);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        itemType = ItemType.valueOf(jsonData.child.asString());
        updateTexture(itemType);
        name = jsonData.child.next.asString();
        level = jsonData.child.next.next.asInt();
        enchantedAbility = EnchantedAbility.valueOf(jsonData.child.next.next.next.asString());
        abilityPointer = jsonData.child.next.next.next.next.asInt();
    }

}
