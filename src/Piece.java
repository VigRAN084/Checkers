//Vignesh Rangarajan
//Period 1
//1/1/2021
//Checkers Final Project Semester 1
public class Piece
{
    public static String RED = "R ";
    public static String WHITE = "W ";

    private int x = -1;
    private int y = -1;
    private boolean king = false;
    private String color;

    public String getStringifiedColor() {
        if(this.kingStatus()) {
            return color.toUpperCase();
        } else {
            return color.toLowerCase();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public Piece()
    {
        king = false;
        color = "-";
    }

    public Piece(int x, int y, boolean b, String character)
    {
        this.x = x;
        this.y = y;
        king = b;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean kingStatus()
    {
        return king;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void makeKing(boolean k)
    {
        this.king = k;
        System.out.println(this.color + " piece is now a king!");
    }
    /**
     * Check if piece within board boundaries
     * @return true/false depending on if inside or outside bounds
     */
    public boolean onBoard(){
        return ((this.x < 8 && this.x >= 0 )&& (this.y < 8 && this.y >= 0));
    }
    /**
     * Removes captured pieces from the board
     */
    public void capture(){
        this.x = -1;
        this.y = -1;
        System.out.println(this.color + " piece just got captured!");
    }
    /**
     *
     * @return
     */
    public boolean isRed(){
        return this.color.equals(RED);
    }

    /**
     *
     * @return
     */
    public boolean isWhite(){
        return this.color.equals(WHITE);
    }
    /**
     * create red piece
     * @return the red piece
     */
    public static Piece redPiece(){
        Piece p = new Piece();
        p.color = Piece.RED;
        return p;
    }

    /**
     * create white piece
     * @return  white piece
     */
    public static Piece whitePiece(){
        Piece p = new Piece();
        p.color = Piece.WHITE;
        return p;
    }
}