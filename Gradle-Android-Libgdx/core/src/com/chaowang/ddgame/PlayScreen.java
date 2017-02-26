package com.chaowang.ddgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import LibgdxExample.Player;
import LibgdxExample.Tile;
import Map.*;


public class PlayScreen implements Screen{
    private SpriteBatch batch;
    private Texture mario;
    private Sprite sprite;
    private Vector2 position;
    private LibgdxExample.Player player;
    private Tree tree, tree1;
    private ShapeRenderer sr;
    private ArrayList<Tree> trees;
    private Iterator<Tree> treeIterator;

    private LibgdxExample.Enemy enemy;
    private ArrayList<LibgdxExample.Enemy> enemies;
    private Iterator<LibgdxExample.Enemy> enemyIterator;

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
        mario = new Texture(Gdx.files.internal("android/assets/Mario.png"));
        sr = new ShapeRenderer();
        tree = new Tree(new Vector2(100, 100),new Vector2(50, 100));
        tree1 = new Tree(new Vector2(200, 100),new Vector2(50, 100));
        trees = new ArrayList<Tree>();
        trees.add(tree);
        trees.add(tree1);

        enemies = new ArrayList<LibgdxExample.Enemy>();

        if(Gdx.files.local("player.dat").exists()){
            try{
                player = new LibgdxExample.Player(new Vector2(Gdx.graphics.getWidth() /2 , Gdx.graphics.getHeight() /2 ), "android/assets/Mario.png");
                player.setPosition(LibgdxExample.Player.readPlayer());
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Player exist, reading file");
        }
        else{
            player = new LibgdxExample.Player(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), "android/assets/Mario.png");
            try{
                Player.savePlayer(player);
            } catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("Player does not exist, create player and save player ");
        }
        enemy = new LibgdxExample.Enemy(new Vector2(50, 50), player);
        enemies.add(enemy);

//        tiles = new ArrayList<Tile>();
//
//        for(int i = 0; i < 10; i++){
//            for (int j = 0 ; j < 10; j++){
//                int R = (int) ((Math.random() * (2 - 0) + 0 ));
//                if ( R == 0) {
//                    tiles.add(new Tile(new Texture(Gdx.files.internal("android/assets/grass.png")), i*50, j*50, 50, 50));
//                }
//                if ( R == 1) {
//                    tiles.add(new Tile(new Texture(Gdx.files.internal("android/assets/dirt.png")), i*50, j*50, 50, 50));
//                }
//            }
//        }

        //sound = Gdx.audio.newSound(Gdx.files.internal("android/assets/sound.mp3"));
        //sound.play();
        map = new TmxMapLoader().load("android/assets/terrain.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        bounds = new ArrayList<Rectangle>();

        for (int i = 0 ; i <20; i++) {
            for (int j = 0 ; j < 20; j++){
                TiledMapTileLayer cur = (TiledMapTileLayer)map.getLayers().get(1);
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (cur.getCell(i, j) != null){
                    cell = cur.getCell(i, j);
                    System.out.println(i + ", " + j + ", "+ cell.getTile().getId());
                    bounds.add(new Rectangle(i*64, j*64, 64 , 64));
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tree.update();

        renderer.setView(cam);
        renderer.render();

        cam.position.set(player.getPosition().x + (player.getCurrentFrame().getRegionWidth() / 2), player.getPosition().y + player.getCurrentFrame().getRegionHeight() / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        // for Android app
//        if(Gdx.input.isTouched()){
//            System.out.println("Application clicked");
//        }
//        System.out.println("mouse x : "+ Gdx.input.getX() + "mouse y : "+ Gdx.input.getY());

        batch.begin();
//
//        tileIterator = tiles.iterator();
//        while (tileIterator.hasNext()){
//            Tile cur = tileIterator.next();
//            cur.render(batch);
//        }

        batch.draw(player.getCurrentFrame(),player.getPosition().x, player.getPosition().y );
        //batch.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);  // if player contains texture (not serializable)
        //tree.draw(batch);  //draw 1 tree

//        treeIterator = trees.iterator();
//        while(treeIterator.hasNext()){
//            Tree cur = treeIterator.next();
//            cur.draw(batch);
//            cur.update();
//            if(player.getBounds().overlaps(cur.getBounds())){
//                player.reAdjust();
//            }
//        }

        enemyIterator = enemies.iterator();
        while(enemyIterator.hasNext()){
            LibgdxExample.Enemy cur = enemyIterator.next();

            cur.update();
            batch.draw(cur.getEnemyTexture(),cur.getPosition().x,cur.getPosition().y, 25, 25);

            if(player.getBounds().overlaps(cur.getBounds())){
                //System.out.println("Player hit!");
            }
        }



        for (int i = 0; i< bounds.size(); i++){
            if (bounds.get(i).overlaps(player.getBounds())){
                player.reAdjust();
            }
        }

        MapProperties prop = map.getProperties();
        Rectangle mapBounds = new Rectangle(0, 0, prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class), prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));

        if (!mapBounds.contains(player.getBounds())){
            player.reAdjust();
        }
        
        player.update();
//
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
//// Move camera after player as normal
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

        batch.end();

//        sr.begin(ShapeRenderer.ShapeType.Line);
//        sr.setColor(Color.BLUE);
//        sr.rect(player.getPosition().x, player.getPosition().y, player.getCurrentFrame().getRegionWidth(), player.getCurrentFrame().getRegionHeight());
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
            LibgdxExample.Player.savePlayer(player);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
