package br.com.eskinfotechweb.eskfinpessoal.services.exceptions;

public class PessoaInexistenteOuInativaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PessoaInexistenteOuInativaException(String msg) {
		super(msg);
	}

	public PessoaInexistenteOuInativaException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
