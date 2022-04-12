package func.op;

import func.Function;

public class SubTrackFun implements Function {
    Function f1;
    Function f2;
    public SubTrackFun(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }
    @Override
    public double fun(double x) {
        if (f1 == null && f2 == null) return 0;
        else if (f2 == null) return f1.fun(x);
        else if (f1 == null) return f2.fun(x);
        else return f1.fun(x) - f2.fun(x);
    }
}
