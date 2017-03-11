package me.jasperandrew.notdoodlejump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Jasper on 2/11/2017.
**/

class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    //private double averageFPS;



    void setRunning(boolean running) {
        this.running = running;
    }

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/Const.MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            Canvas canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch(Exception e){ e.printStackTrace(); }
            finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e) { e.printStackTrace(); }
                }
            }

            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) sleep(waitTime);
            } catch (Exception e){ e.printStackTrace(); }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == Const.MAX_FPS){
                //averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}
