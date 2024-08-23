package com.damas.objetos;

import java.util.ArrayList;

public class Dama extends Pedra{

    public Dama(Casa casa, String nomeImage, Cor cor) {
        super(casa, nomeImage, cor);
    }

    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs((destino.getX() - super.getCasa().getX()));
        int distanciaY = Math.abs((destino.getY() - super.getCasa().getY()));

        return distanciaX == distanciaY;
    }

    public boolean podePercorrer(Tabuleiro tabuleiro, int deltaX, int deltaY) {
        int x = super.getCasa().getX();
        int y = super.getCasa().getY();
        int pecasSeguidasNoCaminho = 0;

        while (!((x == -1 || x == 8) || (y == -1 || y == 8))) {
            x += deltaX;
            y += deltaY;

            try {
                Peca pecaAtual = tabuleiro.getCasa(x, y).getPeca();

                if (pecaAtual != null) {
                    pecasSeguidasNoCaminho += 1;

                    if (pecaAtual.getCor() == super.getCor()) {
                        return false;
                    }
                } else {
                    // Se houver uma peça capturável no caminho, permite o movimento
                    if (pecasSeguidasNoCaminho == 1) {
                        return true;
                    }

                    // Se houver mais de uma peça no caminho, movimento é inválido
                    if (pecasSeguidasNoCaminho == 2) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public boolean simularMovimento(int distanciaX, int distanciaY, int sentidoX, int sentidoY, Tabuleiro tabuleiro, Casa destino, ArrayList<Casa> pecasAComer) {
        //PERCORRER AS CASAS E VERIFICAR:
        // 1 - SE HÁ MAIS DE UMA PEÇA SEGUIDA NO CAMINHO (VERDADEIRO RETORNA FALSO)
        // 2 - SE HÁ UMA PEÇA NO CAMINHO E É DA MESMA COR (VERDADEIRO RETORNA FALSO)
        int i = super.getCasa().getX();
        int j = super.getCasa().getY();
        int casasComPecaSeguidas = 0;

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = tabuleiro.getCasa(i, j);
            Peca pecaAlvo = alvo.getPeca();

            if (pecaAlvo != null) {
                casasComPecaSeguidas += 1;

                if(this.getCor() == pecaAlvo.getCor()) {
                    if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                    return false;
                }
            } else {

                // VE SE HÁ PEÇA PARA COMER NO CAMINHO E PASSAR A CASA À COLEÇÃO pecasAComer() PARA DEPOIS COME-LAS
                if (casasComPecaSeguidas == 1) {
                    Casa casa = tabuleiro.getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                if (pecasAComer.size() > 0) pecasAComer.removeAll(pecasAComer);
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean podeSerDama() {return false;}
    public void transformarDama(){}
}
