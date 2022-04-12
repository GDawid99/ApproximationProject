import java.util.ArrayList;
import java.util.List;
import func.Function;
import func.example.Fun;
import func.example.Fun1;
import func.example.Fun2;
import func.example.Fun3;
import func.example.Fun4;
import func.example.FunSinExp;
import func.op.DivFunVar;
import func.op.ProductFun;
import func.op.ProdVarFun;
import func.op.SumVarFun;
import func.op.SumFuns;
import func.op.SubTrackFun;


public class Main {
    static List<Function> functions;
    static List<String> tmpStr;
    static List<String> str1;
    static List<String> str2;

    public static void main(String[] args) {

        functions = new ArrayList<>();
        tmpStr = new ArrayList<>();
        str1 = new ArrayList<>();
        str2 = new ArrayList<>();

        functions.add(new Fun());
        functions.add(new Fun1());
        functions.add(new Fun2());
        functions.add(new Fun3());
        functions.add(new Fun4());

        ProductFun pFun = new ProductFun();
        Function fSinExp = new FunSinExp();

        final double a1 = -1;
        final double b1 = 1;

        final double a2 = 0;
        final double b2 = 1;

        final int n = 1000;

        List<Function> funsOrt1 = new ArrayList<>();
        List<Function> funsOrt2 = new ArrayList<>();

        //funsOrt1 = regulaTrojczlonowa(functions,pFun,a1,b1,n);


        funsOrt1 = processGramSchmidt(functions,pFun,a1,b1,n);
        str1.addAll(tmpStr);
        tmpStr.clear();

        /*
        funsOrt2 = processGramSchmidt(functions,pFun,a2,b2,n);
        str2.addAll(tmpStr);
        tmpStr.clear();
         */

        List<Double> X = new ArrayList<>();
        List<Function> XFun = new ArrayList<>();

        X = approximation(functions,fSinExp, pFun, a1, b1, n);

        System.out.println("-------------------------------------");
        System.out.println("Wartości po aproksymacji - baza standardowa:");

        for (int i = 0; i < X.size(); i++) {
            System.out.format("%.2f%n", X.get(i));
        }

        System.out.println("-------------------------------------");

        System.out.println("Wzory po aproksymacji - baza zortogonalizowana moG-S:");
        XFun = approximation2(funsOrt1,fSinExp, pFun, a1, b1, n);

    }

    static List<Function> processGramSchmidt(List<Function> f, ProductFun pFun, double a, double b, int n) {
        List<Function> g = new ArrayList<>();
        g.add(f.get(0));
        SubTrackFun tmpSub;
        ProdVarFun pvf;
        SumFuns sf = new SumFuns(null,null);

        tmpStr.add("1");
        System.out.println(tmpStr.get(0));

        for (int i = 1; i < f.size(); i++) {
            List<Double> list = new ArrayList<>();
            for (int j = 1; j < i+1; j++) {
                double s1 = simpson(a, b, f.get(i), g.get(j-1), pFun, n) / simpson(a, b, g.get(j-1), g.get(j-1), pFun, n);
                pvf = new ProdVarFun(s1,g.get(j-1));
                sf = new SumFuns(sf,pvf);
                list.add(s1);
            }
            tmpSub = new SubTrackFun(f.get(i),sf);
            g.add(tmpSub);
            String st = print(list);
            tmpStr.add(st);
            System.out.println(tmpStr.get(i));
            //print2(list);
            list.clear();
            sf = new SumFuns(null,null);
        }
        return g;
    }

    //ZADANIE 2
    /*
    static List<Function> regulaTrojczlonowa(List<Function> f, ProductFun pFun, double a, double b, int n) {
        List<Function> g = new ArrayList<>();
        Fun f1 = new Fun();
        Fun1 fx = new Fun1();
        SumVarFun s;
        Product pr;
        SumVarFun s1;
        SumVarFun s2;
        SumFuns sf;

        double beta = beta(f1,fx,pFun,a,b,n);
        double gamma;

        s = new SumVarFun(beta,fx);
        g.add(s);     //1
        beta = beta(g.get(0),fx,pFun,a,b,n);
        gamma = gamma(g.get(0),f1,pFun,a,b,n);
        pr = new Product(new SumVarFun(beta,fx),g.get(0));
        s = new SumVarFun(gamma,pr);
        g.add(s);     //2
        for (int i = 2; i < f.size(); i++) {
            beta = beta(g.get(i-1),fx,pFun,a,b,n);
            gamma = gamma(g.get(i-1),g.get(i-2),pFun,a,b,n);
            s1 = new SumVarFun(beta,fx);
            s2 = new SumVarFun(gamma,g.get(i-2));


            pr = new Product(s1,g.get(i-1));

            sf = new SumFuns(pr,s2);
            g.add(sf);
        }

        return g;
    }

    static double beta(Function f, Function fx, ProductFun pFun, double a, double b, int n) {
        Product tmpFun = new Product(f,fx);
        double beta = (-1.0) * (simpson(a, b, tmpFun, f, pFun, n) / simpson(a, b, f, f, pFun, n));
        //System.out.println("Beta: " + beta);
        if (beta < 0.0000000001) return 0.0;
        else return beta;
    }

    static double gamma(Function f1, Function f2, ProductFun pFun, double a, double b, int n) {
        double gamma = (-1.0) * (simpson(a, b, f1, f1, pFun, n) / simpson(a, b, f2, f2, pFun, n));
        System.out.println("Gamma: " + gamma);
        if (gamma < 0.0000000001) return 0.0;
        else return gamma;
    }

    */

