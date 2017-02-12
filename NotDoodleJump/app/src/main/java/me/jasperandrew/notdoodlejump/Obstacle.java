package me.jasperandrew.notdoodlejump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jasper on 2/11/2017.
 **/

public class Obstacle implements GameObject {
    private Rect rectangle;
    private int startX;
    private int startY;
    private int width;
    private int height;
    private Const.Collision type;
    private boolean moving;
    private int direction;
    private Bitmap image;

    public Rect getRectangle() {
        return rectangle;
    }

    public Const.Collision getType() {
        return type;
    }

    public boolean isMoving() {
        return moving;
    }

    public void moveDown(float dx) {
        rectangle.top += dx;
        rectangle.bottom += dx;
    }

    public Obstacle(int startX, int startY, int width, int height, Const.Collision type, Bitmap img) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = img;

        rectangle = new Rect(startX, startY, startX+width, startY+height);

        moving = Math.random() >= 0.8;
        direction = moving ? (int)((Math.random() * (3 + 3) + 1)) + 3 : 0;
        if(type == Const.Collision.GROUND) moving = false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, null, rectangle, new Paint());
    }

    @Override
    public void update() {
        if(moving){
            rectangle.left += direction;
            rectangle.right += direction;

            if(rectangle.left < 1 || rectangle.right > Const.SCREEN_WIDTH-1) direction *= -1;

        }
    }
}
