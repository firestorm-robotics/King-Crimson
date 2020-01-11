package frc.utils;

public class KingMathUtils {
    public static double logit(double x) {
        double scaledX = 0;
        if (x == 0) {
            return 0;
        } else if (x < 0) {
            scaledX = Math.abs(x);
            scaledX = (0.15 * Math.log((scaledX / (1 - 0.999535 * scaledX))) + 0.5) / 2;// (0.15*Math.log((-x+1)/(1-(0.999535*(-x+1))))+0.5);
            scaledX = Math.copySign(scaledX, x);
        } else {
            scaledX = 0.15 * Math.log((scaledX / (1 - 0.999535 * scaledX))) + 0.5;
        }

        return x;
    }

    public static double turnExp(double x) {
        double scaledX = 0;
        if (x == 0) {
            return 0;
        } else if (Math.abs(x) < 0.3) {
            scaledX = 4 * Math.pow(x, 2);
        } else if (Math.abs(x) >= 0.3 && Math.abs(x) < 0.6) {
            scaledX = 0.4 * Math.pow(Math.abs(x), 0.1);
        } else if (Math.abs(x) >= 0.6) {
            scaledX = 0.6 * Math.pow((Math.abs(x) - 0.6), 0.1);
        }
        scaledX = Math.copySign(scaledX, x);
        return scaledX;
    }

    public static double clampD(double x, double bounds) {
        if (Math.abs(x) <= bounds) {
            return 0;
        }
        return x;
    }
}