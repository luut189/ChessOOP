package engine;

import java.util.HashMap;

public class Board {

    private Piece[][] gameBoard;

    private int playerToMove;
    private boolean isWhiteToMove;
    
    private boolean hasCaptured;
    private int capturedPiece;

    private boolean hasEnPassant;
    private int enPassantRank;
    private int enPassantFile;

    private int previousEPRank;
    private int previousEPFile;

    private int halfmoves;
    private int tempHalfmoves;
    private int fullmoves;

    public Board(String fen) {
        initFEN(fen);
    }

    public void initFEN(String fen) {
        gameBoard = new Piece[8][8];

        // A look up table for symbols to chess pieces
        HashMap<Character, Integer> symbolToPiece = new HashMap<>();
        symbolToPiece.put('k', Piece.K);
        symbolToPiece.put('q', Piece.Q);
        symbolToPiece.put('r', Piece.R);
        symbolToPiece.put('b', Piece.B);
        symbolToPiece.put('n', Piece.N);
        symbolToPiece.put('p', Piece.P);

        int file = 0, rank = 0;

        // Split the string into segments
        String splittedFen[] = fen.split(" ");

        // Put the pieces into the board
        for(char symbol : splittedFen[0].toCharArray()) {
            if(symbol == '/') {
                file = 0;
                rank++;
            } else {
                if(Character.isDigit(symbol)) {
                    int time = Character.getNumericValue(symbol);
                    for(int i = 0; i < time; i++) {
                        this.gameBoard[rank][file] = new Piece(rank, file, Piece.None);
                        file++;
                    }
                } else {
                    int pieceColor = Character.isUpperCase(symbol) ? Piece.White : Piece.Black;
                    int pieceType = symbolToPiece.get(Character.toLowerCase(symbol));
                    this.gameBoard[rank][file] = new Piece(rank, file, pieceColor | pieceType);
                    file++;
                }
            }
        }
        
        // Get the player to move
        this.playerToMove = splittedFen[1].equals("w") ? Piece.White : Piece.Black;
        this.isWhiteToMove = playerToMove == Piece.White;

        // Get the En passant position if there is any
        if(!splittedFen[2].equals("-")) {
            this.hasEnPassant = true;
            this.enPassantRank = Math.abs(8 - Integer.parseInt(String.valueOf(splittedFen[2].charAt(1))));
            this.enPassantFile = (int) splittedFen[2].charAt(0) - 97;
        } else {
            this.hasEnPassant = false;
            this.enPassantRank = -1;
            this.enPassantFile = -1;
        }

        // Get the halfmoves and fullmoves clock
        this.halfmoves = Integer.parseInt(splittedFen[3]);
        this.fullmoves = Integer.parseInt(splittedFen[4]);
    }

    public int getPlayerToMove() {
        return isWhiteToMove ? Piece.White : Piece.Black;
    }

    public Piece[][] getGameBoard() {
        return this.gameBoard;
    }

    public boolean isHasCaptured() {
        return hasCaptured;
    }

    public int getCapturedPiece() {
        return capturedPiece;
    }

    public boolean hasEnPassant() {
        return hasEnPassant;
    }

    public int getEnPassantRank() {
        return enPassantRank;
    }

    public int getEnPassantFile() {
        return enPassantFile;
    }

    public int getPreviousEPRank() {
        return previousEPRank;
    }

    public int getPreviousEPFile() {
        return previousEPFile;
    }

    public int getHalfmoves() {
        return halfmoves;
    }

    public int getTempHalfmoves() {
        return tempHalfmoves;
    }

    public int getFullmoves() {
        return fullmoves;
    }

}
