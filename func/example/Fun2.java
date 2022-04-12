package func.example;

import func.Function;

public class Fun2 implements Function {
    @Override
    public double fun(double x) {
        return Math.pow(x,2);
    }
}
