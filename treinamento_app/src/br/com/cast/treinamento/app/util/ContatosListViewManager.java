package br.com.cast.treinamento.app.util;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.widget.ListView;
import br.com.cast.treinamento.adapters.ContatoAdapter;
import br.com.cast.treinamento.app.ContatoActivity;
import br.com.cast.treinamento.domain.Contato;
import br.com.cast.treinamento.service.ContatoService;

public class ContatosListViewManager {

    private final Activity contexto;
    private final ListView listViewContatos;
    private final ContatoService service;

    private Contato contato;
    private Contato filtro;

    public ContatosListViewManager(Activity contexto,
            ListView listViewContatos, ContatoService service,
            Contato filtro) {
        this.contexto = contexto;
        this.listViewContatos = listViewContatos;
        this.service = service;
        this.filtro = filtro;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Contato getContato() {
        return contato;
    }
    
    public boolean isContatoSelecionado(){
        return contato != null;
    }

    public void carregarListView() {
        listViewContatos.setAdapter(criarAdapter());
    }

    private ContatoAdapter criarAdapter() {
        return new ContatoAdapter(contexto, carregarContatos());
    }

    private List<Contato> carregarContatos() {
        return service.pesquisar(filtro);
    }

    public void editar() {
        navegarTelaEdicao(getContato());
    }

    public void excluir() {
        service.excluir(getContato());
        carregarListView();
    }

    public void navegarTelaEdicao() {
        navegarTelaEdicao(null);
    }

    public void navegarTelaEdicao(Contato contato) {
        Intent intent = new Intent(contexto, ContatoActivity.class);
        if (contato != null) {
            intent.putExtra(ContatoActivity.CONTATO_EDICAO, contato);
        }
        contexto.startActivity(intent);
    }
}
