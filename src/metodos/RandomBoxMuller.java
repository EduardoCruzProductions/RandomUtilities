package metodos;

public class RandomBoxMuller {

    public static final double pi = 3.14159265359;

    public static double[][] gerarMatriz(double[] u1, double[] u2) throws Exception {

        if (u1.length == u2.length) {

            double[][] result = new double[u1.length][2];
            for (int i = 0; i < u1.length; i++) {

                double raiz = Math.sqrt(-2 * Math.log(u1[i]));
                double angulo = 2 * pi * u2[i];
                result[i][0] = raiz * Math.cos(angulo);
                result[i][1] = raiz * Math.sin(angulo);

            }

            return result;

        } else {
            throw new Exception("Arrays de tamanhos diferentes.");
        }

    }

}
