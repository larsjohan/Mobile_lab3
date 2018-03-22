package no.ntnu.stud.larsjny.lab3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

/**
 * Main activity.
 * Sets up the sensor and the listener, and creates and loads the Layout for drawing the ball.
 *
 * @author Lars Johan
 */
public class BallGame extends Activity {


    /**
     * A Layout that draws the ball on screen
     * and controls collision
     */
    private BallGameLayout game;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the background/frame
        setContentView(R.layout.ballgamebaselayout);

        // Enforce layout orientation to Landscape:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Find container for the ball
        ConstraintLayout gameBoard = findViewById(R.id.gameBoard);

        // Create the ball and it's
        this.game = new BallGameLayout(this);

        // Add the ball
        gameBoard.addView(this.game);

        // Setup the RotationVectorSensor
        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        assert sensorManager != null;

        Sensor rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        // Add sensor-listener
        sensorManager.registerListener(new RotationChangedListener(), rotationSensor, SensorManager.SENSOR_DELAY_GAME);
    }


    private class RotationChangedListener implements SensorEventListener {

        /**
         * A bias for controlling the speed of the ball
         */
        private static final int BIAS = 50;

        /**
         * Difference in rotation around the X-axis
         */
        private float dX = 0;

        /**
         * Difference in rotation around the Y-axis
         */
        private float dY = 0;


        /**
         * {@inheritDoc}
         * Calculates the difference in rotation and direction of the rotation
         * in both the X- and Y-direction. Then updates the position of the ball with these values
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }


}
