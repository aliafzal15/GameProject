package com.chaowang.ddgame.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import com.chaowang.ddgame.MapModel.Tree;

import LibgdxExample.EnemyEg;
import LibgdxExample.PlayerEg;
import LibgdxExample.Tile;

/**
 * view for playing game(not for current build)
 * @author chao wang
 * @version 1.0
 */
public class PlayScreen implements Screen{
    private SpriteBatch batch;
    private Texture mario;
    private Sprite sprite;
    private Vector2 position;
    private PlayerEg playerEg;
    private com.chaowang.ddgame.MapModel.Tree tree, tree1;
    //private ShapeRenderer sr;  //        draw boundary rectangle for testing
    private ArrayList<com.chaowang.ddgame.MapModel.Tree> trees;
    private Iterator<com.chaowang.ddgame.MapModel.Tree> treeIterator;

    private EnemyEg enemyEg;
    private ArrayList<EnemyEg> enemies;
    private Iterator<EnemyEg> enemyIterator;

    private Game game;
    private ArrayList<Tile> tiles;
    Iterator<LibgdxExample.Tile> tileIterator;

    OrthographicCamera cam;
    Sound sound;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    ArrayList<Rectangle> bounds;

    public PlayScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();
        mario = new Texture(Gdx.files.internal("Mario.png"));
        //sr = new ShapeRenderer(); //        draw boundary rectangle for testing
        tree = new Tree(new Vector2(128, 256),new Vector2(96, 96));
        tree1 = new Tree(new Vector2(128, 128),new Vector2(96, 96));
        trees = new ArrayList<Tree>();
        trees.add(tree);
        trees.add(tree1);

        enemies = new ArrayList<EnemyEg>();

