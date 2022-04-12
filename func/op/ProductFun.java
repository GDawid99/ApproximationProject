package func.op;

import func.Function;

public class ProductFun {
    public double fun(Function f1, Function f2, double x) {
        return f1.fun(x)*f2.fun(x);
    }
}
