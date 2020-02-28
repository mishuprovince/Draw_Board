package com.example.drawboard;

public class Paints {
    private int widths;
    private int colors;

    public Paints(int widths, int colors) {
        this.widths = widths;
        this.colors = colors;
    }

    public int getWidths() {
        return widths;
    }

    public void setWidths(int widths) {
        this.widths = widths;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }
}
