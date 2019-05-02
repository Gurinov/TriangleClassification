package by.bsuir.gurinov.triangleclassification.models;

import java.math.BigDecimal;
import java.math.BigInteger;

import ch.obermuhlner.math.big.BigFloat;

public class Triangle {

    private Point[] points;

    private int side1;
    private int side2;
    private int side3;

    public boolean isValid;
    public String type;

    public Triangle(int side1, int side2, int side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;

        isValid = validation();
        if (isValid) {
            setType();
            setPoints();
        }
    }

    private void setType() {
        if ((side1 == side2) && (side1 == side3)) {
            type = "равносторонний";
        } else if ((side1 == side2) || (side1 == side3) || (side2 == side3)) {
            type = "равнобедренный";
        } else type = "неравносторонний";
    }

    private void setPoints() {
        points = new Point[3];
        points[0] = new Point(0, 0);
        points[1] = new Point(getMaxSide(), 0);
        points[2] = new Point(getLegInRightTriangle(), (float) getHeight().toDouble());
    }

    public Point[] getPoints() {
        return points;
    }

    public Point[] getPointsForDrawing(long width) {
        Point[] pointsForDrawing = new Point[]{points[0].clone(), points[1].clone(),
                points[2].clone()};
        pointsForDrawing[1].setX(width);
        pointsForDrawing[2].setX((points[2].getX() * width / getMaxSide()));
        pointsForDrawing[2].setY((points[2].getY() * width / getMaxSide()));

        for (Point point : pointsForDrawing) {
            point.setY(point.getY() + 10);
        }

        return pointsForDrawing;
    }

    private BigFloat getHeight() {
        BigFloat.Context context = BigFloat.context(100);
        BigFloat p = context.valueOf((side1 + side2 + side3));
        p = p.divide(2);
        BigFloat result = p.multiply((p.subtract(context.valueOf(side1))))
                .multiply((p.subtract(context.valueOf(side2))))
                .multiply((p.subtract(context.valueOf(side3))));
        result = BigFloat.sqrt(result);
        result = result.multiply(context.valueOf(2)).divide(context.valueOf(getMaxSide()));
        return result;
    }

    private int getMaxSide() {
        return Math.max(Math.max(side1, side2), side3);
    }

    private int getMinSide() {
        return Math.min(Math.min(side1, side2), side3);
    }

    private float getLegInRightTriangle() {
        return (float) Math.sqrt(Math.pow(getMinSide(), 2) - Math.pow(getHeight().toDouble(), 2));
    }

    private boolean validation() {
        return (side1 + side2 > side3) && (side2 + side3 > side1) && (side1 + side3 > side2) && (side1 > 0) && (side2 > 0) && (side3 > 0);
    }
}
