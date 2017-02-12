package me.jasperandrew.notdoodlejump;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jasper on 2/12/2017.
 **/

public class GameOverScreen {
    private Rect rectangle;
    private Paint fadePaint;
    private Paint textPaint;

    public GameOverScreen() {
        this.rectangle = new Rect(0, 0, Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        fadePaint = new Paint();
        textPaint = new Paint();

        fadePaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(250);
        textPaint.setFakeBoldText(true);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle, fadePaint);
        canvas.drawText("me too", Const.SCREEN_WIDTH/2, Const.SCREEN_HEIGHT/2-200, textPaint);
        canvas.drawText("thanks", Const.SCREEN_WIDTH/2, Const.SCREEN_HEIGHT/2+200, textPaint);

        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.GRAY);
        scorePaint.setTextSize(100);
        scorePaint.setFakeBoldText(true);
        canvas.drawText(Integer.toString(Const.SCORE), 50, Const.SCREEN_HEIGHT-50, scorePaint);
    }
}
