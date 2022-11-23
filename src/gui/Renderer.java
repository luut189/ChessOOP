package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import engine.Board;
import engine.Piece;

public class Renderer extends JPanel {

    private int width, height, size;
    private Board board;

    private final Color lightColor = new Color(255, 237, 213);
    private final Color darkColor = new Color(115, 82, 71);

    public Renderer(Board board, int width, int height) {
        this.width = width/8*8;
        this.height = height/8*8;
        this.size = width/8;

        this.board = board;

        setPreferredSize(new Dimension(this.width, this.height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawPieces(this.board, g);
    }

    public void drawPieces(Board board, Graphics g) {
        Piece[][] game = board.getGameBoard();

        for(int rank = 0; rank < 8; rank++) {
            for(int file = 0; file < 8; file++) {
                g.drawImage(game[rank][file].getImage(), file*size, rank*size, size, size, null);
            }
        }
    }

    public void drawBoard(Graphics g) {
        for(int rank = 0; rank < 8; rank++) {
            for(int file = 0; file < 8; file++) {
                boolean isLight = (file + rank) % 2 == 0;
                g.setColor((isLight) ? lightColor : darkColor);
                g.fillRect(file*size, rank*size, size, size);
            }
        }
    }
    
}