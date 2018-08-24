
package metodos;

public class RandomCongruenteLinear {
    
    public static int m = 2147483647;
    public static int a = 25717;
    
    public static double[] gerarArray(int n){
        
        double z;
        double za = 1;
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            z = (a*za)%m;
            x[i] = (double) za/(m-1);
            za = z;
        }
        
        return x;
        
    }
    
}
