package br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.eskinfotechweb.eskfinpessoal.domain.enums.TipoLancamento;

public class ResumoLancamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private Integer tipo;
	private String categoria;
	private String pessoa;

	public ResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, Integer tipo, String categoria, String pessoa) {
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return TipoLancamento.toEnum(tipo).getDescricao();
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

}
