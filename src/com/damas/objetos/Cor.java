package com.damas.objetos;

public enum Cor {
    BRANCA {
        public int getSentido() {
            return 1;
        }

    },
    VERMELHA {
        public int getSentido() {
            return -1;
        }
    };

    public abstract int getSentido();
}
