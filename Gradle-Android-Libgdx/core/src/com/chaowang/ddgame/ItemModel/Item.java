package com.chaowang.ddgame.ItemModel;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.chaowang.ddgame.PublicParameter;

/**
 * the class is for item editor
 * @author chao wang
 * @version 1.0
 */
public class Item extends Rectangle implements Json.Serializable {

	/**
	 * all item types
	 */
    public enum ItemType {
        HELMET(0, new EnchantedAbility[]{EnchantedAbility.INTELLIGENCE, EnchantedAbility.ARMORCLASS, EnchantedAbility.WISDOM}),
        ARMOR(1, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS}),
        WEAPON(2, new EnchantedAbility[]{EnchantedAbility.ATTACKBONUS, EnchantedAbility.DAMAGEBONUS}),
        BELT(3, new EnchantedAbility[]{EnchantedAbility.CONSTITUTION, EnchantedAbility.STRENGTH}),
        SHIELD(4, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS}),
        BOOTS(5, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS, EnchantedAbility.DEXTERITY}),
        RING(6, new EnchantedAbility[]{EnchantedAbility.ARMORCLASS, EnchantedAbility.STRENGTH, EnchantedAbility.WISDOM, EnchantedAbility.CONSTITUTION, EnchantedAbility.CHARISMA});

        EnchantedAbility[] abilityArr;
        private int index;
        /**
         * construct item type
         * @param index
         * @param abilityArr
         */
        private ItemType(int index, EnchantedAbility[] abilityArr) {
            this.index = index;
            this.abilityArr = abilityArr;
        }
        /**
         * get enchanted ability
         * @return a array as enchanted abilities
         */
        public EnchantedAbility[] getEnchantedAbility() {
            return abilityArr;
        }
        /**
         * get item index
         * @return index
         */
        public int getIndex() {
            return index;
        }
        /**
         * get the item type
         * @param index to choose which specific item
         * @return a specific item type
         */
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
    private Texture textureOnMap;
    /**
     * constructor
     * @param type
     * @param name
     * @param level
     * @param enchantedAbility
     */
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
        updateTexture(this.itemType);
        textureOnMap = new Texture(Gdx.files.internal("map/chest.png"));
    }
    /**
     * update the item type image
     * @param type a item type
     */
    private void updateTexture(ItemType type) {
//        if (texture != null){
//            texture.dispose();
//        }
        switch (type){
            case ARMOR:
                texture = new Texture(Gdx.files.internal("items/armor.png"));
                break;
            case BELT:
                texture = new Texture(Gdx.files.internal("items/belt.png"));
                break;
            case BOOTS:
                texture = new Texture(Gdx.files.internal("items/boots.png"));
                break;
            case HELMET:
                texture = new Texture(Gdx.files.internal("items/helmet.png"));
                break;
            case RING:
                texture = new Texture(Gdx.files.internal("items/ring.png"));
                break;
            case SHIELD:
                texture = new Texture(Gdx.files.internal("items/shield.jpg"));
                break;
            case WEAPON:
                texture = new Texture(Gdx.files.internal("items/sword.png"));
                break;
        }
    }
    /**
     * constructor
     */
    public Item(){
        this(ItemType.HELMET, "HELMET", 0, ItemType.ARMOR.getEnchantedAbility()[0]);
    }
    /**
     * set the item level
     * @param level the level of the item
     */
    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * set the item name
     * @param name the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * get the item type
     * @return the item type
     */
    public ItemType getItemType() {
        return itemType;
    }
    /**
     * get the item level
     * @return the item level
     */
    public int getLevel() {
        return level;
    }
    /**
     * get the item name 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }
    /**
     * get the item image
     * @return the item image
     */
    public Texture getTexture() {
        return texture;
    }
    /**
     * get the enchanted ability
     * @return the enchanted ability
     */
    public EnchantedAbility getEnchantedAbility() {
        return enchantedAbility;
	}
    /**
     * switch to the string type
     */
	public  String toString(){
        return "Type: " + this.itemType.toString()+ "| Name: "+this.name+"| Bonus: "+this.level+"| Ability: "+this.enchantedAbility.toString();
    }
	/**
	 * switch to next ability type
	 * @return if successfully change to the next ability type
	 */
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
	/**
	 * switch to the previous ability type
	 * @return if successfully change back to the previous ability type
	 */
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
    /**
     * switch to next item type
     * @return if successfully change to the next item type
     */
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
    /**
     * switch to the previous item type
     * @return if successfully change back to the previous item type
     */
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

    /**
     * write files for item information
     */
    @Override
    public void write(Json json) {
        json.writeValue("ItemType", itemType);
        json.writeValue("Name", name);
        json.writeValue("Level", level);
        json.writeValue("enchantedAbility", enchantedAbility);
        json.writeValue("abiltyPointer", abilityPointer);
    }
    /**
     * read files for item information
     */
    @Override
    public void read(Json json, JsonValue jsonData) {
        itemType = ItemType.valueOf(jsonData.child.asString());
        updateTexture(itemType);
        name = jsonData.child.next.asString();
        level = jsonData.child.next.next.asInt();
        enchantedAbility = EnchantedAbility.valueOf(jsonData.child.next.next.next.asString());
        abilityPointer = jsonData.child.next.next.next.next.asInt();
    }

    /**
     * put the item image on the map
     * @param batch
     */
    public void draw(SpriteBatch batch, Vector2 cur){
        this.x = cur.x;
        this.y = cur.y;
        this.width = PublicParameter.MAP_PIXEL_SIZE  / 3;
        this.height = PublicParameter.MAP_PIXEL_SIZE  / 3;
        batch.draw(textureOnMap, this.x , this.y, this.width, this.height );
    }

}
