public interface GameBoard {
    public abstract char[][] newRandBoard();
    public abstract void printBoard();
    public abstract char[][] getSolution();
    public abstract char getValue(int x, int y);
}