import engine.Board;
import gfx.GUI;

public class App {

    final static int WINDOW_SIZE = 800;

    final static String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - 0 1";
    final static String testFEN = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w - 0 1";

    public static void main(String[] args) {
        Board board = new Board(startFEN);
        new GUI(board, "Chess", WINDOW_SIZE, WINDOW_SIZE);
    }
}
