package gfx;

import javax.swing.JFrame;

import engine.Board;

public class GUI extends JFrame {
    
    public GUI(Board board, String title, int width, int height) {
        Renderer render = new Renderer(board, width, height);

        this.setTitle(title);
        this.add(render);
        this.addMouseListener(new MouseHandler(board));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
