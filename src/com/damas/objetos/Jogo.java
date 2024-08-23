package com.damas.objetos;

import java.util.ArrayList;

/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 * @author José Alisson Rocha da Silva {@link jose.alisson2@academico.ufpb.br}
 */
public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private VezManage vezManage;
    private MovimentoManage movimentoManage;

    private int jogadas = 0;
    private int jogadasSemComerPeca = 0;
    private ArrayList<Casa> pecasAComer;
    private Casa casaBloqueadaOrigem;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        pecasAComer = new ArrayList<Casa>();
        jogadorUm = new Jogador("player branco", Cor.BRANCA);
        jogadorDois = new Jogador("player vermelho", Cor.VERMELHA);
        vezManage = new VezManage(jogadorUm, jogadorDois);
        movimentoManage = new MovimentoManage(tabuleiro, pecasAComer, vezManage);

        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        colocarPecas(tabuleiro);
    }

    private void processarJogada(Casa destino) {
        if (!pecasAComer.isEmpty()) {
            comerPecas();
            if (deveContinuarJogando(destino)) {
                casaBloqueadaOrigem = destino;
            } else {
                vezManage.trocarJogadorVez();
            }
        } else {
            jogadasSemComerPeca++;
            vezManage.trocarJogadorVez();
        }
        jogadas++;
        if (podeTransformarParaDama(destino)) {
            transformarPedraParaDama(destino);
        }
    }
    
    /**
     * Realiza uma serie de paços para comandar uma peça na posicão 
     * (origemX, origemY) fazer um movimento para (destinoX, destinoY).
     * 
     * @param origemX - {@code int} linha da Casa de origem.
     * @param origemY - {@code int} coluna da Casa de origem.
     * @param destinoX - {@code int} linha da Casa de destino.
     * @param destinoY - {@code int} coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);

        if(movimentoManage.moverPeca(origem, destino)) {
            processarJogada(destino);
        }
    }

//    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
//        Casa origem = tabuleiro.getCasa(origemX, origemY);
//        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
//        Pedra peca = origem.getPeca();
//
////        if (casaBloqueadaOrigem == null) {
////            if (peca.isMovimentoValido(destino)) {
////                if (simularMovimentoEValidar(origem, destino)) {
////                    peca.mover(destino);
////                    processarJogada(destino);
////                }
////            }
//        if ((vezManage.getJogadorVez() == jogadorUm && (peca.getTipo() == Pedra.PEDRA_BRANCA || peca.getTipo() == Pedra.DAMA_BRANCA)) ||
//            (vezManage.getJogadorVez() == jogadorDois && (peca.getTipo() == Pedra.PEDRA_VERMELHA || peca.getTipo() == Pedra.DAMA_VERMELHA))) {
//
//            if (peca.isMovimentoValido(destino)) {
//                if (simularMovimentoEValidar(origem, destino)) {
//                    peca.mover(destino);
//                    processarJogada(destino);
//                }
//            }
//        } else {
//            if (origem.equals(casaBloqueadaOrigem)) {
//                if(simularMovimentoEValidar(origem, destino)) {
//                    if (pecasAComer.size() != 0) {
//                        casaBloqueadaOrigem = null;
//                        moverPeca(origemX, origemY, destinoX, destinoY);
//                    }
//                }
//            }
//        }
//    }
//

    /**
     * <p>
     * Percorre as casas do tabuleirio a partir da casa de origem indicada no sentido dado
     * por {@code sentidoX} e {@code sentidoY} até o limite do tabuleiro.
     * </p>
     * @param origem Casa de origem da peça
     * @param deltaX {@code Tabuleiro.X_ESQUERDA} ou {@code Tabuleiro.X_DIREITA} 
     * @param deltaY {@code Tabuleiro.Y_BAIXO} ou {@code Tabuleiro.Y_CIMA}
     * @return
     * {@code false} - se não há peça para comer
     * <li> {@code false} - se houver mais de uma peça no caminho </li>
     * <li> {@code false} - se houver peça de mesma cor no caminho </li>
     * <li> {@code true} - se há peça para comer </li>
     */
    // Fazer manutenção
    /**
    private boolean percorrerEVerificar(Casa origem, int deltaX, int deltaY) {

        Peca peca = origem.getPeca();
        int x = origem.getX();
        int y = origem.getY();
        int pecasSeguidasNoCaminho = 0;

        // SE O TIPO FOR PEDRA
        if ((peca.getTipo() == Peca.PEDRA_BRANCA) || (peca.getTipo() == Peca.PEDRA_VERMELHA)) {

            x += deltaX;
            y += deltaY;

            try {

                Pedra pecaAtual = tabuleiro.getCasa(x, y).getPeca();

                //if (!(pecaAtual == null)) {
                if (pecaAtual != null) {

                    if (tabuleiro.getCasa((x + deltaX), (y + deltaY)).getPeca() != null) {
                        return false;
                    }

                    // VERIFICA SE A PEÇA NO CAMINHO É DA MESMA COR
                    if ((peca.getTipo() == Peca.PEDRA_BRANCA) &&
                        ((pecaAtual.getTipo() == Peca.DAMA_BRANCA || pecaAtual.getTipo() == Peca.PEDRA_BRANCA))) {
                            return false;
                    } else {
                        if ((peca.getTipo() == Peca.PEDRA_VERMELHA) &&
                        ((pecaAtual.getTipo() == Peca.DAMA_VERMELHA || pecaAtual.getTipo() == Peca.PEDRA_VERMELHA))) {
                            return false;
                        }
                    }

                    return true;
                }

            } catch (Exception e) {
                return false;
            }

        }

        else {
            while (!((x == -1 || x == 8) || (y == -1 || y == 8))) {
                x += deltaX;
                y += deltaY;

                try {
                    Pedra pecaAtual = tabuleiro.getCasa(x, y).getPeca();

                    if (!( pecaAtual == null)) {

                        pecasSeguidasNoCaminho += 1;

                        // VERIFICA SE HÁ ALGUMA PEÇA DO MESMO TIPO NO CAMINHO SE SIM, RETORNA FALSE;
                        if ((peca.getTipo() == Peca.DAMA_BRANCA) &&
                            ((pecaAtual.getTipo() == Peca.PEDRA_BRANCA) || (pecaAtual.getTipo() == Peca.DAMA_BRANCA))) {
                                return false;
                        } else {
                            if ((peca.getTipo() == Peca.DAMA_VERMELHA) &&
                            ((pecaAtual.getTipo() == Peca.PEDRA_VERMELHA) || (pecaAtual.getTipo() == Peca.DAMA_VERMELHA))) {
                                    return false;
                            }
                        }
                    } else {

                        if (pecasSeguidasNoCaminho == 1) {
                            return true;
                        }

                        if (pecasSeguidasNoCaminho == 2) {
                            return false;
                        }
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }*/
    private boolean percorrerEVerificar(Casa origem, int deltaX, int deltaY) {
        return origem.getPeca().podePercorrer(tabuleiro, deltaX, deltaY);
    }

    /**
     * <p>
     * Dispara o método {@code percorrerEVerificar()} no sentido
     * das quatro diagonais a partir da casa indicada.
     * </p>
     * @param origem tipo {@code Casa} de onde vai partir a verifição
     * @return {@code true} Se há peça para comer em alguma diagonal
     */
    private boolean deveContinuarJogando(Casa origem) {

        if (percorrerEVerificar(origem, Tabuleiro.X_ESQUERDA, Tabuleiro.Y_CIMA)) {
            return true;
        } else {
            
            if (percorrerEVerificar(origem, Tabuleiro.X_DIREITA, Tabuleiro.Y_CIMA)) {
                return true;
            } else {
                        
                if (percorrerEVerificar(origem, Tabuleiro.X_DIREITA, Tabuleiro.Y_BAIXO)) {
                    return true;
                } else {

                    if (percorrerEVerificar(origem, Tabuleiro.X_ESQUERDA, Tabuleiro.Y_BAIXO)) {
                        return true;
                    }                    
                }
            }

        }

        return false;
    } 

    /**
     * Limpa as peças na variável {@code ArrayList pecasAComer}, adiciona pontos ao jogador
     */
    private void comerPecas() {
        int pecasComidas = pecasAComer.size();

        vezManage.addPontoJogadorVez(pecasComidas);
//        if (getVez() == 1) jogadorUm.addPonto(pecasComidas);
//        if (getVez() == 2) jogadorDois.addPonto(pecasComidas);

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.removeAll(pecasAComer);

        jogadasSemComerPeca = 0;
    }

    /**
     * Verifica se a pedra da casa pode virar dama.
     * @param casa {@code Casa}
     * @return {@code boolean}
     */
