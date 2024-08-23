package com.damas.objetos;

import java.util.ArrayList;

public abstract class Pedra implements Peca {
    private Casa casa;
    private String nomeImagem;
    private Cor cor;

    public Pedra(Casa casa, String nomeImagem, Cor cor) {
        this.casa = casa;
        this.nomeImagem = nomeImagem;
        this.cor = cor;
        casa.colocarPeca(this);
    }

    public Casa getCasa() {
        return casa;
    }

    @Override
    public String getNomeImagem() {
        return nomeImagem;
    }

    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    public boolean isMovimentoValido(Casa destino) {
        // SENTIDO UNITÁRIO E DISTANCIA X E Y DA CASA ATUAL ATÉ A CASA DE DESTINO
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        // REGRA DE MOVIMENTO NO CASO DA DISTÂNCIA SER DE 2 CASAS (MOVIMENTO DE COMER PEÇA)
        return (distanciaX <= 2 || distanciaY <= 2) && (distanciaX == distanciaY);
    }

    public abstract boolean podeSerDama();

    public abstract void transformarDama();

    public boolean podeMover(int distanciaX, int distanciaY, int sentidoY) {
        return (distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == cor.getSentido();
    }

    @Override
    public Cor getCor() {
        return cor;
    }

    @Override
    public boolean podePercorrer(Tabuleiro tabuleiro, int deltaX, int deltaY) {
        int x = casa.getX();
        int y = casa.getY();
        x += deltaX;
        y += deltaY;

        try {
            Peca pecaAtual = tabuleiro.getCasa(x, y).getPeca();

            if (pecaAtual != null) {
                // Verifica se há uma peça no destino e se a casa após a peça está livre para uma captura
                if (tabuleiro.getCasa((x + deltaX), (y + deltaY)).getPeca() != null) {
                    return false; // Há uma peça bloqueando o caminho
                }
                // Verifica se a peça no caminho é da mesma cor
                if (pecaAtual.getCor() == this.cor) {
                    return false; // A peça no caminho é da mesma cor
                }
                return true; // Captura válida
            }
        } catch (IndexOutOfBoundsException e) {
            return false; // Movimento fora dos limites do tabuleiro
        }

        return false; // Sem peça para capturar, movimento inválido
    }

    public boolean simularMovimento(int distanciaX, int distanciaY, int sentidoX, int sentidoY, Tabuleiro tabuleiro, Casa destino, ArrayList<Casa> pecasAComer) {
        if ((distanciaX == 2 && distanciaY == 2)) {
            Casa casa = tabuleiro.getCasa((destino.getX() - sentidoX), (destino.getY() - sentidoY));
            if (casa.getPeca() == null) return false;
        } else {
            // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 1 BLOCO
            return this.podeMover(distanciaX, distanciaY, sentidoY);
        }
        return true;
    }
}
