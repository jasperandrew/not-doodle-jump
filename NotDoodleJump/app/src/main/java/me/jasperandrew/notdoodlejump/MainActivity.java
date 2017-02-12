package me.jasperandrew.notdoodlejump;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Const.SCREEN_WIDTH = dm.widthPixels;
        Const.SCREEN_HEIGHT = dm.heightPixels;

        Const.ORIENTATION_SENSOR = new OrientationSensor(this);
        Const.ORIENTATION_SENSOR.register();

        setContentView(new GamePanel(this));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Const.ORIENTATION_SENSOR.unregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Const.ORIENTATION_SENSOR.register();
    }

}
