package com.chaowang.ddgame.ClassesModel;

/**
 * Class for all different type in d &d
 */
public interface  Class {
	public enum ClassType {FIGHTER, BARBARIAN, MONK, PALADIN, RANGER, WARLOCK};
	public ClassType classType();

}
