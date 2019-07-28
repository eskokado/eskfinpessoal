package br.com.eskinfotechweb.eskfinpessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.eskinfotechweb.eskfinpessoal.domain.enums.TipoLancamento;

public class LancamentoEstatisticaDiaTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate dia;
	private Integer tipo;
	private BigDecimal total;

	public LancamentoEstatisticaDiaTipo(LocalDate dia, TipoLancamento tipo, BigDecimal total) {
		super();
		this.dia = dia;
		this.tipo = (tipo == null) ? null : tipo.getCod();
		this.total = total;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public TipoLancamento getTipo() {
		return TipoLancamento.toEnum(tipo);
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo.getCod();
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
