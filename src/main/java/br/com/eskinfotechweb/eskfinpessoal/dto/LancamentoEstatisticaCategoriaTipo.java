package br.com.eskinfotechweb.eskfinpessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.eskinfotechweb.eskfinpessoal.domain.Categoria;
import br.com.eskinfotechweb.eskfinpessoal.domain.enums.TipoLancamento;

public class LancamentoEstatisticaCategoriaTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private TipoLancamento tipo;
	private BigDecimal total;
	
	public LancamentoEstatisticaCategoriaTipo(Categoria categoria, Integer tipo, BigDecimal total) {
		super();
		this.categoria = categoria;
		this.tipo = TipoLancamento.toEnum(tipo);
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