//    private boolean podeTransformarParaDama(Casa casa) {
//        // REGRA PARA PEÇAS BRANCAS
//        if (casa.getPeca().getTipo() == Peca.PEDRA_BRANCA) {
//            if (casa.getY() == 7) return true;
//        }
//        // REGRA PARA PEÇAS VERMELHAS
//        if (casa.getPeca().getTipo() == Peca.PEDRA_VERMELHA) {
//            if (casa.getY() == 0) return true;
//        }
//        return false;
//    }
    private boolean podeTransformarParaDama(Casa casa) {
        return casa.getPeca().podeSerDama();
    }

    /**
     * Transforma a pedra da casa passada como parametro em dama
     * @param casa - tipo {@code Casa} contendo a peça a ser ser transformada.
     */
//    private void transformarPedraParaDama(Casa casa) {
//        Pedra pedra = casa.getPeca();
//
//        if (pedra.getTipo() == Peca.PEDRA_BRANCA) {
//            Dama dama = new Dama(casa, Peca.DAMA_BRANCA);
//            pedra = (Dama) dama;
//        } else {
//            Dama dama = new Dama(casa, Peca.DAMA_VERMELHA);
//            pedra = (Dama) dama;
//        }
//    }
    private void transformarPedraParaDama(Casa casa) {
        Peca peca = casa.getPeca();
        peca.transformarDama();
    }

    /**
     * Posiciona peças no tabuleiro.
     * Utilizado na inicialização do jogo.
     * @param tabuleiro - tipo {@code Tabuleiro} onde as peças serão posicionadas
     */
    public void colocarPecas(Tabuleiro tabuleiro) {

        // CRIA E PÕE AS PEÇAS NA PARTE INFERIOR DO TABULEIRO
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    new PedraBranca(casa);
                }
                
                else if ((x % 2 != 0) && (y % 2 != 0)){
                    Casa casa = tabuleiro.getCasa(x, y);
                    new PedraBranca(casa);
                }
            }

        }
        // CRIA E POE AS PEÇAS NA PARTE SUPERIOR DO TABULEIRO
        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    new PedraVermelha(casa);
                }
                else if ((x % 2 == 0) && (y % 2 == 0)) {
                    Casa casa = tabuleiro.getCasa(x, y);
                    new PedraVermelha(casa);
                }
            }
        }
    }

    /**
     * 
     * @return
     * {@code Jogador }
     * <li>null - Nenhum Ganhador</li>
     * <li>Obj Jogador - Jogador Ganhador</li>
     */
    public Jogador getGanhador() {
        if (jogadorUm.getPontos() >= 12) {
            return jogadorUm;
        } else if (jogadorDois.getPontos() >= 12) {
            return jogadorDois;
        } else {
            return null;
        }
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    
    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }

    public Casa getCasaBloqueada() {
        return casaBloqueadaOrigem;
    }

    @Override
    public String toString() {

        String retorno = "Vez: ";
        retorno += vezManage.getNomeJogadorVez();
        retorno += "\n";
//        if (getVez() == 1) {
//            retorno += jogadorUm.getNome();
//            retorno += "\n";
//        } else if (getVez() == 2) {
//            retorno += jogadorDois.getNome();
//            retorno += "\n";
//        }

        retorno += "Nº de jogadas: " + getJogada() + "\n";
        retorno += "Jogadas sem comer peça: " + getJogadasSemComerPecas() + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + jogadorUm.getNome() + "\n";
        retorno += "Pontos: " + jogadorUm.getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogadorDois.getPontos()) + "\n";
        retorno += "\n";        
        retorno += "Informações do(a) jogador(a) " + jogadorDois.getNome() + "\n";
        retorno += "Pontos: " + jogadorDois.getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogadorUm.getPontos()) + "\n";

        if (casaBloqueadaOrigem != null) {
            retorno += "\n";
            retorno += "Mova a peça na casa " + casaBloqueadaOrigem.getX() + ":" + casaBloqueadaOrigem.getY() + "!";
        }

        return retorno;
    }
}