package me.jasperandrew.notdoodlejump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Jasper on 2/12/2017.
 **/

class PlatformGenerator {
    private ArrayList<Obstacle> platforms;
    private int width;
    private int height;

    private ArrayList<Bitmap> normalImgs;
    private Bitmap boostImg;
    private Bitmap rocketImg;
    private Bitmap groundImg;


    PlatformGenerator(int width, int height, Context context) {
        this.width = width;
        this.height = height;
        platforms = new ArrayList<>();

        normalImgs = new ArrayList<>();

        normalImgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.platform1));
        normalImgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.platform2));
        normalImgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.platform3));
        normalImgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.platform4));
        normalImgs.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.platform5));
        boostImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.platform_rainbow);
        rocketImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.platform_wendys);
        groundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground);

    }

    void init(int n) {
        clearPlatforms();

        platforms.add(new Obstacle(0, Const.SCREEN_HEIGHT-100, Const.SCREEN_WIDTH, 100, Const.Collision.GROUND, groundImg));

        int prevY = Const.SCREEN_HEIGHT-100;
        for(int i = 0; i < n; i++){
            int x = (int)(Math.random() * (Const.SCREEN_WIDTH - width + 1));
            int y = (int)(Math.random() * ((prevY - 4*height) - (prevY - Const.MAX_PLATFORM_GAP) + 1)) + (prevY - Const.MAX_PLATFORM_GAP);

            Const.Collision type = Math.random() < 0.9 ? Const.Collision.NORMAL : Const.Collision.BOOST;
            type = Math.random() < 0.99 ? type : Const.Collision.ROCKET;
            Bitmap img;
            switch(type){
                case ROCKET:
                    img = rocketImg;break;
                case BOOST:
                    img = boostImg;break;
                default:
                    img = normalImgs.get((int)(Math.random() * 5));
            }
            Obstacle ob = new Obstacle(x, y, width, height, type, img);
            platforms.add(ob);
            prevY = y;
        }
    }

    void add(Obstacle ob) {
        platforms.add(ob);
    }

    void clearPlatforms() {
        platforms.clear();
    }

    ArrayList<Obstacle> getPlatforms() {
        return platforms;
    }

    void update() {
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

    void draw(Canvas canvas) {
        for(Obstacle plat : platforms)
            plat.draw(canvas);
    }
}
