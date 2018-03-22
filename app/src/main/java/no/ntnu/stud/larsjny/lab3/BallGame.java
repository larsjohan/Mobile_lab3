package no.ntnu.stud.larsjny.lab3;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class BallGame extends Activity {

    private ConstraintLayout gameBoard;

    private BallGameLayout game;

    private SensorManager manager;

    private SensorEventListener listener;

    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ballgamebaselayout);

        // Enforce layout orientation to Landscape:
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Find container for the ball
        this.gameBoard = findViewById(R.id.gameBoard);

        // Create the ball and it's
        this.game = new BallGameLayout(this);

        // Add the ball
        this.gameBoard.addView(this.game);

        this.manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        assert this.manager != null;
        this.sensor = this.manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        this.listener = new GyroscopeListener();

        // TODO: Change to GAME
        this.manager.registerListener(this.listener, this.sensor, SensorManager.SENSOR_DELAY_GAME);
    }


    private class GyroscopeListener implements SensorEventListener {

        private static final int BIAS = 100;

        private float dX = 0;

        private float dY = 0;


        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            int xDir = (x > 0) ? 1 : -1;
            int yDir = (y > 0) ? -1 : 1;

            this.dX = Math.abs(Math.abs(this.dX) - Math.abs(x)); // X * sin(theta / 2)
            this.dY = Math.abs(Math.abs(this.dY) - Math.abs(y)); // Y * sin(theta / 2)

            //Log.d("Lab3", "Sensor changed --- [" + x + ", " + y + "]");

            float magnitude = (float) Math.sqrt(Math.pow(this.dX, 2) + Math.pow(this.dY, 2));

            Log.d("Lab3", "dX: " + (dX * BIAS) + ", dY: " + (dY * BIAS) + " :: " + magnitude);

            game.setxBias(xDir * this.dX * BIAS);
            game.setyBias(yDir * this.dY * BIAS);
            game.invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
