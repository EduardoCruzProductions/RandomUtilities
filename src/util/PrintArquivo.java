
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PrintArquivo {
    
    public static void printDoubleArray(String title, double[] array){
        
        System.out.println("Gerando arquivo...");
        try (PrintWriter writer = new PrintWriter(new File(title.trim()+".txt"))) {

            for(double d : array){
                writer.println(d);
            }

            System.out.println("Arquivo gerado com sucesso!");
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
}
