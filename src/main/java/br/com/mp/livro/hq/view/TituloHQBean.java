package br.com.mp.livro.hq.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import br.com.mp.livro.hq.model.HQ;
import br.com.mp.livro.hq.model.TituloHQ;
import br.com.mp.livro.hq.repository.HQs;
import br.com.mp.livro.hq.repository.TitulosHQs;
import br.com.mp.util.RegraNegocioException;

@Named(value = "tituloHQBean")
@ViewScoped
public class TituloHQBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TitulosHQs titulosHQs;
	@Inject
	private HQs hqs;
	
	private TituloHQ tituloHQ;
	
	private DualListModel<HQ> dualListasHQs;
	
	private List<HQ> todosHQs;
	
	private TreeNode raiz;
	private TreeNode treeSelecionado;
	
	public void init() {
		this.tituloHQ = new TituloHQ();
		this.todosHQs = new ArrayList<HQ>();
		
		this.atualizarArvore();
		this.dualListasHQs = new DualListModel<HQ>();
	}
	
	public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((HQ) item).getNome()).append("<br />");
        }        
    }
	
	public void adicionarFilho() {
		if(this.treeSelecionado != null) {
			this.dualLista();
			this.tituloHQ = new TituloHQ();
			TituloHQ tituloHQPai = ((TituloHQ) treeSelecionado.getData()); 
			this.tituloHQ.setTituloHQPai(tituloHQPai);
		}
	}
	
	public void editar() {
		if(this.treeSelecionado != null) {
			this.dualLista();
			TituloHQ tituloHQSelecionado = ((TituloHQ) treeSelecionado.getData());
			
			this.tituloHQ = this.titulosHQs.todosHQsPorTituloHQ(tituloHQSelecionado);
			List<HQ> listaHQs = this.tituloHQ.getHqs();
		
			this.dualListasHQs.setTarget(listaHQs);
		}
	}
	
	public void salvar() {
		this.tituloHQ.removeTodosHQs();
		
		List<HQ> novaListaHQs = this.dualListasHQs.getTarget();
		this.tituloHQ.adicionarHQs(novaListaHQs);
		
		try {
			this.titulosHQs.salvar(this.tituloHQ);
		
			this.atualizarArvore();
			this.raiz.setExpanded(true);
			
			this.tituloHQ = new TituloHQ();
			this.todosHQs = new ArrayList<HQ>();
			
			Messages.addFlashGlobalInfo("O titulo HQ foi salvo com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Não foi possivel salvar titulo HQ!");
		}		
	}
	
	public void excluir() {		
		try {
			TituloHQ tituloHQSelecionado = ((TituloHQ) treeSelecionado.getData());
			this.titulosHQs.remover(tituloHQSelecionado);
			this.atualizarArvore();
			
			Messages.addFlashGlobalInfo("Titulo HQ excluido com sucesso!");
		} catch (RegraNegocioException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um error ao excluir titulo HQ!");
		}
	}
	
	public void mostrarListaHQs(NodeSelectEvent event) {
		if(this.treeSelecionado != null && !treeSelecionado.getData().equals("HQs")) {
			TituloHQ tituloHQSelecionado = ((TituloHQ) treeSelecionado.getData());
			
			this.todosHQs = (this.titulosHQs.todosHQsPorTituloHQ(tituloHQSelecionado)).getHqs();
		}
	}
	
    private void dualLista() {
		List<HQ> todosHQsOrigem = this.hqs.todosPorTipoVirtual();
		List<HQ> todosHQsAlvo = new ArrayList<HQ>();
		
		this.dualListasHQs = new DualListModel<HQ>(todosHQsOrigem, todosHQsAlvo);
	}
    
    private void atualizarArvore() {
    	List<TituloHQ> titulosHQsRaizes = titulosHQs.raizes();
		this.raiz = new DefaultTreeNode("HQs", null);
		this.adicionarNos(titulosHQsRaizes, this.raiz);
    }
    
	private void adicionarNos(List<TituloHQ> titulosHQs, TreeNode pai) {
		for (TituloHQ tituloHQ : titulosHQs) {
			TreeNode no = new DefaultTreeNode(tituloHQ, pai);
			if(tituloHQ.getTitulosHQFilhos() != null)
				adicionarNos(tituloHQ.getTitulosHQFilhos(), no);
		}
	}
	
	public TituloHQ getTituloHQ() {
		return tituloHQ;
	}
	
	public void setTituloHQ(TituloHQ tituloHQ) {
		this.tituloHQ = tituloHQ;
	}
	
	public DualListModel<HQ> getDualListasHQs() {
		return dualListasHQs;
	}
	
	public void setDualListasHQs(DualListModel<HQ> dualListasHQs) {
		this.dualListasHQs = dualListasHQs;
	}
	
	public List<HQ> getTodosHQs() {
		return todosHQs;
	}
	
	public TreeNode getRaiz() {
		return raiz;
	}
	
	public TreeNode getTreeSelecionado() {
		return treeSelecionado;
	}
	
	public void setTreeSelecionado(TreeNode treeSelecionado) {
		this.treeSelecionado = treeSelecionado;
	}
}
