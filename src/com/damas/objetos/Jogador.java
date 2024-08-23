package com.damas.objetos;

/**
 * Jogador, recebe um nome e armazena os pontos
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public class Jogador {
    public static final String DEFAULT_NAME = "Anônimo";
    
    private String nome;
    private int pontos;
    private Cor cor;

    public Jogador (String nome, Cor cor) {
        this.nome = validarNome(nome) ? nome : DEFAULT_NAME;
        this.cor = cor;
        pontos = 0;

    }

    public Cor getCor() {
        return cor;
    }

    private boolean validarNome(String nome) {
        return nome.length() <= 16;
    }

    public void addPonto() {
        pontos++;
    }

    public void addPonto(int pontos) {
        this.pontos += pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(validarNome(nome)) {
            this.nome = nome;
        }
    }

}
