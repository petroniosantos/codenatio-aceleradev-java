package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.modelos.Jogador;
import br.com.codenation.modelos.Time;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> times = new ArrayList<>();
	private List<Jogador> jogadores = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if(existeTime(id)) {
			throw new IdentificadorUtilizadoException("Identificador " + id + " já utilizado");
		}

		times.add(new Time(id,nome,dataCriacao,corUniformePrincipal,corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		if(existeJogador(id)) {
			throw new IdentificadorUtilizadoException("Identificador " + id + " já utilizado");
		}
		if(!existeTime(idTime))
			throw new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado");

		jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Jogador jogador = jogadores.stream().filter(jogador1 -> jogador1.getId().equals(idJogador))
				.findFirst().orElseThrow(() -> new JogadorNaoEncontradoException("Jogador de id: " + idJogador + " não encontrado"));

		times.forEach(time -> {
			if (time.getId().equals(jogador.getIdTime())) {
				time.setIdCapitao(idJogador);
			}
		});
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		if(!existeTime(idTime))
			throw new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado");

		Time time = buscarTime(idTime);

		if (time.getIdCapitao() != null) {
			return time.getIdCapitao();
		} else {
			throw new CapitaoNaoInformadoException();
		}
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return jogadores.stream().filter(jogador -> jogador.getId().equals(idJogador))
				.map(Jogador::getNome).findFirst()
				.orElseThrow(() -> new JogadorNaoEncontradoException("Jogador de id: " + idJogador + " não encontrado"));
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return times.stream().filter(time -> time.getId().equals(idTime))
				.map(Time::getNome).findFirst()
				.orElseThrow(() -> new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado"));
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		if(!existeTime(idTime))
			throw new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado");

		return jogadores.stream().sorted(Comparator.comparingLong(Jogador::getId))
				.filter(jogador -> jogador.getIdTime().equals(idTime))
				.map(Jogador::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		return jogadores.stream().sorted(Comparator.comparingLong(Jogador::getNivelHabilidade).reversed())
				.filter(jogador -> jogador.getIdTime().equals(idTime))
				.map(Jogador::getId).findFirst()
				.orElseThrow(() -> new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado"));
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		return jogadores.stream().sorted(Comparator.comparing(Jogador::getDataNascimento)
					.thenComparingLong(Jogador::getId))
				.filter(jogador -> jogador.getIdTime().equals(idTime))
				.map(Jogador::getId).findFirst()
				.orElseThrow(() -> new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado"));
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return times.stream().sorted(Comparator.comparingLong(Time::getId))
				.map(Time::getId).collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return jogadores.stream().sorted(Comparator.comparing(Jogador::getSalario)
					.reversed()
					.thenComparingLong(Jogador::getId))
				.filter(jogador -> jogador.getIdTime().equals(idTime))
				.map(Jogador::getId).findFirst()
				.orElseThrow(() -> new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado"));
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return jogadores.stream().filter(jogador -> jogador.getId().equals(idJogador))
				.map(Jogador::getSalario).findFirst()
				.orElseThrow(() -> new JogadorNaoEncontradoException("Jogador de id: " + idJogador + " não encontrado"));
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		return jogadores.stream()
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade)
						.reversed()
						.thenComparingLong(Jogador::getId))
				.limit(top.longValue())
				.map(Jogador::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		if(!existeTime(timeDaCasa))
			throw new TimeNaoEncontradoException("Time de id: " + timeDaCasa + " não encontrado");
		if(!existeTime(timeDeFora))
			throw new TimeNaoEncontradoException("Time de id: " + timeDeFora + " não encontrado");

		Time timeCasa = buscarTime(timeDaCasa);
		Time timeFora = buscarTime(timeDeFora);

		if (timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())) {
			return timeFora.getCorUniformeSecundario();
		} else {
			return timeFora.getCorUniformePrincipal();
		}
	}

	private Time buscarTime(Long idTime) {
		return times.stream().filter(time -> time.getId().equals(idTime))
				.findFirst()
				.orElseThrow(() -> new TimeNaoEncontradoException("Time de id: " + idTime + " não encontrado"));
	}

	private boolean existeJogador(Long id) {
		return jogadores.stream().anyMatch(jogador -> jogador.getId().equals(id));
	}

	private boolean existeTime(Long id) {
		return times.stream().anyMatch(time -> time.getId().equals(id));
	}

}