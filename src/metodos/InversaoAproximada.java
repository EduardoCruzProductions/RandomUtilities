
package metodos;

public class InversaoAproximada {
    
    public double[] inverter(double[] entrada){

        for (int i = 0; i < entrada.length; i++) {
            
            entrada[i] = (Math.pow(entrada[i], 0.135) - Math.pow(1-entrada[i], 0.135)) / 0.1975;
            
        }
        
        return entrada;
            
    }
    
}
