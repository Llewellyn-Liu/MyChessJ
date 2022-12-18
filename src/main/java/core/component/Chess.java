package core.component;

/**
 * Chess instances that would appear on a chessboard.
 * Due to the logic of ChessBoard, the Chess class has 2 different types: Specific Type and Empty Type.
 * When using an Empty Type as position-taker on a chessboard, the isEmpty attribute should be true.
 *
 * Attribute 'identifier':
 * For Chess like Zu, Ju, Ma, etc., that has more instances for each side, each chess is marked with an identifier.
 */
public class Chess {

    //Possible value:
    //Specific type("Ma"), None
    private String type;

    private Coordinate coordinate;

    private String side;

    private boolean isEmpty;

    private boolean isConquered;

    private int identifier;

    public Chess(){
        this.type = "None";
        this.coordinate = new Coordinate(0,0);
        this.side = "None";
        this.isEmpty = true;
        this.isConquered = false;
    }

    Chess(String type, Coordinate coordinate, String side, int identifier){
        this.type = type;
        this.coordinate = coordinate;
        this.side = side;
        this.isConquered = false;
        this.isEmpty = false;
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        setCoordinate(coordinate.getRow(),coordinate.getCol());
    }

    public void setCoordinate(int row, int col){
        this.coordinate.setRow(row);
        this.coordinate.setCol(col);
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isConquered() {
        return isConquered;
    }

    public void setConquered(boolean conquered) {
        isConquered = conquered;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
}
