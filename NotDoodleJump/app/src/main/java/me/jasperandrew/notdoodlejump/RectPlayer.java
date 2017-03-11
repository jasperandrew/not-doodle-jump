package me.jasperandrew.notdoodlejump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jasper on 2/11/2017.
 **/

class RectPlayer implements GameObject {

    private Rect rectangle;
    private int width;
    private int height;
    private int startX;
    private int startY;

    private float vertAccel;
    private float horizAccel;

    private Bitmap risingLeft;
    private Bitmap risingRight;
    private Bitmap fallingLeft;
    private Bitmap fallingRight;
    private Bitmap currentImg;

    RectPlayer(int startX, int startY, int width, int height, Context context) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;

        rectangle = new Rect(startX, startY, startX+width, startY+height);

        risingLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.datboi_l1);
        risingRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.datboi_r1);
        fallingLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.datboi_l2);
        fallingRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.datboi_r2);

        currentImg = risingLeft;

    }

    void reset() {
        rectangle.top = startY;
        rectangle.bottom = startY+height;
        rectangle.left = startX;
        rectangle.right = startX+width;
    }

    private Const.Collision getCollision(Obstacle ob) {
        Rect obRect = ob.getRectangle();
        if(vertAccel <= 0 && (obRect.contains(rectangle.left, rectangle.bottom) || obRect.contains(rectangle.right, rectangle.bottom))){
               return ob.getType();
        }
        return Const.Collision.NONE;
    }

    @Override
    public void draw(Canvas canvas) {
        if(vertAccel > 0) {
            if(horizAccel >= 4){
                currentImg = risingRight;
            }else if(horizAccel < -4){
                currentImg = risingLeft;
            }else{
                if(currentImg == fallingRight) currentImg = risingRight;
                if(currentImg == fallingLeft) currentImg = risingLeft;
            }
        }else{
            if(horizAccel >= 4){
                currentImg = fallingRight;
            }else if(horizAccel < -4){
                currentImg = fallingLeft;
            }else{
                if(currentImg == risingRight) currentImg = fallingRight;
                if(currentImg == risingLeft) currentImg = fallingLeft;
            }
        }
        canvas.drawBitmap(currentImg, null, rectangle, new Paint());
    }

    @Override
    public void update() {
        horizAccel = 47.0f*Const.ROLL;
        rectangle.left += horizAccel;
        rectangle.right += horizAccel;

        if(rectangle.top > Const.SCREEN_HEIGHT/4 || vertAccel <= 0) {
            rectangle.top -= vertAccel;
            rectangle.bottom -= vertAccel;
        }else{
            for(Obstacle platform : GamePanel.platformGenerator.getPlatforms()) {
                platform.moveDown(vertAccel);
            }
        }

        Const.SCORE += vertAccel;

        vertAccel -= Const.GRAVITY;

        for(Obstacle platform : GamePanel.platformGenerator.getPlatforms()) {
            Const.Collision bounce = getCollision(platform);
            switch(bounce) {
                case ROCKET: vertAccel = 360.0f * Const.GRAVITY; break;
                case BOOST: vertAccel = 120.0f * Const.GRAVITY; break;
                case GROUND:
                case NORMAL: vertAccel = 40.0f * Const.GRAVITY;
                default:
            }
            if(bounce != Const.Collision.NONE) {
                rectangle.top = platform.getRectangle().top - height;
                rectangle.bottom = platform.getRectangle().top;
            }
        }

        if(rectangle.right < 1){
            rectangle.right = Const.SCREEN_WIDTH+width-1;
            rectangle.left = Const.SCREEN_WIDTH-1;
        }
        if(rectangle.left > Const.SCREEN_WIDTH-1){
            rectangle.left = 0 - width + 1;
            rectangle.right = 1;
        }

        if(rectangle.left > 0 && rectangle.right < Const.SCREEN_WIDTH && rectangle.right - rectangle.left < width){
            if(rectangle.centerX() >= Const.SCREEN_WIDTH/2){
                rectangle.left -= width - (rectangle.right - rectangle.left);
            }else{
                rectangle.right += width - (rectangle.right - rectangle.left);
            }
        }

        if(rectangle.top > Const.SCREEN_HEIGHT){
            Const.GAME_OVER = true;
        }
    }
}
