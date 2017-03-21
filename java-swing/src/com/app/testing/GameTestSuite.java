package com.app.testing;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UnitTestAll.class,
			   UnitTestBuild2Character.class,
			   UnitTestBuild2Map.class})


public class GameTestSuite {

}
