package principal;

import java.util.Random;
import java.util.Scanner;
import metodos.InversaoAproximada;
import metodos.RandomBoxMuller;
import metodos.RandomCongruenteLinear;
import util.PrintArquivo;

public class Gndn {

    private static double[] rlcGeneratedArray;
    private static double[] ivaGeneratedArray;
    private static double[] rbmGeneratedArray;
    private static double[] rbmrGeneratedArray;

    private static double[] tempRbmArray1;
    private static double[] tempRbmArray2;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Erro! Nenhum argumento identificado.");
            System.out.println("Acesse o comando 'Gndn -help', para mais informações");
        } else {

            String op = "";
            int n = 0;
            boolean save = false;
            boolean err = false;

            for (String arg : args) {

                arg = arg.toLowerCase().trim();

                if (arg.equals("-help") && args.length == 1) {

                    System.out.println("Comandos disponívels: ");
                    System.out.println("        -rcl n -> Gera um vetor de tamanho n usando o método Congruente Linear.");
                    System.out.println("        -iva n -> Gera um vetor de tamanho n usando o método Congruente Linear e usa a Inversão Aproximada para transformar o vetor.");
                    System.out.println("        -rbm n -> Gera dois vetores de tamanho n usando o método Congruente Linear e usa o método de Box Muller para obter um vetor de números"
                            + "aleatórios com distribuição normal.");
                    System.out.println("        -rbmr n -> Gera dois vetores de tamanho n usando o método Random e usa o método de Box Muller para obter um vetor de números"
                            + "aleatórios com distribuição normal.");
                    System.out.println("        -s -> Imprime os números gerados em um arquivo de texto.");

                }

                if (n == 0) {

                    if (op.isEmpty()) {
                        if (arg.equals("-rcl")
                                || arg.equals("-iva")
                                || arg.equals("-rbm")
                                || arg.equals("-rbmr")) {
                            op = arg;
                        }
                    } else {

                        try {

                            n = Integer.parseInt(arg);

                        } catch (Exception e) {
                            err = true;
                        }

                    }

                } else {

                    if (arg.equals("-s")) {
                        save = true;
                    }

                }

            }

            if (op.isEmpty() || n == 0 || err) {

                System.err.println("Erro! Argumentos incorretos");
                System.out.println("Acesse o comando 'Gndn -help', para mais informações");

            } else {
                process(op, n, save);
            }

        }
    }

    private static void process(String op, int n, boolean save) {

        switch (op) {

            case "-rcl":
                rcl(n, save);
                break;

            case "-iva":

                iva(n, save);
                break;

            case "-rbm":

                System.out.println("Executar a geração de números de forma paralela? (s/n)");
                Scanner e = new Scanner(System.in);

                if (e.nextLine().toLowerCase().trim().equals("s")) {
                    rbmParalelo(n, save);
                } else {
                    rbm(n, save);
                }

                break;

            case "-rbmr":

                System.out.println("Executar a geração de números de forma paralela? (s/n)");
                Scanner e2 = new Scanner(System.in);

                if (e2.nextLine().toLowerCase().trim().equals("s")) {
                    rbmrParalelo(n, save);
                } else {
                    rbmr(n, save);
                }
                break;

            default:

                System.err.println("Erro! Argumentos incorretos");
                System.out.println("Acesse o comando 'Gndn -help', para mais informações");
                break;

        }

    }

    private static void rcl(int n, boolean save) {

        System.out.println("Iniciando processo de geração de números...");
        RandomCongruenteLinear rcl = new RandomCongruenteLinear();
        rlcGeneratedArray = rcl.gerarArray(n);
        show(rlcGeneratedArray);

        if (save) {
            PrintArquivo.printDoubleArray("numerosGeradosRCL", rlcGeneratedArray);
        }

        System.out.println("Processo concluído com sucesso!");

    }

    private static void iva(int n, boolean save) {

        rcl(n, save);
        System.out.println("Iniciando inversão...");
        ivaGeneratedArray = new InversaoAproximada().inverter(rlcGeneratedArray);
        show(ivaGeneratedArray);
        if (save) {
            PrintArquivo.printDoubleArray("numerosGeradosRCL_Invertidos", ivaGeneratedArray);
        }
        System.out.println("Processo concluído com sucesso!");

    }

    private static void rbmParalelo(int n, boolean save) {

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Gerando valores array 1...");
                RandomCongruenteLinear rcl = new RandomCongruenteLinear();
                Gndn.tempRbmArray1 = rcl.gerarArray(n);

            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Gerando valores array 2...");
                RandomCongruenteLinear rcl = new RandomCongruenteLinear();
                Gndn.tempRbmArray2 = rcl.gerarArray(n);

            }
        });
        t2.start();

        try {

            t1.join();
            t2.join();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Iniciando método de Box Muller");

        double[][] temp;

        try {

            temp = new RandomBoxMuller().gerarMatriz(tempRbmArray1, tempRbmArray2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        if (temp != null) {

            System.out.println("Ajustando lista de valores...");
            rbmGeneratedArray = turnMatrizToVetor(temp);

            show(rbmGeneratedArray);
            if (save) {
                PrintArquivo.printDoubleArray("numerosGeradosRBM", rbmGeneratedArray);
            }

        }

        System.out.println("Processo concluído com sucesso!");

    }

    private static void rbm(int n, boolean save) {

        System.out.println("Gerando valores array 1...");

        RandomCongruenteLinear rcl = new RandomCongruenteLinear();
        double[] array1 = rcl.gerarArray(n);

        System.out.println("Gerando valores array 2...");
        double[] array2 = rcl.gerarArray(n);

        System.out.println("Iniciando método de Box Muller");

        double[][] temp;

        try {

            temp = new RandomBoxMuller().gerarMatriz(array1, array2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        if (temp != null) {

            System.out.println("Ajustando lista de valores...");
            rbmGeneratedArray = turnMatrizToVetor(temp);

            show(rbmGeneratedArray);
            if (save) {
                PrintArquivo.printDoubleArray("numerosGeradosRBM", rbmGeneratedArray);
            }

        }

        System.out.println("Processo concluído com sucesso!");

    }

    private static void rbmrParalelo(int n, boolean save) {

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Gerando valores array 1...");
                Random r = new Random();

                Gndn.tempRbmArray1 = new double[n];
                for (int i = 0; i < n; i++) {
                    Gndn.tempRbmArray1[i] = r.nextDouble();
                }

            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Gerando valores array 2...");
                Random r = new Random();

                Gndn.tempRbmArray2 = new double[n];
                for (int i = 0; i < n; i++) {
                    Gndn.tempRbmArray2[i] = r.nextDouble();
                }

            }
        });
        t2.start();

        try {

            t1.join();
            t2.join();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Iniciando método de Box Muller");

        double[][] temp;

        try {

            temp = new RandomBoxMuller().gerarMatriz(tempRbmArray1, tempRbmArray2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        if (temp != null) {

            System.out.println("Ajustando lista de valores...");
            rbmrGeneratedArray = turnMatrizToVetor(temp);

            show(rbmrGeneratedArray);
            if (save) {
                PrintArquivo.printDoubleArray("numerosGeradosRBM", rbmrGeneratedArray);
            }

        }

        System.out.println("Processo concluído com sucesso!");

    }

    private static void rbmr(int n, boolean save) {

        System.out.println("Gerando valores array 1...");

        Random r = new Random();

        double[] array1 = new double[n];
        for (int i = 0; i < array1.length; i++) {
            array1[i] = r.nextDouble();
        }

        System.out.println("Gerando valores array 2...");
        double[] array2 = new double[n];
        for (int i = 0; i < array2.length; i++) {
            array2[i] = r.nextDouble();
        }

        System.out.println("Iniciando método de Box Muller");

        double[][] temp;

        try {

            temp = new RandomBoxMuller().gerarMatriz(array1, array2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        if (temp != null) {

            System.out.println("Ajustando lista de valores...");
            rbmrGeneratedArray = turnMatrizToVetor(temp);

            show(rbmrGeneratedArray);
            if (save) {
                PrintArquivo.printDoubleArray("numerosGeradosRBM", rbmrGeneratedArray);
            }

        }

        System.out.println("Processo concluído com sucesso!");

    }

    private static double[] turnMatrizToVetor(double[][] matriz) {

        double[] temp = new double[matriz.length * matriz[0].length];
        int i = 0;

        for (int j = 0; j < matriz.length; j++) {
            for (int k = 0; k < matriz[0].length; k++) {
                temp[i] = matriz[j][k];
                i++;
            }
        }

        return temp;

    }

    public static void show(double[] array) {

        if (array.length > 10000) {
            System.out.println("Foi identificado um grande número de registros, portanto, o processo de exibição dos valores pode levar algum tempo.");
            System.out.println("Deseja exibir as informações no console? (s/n)");
            Scanner e = new Scanner(System.in);
            if (e.nextLine().toLowerCase().trim().equals("s")) {
                for (double d : array) {
                    System.out.println(d);
                }
            }
        } else {

            for (double d : array) {
                System.out.println(d);
            }
        }

    }

}
