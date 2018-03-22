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

    private static final Paint PAINTLINE = new Paint();

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

        PAINTLINE.setAntiAlias(true);
        PAINTLINE.setColor(Color.RED);
        PAINTLINE.setStyle(Paint.Style.FILL);
        PAINTLINE.setStrokeWidth(2f);

        this.posX = 0;
        this.posY = 0;

        this.xBias = 0;
        this.yBias = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float nextPosX = this.posX + this.xBias;
        float nextPosY = this.posY + this.yBias;

        if(Math.abs(nextPosX) <= width / 2 - this.radius)
            this.posX += this.xBias;

        if(Math.abs(nextPosY) <= height / 2 - this.radius)
            this.posY += this.yBias;

        // Draw the background with a frame
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(this.posX, this.posY, this.radius, PAINTBALL);

        Paint linePaint = PAINTLINE;
        if(Math.abs(this.posX) > width / 2 || Math.abs(this.posY) > height / 2)
            linePaint.setColor(Color.BLUE);
        else
            linePaint.setColor(Color.RED);


        canvas.drawLine(0.0f,0.0f, this.posX, this.posY, linePaint);
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
