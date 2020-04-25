package br.com.codenation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatisticUtil {

    public static int average(int[] elements) {
        int media = (int) vetorParaLista(elements)
                .stream()
                .mapToInt(x -> x)
                .average()
                .getAsDouble();
        return media;
    }

    public static int mode(int[] elements) {
        List<Integer> numeros = vetorParaLista(elements);
        int quantidadeDeRepeticoes = 0;
        int moda = 0;
        for (int elemento : elements) {
            int contador = (int) numeros.stream().filter(x -> x == elemento).count();
            if (quantidadeDeRepeticoes < contador) {
                moda = elemento;
                quantidadeDeRepeticoes = contador;
            }
        }
        return moda;
    }

    public static int median(int[] elements) {
        List list = vetorParaLista(elements);
        int numero = (int) list.stream().count();
        if (numero % 2 == 1) return (int) list.get(numero / 2);
        else return ((int) list.get((numero-1) / 2) + (int) list.get(numero / 2))/2;
    }

    private static List<Integer> vetorParaLista(int[] vetor) {
        List<Integer> lista = new ArrayList<>();
        for (int elemento : vetor) lista.add(elemento);
        lista.sort(Comparator.comparing(Integer::intValue));
        return lista;
    }

    public static void main(String[] args) {
        int[] elementos = {1,4,6,10,12,14, 15};
        System.out.println(median(elementos));
    }
}