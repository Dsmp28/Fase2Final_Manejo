package org.java.fase2final_manejo.models;

public class IndexRecord {
    private final String name;
    private final int startIndex;
    private final int length;

    public IndexRecord(String name, int startIndex, int length) {
        this.name = name;
        this.startIndex = startIndex;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getLength() {
        return length;
    }
}

