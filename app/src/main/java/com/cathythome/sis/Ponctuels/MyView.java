package com.cathythome.sis.Ponctuels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.PathParser;
import com.cathythome.sis.MainActivity;

public class MyView extends View {

    Paint bleu, orange, vert, jaune, rose, violet, gris;
    MainActivity context;
    int id;

    public MyView(MainActivity context, int id) {
        super(context);
        this.context = context;
        this.id = id;
        Integer[] couleurs = {Color.rgb(68, 114, 196), Color.rgb(237, 125, 49), Color.rgb(112, 173, 71), Color.rgb(255, 192, 2), Color.rgb(230, 125, 183), Color.rgb(158, 67, 230)};

        bleu = new Paint();
        bleu.setStyle(Paint.Style.STROKE);
        bleu.setColor(couleurs[0]);
        bleu.setStrokeWidth(10);

        orange = new Paint();
        orange.setStyle(Paint.Style.STROKE);
        orange.setColor(couleurs[1]);
        orange.setStrokeWidth(10);

        vert = new Paint();
        vert.setStyle(Paint.Style.STROKE);
        vert.setColor(couleurs[2]);
        vert.setStrokeWidth(10);

        jaune = new Paint();
        jaune.setStyle(Paint.Style.STROKE);
        jaune.setColor(couleurs[3]);
        jaune.setStrokeWidth(10);

        rose = new Paint();
        rose.setStyle(Paint.Style.STROKE);
        rose.setColor(couleurs[4]);
        rose.setStrokeWidth(10);

        violet = new Paint();
        violet.setStyle(Paint.Style.STROKE);
        violet.setColor(couleurs[5]);
        violet.setStrokeWidth(10);

        gris = new Paint();
        gris.setStyle(Paint.Style.FILL);
        gris.setColor(Color.argb(75, 100, 100, 100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float height = this.getHeight();
        float width = this.getWidth();
        float d = 0;
        float e = 0;

        if (width<height) {
            d = width;
            e = (height - width) / 2;
        }
        else {
            d = height;
            e = (width-height) / 2;
        }
        float h = d/2;

        System.out.println(context.alpha[13]);
        if(this.id == 0) {
            gris.setColor(Color.argb(context.alpha[13]*15+75, 100, 100, 100));
            canvas.drawCircle(7 * d / 10, h + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[14]*15+75, 100, 100, 100));
            canvas.drawCircle(9 * d / 10, h + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[15]*15+75, 100, 100, 100));
            canvas.drawCircle(h, 7 * d / 10 + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[16]*15+75, 100, 100, 100));
            canvas.drawCircle(h, 9 * d / 10 + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[17]*15+75, 100, 100, 100));
            canvas.drawCircle(3 * d / 10, h + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[18]*15+75, 100, 100, 100));
            canvas.drawCircle(d / 10, h + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[19]*15+75, 100, 100, 100));
            canvas.drawCircle(h, 3 * d / 10 + e, 30, gris);
            gris.setColor(Color.argb(context.alpha[20]*15+75, 100, 100, 100));
            canvas.drawCircle(h, d / 10 + e, 30, gris);

            canvas.drawPath(PathParser.createPathFromPathData("M" + String.valueOf(h + 20) + "," + String.valueOf(e) + "l0," + String.valueOf(h - 100) + "c0,40 40,80 80,80L" + String.valueOf(d) + "," + String.valueOf(h - 20 + e)), orange);
            canvas.drawPath(PathParser.createPathFromPathData("M0," + String.valueOf(h + 20 + e) + "l" + String.valueOf(h - 100) + ",0c40,0 80,40 80,80L" + String.valueOf(h - 20) + "," + String.valueOf(d + e)), jaune);
            canvas.drawPath(PathParser.createPathFromPathData("M0," + String.valueOf(h - 20 + e) + "l" + String.valueOf(h - 100) + ",0c40,0 80,-40 80,-80L" + String.valueOf(h - 20) + "," + String.valueOf(e)), violet);
            canvas.drawPath(PathParser.createPathFromPathData("M" + String.valueOf(h + 20) + "," + String.valueOf(d + e) + "l0,-" + String.valueOf(h - 100) + "c0, -40 40,-80 80,-80L" + String.valueOf(d) + "," + String.valueOf(h + 20 + e)), rose);
            canvas.drawPath(PathParser.createPathFromPathData("M0," + String.valueOf(h + e) + "L" + String.valueOf(d) + "," + String.valueOf(h + e)), bleu);
            canvas.drawPath(PathParser.createPathFromPathData("M" + String.valueOf(h) + "," + String.valueOf(e) + "L" + String.valueOf(h) + "," + String.valueOf(d + e)), vert);

        }
        else {
            gris.setColor(Color.argb(context.alpha[13]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 2*d/7, 20, gris);
            gris.setColor(Color.argb(context.alpha[14]*15+75, 100, 100, 100));
            canvas.drawCircle(d+2*e-30, h, 20, gris);
            gris.setColor(Color.argb(context.alpha[15]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 5*d/7, 20, gris);
            gris.setColor(Color.argb(context.alpha[16]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 6*d/7, 20, gris);
            gris.setColor(Color.argb(context.alpha[17]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 4*d/7, 20, gris);
            gris.setColor(Color.argb(context.alpha[18]*15+75, 100, 100, 100));
            canvas.drawCircle(30, h, 20, gris);
            gris.setColor(Color.argb(context.alpha[19]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 3*d/7, 20, gris);
            gris.setColor(Color.argb(context.alpha[20]*15+75, 100, 100, 100));
            canvas.drawCircle(h+e, 1*d/7, 20, gris);

            canvas.drawPath(PathParser.createPathFromPathData("M" + String.valueOf(h+e) + "," + String.valueOf(0) + "L" + String.valueOf(h+e) + "," + String.valueOf(d)), vert);
        }
        invalidate();
    }
}
