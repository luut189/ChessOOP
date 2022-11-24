package gfx;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import engine.Board;

public class MouseHandler extends MouseAdapter {

    private class Position {
        private int rank;
        private int file;

        Position(int rank, int file) {
            this.rank = rank;
            this.file = file;
        }

		public int getRank() {
			return rank;
		}

		public int getFile() {
			return file;
		}
    }

    private Board board;

    private Renderer render;
    private int size;

    public MouseHandler(Renderer render, Board board, int windowSize) {
        this.board = board;
        
        this.render = render;
        this.size = windowSize/8;
    }

    public Position getPosition(MouseEvent e) {
        return new Position(e.getY()/this.size, e.getX()/this.size);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            Position pos = getPosition(e);
            this.board.setSelectedStartPosition(pos.getRank(), pos.getFile());
            this.render.repaint();
        } else if(SwingUtilities.isRightMouseButton(e)) {
            
        }
    }

}