package com.fidelidad;

public enum Nivel {
    BRONCE {
        public int bono(int base) { return 0; }
    },
    PLATA {
        public int bono(int base) { return (int) (base * 0.05); }
    },
    ORO {
        public int bono(int base) { return (int) (base * 0.10); }
    },
    PLATINO {
        public int bono(int base) { return (int) (base * 0.15); }
    };

    public abstract int bono(int base);
}
