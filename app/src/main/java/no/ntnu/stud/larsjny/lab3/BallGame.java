package no.ntnu.stud.larsjny.lab3;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
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
        this.sensor = this.manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }




}
