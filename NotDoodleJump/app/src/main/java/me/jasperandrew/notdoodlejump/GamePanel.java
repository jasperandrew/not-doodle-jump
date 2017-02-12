package me.jasperandrew.notdoodlejump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jasper on 2/11/2017.
 **/

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private RectPlayer player;
    public static PlatformGenerator platformGenerator;

    Paint scorePaint = new Paint();
    Bitmap groundImg;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        Const.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        BitmapFactory bf = new BitmapFactory();
        groundImg = bf.decodeResource(Const.CURRENT_CONTEXT.getResources(), R.drawable.ground);

        player = new RectPlayer(Const.SCREEN_WIDTH/2-50, Const.SCREEN_HEIGHT-200, 79, 150, Color.RED);

        platformGenerator = new PlatformGenerator(200, 70);
        platformGenerator.add(new Obstacle(0, Const.SCREEN_HEIGHT-100, Const.SCREEN_WIDTH, 100, Const.Collision.GROUND, groundImg));
        platformGenerator.init(10);

        Const.SCORE = 0;
        scorePaint.setColor(Color.GRAY);
        scorePaint.setTextSize(42);
        scorePaint.setFakeBoldText(true);

        setFocusable(true);
    }

    private void resetGame() {
        player = new RectPlayer(Const.SCREEN_WIDTH/2-50, Const.SCREEN_HEIGHT-200, 79, 150, Color.RED);

        platformGenerator = new PlatformGenerator(200, 70);
        platformGenerator.add(new Obstacle(0, Const.SCREEN_HEIGHT-100, Const.SCREEN_WIDTH, 100, Const.Collision.GROUND, groundImg));
        platformGenerator.init(10);

        Const.SCORE = 0;

        Const.GAME_OVER = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) { e.printStackTrace(); }

            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(Const.GAME_OVER){
                    resetGame();
                }
        }
        return true;
    }

    public void update() {
        if(!Const.GAME_OVER) {
            player.update();
            platformGenerator.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(!Const.GAME_OVER){
            canvas.drawColor(Color.WHITE);
            platformGenerator.draw(canvas);
            player.draw(canvas);

            canvas.drawText(Integer.toString(Const.SCORE), 50, Const.SCREEN_HEIGHT-50, scorePaint);
        }else{
            GameOverScreen gameOverScreen = new GameOverScreen();
            gameOverScreen.draw(canvas);
        }
    }
}
