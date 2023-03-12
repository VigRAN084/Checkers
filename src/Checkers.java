import java.util.Scanner;

/**
 *
 */
public class Checkers {
    private Piece[] redPieces = new Piece[12];
    private Piece[] whitePieces = new Piece[12];
    private Board board;
    /**
     *
     */
    /*
    * W * W * W * W
    W * W * W * W *
    * W * W * W * W
    - * - * - * - *
    * - * - * - * -
    R * R * R * R *
    * R * R * R * R
    R * R * R * R *
     */
    public Checkers(){
        this.board = board.newBoard();
        for (int i = 0; i < redPieces.length; i++){
            redPieces[i] = Piece.redPiece();
            whitePieces[i] = Piece.whitePiece();
        }
        placeRedPiecesOnBoard();
        placeWhitePiecesOnBoard();
    }
    /**
     * Play the Game
     */
    public void gamePlay(){

        printGameIntro();
        board.printBoard();
        //if even, then red player; if odd, then white player
        int tries = 0;

        String currentPieceColor;
        String opponentPieceColor;
        //set true if player gets a bonus turn to play
        boolean bonusMove = false;
        Square currSquare=null;
        Piece currentPiece=null;
        Square newSquare =null;
        //play game until either player is fully out of pieces
        while (!gameOver()){
            if (tries % 2 == 0){
                currentPieceColor = Piece.RED;
                opponentPieceColor = Piece.WHITE;

            } else {
                currentPieceColor = Piece.WHITE;
                opponentPieceColor = Piece.RED;
            }
            //if it is a bonus move, set current square based on the current piece
            //otherwise, prompt user to enter location of current piece to move
            if (bonusMove){
                Scanner scanner = new Scanner (System.in);
                System.out.println(currentPieceColor + "player, you are on a bonus move. The game lets you move the same piece another time!");
                System.out.print("Please note that the program will only accept capture moves here! Do you want to play this move (Y/n): ");
                String bonusResponse= scanner.nextLine();
                if (!bonusResponse.equals("Y")){
                    bonusMove = false;
                    tries++;
                    continue;
                }
                currSquare = board.getSquare(currentPiece.getX(),currentPiece.getY());
                System.out.println(currentPieceColor + "player, your piece is at this position: Row: " + currentPiece.getX() + " Column: " + currentPiece.getY());
            } else {
                currSquare = inputPieceToMove(currentPieceColor);
                if (currSquare==null) continue;
                currentPiece = currSquare.getPiece();
            }
            //prompt user to enter new position
            //validate if move is diagonal
            newSquare = inputNewPosition(currentPieceColor);
            if (newSquare == null) continue;
            //check if the move is legal and within bounds
            boolean legal = isMoveLegal(currentPiece,newSquare);
            if (!legal) continue;
            //if new square is empty check if move is bonus move; if so, reject
            //if not bonus move, move the piece to the empty spot
            if (newSquare.isEmpty()){
                if (bonusMove){
                    System.out.println("Bonus move must be a capture move. Please try again.");
                    continue;
                } else {
                    newSquare.setPiece(currentPiece);
                    currSquare.setPiece(null);
                }
            }
            //if new square has a piece of the same color, reject and prompt user to enter new position
            else if (newSquare.hasColor(currentPieceColor)){
                System.out.println("The new position has already a piece of the same color. Please try again.");
                continue;
            }
            //if new square has a piece of opponent color, allow for capture
            else if(newSquare.hasColor(opponentPieceColor)){
                Square jumpSquare = canJump(currentPiece,currSquare,newSquare);
                if (jumpSquare != null){
                    //Remove opponent piece from the board
                    Piece opponentPiece = newSquare.getPiece();
                    opponentPiece.capture();
                    //if opponent can make multiple captures, increment tries twice to give multiple opportunities
                    tries++;
                    bonusMove = true;
                    System.out.println(currentPieceColor + " player, you get a bonus move!");
                    //clear new square and clear current square
                    newSquare.setPiece(null);
                    currSquare.setPiece(null);
                    //move current piece to jump square
                    jumpSquare.setPiece(currentPiece);
                } else {
                    System.out.println("Either there is a piece on the jump position or it is out of bounds." +
                            " Please enter another position.");
                    continue;
                }
            }
            printGameInfo();
            tries++;
        }
    }

    /**
     * Print Game Information
     */
    private void printGameInfo() {
        board.printBoard();
        printSummary();
    }
    /**
     * @param p
     * @param newSquare
     */
    private Square canJump(Piece p, Square currSquare, Square newSquare) {
        //get positions of the piece after the jump over opponent's piece
        int vertJump = newSquare.getX()-currSquare.getX();
        int horizontalJump = newSquare.getY()-currSquare.getY();
        int newX= newSquare.getX()+vertJump;
        int newY= newSquare.getY()+horizontalJump;
        Square jumpSquare = board.getSquare(newX,newY);
        if (jumpSquare != null && jumpSquare.isEmpty()) {
            return jumpSquare;
        } else {
            return null;
        }
    }
    /**
     * Prints count of how many red and white pieces remaining on the board
     */
    private void printSummary(){
        int numRedPieces = 0;
        int numRedKings = 0;
        int numWhitePieces = 0;
        int numWhiteKings = 0;
        for (int i = 0; i < redPieces.length; i++){
            Piece p = redPieces[i];
            if (p.onBoard()) {
                numRedPieces++;
                if (p.kingStatus()) numRedKings++;
            }
        }
        for (int i = 0; i < whitePieces.length; i++){
            Piece p = whitePieces[i];
            if (p.onBoard()) {
                numWhitePieces++;
                if (p.kingStatus()) numWhiteKings++;
            }
        }
        System.out.println("Red pieces remaining on board: " + numRedPieces + " Red Kings: " + numRedKings);
        System.out.println("White pieces remaining on board: " + numWhitePieces + " White Kings: " + numWhiteKings );
        if (numRedPieces == 0){
            System.out.println("W player has won!");
        } else if (numWhitePieces == 0){
            System.out.println("R player has won!");
        }
    }
    /**
     * Prompt for inputs for new position, check if inputs are valid
     * If invalid, return null and ask user to re-enter
     * Otherwise, return the square
     * @param currentPieceColor
     * @return
     */
    private Square inputNewPosition(String currentPieceColor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(currentPieceColor + " player, enter new row position to move: ");
        int nextRow = scanner.nextInt();
        System.out.print(currentPieceColor + " player, enter new column position to move: ");
        int nextCol = scanner.nextInt();

        Square nextSquare = board.getSquare(nextRow,nextCol);
        if (nextSquare == null){
            System.out.println("The new position is out of bounds. Please try again.");
            return null;
        }
        return nextSquare;
    }

