package org.java.fase2final_manejo.models;

public class IndexEntry {
    private final String vin;
    private final int start;
    private final int length;
    private int nextByteOffset;

    public IndexEntry(String vin, int start, int length, int nextByteOffset) {
        this.vin = vin;
        this.start = start;
        this.length = length;
        this.nextByteOffset = nextByteOffset;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public void setNextByteOffset(int nextByteOffset) {
        this.nextByteOffset = nextByteOffset;
    }

    @Override
    public String toString() {
        return vin + "," + start + "," + length + "," + nextByteOffset;
    }
}
