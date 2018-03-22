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


    public BallGameLayout(Context context) {
        super(context);

        PAINTBALL.setAntiAlias(true);
        PAINTBALL.setColor(Color.GREEN);
        PAINTBALL.setStyle(Paint.Style.FILL);

        this.posX = 0;
        this.posY = 0;

    }

    @Override
    protected void onDraw(Canvas canvas) {

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
}
