package br.com.eskinfotechweb.eskfinpessoal.domain.enums;

public enum TipoLancamento {
	RECEITA(1, "Receita"), DESPESA(2, "Despesa");
	
	private Integer cod;
	private String descricao;
	
	private TipoLancamento(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoLancamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TipoLancamento x : TipoLancamento.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
