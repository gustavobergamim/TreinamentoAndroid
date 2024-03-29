package br.com.cast.treinamento.domain;

import java.io.Serializable;

public class Contato implements IEntidade, Serializable {

    private static final long serialVersionUID = 1478888211661368723L;

    private Long id;
    private String nome;
    private String endereco;
    private String site;
    private String telefone;
    private Float avaliacao;
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Contato) {
            return getId().equals(((Contato) o).getId());
        }
        return super.equals(o);
    }
}
