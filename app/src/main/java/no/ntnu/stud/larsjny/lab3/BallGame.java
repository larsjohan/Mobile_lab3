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
        this.sensor = this.manager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        this.listener = new GyroscopeListener();

        this.manager.registerListener(this.listener, this.sensor, SensorManager.SENSOR_DELAY_GAME);
    }


    private class GyroscopeListener implements SensorEventListener {

        private static final int BIAS = 75;

        private float dX = 0;

        private float dY = 0;


        @Override
        public void onSensorChanged(SensorEvent event) {
            // Get new x,y values
            float x = event.values[0];
            float y = event.values[1];

            // Calculate the direction of the rotation in X and Y direction
            int xDir = (x > 0) ? 1 : -1;
            int yDir = (y > 0) ? -1 : 1;

            // Find the difference in rotation in X and Y direction
            this.dX = Math.abs(Math.abs(this.dX) - Math.abs(x));
            this.dY = Math.abs(Math.abs(this.dY) - Math.abs(y));

            // calculate the new interval to "jump" for X and Y
            // The bias controls the speed of the movement
            game.setxBias(xDir * this.dX * BIAS);
            game.setyBias(yDir * this.dY * BIAS);

            // Update the view
            game.invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
