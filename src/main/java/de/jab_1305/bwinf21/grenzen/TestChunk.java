package de.jab_1305.bwinf21.grenzen;

public class TestChunk {

    public TestChunk(int x, int z) {
        X = x;
        Z = z;
    }

    private int X;
    private int Z;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }
}
