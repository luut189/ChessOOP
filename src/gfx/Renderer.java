package gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import engine.Board;
import engine.Piece;

public class Renderer extends JPanel {

    private int windowSize, size;
    private Board board;

    private final Color lightColor = new Color(255, 237, 213);
    private final Color darkColor = new Color(115, 82, 71);

    public Renderer(Board board, int windowSize) {
        this.windowSize = windowSize/8*8;
        this.size = windowSize/8;

        this.board = board;

        this.setPreferredSize(new Dimension(this.windowSize, this.windowSize));
        this.addMouseListener(new MouseHandler(this, board, windowSize));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawSelected(g);
        drawPieces(this.board, g);
    }

    public void drawPieces(Board board, Graphics g) {
        for(int rank = 0; rank < 8; rank++) {
            for(int file = 0; file < 8; file++) {
                if(board.getPieceAtPosition(rank, file) != null) {
                    g.drawImage(board.getPieceAtPosition(rank, file).getImage(), file*size, rank*size, size, size, null);
                }
            }
        }
    }

    public void drawSelected(Graphics g) {
        int selectedStartRank = board.getSelectedStartRank();
        int selectedStartFile = board.getSelectedStartFile();

        if(selectedStartRank != -1 && selectedStartFile != -1) {
            Color rightColor = new Color(0, 0, 255, 75);
            Color wrongColor = new Color(255, 0, 0, 75);
            Piece piece = board.getPieceAtPosition(selectedStartRank, selectedStartFile);
            if(piece != null) {
                g.setColor(piece.isColorToMove(board) ? rightColor : wrongColor);
            } else {
                g.setColor(wrongColor);
            }
            g.fillRect(selectedStartFile*size, selectedStartRank*size, size, size);
        }

        // Visualize the selected target (Won't be used later on)
        int selectedTargetRank = board.getSelectedTargetRank();
        int selectedTargetFile = board.getSelectedTargetFile();

        if(selectedTargetRank != -1 && selectedTargetFile != -1) {
            g.setColor(new Color(233, 0, 234, 50));
            g.fillRect(selectedTargetFile*size, selectedTargetRank*size, size, size);
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