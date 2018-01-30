package engine;

public class Move {

    int x = 0, y = 0;

    IPieceInfo pieceInfo;

    public Move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
