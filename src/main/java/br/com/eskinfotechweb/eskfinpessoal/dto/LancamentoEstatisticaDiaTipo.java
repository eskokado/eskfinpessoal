package br.com.eskinfotechweb.eskfinpessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.eskinfotechweb.eskfinpessoal.domain.enums.TipoLancamento;

public class LancamentoEstatisticaDiaTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoLancamento tipo;
	private LocalDate dia;
	private BigDecimal total;

	public LancamentoEstatisticaDiaTipo(Integer tipo, LocalDate dia, BigDecimal total) {
		super();
		this.tipo = TipoLancamento.toEnum(tipo);
		this.dia = dia;
		this.total = total;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
