package frc.utils;

public class KingMathUtils {
    public static double logit(double x) {
        double y = 0;
        if(x == 0) {
          y = 0;
        }else if(x < 0) {
          x = Math.abs(x);
          y = (0.15*Math.log((x/(1-0.999535*x)))+0.5)/2;//(0.15*Math.log((-x+1)/(1-(0.999535*(-x+1))))+0.5);
          y = -y;
        }else {
          y = 0.15*Math.log((x/(1-0.999535*x)))+0.5;
        }
        
        return y;
    }

    public static double turnExp(double x) {
        double y = 0;
        if(x == 0) {
            y = 0;
        }else if( Math.abs(x) < 0.3) {
            y = 4*Math.pow(x,2);
        } else if(Math.abs(x) >= 0.3 && Math.abs(x) < 0.6) {
            y = 0.4*Math.pow(Math.abs(x),0.1);
        }else if(Math.abs(x) >= 0.6) {
            y = 0.6*Math.pow((Math.abs(x)-0.6),0.1);
        }
        y = Math.copySign(y, x);
        return y;
    }

    public static double clampD(double x, double bounds) {
        if(Math.abs(x) <= bounds) {
            return 0;
        }
        return x;
    }
}