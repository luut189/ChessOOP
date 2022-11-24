package gfx;

import javax.swing.JFrame;

import engine.Board;

public class GUI extends JFrame {
    
    public GUI(Board board, String title, int windowSize) {
        Renderer render = new Renderer(board, windowSize);

        this.setTitle(title);
        this.add(render);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}