    /**
     * Prompt for inputs, check if the inputs are valid.
     * If invalid, ask user to re-enter
     * @param currentPieceColor
     * @return
     */
    private Square inputPieceToMove(String currentPieceColor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(currentPieceColor + " player, please enter row of piece to move: ");
        int currRow = scanner.nextInt();
        System.out.print(currentPieceColor + " player, please enter column of piece to move: ");
        int currColumn = scanner.nextInt();
        Square currSquare = board.getSquare(currRow,currColumn);
        if (currSquare == null){
            System.out.println("This position is out of bounds. Please try again.");
            return null;
        }
        if (currSquare.isEmpty()){
            System.out.println("There is no piece in this square. Please try again.");
            return null;
        }
        if (!currSquare.hasColor(currentPieceColor)){
            System.out.println("You have chosen the wrong color to move. Please try again.");
            return null;
        }
        return currSquare;
    }

    /**
     * Introduction for the Game
     */
    public void printGameIntro() {
        System.out.println("Welcome to checkers! Player 1 will use the checker Piece" +
                " denoted as 'r' and Player 2 will use the checker Piece denoted\n" +
                "as 'w'. Both players must select which piece they would like to move. " +
                "Please enter the row and " +
                "column number of the piece,\nwhere [0,0]" +
                "referes to the top left corner for example. "+
                "The directions players can move are as follows:\n" +
                "For Player 1, [1,1] indicates moving 1 up and 1 right for example." +
                " For Player 2, [1,1] indicates moving 1 down and 1 right\nfor example. When one of the pieces " +
                " reaches the other side of the board, then the program will replace the character with \nits corresponding uppercase " +
                "letter to represent a king.");
        System.out.println();
    }
    /**
     * place white pieces in initial position
     */
    private void placeWhitePiecesOnBoard(){
        int[][] initialWhitePositions = {
                {0,1}, {0,3}, {0,5}, {0,7},
                {1,0}, {1,2}, {1,4}, {1,6},
                {2,1}, {2,3}, {2,5}, {2,7}
        };
        for (int i = 0; i < whitePieces.length; i++){
            Piece white = whitePieces[i];
            int[] pos = initialWhitePositions[i];
            board.setPieceOnSquare(white,pos[0],pos[1]);
        }
    }
    /**
     * place red pieces in initial position
     */
    private void placeRedPiecesOnBoard(){
        int[][] initialRedPositions = {
                {5,0}, {5,2}, {5,4}, {5,6},
                {6,1}, {6,3}, {6,5}, {6,7},
                {7,0}, {7,2}, {7,4}, {7,6}
        };
        for (int i = 0; i < redPieces.length; i++){
            Piece red = redPieces[i];
            int[] pos = initialRedPositions[i];
            board.setPieceOnSquare(red,pos[0],pos[1]);
        }
    }
    /**
     * returns true even if 1 red piece is there on board
     * @return
     */
    public boolean anyRedPiecesLeft(){
        for (int i = 0; i < redPieces.length; i++){
            Piece redPiece = redPieces[i];
            if (redPiece.onBoard()){
                return true;
            }
        }
        return false;
    }
    /**
     * returns true even if 1 red piece is there on board
     * @return
     */
    public boolean anyWhitePiecesLeft(){
        for (int i = 0; i < whitePieces.length; i++){
            Piece whitePiece = whitePieces[i];
            if (whitePiece.onBoard()){
                return true;
            }
        }
        return false;
    }
    /**
     * checks to see if the game is over
     * @return
     */
    public boolean gameOver(){
         return (!anyRedPiecesLeft()) || (!anyWhitePiecesLeft());
    }
    /**
     * Checks to see if the player's move is legal
     * @return
     */
    public boolean isMoveLegal(Piece p,Square s){
        boolean legal = true;
        legal = p.onBoard();
        if (!legal) {
            System.out.println("Piece is outside the board. Pleas enter another piece");
            return false;
        }
        legal = isMoveDiagonal(p,s);
        if (!legal){
            System.out.println("You cannot move in this direction. Please enter another position to move");
            return false;
        }

        return true;
    }
    /**
     *
     */
    public boolean isMoveDiagonal(Piece p,Square s){
        int currX = p.getX();
        int currY = p.getY();
        int x = s.getX();
        int y = s.getY();
        int deltaX = currX - x;
        int deltaY = Math.abs(currY - y);
        if (p.kingStatus()) {
            return (Math.abs(deltaX) == 1) && (deltaY == 1);
        } else {
            if (p.isRed()) {
                return (deltaX == 1) && (deltaY == 1);
            } else if (p.isWhite()) {
                return (deltaX == -1) && (deltaY == 1);
            } else{
                return false;
            }
        }
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Checkers game = new Checkers();
        game.gamePlay();
    }

}
