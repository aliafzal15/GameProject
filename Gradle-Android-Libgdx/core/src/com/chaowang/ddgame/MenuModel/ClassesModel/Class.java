package com.chaowang.ddgame.MenuModel.ClassesModel;

/**
 * Class for all different type in d &d
 */
public interface  Class {
		public enum ClassType {FIGHTER, BARBARIAN, BARD, DRUID, WIZARD, PALADIN, RANGER, THIEF, WARLOCK}

	public ClassType getClassType();

}
