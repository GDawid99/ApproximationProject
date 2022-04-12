package func.op;

import func.Function;

public class DivFunVar implements Function {
    double var;
    Function f;
    public DivFunVar(Function f, double var) {
        this.var = var;
        this.f = f;
    }


    @Override
    public double fun(double x) {
        if (var < 0.0000000001 && f == null) return 0;
        else if (var < 0.0000000001) return f.fun(x);
        else if (f == null) return var;
        else return f.fun(x) / var;
    }
}
