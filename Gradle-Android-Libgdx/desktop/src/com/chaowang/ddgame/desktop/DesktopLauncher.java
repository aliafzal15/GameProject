package com.chaowang.ddgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chaowang.ddgame.DDGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dungeons & Dragons 1.0";
		config.useGL30 = true;
		config.width = 1080;
		config.height = 720;
		new LwjglApplication(new DDGame(), config);
	}
}
