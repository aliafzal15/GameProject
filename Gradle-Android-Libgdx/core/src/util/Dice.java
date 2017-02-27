package util;
/**
 * class for dice
 * @author chao wang
 * @version 1.0
 */
public class Dice {
	public static final int DICENUMBER = 3;
	public static final int DICESIDE = 6;

	/**
	 * create a random number
	 * @param sides a number
	 * @return a random number
	 */
	public static int roll(int sides) {
		return (int)(Math.random()*sides) + 1;
	}
	/**
	 * create a random number
	 * @param numberOfDice
	 * @param sides
	 * @return a random number
	 */
	public static int roll(int numberOfDice, int sides) {
		return roll(numberOfDice, sides, 0);
	}
	/**
	 * create a random number
	 * @param numberOfDice
	 * @param sides
	 * @param extra
	 * @return a random number
	 */
	public static int roll(int numberOfDice, int sides, int extra) {
		int roll = 0;
		for(int die = 1; die <= numberOfDice; die++) {
			roll += roll(sides);
		}
		return roll + extra;
	}
}