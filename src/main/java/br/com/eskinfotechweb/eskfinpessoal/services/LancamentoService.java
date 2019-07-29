package br.com.eskinfotechweb.eskfinpessoal.services;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.eskinfotechweb.eskfinpessoal.domain.Lancamento;
import br.com.eskinfotechweb.eskfinpessoal.domain.Pessoa;
import br.com.eskinfotechweb.eskfinpessoal.domain.Usuario;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoria;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaCategoriaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaDiaTipo;
import br.com.eskinfotechweb.eskfinpessoal.dto.LancamentoEstatisticaPessoa;
import br.com.eskinfotechweb.eskfinpessoal.mail.Mailer;
import br.com.eskinfotechweb.eskfinpessoal.repositories.LancamentoRepository;
import br.com.eskinfotechweb.eskfinpessoal.repositories.PessoaRepository;
import br.com.eskinfotechweb.eskfinpessoal.repositories.UsuarioRepository;
import br.com.eskinfotechweb.eskfinpessoal.repositories.filter.LancamentoFilter;
import br.com.eskinfotechweb.eskfinpessoal.repositories.lancamentos.projection.ResumoLancamento;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.DataIntegrityException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.ObjectNotFoundException;
import br.com.eskinfotechweb.eskfinpessoal.services.exceptions.PessoaInexistenteOuInativaException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTOS";

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Mailer mailer;

	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}

	public Lancamento findById(Long id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Lancamento.class.getName()));
	}

	public List<Lancamento> findVencidos(LocalDate data) {
		return lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(data);
	}

	public List<Lancamento> search(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.search(lancamentoFilter);
	}

	public Page<Lancamento> page(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.page(lancamentoFilter, pageable);
	}

	public Page<ResumoLancamento> resum(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resum(lancamentoFilter, pageable);
	}

	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate dataDe, LocalDate dataAte) {
		return lancamentoRepository.porCategoria(dataDe, dataAte);
	}

	public List<LancamentoEstatisticaDiaTipo> porDiaTipo(LocalDate dataDe, LocalDate dataAte) {
		return lancamentoRepository.porDiaTipo(dataDe, dataAte);
	}

	public List<LancamentoEstatisticaCategoriaTipo> porCategoriaTipo(LocalDate dataDe, LocalDate dataAte) {
		return lancamentoRepository.porCategoriaTipo(dataDe, dataAte);
	}

	public Lancamento insert(Lancamento lancamento) {
		validarPessoa(lancamento);
		lancamento.setId(null);
		Lancamento lancamentoInsert = lancamentoRepository.save(lancamento);
		return lancamentoInsert;
	}

	private void validarPessoa(Lancamento lancamento) {
		if (!pessoaRepository.existsById(lancamento.getPessoa().getId())) {
			throw new PessoaInexistenteOuInativaException("Pessoa Inexistente");
		}
		Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getId());
		if (pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException("Pessoa Inativa");
		}
	}

	public Lancamento update(Long id, Lancamento lancamento) {
		Lancamento lancamentoUpdate = findById(id);
		if (!lancamento.getPessoa().getId().equals(lancamentoUpdate.getPessoa().getId())) {
			validarPessoa(lancamento);
		}
		BeanUtils.copyProperties(lancamento, lancamentoUpdate, "id");
		return lancamentoRepository.save(lancamentoUpdate);
	}

	public void delete(Long id) {
		Lancamento lancamento = findById(id);
		try {
			lancamentoRepository.delete(lancamento);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas! Id: " + id
					+ ", Tipo: " + Lancamento.class.getName());
		}
	}

	public byte[] relatorioPorPessoa(LocalDate dt_inicio, LocalDate dt_fim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(dt_inicio, dt_fim);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(dt_inicio));
		parametros.put("DT_FIM", Date.valueOf(dt_fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));

		return JasperExportManager.exportReportToPdf(jasperPrint);

	}

	// @Scheduled(fixedDelay = 1000 * 60)
	@Scheduled(cron = "0 59 09 * * *")
	public void avisarSobreLancamentosVencidos() {
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());

		List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);

		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);

		System.out.println("Método de aviso sobre lançamentos vencidos executado com sucesso!");

	}

}
