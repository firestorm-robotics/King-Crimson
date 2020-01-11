package frc.utils;

public class KingMathUtils {
    public static double logit(double x) {
        if(x == 0) {
          return x;
        }else if(x < 0) {
          x = Math.abs(x);
          x = (0.15*Math.log((x/(1-0.999535*x)))+0.5)/2;//(0.15*Math.log((-x+1)/(1-(0.999535*(-x+1))))+0.5);
          x = -x;
        }else {
          x = 0.15*Math.log((x/(1-0.999535*x)))+0.5;
        }
        
        return x;
    }

    public static double turnExp(double x) {
        double xSign = x;
        if(x == 0) {
            return x;
        }else if( Math.abs(x) < 0.3) {
            x = 4*Math.pow(x,2);
        } else if(Math.abs(x) >= 0.3 && Math.abs(x) < 0.6) {
            x = 0.4*Math.pow(Math.abs(x),0.1);
        }else if(Math.abs(x) >= 0.6) {
            x = 0.6*Math.pow((Math.abs(x)-0.6),0.1);
        }
        x = Math.copySign(x, xSign);
        return x;
    }

    public static double clampD(double x, double bounds) {
        if(Math.abs(x) <= bounds) {
            return 0;
        }
        return x;
    }
}