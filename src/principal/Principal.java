package principal;

import metodos.InversaoAproximada;
import metodos.RandomCongruenteLinear;

public class Principal {

    public static void main(String[] args) {

        double[] array = RandomCongruenteLinear.gerarArray(100);

        for (double d : array) {
            
            System.out.println(d);
            
        }

        System.out.println("------------------------------------------------------------------------ Inverter");
        
        array = InversaoAproximada.inverter(array);
        
        for (double d : array) {
            
            System.out.println(d);
            
        }
        
    }

}
