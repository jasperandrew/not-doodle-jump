package me.jasperandrew.notdoodlejump;

import android.content.Context;

/**
 * Created by Jasper on 2/11/2017.
 **/

public class Const {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static final int MAX_FPS = 60;
    public static Context CURRENT_CONTEXT;


    public static float GRAVITY = 0.85f;
    public static int MAX_PLATFORM_GAP = 700;

    public static OrientationSensor ORIENTATION_SENSOR;
    public static float ROLL;

    public enum Collision {
        NONE,
        GROUND,
        NORMAL,
        BOOST,
        ROCKET
    }

    public static int SCORE;

    public static boolean GAME_OVER = false;
}
