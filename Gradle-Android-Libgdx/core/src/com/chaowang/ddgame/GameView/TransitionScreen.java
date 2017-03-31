package com.chaowang.ddgame.GameView;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chaowang.ddgame.DDGame;
import com.chaowang.ddgame.GameModel.Transition.Transition;
import com.chaowang.ddgame.GameUtl.AbstractScreen;

/**
 * Created by Chao on 25/03/2017.
 */

public class TransitionScreen extends AbstractScreen {

    private AbstractScreen from;
    private AbstractScreen to;

    private Transition outTransition;
    private Transition inTransition;

    private Action action;

    private SpriteBatch batch;
    private Viewport viewport;

    private TRANSITION_STATE state;

    private enum TRANSITION_STATE {
        OUT,
        IN,
        ;
    }

    public TransitionScreen(DDGame app) {
        super(app);
        batch = new SpriteBatch();
        viewport = new ScreenViewport();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        if (state == TRANSITION_STATE.OUT) {
            outTransition.update(delta);
            if (outTransition.isFinished()) {
                action.act(delta);
                state = TRANSITION_STATE.IN;
                return;
            }
        } else if (state == TRANSITION_STATE.IN) {
            inTransition.update(delta);
            if (inTransition.isFinished()) {
                getApp().setScreen(to);
            }
        }
    }

    @Override
    public void render(float delta) {
        if (state == TRANSITION_STATE.OUT) {
            from.render(delta);

            viewport.apply();
            outTransition.render(delta, batch);
        } else if (state == TRANSITION_STATE.IN) {
            to.render(delta);

            viewport.apply();
            inTransition.render(delta, batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        to.resize(width, height);
        from.resize(width, height);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in, Action action) {
        this.from = from;
        this.to = to;
        this.outTransition = out;
        this.inTransition = in;
        this.action = action;
        this.state = TRANSITION_STATE.OUT;
        getApp().setScreen(this);
    }
}

