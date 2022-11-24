package gfx;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import engine.Board;

public class MouseHandler extends MouseAdapter {

    Board board;

    public MouseHandler(Board board) {
        this.board = board;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {

        } else if(SwingUtilities.isRightMouseButton(e)) {
            
        }
    }

}
