package br.com.eskinfotechweb.eskfinpessoal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.eskinfotechweb.eskfinpessoal.domain.Categoria;

public class LancamentoEstatisticaCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	private Categoria categoria;
	private BigDecimal total;
	
	public LancamentoEstatisticaCategoria(Categoria categoria, BigDecimal total) {
		super();
		this.categoria = categoria;
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
				
}
