package engine;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Piece {

    public final static int None = 0;
    public final static int K = 1;
    public final static int Q = 2;
    public final static int R = 3;
    public final static int B = 4;
    public final static int N = 5;
    public final static int P = 6;
    
    public final static String[] pieceTypes = {"-", "K", "Q", "R", "B", "N", "P"};

    public final static int White = 8;
    public final static int Black = 16;

    // Binary mask used to decode the piece
    private final static int typeMask = 0b00111;
    private final static int colorMask = 0b11000;
    
    private int rank, file, piece;

    public Piece(int rank, int file, int piece) {
        this.rank = rank;
        this.file = file;
        this.piece = piece;
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }

    public void setRank(int newRank) {
        this.rank = newRank;
    }

    public void setFile(int newFile) {
        this.file = newFile;
    }

    public int getPieceType() {
        return this.piece & typeMask;
    }

    public int getPieceColor() {
        return this.piece & colorMask;
    }

    public boolean isColorToMove(Board board) {
        return getPieceColor() == board.getPlayerToMove();
    }

    public Image getImage() {
        String color = getPieceColor() == White ? "W" : "B";
        String type = pieceTypes[getPieceType()];

        return new ImageIcon("../res/" + color + type + ".png").getImage();
    }
}