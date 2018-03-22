package no.ntnu.stud.larsjny.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by Lars Johan on 22.03.2018.
 */

public class BallGameLayout extends View {

    /**
     * The color of the ball
     */
    private static final Paint PAINTBALL = new Paint();

    private float radius = 75;

    private float posX;

    private float posY;

    private float xBias;

    private float yBias;


    public BallGameLayout(Context context) {
        super(context);

        PAINTBALL.setAntiAlias(true);
        PAINTBALL.setColor(Color.GREEN);
        PAINTBALL.setStyle(Paint.Style.FILL);

        this.posX = 0;
        this.posY = 0;

        this.xBias = 0;
        this.yBias = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        this.posX += this.xBias;
        this.posY += this.yBias;

        // Draw the background with a frame
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(this.posX, this.posY, this.radius, PAINTBALL);
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
}
