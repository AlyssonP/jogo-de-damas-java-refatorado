package com.damas.objetos;

public class VezManage {
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private Jogador jogadorVez;

    public VezManage(Jogador jogadorUm, Jogador jogadorDois) {
        this.jogadorUm = jogadorUm;
        this.jogadorDois = jogadorDois;
        this.jogadorVez = jogadorUm;
    }

    public Jogador getJogadorVez() {
        return jogadorVez;
    }

    public void trocarJogadorVez() {
        jogadorVez = (jogadorVez == jogadorUm) ? jogadorDois : jogadorUm;
    }

    // Diminuido acoplamento
    public void addPontoJogadorVez(int pontos) {
        jogadorVez.addPonto(pontos);
    }

    public String getNomeJogadorVez() {
        return jogadorVez.getNome();
    }
}
