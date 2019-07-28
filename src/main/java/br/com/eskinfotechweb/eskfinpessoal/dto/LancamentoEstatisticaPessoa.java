package br.com.eskinfotechweb.eskfinpessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.domain.enums.TipoLancamento;

public class LancamentoEstatisticaPessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoLancamento tipo;
	private Pessoa pessoa;
	private BigDecimal total;

	public LancamentoEstatisticaPessoa(Integer tipo, Pessoa pessoa, BigDecimal total) {
		super();
		this.tipo = TipoLancamento.toEnum(tipo);
		this.pessoa = pessoa;
		this.total = total;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
