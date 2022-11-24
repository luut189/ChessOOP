package gfx;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import engine.Board;

public class MouseHandler extends MouseAdapter {

    private Board board;

    private Renderer render;
    private int size;

    public MouseHandler(Renderer render, Board board, int windowSize) {
        this.board = board;
        
        this.render = render;
        this.size = windowSize/8;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {

        } else if(SwingUtilities.isRightMouseButton(e)) {
            
        }
    }

}
