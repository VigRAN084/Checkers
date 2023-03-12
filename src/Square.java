/**
 * This class generates a square for each of the 8*8 = 64 positions on the board. The square is defined as having a
 * definite color and a piece depending on if it is one of the positions designated to have a piece.
 */
public class Square {

    public static  String WHITE_COLOR = "* ";
    public static  String BLACK_COLOR = "- ";

    private String color;
    private Piece piece = null;
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public String getColor() {
        return color;
    }
    public void setPiece(Piece p) {
        this.piece=p;
        if (p != null){
            p.setX(this.x);
            p.setY(this.y);
            if(p.isRed() && this.x == 0) {
                p.makeKing(true);
            } else if (p.isWhite() && this.x == 7) {
                p.makeKing((true));
            }
        }

    }

    public void setColor(String color) {
        this.color = color;
    }

    public Piece getPiece() {
        return piece;
    }
    public Square(String c, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = c;
    }
    /**
     * check if square has red piece
     * @return
     */
    public boolean hasRed(){
        return (!this.isEmpty()) && (this.piece.isRed());
    }
    /**
     * check if square has white piece
     * @return
     */
    public boolean hasWhite(){
        return (!this.isEmpty()) && (this.piece.isWhite());
    }
    /**
     * Check if square has a piece of this color
     * @param color
     * @return
     */
    public boolean hasColor(String color){
        return (!this.isEmpty()) && (this.piece.getColor().equals(color));
    }
    /**
     * returns true if no piece
     * @return
     */
    public boolean isEmpty (){
        return (piece==null);
    }
    /**
     *
     * @return
     */
    public String toString() {
        if(isEmpty()) {
          return this.color;
        } else {
            return piece.getStringifiedColor();
        }
    }
    public static Square emptyWhiteSquare(int x, int y){
        return new Square(Square.WHITE_COLOR,x,y);
    }
    public static Square emptyBlackSquare(int x, int y){
        return new Square(Square.BLACK_COLOR,x,y);
    }


}
