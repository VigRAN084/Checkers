/**
 * This class represents the actual checkerboard, which has 8x8 squares.
 */
public class Board {
    public static int SIZE=8;
    private Square[][] squares = new Square[SIZE][SIZE];

    /*
    * - * - * - * -
    - * - * - * - *
    * - * - * - * -
    - * - * - * - *
    * - * - * - * -
    - * - * - * - *
    * - * - * - * -
    - * - * - * - *
     */
    public void drawCleanBoard() {
        for (int i = 0; i <= 7; i++){
            this.drawRow(i);
        }
    }
    /**
     *
     * @param rowIndex
     */
    public void drawRow(int rowIndex){
        if ( rowIndex % 2 == 0){
            this.squares[rowIndex][0] = Square.emptyWhiteSquare(rowIndex,0);
            this.squares[rowIndex][1] = Square.emptyBlackSquare(rowIndex,1);
            squares[rowIndex][2] = Square.emptyWhiteSquare(rowIndex,2);
            squares[rowIndex][3] = Square.emptyBlackSquare(rowIndex,3);
            squares[rowIndex][4] = Square.emptyWhiteSquare(rowIndex,4);
            squares[rowIndex][5] = Square.emptyBlackSquare(rowIndex,5);
            squares[rowIndex][6] = Square.emptyWhiteSquare(rowIndex,6);
            squares[rowIndex][7] = Square.emptyBlackSquare(rowIndex,7);
        }

        else{
            squares[rowIndex][0] = Square.emptyBlackSquare(rowIndex,0);
            squares[rowIndex][1] = Square.emptyWhiteSquare(rowIndex,1);
            squares[rowIndex][2] = Square.emptyBlackSquare(rowIndex,2);
            squares[rowIndex][3] = Square.emptyWhiteSquare(rowIndex,3);
            squares[rowIndex][4] = Square.emptyBlackSquare(rowIndex,4);
            squares[rowIndex][5] = Square.emptyWhiteSquare(rowIndex,5);
            squares[rowIndex][6] = Square.emptyBlackSquare(rowIndex,6);
            squares[rowIndex][7] = Square.emptyWhiteSquare(rowIndex,7);
        }

    }
    /**
     *
     * @param piece
     * @param x
     * @param y
     */
    public void setPieceOnSquare(Piece piece, int x, int y) {
        Square s = squares[x][y];
        s.setPiece(piece);
    }
    /**
     * Get square at position x,y on the board
     * @param x - row of the square
     * @param y - column of the square
     * @return square
     */
    public Square getSquare(int x, int y){
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }
        return squares[x][y];
    }
    /**
     * Print board
     */
    public void printBoard(){
        for (int i = 0; i <SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                Square square = squares[i][j];
                System.out.print(square.toString());
            }
            System.out.println();
        }

    }
    /**
     *
     * @return
     */
    public static Board newBoard(){
        Board b = new Board();
        b.drawCleanBoard();
        return b;
    }

}
