package com.chaowang.ddgame.util;
import java.lang.Integer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
/**
 * 
 * @author chao wang
 * @version 3.0
 */
public class IntVector2Pair implements Json.Serializable, Comparable<IntVector2Pair>{
	private Integer key;
	private Vector2 value;
	/**
	 * constructor
	 * @param ki
	 * @param vec
	 */
	public IntVector2Pair(Integer ki, Vector2 vec){
		this.key = ki;
		this.value = vec;
	}
	/**
	 * constructor
	 */
	public IntVector2Pair(){
		this.key = new Integer(0);
		this.value = new Vector2();
	}
	/**
	 * get key
	 * @return
	 */
	public Integer getKey() {
		return key;
	}
	/**
	 * get value
	 * @return
	 */
	public Vector2 getValue() {
		return value;
	}
	/**
	 * set key
	 * @param key
	 */
	public void setKey(Integer key) {
		this.key = key;
	}
	/**
	 * set value
	 * @param value
	 */
	public void setValue(Vector2 value) {
		this.value = value;
	}
	/**
	 * write file
	 */
	@Override
	public void write(Json json) {
        json.writeValue("Key", key);
        json.writeValue("Value", value);
		
	}
	/**
	 * read file
	 */
	@Override
	public void read(Json json, JsonValue jsonData) {
        String context;
        key = jsonData.child.next.asInt();
        context = jsonData.child.next.next.toString();
        context = context.substring(context.indexOf("{")-1);
        value = json.fromJson(Vector2.class, context);

		
	}
	/**
	 * compare IntVector2Pair
	 */
	@Override
	public int compareTo(IntVector2Pair o) {
		return o.getKey()-this.getKey();
	}
	/**
	 * convert to String type
	 */
	@Override 
	public String toString(){
		return "["+key+" : "+value+"]";
	}
	
	

}
