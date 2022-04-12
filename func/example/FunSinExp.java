package func.example;

import func.Function;

public class FunSinExp implements Function {
    @Override
    public double fun(double x) {
        return Math.sin(-x) + Math.exp(-x) - Math.pow(x,3);
    }
}
