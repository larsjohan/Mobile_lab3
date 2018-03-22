package no.ntnu.stud.larsjny.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import java.time.LocalTime;
import java.util.Date;

/**
 * Created by Lars Johan on 22.03.2018.
 */

public class BallGameLayout extends View {

    /**
     * The color of the ball
     */
    private static final Paint PAINTBALL = new Paint();

    private static final Paint PAINTLINE = new Paint();

    private static long prevHapticTime;

    private float radius = 75;

    private float posX;

    private float posY;

    private float xBias;

    private float yBias;

    private Vibrator haptic;


    public BallGameLayout(Context context) {
        super(context);

        PAINTBALL.setAntiAlias(true);
        PAINTBALL.setColor(Color.GREEN);
        PAINTBALL.setStyle(Paint.Style.FILL);

        PAINTLINE.setAntiAlias(true);
        PAINTLINE.setColor(Color.RED);
        PAINTLINE.setStyle(Paint.Style.FILL);
        PAINTLINE.setStrokeWidth(2f);

        this.haptic = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Get canvas-size
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Calculate the position to move to
        float nextPosX = this.posX + this.xBias;
        float nextPosY = this.posY + this.yBias;

        // Chack collision on X-axis
        if(Math.abs(nextPosX) <= width / 2 - this.radius)
            this.posX = nextPosX;
        else
            runCollisionFeedback();

        // Check collision on Y-axis
        if(Math.abs(nextPosY) <= height / 2 - this.radius)
            this.posY = nextPosY;
        else
            runCollisionFeedback();

        // Translate the canvas to have 0,0 in the center of the screen
        canvas.translate(getWidth() / 2, getHeight() / 2);

        // Draw the ball at the new position
        canvas.drawCircle(nextPosX, nextPosY, this.radius, PAINTBALL);

        // Draw a line from the center to the ball (Artistic freedom!)
        canvas.drawLine(0.0f,0.0f, nextPosX, nextPosY, PAINTLINE);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        Log.d("Lab3", "Radius is: " + radius);
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getxBias() {
        return xBias;
    }

    public void setxBias(float xBias) {
        this.xBias = xBias;
    }

    public float getyBias() {
        return yBias;
    }

    public void setyBias(float yBias) {
        this.yBias = yBias;
    }


    private void runCollisionFeedback() {

        long now = new Date().getTime();

        // Limit by time to get cleaner haptics
        if(this.haptic.hasVibrator() && now - prevHapticTime > 1000) {
            this.haptic.vibrate(50);
            prevHapticTime = now;
        }

        // TODO: Add sound
    }
}
