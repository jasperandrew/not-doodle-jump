package me.jasperandrew.notdoodlejump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

import static me.jasperandrew.notdoodlejump.MainThread.canvas;

/**
 * Created by Jasper on 2/12/2017.
 **/

public class PlatformGenerator {
    private ArrayList<Obstacle> platforms;
    private int width;
    private int height;

    private ArrayList<Bitmap> normalImgs;
    private Bitmap groundImg;
    private Bitmap boostImg;
    private Bitmap rocketImg;


    public PlatformGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        platforms = new ArrayList<>();

        normalImgs = new ArrayList<>();

        BitmapFactory bf = new BitmapFactory();
        normalImgs.add(bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform1));
        normalImgs.add(bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform2));
        normalImgs.add(bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform3));
        normalImgs.add(bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform4));
        normalImgs.add(bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform5));
        //groundImg = bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.ground);
        boostImg = bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform_rainbow);
        rocketImg = bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.platform_wendys);
    }

    public void init(int n) {
        int prevY = Const.SCREEN_HEIGHT-100;
        for(int i = 0; i < n; i++){
            int x = (int)(Math.random() * (Const.SCREEN_WIDTH - width + 1));
            int y = (int)(Math.random() * ((prevY - 4*height) - (prevY - Const.MAX_PLATFORM_GAP) + 1)) + (prevY - Const.MAX_PLATFORM_GAP);

            Const.Collision type = Math.random() < 0.9 ? Const.Collision.NORMAL : Const.Collision.BOOST;
            Bitmap img;
            switch(type){
                case ROCKET:
                    img = rocketImg;break;
                case BOOST:
                    img = boostImg;break;
                case GROUND:
                    img = groundImg;break;
                default:
                    img = normalImgs.get((int)(Math.random() * 5));
            }
            Obstacle ob = new Obstacle(x, y, width, height, type, img);
            platforms.add(ob);
            prevY = y;
        }
    }

    public void add(Obstacle ob){
        platforms.add(ob);
    }

    public ArrayList<Obstacle> getPlatforms() {
        return platforms;
    }

    public void update() {
        if(platforms.get(0).getRectangle().top > Const.SCREEN_HEIGHT){
            platforms.remove(0);
            int prevY = platforms.get(platforms.size()-1).getRectangle().top;
            int x = (int)(Math.random() * (Const.SCREEN_WIDTH - width + 1));
            int y = (int)(Math.random() * ((prevY - 4*height) - (prevY - Const.MAX_PLATFORM_GAP) + 1)) + (prevY - Const.MAX_PLATFORM_GAP);
            Const.Collision type = Math.random() < 0.95 ? Const.Collision.NORMAL : Const.Collision.BOOST;
            type = Math.random() < 0.99 ? type : Const.Collision.ROCKET;
            Bitmap img;
            switch(type){
                case ROCKET:
                    img = rocketImg; break;
                case BOOST:
                    img = boostImg; break;
                case GROUND:
                    img = groundImg; break;
                default:
                    img = normalImgs.get((int)(Math.random() * 5));
            }
            Obstacle ob = new Obstacle(x, y, width, height, type, img);
            platforms.add(ob);
        }
        for(Obstacle platform : platforms){
            if(platform.isMoving()) platform.update();
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle plat : platforms)
            plat.draw(canvas);
    }
}
