package me.jasperandrew.notdoodlejump;

/**
 * Created by Jasper on 2/11/2017.
 **/

class Const {
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;
    static final int MAX_FPS = 60;


    static float GRAVITY = 0.85f;
    static int MAX_PLATFORM_GAP = 700;

    static OrientationSensor ORIENTATION_SENSOR;
    static float ROLL;

    enum Collision {
        NONE,
        GROUND,
        NORMAL,
        BOOST,
        ROCKET
    }

    static int SCORE;

    static boolean GAME_OVER = false;
}
