package com.chaowang.ddgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * class for Fade
 * @author chao wang
 * @version 2.0
 */
public class Fade extends Actor {

    public enum FadeType{
        FADE_IN,
        FADE_OUT
    }

    private Texture texture;

    private Color color;
    private AtomicFloat alpha = new AtomicFloat(); //I actually use a custom made AtomicFloat class
    private final AtomicInteger duration;

    private FadeType fade;

    private AtomicBoolean isFading = new AtomicBoolean(false);


    public Fade(int width, int height, Color color, FadeType fade, int duration){

        this.color = color;
        this.fade = fade;
        this.duration = new AtomicInteger(duration);

        Pixmap map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        map.setColor(new Color(color.r, color.g, color.b, alpha.get()));
        map.fillRectangle(0, 0, width, height);

        texture = new Texture(map);

        map.dispose();
    }

    public void startFade(){
        isFading.set(true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final int start = (int)System.currentTimeMillis();

                int current;
                float delta;

                do{
                    current = (int)System.currentTimeMillis();

                    delta = current - start;

                    alpha.set( (fade == FadeType.FADE_OUT) ? delta/duration.get() : 1 - delta/duration.get() );

                }while (delta < duration.get());

                isFading.set(false);
            }
        }).start();

    }//startFade

    @Override
    public void draw(Batch batch, float parentAlpha){

        if (isFading.get())
            batch.setColor(new Color(color.r, color.g, color.b, alpha.get()));

        batch.draw(texture, 0, 0);
    }

}//Fade