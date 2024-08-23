package com.damas.objetos;

public class PedraBranca extends Pedra{

    public PedraBranca(Casa casa) {
        super(casa, "pedra_branca", Cor.BRANCA);
    }

    public boolean podeSerDama() {
        return super.getCasa().getY() == 7;
    }

    public void transformarDama() {
        super.getCasa().colocarPeca(new Dama(super.getCasa(), "dama_branca", super.getCor()));
    }

}
