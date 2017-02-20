package br.com.mp.livro.hq.repository;

import java.util.List;

import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.model.Tipo;
import br.com.mp.util.RegraNegocioException;

public interface HQs {
	
	List<HQ> todos();
	List<HQ> todosPorTipoVirtual();
	List<HQ> todosPorTipo(Tipo tipo);
	HQ porId(Long id);
	
	HQ salvar(HQ hq) throws RegraNegocioException;
	void remover(HQ hq) throws RegraNegocioException;
}
