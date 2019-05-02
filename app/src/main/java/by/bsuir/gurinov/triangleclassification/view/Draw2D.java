package by.bsuir.gurinov.triangleclassification.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import by.bsuir.gurinov.triangleclassification.models.Point;
import by.bsuir.gurinov.triangleclassification.models.Triangle;

public class Draw2D extends View {

    private Triangle triangle;

    public Draw2D(Context context, Triangle triangle) {
        super(context);
        this.triangle = triangle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawPaint(mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);

        if (triangle != null) {
            Point[] points = triangle.getPointsForDrawing(getWidth());
            canvas.drawLine(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), mPaint);
            canvas.drawLine(points[0].getX(), points[0].getY(), points[2].getX(), points[2].getY(), mPaint);
            canvas.drawLine(points[2].getX(), points[2].getY(), points[1].getX(), points[1].getY(), mPaint);
        }
    }
}
