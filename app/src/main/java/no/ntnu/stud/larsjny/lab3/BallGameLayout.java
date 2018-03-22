package no.ntnu.stud.larsjny.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.view.View;

import java.util.Date;

/**
 * Draws a Ball on the center of the screen whose position can be changed by
 * setting a new {@link #setxBias(float)} and/or {@link #setyBias(float)}
 * and calling {@link #invalidate()} to update the location
 *
 * @author Lars Johan
 * @see View
 */

public class BallGameLayout extends View {

    /**
     * The color of the ball
     */
    private static final Paint PAINTBALL = new Paint();

    /**
     * Color of the line from 0,0 to the ball
     */
    private static final Paint PAINTLINE = new Paint();

    /**
     * Time of the last haptic feedback
     */
    private static long prevHapticTime;

    /**
     * The current position of the ball on the X-axis
     */
    private float posX;

    /**
     * The current position of the ball on the Y-axis
     */
    private float posY;

    /**
     * The distance (and direction) to move the ball on each iteration
     * in the X-direction
     */
    private float xBias;

    /**
     * The distance (and direction) to move the vall on each iteration
     * in the Y-direction
     */
    private float yBias;

    /**
     * Vibrator for haptic feedback on collision
     */
    private Vibrator haptic;

    /**
     * Audio for audible feedback on collision
     */
    private Ringtone audio;


    /**
     * Constructor.
     * Sets default parameters and fetches the necessary resource.
     * @param context The activity the ball is displayed in
     */
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
        Uri notification = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);
        this.audio = RingtoneManager.getRingtone(context, notification);
    }

    /**
     * Draws the ball on screen.
     * Calculates the size, borders and position of the ball.
     * Runs the methos {@link #runCollisionFeedback()} if the ball collides with the borders
     *
     * @param canvas The canvas to draw to
     */
    @Override
    protected void onDraw(Canvas canvas) {

        // Set the radius to 5% of the screen-width if the UI has drawn the canvas,
        // or 75 by default
        int radius = (canvas.getWidth() > 0) ? (int) (canvas.getWidth() * 0.05) : 75;

        // Get canvas-size
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Calculate the position to move to
        float nextPosX = this.posX + this.xBias;
        float nextPosY = this.posY + this.yBias;

        // Chack collision on X-axis
        if(Math.abs(nextPosX) <= width / 2 - radius)
            this.posX = nextPosX;
        else
            runCollisionFeedback();

        // Check collision on Y-axis
        if(Math.abs(nextPosY) <= height / 2 - radius)
            this.posY = nextPosY;
        else
            runCollisionFeedback();

        // Translate the canvas to have 0,0 in the center of the screen
        canvas.translate(getWidth() / 2, getHeight() / 2);

        // Draw the ball at the new position
        canvas.drawCircle(nextPosX, nextPosY, radius, PAINTBALL);

        // Draw a line from the center to the ball (Artistic freedom!)
        canvas.drawLine(0.0f,0.0f, nextPosX, nextPosY, PAINTLINE);
    }


    /**
     * Set the difference between the current position and the new position
     * in the X-direction
     * @param xBias positive to move left, negative to move right
     */
    public void setxBias(float xBias) {
        this.xBias = xBias;
    }

    /**
     * Set the difference between the current position and the new position
     * in the X-direction
     * @param yBias positive to move up, negative to move down
     */
    public void setyBias(float yBias) {
        this.yBias = yBias;
    }


    /**
     * Method to run when a collision occurs.
     * Sends a haptic and audible feedback to the user
     */
    private void runCollisionFeedback() {

        long now = new Date().getTime();

        // Limit by time to get cleaner haptics/audio
        if(this.haptic.hasVibrator() && now - prevHapticTime > 1000) {

            this.haptic.vibrate(50);

            prevHapticTime = now;

            this.audio.play();
        }

    }
}