        if(Gdx.files.local("playerEg.dat").exists()){
            try{
                playerEg = new PlayerEg(new Vector2(Gdx.graphics.getWidth() /2 , Gdx.graphics.getHeight() /2 ));
                playerEg.setPosition(PlayerEg.readPlayer());
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("PlayerEg exist, reading file");
        }
        else{
            playerEg = new PlayerEg(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
            try{
                PlayerEg.savePlayer(playerEg);
            } catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("PlayerEg does not exist, create playerEg and save playerEg ");
        }
        enemyEg = new EnemyEg(new Vector2(50, 50), playerEg);
        enemies.add(enemyEg);

        //sound = Gdx.audio.newSound(Gdx.files.internal("sound.mp3"));
        //sound.play();
        map = new TmxMapLoader().load("terrain/terrain5x5.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tree.update();

        renderer.setView(cam);
        renderer.render();

        cam.position.set(playerEg.getPosition().x + (playerEg.getCurrentFrame().getRegionWidth() / 2), playerEg.getPosition().y + playerEg.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();

        

        batch.begin();

        batch.draw(playerEg.getCurrentFrame(), playerEg.getPosition().x, playerEg.getPosition().y );
        //batch.draw(playerEg.getTexture(), playerEg.getPosition().x, playerEg.getPosition().y);  // if playerEg contains texture (not serializable)
        //tree.draw(batch);  //draw 1 tree

        treeIterator = trees.iterator();
        while(treeIterator.hasNext()){
            Tree cur = treeIterator.next();
            cur.draw(batch);
            if(playerEg.getBounds().overlaps(cur.getBounds())){
                playerEg.reAdjust();
            }
        }

        enemyIterator = enemies.iterator();
        while(enemyIterator.hasNext()){
            EnemyEg cur = enemyIterator.next();

            cur.update();
            batch.draw(cur.getEnemyTexture(),cur.getPosition().x,cur.getPosition().y, 25, 25);

            if(playerEg.getBounds().overlaps(cur.getBounds())){
                //System.out.println("PlayerEg hit!");
            }
        }

        MapProperties prop = map.getProperties();
        Rectangle mapBounds = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));

        if (!mapBounds.contains(playerEg.getBounds())){
            playerEg.reAdjust();
        }
        
        playerEg.update();

        batch.end();
//        draw boundary rectangle for testing
//        sr.begin(ShapeRenderer.ShapeType.Line);
//        sr.setColor(Color.BLUE);
//        sr.rect(playerEg.getPosition().x, playerEg.getPosition().y, playerEg.getCurrentFrame().getRegionWidth(), playerEg.getCurrentFrame().getRegionHeight());
//        sr.setColor(Color.RED);
//        sr.rect(tree.getPosition().x, tree.getPosition().y, tree.getSize().x, tree.getSize().y);
//        sr.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        try {
            PlayerEg.savePlayer(playerEg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}


//Additional in rander

//        for Android app movement, click lisenter
//        if(Gdx.input.isTouched()){
//            System.out.println("Application clicked");
//        }
//        System.out.println("mouse x : "+ Gdx.input.getX() + "mouse y : "+ Gdx.input.getY());

//        One by one render tiles
//        tileIterator = tiles.iterator();
//        while (tileIterator.hasNext()){
//            Tile cur = tileIterator.next();
//            cur.render(batch);
//        }


//        collision adjust for playerEg and tree in .tmx layer1
//        for (int i = 0; i< bounds.size(); i++){
//            if (bounds.get(i).overlaps(playerEg.getBounds())){
//                playerEg.reAdjust();
//            }
//        }


//      code for moving camera - before batch end
//        // These values likely need to be scaled according to your world coordinates.
//// The left boundary of the map (x)
//        int mapLeft = 0;
//// The right boundary of the map (x + width)
//        int mapRight = prop.get("width", Integer.class);
//// The bottom boundary of the map (y)
//        int mapBottom = 0;
//// The top boundary of the map (y + height)
//        int mapTop = 0 + prop.get("height", Integer.class);
//// The camera dimensions, halved
//        float cameraHalfWidth = cam.viewportWidth * .5f;
//        float cameraHalfHeight = cam.viewportHeight * .5f;
//
//// Move camera after playerEg as normal
//
//        float cameraLeft = cam.position.x - cameraHalfWidth;
//        float cameraRight = cam.position.x + cameraHalfWidth;
//        float cameraBottom = cam.position.y - cameraHalfHeight;
//        float cameraTop = cam.position.y + cameraHalfHeight;
//
//// Horizontal axis
//        if( prop.get("width", Integer.class) < cam.viewportWidth)
//        {
//            cam.position.x = mapRight / 2;
//        }
//        else if(cameraLeft <= mapLeft)
//        {
//            cam.position.x = mapLeft + cameraHalfWidth;
//        }
//        else if(cameraRight >= mapRight)
//        {
//            cam.position.x = mapRight - cameraHalfWidth;
//        }
//
//// Vertical axis
//        if(prop.get("height", Integer.class) < cam.viewportHeight)
//        {
//            cam.position.y = mapTop / 2;
//        }
//        else if(cameraBottom <= mapBottom)
//        {
//            cam.position.y = mapBottom + cameraHalfHeight;
//        }
//        else if(cameraTop >= mapTop)
//        {
//            cam.position.y = mapTop - cameraHalfHeight;
//        }

//Aadditional in show
// manually add tiles
//        tiles = new ArrayList<Tile>();
//
//        for(int i = 0; i < 10; i++){
//            for (int j = 0 ; j < 10; j++){
//                int R = (int) ((Math.random() * (2 - 0) + 0 ));
//                if ( R == 0) {
//                    tiles.add(new Tile(new Texture(Gdx.files.internal("grass.png")), i*50, j*50, 50, 50));
//                }
//                if ( R == 1) {
//                    tiles.add(new Tile(new Texture(Gdx.files.internal("dirt.png")), i*50, j*50, 50, 50));
//                }
//            }
//        }

// add tree bounds from .tmx map at layer 1
//        bounds = new ArrayList<Rectangle>();
//
//        for (int i = 0 ; i <20; i++) {
//            for (int j = 0 ; j < 20; j++){
//                TiledMapTileLayer cur = (TiledMapTileLayer)map.getLayers().get(1);
//                TiledMapTileLayer.Cell cell ;
//                if (cur.getCell(i, j) != null){
//                    cell = cur.getCell(i, j);
//                    System.out.println(i + ", " + j + ", "+ cell.getTile().getId());
//                    bounds.add(new Rectangle(i*64, j*64, 64 , 64));
//                }
//            }
//        }