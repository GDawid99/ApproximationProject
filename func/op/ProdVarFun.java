package func.op;

import func.Function;

public class ProdVarFun implements Function {
    double var;
    Function f;
    public ProdVarFun(double var, Function f) {
        this.var = var;
        this.f = f;
    }

    @Override
    public double fun(double x) {
        if (var == 0) return 0;
        else return var * f.fun(x);
    }
}
