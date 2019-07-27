package br.com.eskinfotechweb.eskfinpessoal.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(min = 5, max = 120)
	public String logradouro;

	@NotEmpty
	public String numero;
	public String complemento;

	@NotEmpty
	@Size(min = 5, max = 120)
	public String bairro;

	@NotEmpty
	public String cep;

	@NotEmpty
	public String cidade;

	@NotEmpty
	public String estado;

	public Endereco() {
	}

	public Endereco(String logradouro, String numero, String complemento, String bairro, String cep, String cidade,
			String estado) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