    //----------------------------------------------------------------------------


    static String print(List<Double> args) {
        StringBuilder stringBuilder = new StringBuilder();
        String s1 = "", s2 = "";
        int i;
        for (i = 0; i < args.size(); i++) {
            if (args.get(i) > 0.0000000001) {
                s1 = "-" + args.get(i) + "x^" + i + " ";
                stringBuilder.append(s1);
            }
        }
        s2 = "+x^" + i;
        stringBuilder.append(s2);
        return stringBuilder.toString();
    }

    static double simpson(double a, double b, Function f1, Function f2, ProductFun f, int n) {
        double result = 0, mValue = 0, x;
        double h = (b - a)/n;
        for (int i = 0; i < n; i++) {
            x = a + (i + 1) * h;
            mValue += f.fun(f1,f2,x - h * 0.5);
            if (i == n - 1) break;
            else result += f.fun(f1,f2,x);
        }
        result = (h / 6) * (f.fun(f1,f2,a) + f.fun(f1,f2,b) + 2 * result + 4 * mValue);
        return result;
    }

    //---------------------------APROKSYMACJA-------------------------------------------

    //Metoda Eliminacji Gaussa z poprzednich laboratioriów
    //(zmodyfikowana względem poprzednika w celu liczenia dla list zamiast tablic)

    public static List<Double> elimGauss(List<List<Double>> A, List<Double> b, int n) {
        List<List<Double>> Ab = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Ab.add(new ArrayList<>());
        }
        List<Integer> numCol = new ArrayList<>();
        List<Double> X = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Ab.get(i).add(A.get(i).get(j));
            }
            Ab.get(i).add(b.get(i));
            numCol.add(i);
            X.add(0.0);
        }

        numCol.add(n);

        int var, tmp;

        for (int i = 0; i < n - 1; i++) {
            var = i;
            for (int j = i + 1; j < n; j++) {
                if (Ab.get(i).get(numCol.get(var)) < Ab.get(i).get(numCol.get(j))) {
                    var = j;
                }
            }

            tmp = numCol.get(var);

            numCol.set(var,numCol.get(i));

            numCol.set(i,tmp);

            for(int j = i + 1; j < n; j++)
            {
                if (Ab.get(i).get(numCol.get(i)) == 0) {
                    System.out.println("Dzielnik rowny 0.");
                    return X;
                }
                for (var = i + 1; var <= n; var++) {
                    double el = Ab.get(j).get(numCol.get(var)) + ((((-1) * Ab.get(j).get(numCol.get(i)) / Ab.get(i).get(numCol.get(i)) * Ab.get(i).get(numCol.get(var)))));
                    Ab.get(j).set(numCol.get(var),el);
                }
            }
        }

        for(int i = n - 1; i >= 0; i--) {
            if (Ab.get(i).get(numCol.get(i)) == 0) {
                System.out.println("Dzielnik rowny 0.");
                return X;
            }
            for (int j = n - 1; j >= i; j--) {
                double el = Ab.get(i).get(n) - Ab.get(i).get(numCol.get(j)) * X.get(numCol.get(j));
                Ab.get(i).set(n,el);
            }
            double temp = Ab.get(i).get(n) / Ab.get(i).get(numCol.get(i));
            X.set(numCol.get(i),temp);
        }
        return X;
    }

    //Aproksymacja dla bazy standardowej

    public static List<Double> approximation(List<Function> fList, Function f, ProductFun pFun, double a, double b, int n) {
        List<List<Double>> A = new ArrayList<>();
        final int N = fList.size();
        for (int i = 0; i < N; i++) {
            A.add(new ArrayList<>());
        }

        List<Double> B = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double s = simpson(a, b, fList.get(j), fList.get(i), pFun, n);
                A.get(i).add(s);
            }
            double s2 = simpson(a, b, f, fList.get(i), pFun, n);
            B.add(s2);
        }

        return elimGauss(A,B,N);
    }

    //Aproksymacja dla bazy zortogonalizowanej

    public static List<Function> approximation2(List<Function> fList, Function f, ProductFun pFun, double a, double b, int n) {
        List<Function> result = new ArrayList<>();
        fList = normalization(fList,pFun,a,b,n);
        for (int i = 0; i < fList.size(); i++) {
            double s = simpson(a, b, f, fList.get(i),pFun,n);
            result.add(new SumVarFun(s,fList.get(i)));
            System.out.format("%.5f",s);
            System.out.println("(" + str1.get(i) + ")");
        }
        return result;
    }

    public static List<Function> normalization(List<Function> fList, ProductFun pFun, double a, double b, int n) {
        for (int i = 0; i < fList.size(); i++) {
            fList.set(i, new DivFunVar(fList.get(i),simpson(a,b,fList.get(i),fList.get(i),pFun,n)));
        }
        return fList;
    }

}
