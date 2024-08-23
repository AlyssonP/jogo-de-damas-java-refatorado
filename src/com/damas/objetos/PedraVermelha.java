package com.damas.objetos;

public class PedraVermelha extends Pedra{

    public PedraVermelha(Casa casa) {
        super(casa, "pedra_vermelha", Cor.VERMELHA);
    }

    public boolean podeSerDama() {
        return getCasa().getY() == 0;
    }

    public void transformarDama() {
        super.getCasa().colocarPeca(new Dama(super.getCasa(), "dama_vermelha", super.getCor()));
    }
}
