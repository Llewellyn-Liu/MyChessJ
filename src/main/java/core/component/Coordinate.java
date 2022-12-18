package core.component;


public  class Coordinate {
    private int row;

    private int col;

    private boolean hasConqueringMark = false;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Coordinate(int row, int col, boolean hasConqueringMark){
        this.row = row;
        this.col = col;
        this.hasConqueringMark = hasConqueringMark;
    }

    public Coordinate(String coorStr){
        row = Integer.parseInt(coorStr.trim().split(",")[0].trim());

        col = Integer.parseInt(coorStr.trim().split(",")[1].trim());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean hasConqueringMark() {
        return hasConqueringMark;
    }

    /**
     * Only for one input of String formed coordinate.
     * @param stringFormCoordinate
     * @return
     */
    public static Coordinate parseCoordinate(String stringFormCoordinate){
        String[] coordinateStrArr = stringFormCoordinate.split(",");
        int row = Integer.parseInt(coordinateStrArr[0]);
        int col = Integer.parseInt(coordinateStrArr[1]);
        return new Coordinate(row, col);
    }

}
