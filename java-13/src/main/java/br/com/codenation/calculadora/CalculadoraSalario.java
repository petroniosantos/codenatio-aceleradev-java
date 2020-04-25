package br.com.codenation.calculadora;


public class CalculadoraSalario {

	public long calcularSalarioLiquido(double salarioBase) {
		if(salarioBase >= (double) 1039) {
			double menosInss = calcularInss(salarioBase);
			double menosIrrf = calcularIrrf(menosInss);

			return Math.round(menosIrrf);
		}
		else return Math.round(0.0);
		
	}
	
	
	private double calcularInss(double salarioBase) {
		double porCento = salarioBase / (double) 100;
		double restoSalario = 0;

		if (salarioBase <= (double) 1500) {
			restoSalario = salarioBase - porCento * 8;
		}
		else if(salarioBase <= (double) 4000) {
			restoSalario = salarioBase - porCento * 9;
		}
		else restoSalario = salarioBase - porCento * 11;

		return restoSalario;
	}

	private double calcularIrrf(double salarioBase) {
		double porCento = salarioBase / (double) 100;
		double restoSalario = 0;

		if (salarioBase <= (double) 3000) {
			restoSalario = salarioBase;
		}
		else if(salarioBase <= (double) 6000) {
			restoSalario = salarioBase - porCento * 7.5;
		}
		else restoSalario = salarioBase - porCento * 15;

		return restoSalario;
	}

}