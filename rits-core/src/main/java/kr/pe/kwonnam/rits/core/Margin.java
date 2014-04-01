package kr.pe.kwonnam.rits.core;

public class Margin {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Margin(int marginTopBottom, int marginLeftRight) {
        this(marginTopBottom, marginLeftRight, marginTopBottom, marginLeftRight);
    }

    public Margin(int top, int right, int bottom, int left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Margin margin = (Margin) o;

        if (bottom != margin.bottom)
            return false;
        if (left != margin.left)
            return false;
        if (right != margin.right)
            return false;
        if (top != margin.top)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = top;
        result = 31 * result + bottom;
        result = 31 * result + left;
        result = 31 * result + right;
        return result;
    }

    @Override
    public String toString() {
        return "Margin{" + "top=" + top + ", bottom=" + bottom + ", left=" + left + ", right=" + right + '}';
    }
}
