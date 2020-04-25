package br.com.codenation.desafioexe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesafioApplication {

	public static void main(String[] args) {
		System.out.println(fibonacci());
		System.out.println(isFibonacci(233));
	}

	public static List<Integer> fibonacci() {
		Integer[] fibonacci = new Integer[15];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        recursiveFibonacci(14, fibonacci);

        return new ArrayList<Integer>(Arrays.asList(fibonacci));
	}

	public static Boolean isFibonacci(Integer number) {
		return fibonacci().contains(number);
	}

	public static Integer recursiveFibonacci(Integer index, Integer[] list) {
        if (list[index] == null) list[index] = recursiveFibonacci(index - 1, list) + recursiveFibonacci(index - 2, list);
        return list[index];
    }

}