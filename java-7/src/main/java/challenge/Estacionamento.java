package challenge;


import java.util.*;

public class Estacionamento {
    private final int QTD_VAGAS = 10;
    private final int IDADE_SENIOR = 55;
    private final int MAIOR_IDADE = 18;
    private final int LIMITE_PONTOS = 20;

    final LinkedList<Carro> filaCarrosEstacionados = new LinkedList<>();

    public void estacionar(Carro carro) {
      validarMotorista(carro);

      if (filaCarrosEstacionados.size() == QTD_VAGAS){
          for (Carro carroEstacionado:
               filaCarrosEstacionados) {
              if (carroEstacionado.getMotorista().getIdade() <= IDADE_SENIOR){
                  filaCarrosEstacionados.removeFirstOccurrence(carroEstacionado);
                  break;
              }
          }
          if (filaCarrosEstacionados.size() == QTD_VAGAS) throw new EstacionamentoException("Todas as vagas estão ocupadas por pessoas com mais de 55 anos;");
      }
      filaCarrosEstacionados.add(carro);
    }

    public int carrosEstacionados() {
        return filaCarrosEstacionados.size();
    }

    public boolean carroEstacionado(Carro carro) {
        return filaCarrosEstacionados.contains(carro);
    }

    private void validarMotorista(Carro carro){
        if (carro.getMotorista().getIdade() < MAIOR_IDADE) throw new EstacionamentoException("O motorista deve ser maior de idade.");
        if (carro.getMotorista().getPontos() > LIMITE_PONTOS) throw new EstacionamentoException("O motorista excedeu o máximo de pontos.");
    }
}