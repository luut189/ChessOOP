package engine;

import java.util.HashMap;

import engine.utils.Flag;

public class Board {

    private Piece[][] gameBoard;

    private boolean hasSelectedStart;
    private int selectedStartRank, selectedStartFile;
    private int selectedTargetRank, selectedTargetFile;

    private int playerToMove;
    private boolean isWhiteToMove;
    
    private boolean hasCaptured;
    private Piece capturedPiece;

    private boolean hasEnPassant;
    private int enPassantRank;
    private int enPassantFile;

    private int previousEPRank;
    private int previousEPFile;

    private int halfmoves;
    private int tempHalfmoves;
    private int fullmoves;

    public Board(String fen) {
        this.hasSelectedStart = false;
        this.selectedStartRank = -1;
        this.selectedStartFile = -1;

        this.selectedTargetRank = -1;
        this.selectedTargetFile = -1;
        
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
                    file += Character.getNumericValue(symbol);
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
        return this.playerToMove;
    }

    public void setPlayerToMove() {
        this.playerToMove = isWhiteToMove ? Piece.White : Piece.Black;
    }

	public Piece[][] getGameBoard() {
        return this.gameBoard;
    }

    public Piece getPieceAtPosition(int rank, int file) {
        return this.gameBoard[rank][file];    
    }

    public void setPieceAtPosition(int rank, int file, Piece piece) {
        this.gameBoard[rank][file] = piece;
        this.gameBoard[rank][file].setRank(rank);
        this.gameBoard[rank][file].setFile(file);
    }

    public boolean verifyMove(Move move) {
        return move.getTargetRank() == selectedTargetRank && move.getTargetFile() == selectedTargetFile;
    }

    public void makeMove(Move move) {
        int startRank = move.getStartRank();
        int startFile = move.getStartFile();

        int targetRank = move.getTargetRank();
        int targetFile = move.getTargetFile();

        Piece movingPiece = getPieceAtPosition(startRank, startFile);
        Piece targetPiece = getPieceAtPosition(targetRank, targetFile);

        if(targetPiece != null && movingPiece.getPieceColor() == targetPiece.getPieceColor()) {
            deselectTargetPosition();
            return;
        }
        
        this.capturedPiece = targetPiece;

        setPieceAtPosition(startRank, startFile, new Piece(startRank, startFile, Piece.None));
        setPieceAtPosition(targetRank, targetFile, movingPiece);

        isWhiteToMove = !isWhiteToMove;
        setPlayerToMove();

        deselectStartPosition();
    }

    // Getter and Setter for selected start square
    public int getSelectedStartRank() {
		return selectedStartRank;
	}

	public int getSelectedStartFile() {
		return selectedStartFile;
	}

	public void selectStartPosition(int rank, int file) {
        deselectTargetPosition();

        // If user clicked the same square, then deselect the square
        if(this.selectedStartRank == rank && this.selectedStartFile == file) {
            deselectStartPosition();
            return;
        }
		this.selectedStartRank = rank;
        this.selectedStartFile = file;
        
        if(getPieceAtPosition(rank, file) == null) return;
        
        this.hasSelectedStart = getPieceAtPosition(rank, file).getPieceColor() == playerToMove;
	}

    public void deselectStartPosition() {
		this.selectedStartRank = -1;
        this.selectedStartFile = -1;
        this.hasSelectedStart = false;
	}
    
    public boolean hasSelectedStart() {
        return this.hasSelectedStart;
    }

    // Getter and Setter for selected target square
	public int getSelectedTargetRank() {
		return selectedTargetRank;
	}

	public int getSelectedTargetFile() {
		return selectedTargetFile;
	}

	public void selectTargetPosition(int rank, int file) {
        if(this.selectedStartRank == rank && this.selectedStartFile == file) {
            deselectStartPosition();
            return;
        }
		this.selectedTargetRank = rank;
        this.selectedTargetFile = file;

        // Testing (Will be check with actual move later when I have the move generator up and running)
        makeMove(new Move(selectedStartRank, selectedStartFile, selectedTargetRank, selectedTargetFile, Flag.NONE));
	}

    public void deselectTargetPosition() {
		this.selectedTargetRank = -1;
        this.selectedTargetFile = -1;
	}

    // Getter for variables (Not in used)
    public boolean hasCaptured() {
        return hasCaptured;
    }

    public Piece getCapturedPiece() {
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

    // Not really in used
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n     ");
        for(int i = 0;i < 8; i++) {
            sb.append("= ");
        }
        sb.append("\n");
        for(int rank = 0; rank < 8; rank++) {
            sb.append(Math.abs(rank-8) + "  | ");
            for(int file = 0; file < 8; file++) {
                Piece piece = this.gameBoard[rank][file];
                int pieceColor = piece.getPieceColor();
                String pieceType = Piece.pieceTypes[piece.getPieceType()];
                boolean isWhite = pieceColor == Piece.White;
                sb.append((isWhite ? pieceType : pieceType.toLowerCase()) + " ");
            }
            sb.append("|\n");
        }
        sb.append("     ");
        for(int i = 0;i < 8; i++) {
            sb.append("= ");
        }
        sb.append("\n     ");
        for(int i = 0; i < 8; i++) {
            sb.append(Character.toString(i+97) + " ");
        }
        return sb.toString();
    }
}