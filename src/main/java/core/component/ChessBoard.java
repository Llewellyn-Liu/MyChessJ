package core.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Each Game has a Chessboard instance in the ChessBoardController, recording the layout of current situation.
 */

public class ChessBoard implements Iterable {

    //private Logger logger =

    //ChessSet to check chess reversely
    private Chess[][] chessSet;

    private final static String defaultLayout = "1,1 9,1 2,1 8,1 3,1 7,1 4,1 6,1 5,1 5,3 8,3 1,4 3,4 5,4 7,4 9,4 " +
            "1,10 9,10 2,10 8,10 3,10 7,10 4,10 6,10 5,10 2,8 8,8 1,7 3,7 5,7 7,7 9,7 ";

    //Utilities
    private ArrayList<Chess> chessList;
    //Red side
    private Chess chessJuOfRedLeft;

    private Chess chessJuOfRedRight;

    private Chess chessMaOfRedLeft;

    private Chess chessMaOfRedRight;

    private Chess chessXiangOfRedLeft;

    private Chess chessXiangOfRedRight;

    private Chess chessShiOfRedLeft;

    private Chess chessShiOfRedRight;

    private Chess chessJiangOfRed;

    private Chess chessPaoOfRedLeft;

    private Chess chessPaoOfRedRight;

    private Chess chessZuOfRed1;

    private Chess chessZuOfRed2;

    private Chess chessZuOfRed3;

    private Chess chessZuOfRed4;

    private Chess chessZuOfRed5;

    //Black side
    private Chess chessJuOfBlackLeft;

    private Chess chessJuOfBlackRight;

    private Chess chessMaOfBlackLeft;

    private Chess chessMaOfBlackRight;

    private Chess chessXiangOfBlackLeft;

    private Chess chessXiangOfBlackRight;

    private Chess chessShiOfBlackLeft;

    private Chess chessShiOfBlackRight;

    private Chess chessJiangOfBlack;

    private Chess chessPaoOfBlackLeft;

    private Chess chessPaoOfBlackRight;

    private Chess chessZuOfBlack1;

    private Chess chessZuOfBlack2;

    private Chess chessZuOfBlack3;

    private Chess chessZuOfBlack4;

    private Chess chessZuOfBlack5;

    //Constructor
    public ChessBoard(){
        //Start from (1,1)
        chessSet = new Chess[9 + 1][10 + 1];
        for(int i = 0; i < (9 + 1); i++){
            for(int j = 0; j < (10 + 1); j++){
                chessSet[i][j] = new Chess();
            }
        }

        chessList = new ArrayList<>();

        this.chessJuOfRedLeft = new Chess("Ju", new Coordinate(1,1),"Red", 1);
        chessList.add(this.chessJuOfRedLeft);
        this.chessJuOfRedRight = new Chess("Ju", new Coordinate(9,1),"Red",2);
        chessList.add(this.chessJuOfRedRight);
        this.chessMaOfRedLeft = new Chess("Ma", new Coordinate(2,1),"Red",1);
        chessList.add(this.chessMaOfRedLeft);
        this.chessMaOfRedRight = new Chess("Ma", new Coordinate(8,1),"Red",2);
        chessList.add(this.chessMaOfRedRight);
        this.chessXiangOfRedLeft = new Chess("Xiang", new Coordinate(3,1),"Red",1);
        chessList.add(this.chessXiangOfRedLeft);
        this.chessXiangOfRedRight = new Chess("Xiang", new Coordinate(7,1),"Red",2);
        chessList.add(this.chessXiangOfRedRight);
        this.chessShiOfRedLeft = new Chess("Shi", new Coordinate(4,1),"Red",1);
        chessList.add(this.chessShiOfRedLeft);
        this.chessShiOfRedRight = new Chess("Shi", new Coordinate(6,1),"Red",2);
        chessList.add(this.chessShiOfRedRight);
        this.chessJiangOfRed = new Chess("Jiang", new Coordinate(5,1),"Red",1);
        chessList.add(this.chessJiangOfRed);
        this.chessPaoOfRedLeft = new Chess("Pao", new Coordinate(2,3),"Red",1);
        chessList.add(this.chessPaoOfRedLeft);
        this.chessPaoOfRedRight = new Chess("Pao", new Coordinate(8,3),"Red",2);
        chessList.add(this.chessPaoOfRedRight);
        this.chessZuOfRed1 = new Chess("Zu", new Coordinate(1,4),"Red",1);
        chessList.add(this.chessZuOfRed1);
        this.chessZuOfRed2 = new Chess("Zu", new Coordinate(3,4),"Red",2);
        chessList.add(this.chessZuOfRed2);
        this.chessZuOfRed3 = new Chess("Zu", new Coordinate(5,4),"Red",3);
        chessList.add(this.chessZuOfRed3);
        this.chessZuOfRed4 = new Chess("Zu", new Coordinate(7,4),"Red",4);
        chessList.add(this.chessZuOfRed4);
        this.chessZuOfRed5 = new Chess("Zu", new Coordinate(9,4),"Red",5);
        chessList.add(this.chessZuOfRed5);

        this.chessJuOfBlackLeft = new Chess("Ju", new Coordinate(1,10),"Black",2);
        chessList.add(this.chessJuOfBlackLeft);
        this.chessJuOfBlackRight = new Chess("Ju", new Coordinate(9,10),"Black",1);
        chessList.add(this.chessJuOfBlackRight);
        this.chessMaOfBlackLeft = new Chess("Ma", new Coordinate(2,10),"Black",2);
        chessList.add(this.chessMaOfBlackLeft);
        this.chessMaOfBlackRight = new Chess("Ma", new Coordinate(8,10),"Black",1);
        chessList.add(this.chessMaOfBlackRight);
        this.chessXiangOfBlackLeft = new Chess("Xiang", new Coordinate(3,10),"Black",2);
        chessList.add(this.chessXiangOfBlackLeft);
        this.chessXiangOfBlackRight = new Chess("Xiang", new Coordinate(7,10),"Black",1);
        chessList.add(this.chessXiangOfBlackRight);
        this.chessShiOfBlackLeft = new Chess("Shi", new Coordinate(4,10),"Black",2);
        chessList.add(this.chessShiOfBlackLeft);
        this.chessShiOfBlackRight = new Chess("Shi", new Coordinate(6,10),"Black",1);
        chessList.add(this.chessShiOfBlackRight);
        this.chessJiangOfBlack = new Chess("Jiang", new Coordinate(5,10),"Black",1);
        chessList.add(this.chessJiangOfBlack);
        this.chessPaoOfBlackLeft = new Chess("Pao", new Coordinate(2,8),"Black",2);
        chessList.add(this.chessPaoOfBlackLeft);
        this.chessPaoOfBlackRight = new Chess("Pao", new Coordinate(8,8),"Black",1);
        chessList.add(this.chessPaoOfBlackRight);
        this.chessZuOfBlack1 = new Chess("Zu", new Coordinate(1,7),"Black",1);
        chessList.add(this.chessZuOfBlack1);
        this.chessZuOfBlack2 = new Chess("Zu", new Coordinate(3,7),"Black",2);
        chessList.add(this.chessZuOfBlack2);
        this.chessZuOfBlack3 = new Chess("Zu", new Coordinate(5,7),"Black",3);
        chessList.add(this.chessZuOfBlack3);
        this.chessZuOfBlack4 = new Chess("Zu", new Coordinate(7,7),"Black",4);
        chessList.add(this.chessZuOfBlack4);
        this.chessZuOfBlack5 = new Chess("Zu", new Coordinate(9,7),"Black",5);
        chessList.add(this.chessZuOfBlack5);

        for(Chess c: chessList){
            int row = c.getCoordinate().getRow();
            int col = c.getCoordinate().getCol();
            chessSet[row][col] = c;
        }

    }


    public ChessBoard(File readFromFile){
        this();

        Scanner input = null;
        try{
            input = new Scanner(readFromFile);
        } catch (FileNotFoundException e) {
            //logger.log(Level.);
            return;
        }

        this.chessSet = new Chess[this.chessSet.length][this.chessSet[0].length];
        for(int i = 0; i< chessSet.length; i++){
            for(int j = 0; j<chessSet[0].length; j++){
                chessSet[i][j] = new Chess();
            }
        }
        int chessAt = 0;
        while (input.hasNext()){
            Coordinate co = parseCoordinate(input.next());
            this.chessList.get(chessAt).setCoordinate(co);
            chessAt++;
        }

        for(Chess c: chessList){
            int row = c.getCoordinate().getRow();
            int col = c.getCoordinate().getCol();
            chessSet[row][col] = c;
        }
        //logger.info("Chess plate created with file.");

    }

    //Getters & setters


    public ArrayList<Chess> getChessList() {
        return chessList;
    }

    public Chess[][] getChessSet() {
        return chessSet;
    }

    //Iterator
    @Override
    public Iterator iterator() {
        return chessList.iterator();
    }

    //Methods
    public boolean isEmpty(Coordinate coordinate){
        return isEmpty(coordinate.getRow(), coordinate.getCol());
    }
    public boolean isEmpty(int row, int col){

        return chessSet[row][col].isEmpty();

    }

    public Chess getChess(Coordinate c){
        return chessSet[c.getRow()][c.getCol()];
    }

    public Coordinate getCoordinateUsingType(String side, String type, int id){
        Chess c = null;
        try {
            if(id == 0){
                c = (Chess) this.getClass().getField("chess"+type+"Of"+side).get(this);
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return c.getCoordinate();
    }
    private Coordinate parseCoordinate(String coordinateStr) {
        String[] coordinateStrArr = coordinateStr.split(",");
        int row = Integer.parseInt(coordinateStrArr[0]);
        int col = Integer.parseInt(coordinateStrArr[1]);
        return new Coordinate(row, col);
    }

    public void setChessSetPosition(int row, int col, Chess c){
        this.chessSet[row][col] = c;
    }

    public void displayChessPlate(){
        //logger.info("Display the chess plate:");
        System.out.println("---");
        for(Chess c: chessList){
            System.out.println("Type: " + c.getType()+", side: "+c.getSide()+", id: "+ c.getIdentifier()+", position: "
                    +c.getCoordinate().getRow()+", "+c.getCoordinate().getCol());
        }
        for(int i = 0; i< chessSet.length;i++){
            for(int j = 0; j< chessSet[0].length; j++){
                Chess c = chessSet[i][j];
                System.out.println("Type: " + c.getType()+", side: "+c.getSide()+", id: "+ c.getIdentifier()+", position: "
                        +c.getCoordinate().getRow()+", "+c.getCoordinate().getCol());
            }
        }

    }


    public String state(){
        String s = "";
        for(Chess c: chessList){
            String co = "" + c.getCoordinate().getRow() + "," + c.getCoordinate().getCol()+" ";
            s += co;
        }

        return s;
    }

    /**
     * Print the current coordinates of chess, in default order(how attr lists above)
     * @param output
     */
    public void printChessPlateToFile(FileOutputStream output) {
        //logger.info("Print the chess plate to File: "+output.toString());
        try{
            output.write(state().getBytes(StandardCharsets.UTF_8));
            output.close();
            //logger.info("Print success.");
        }catch (Exception e){
            //logger.error("I/O failed when print");
        }

    }

    public void printChessPlate() {
        //logger.info("Print the chess plate.");
        for(Chess c: chessList){
            String s = "" + c.getCoordinate().getRow()+","+c.getCoordinate().getCol()+" ";
            System.out.println(s);
        }

        for(int i = 0; i< chessSet.length; i++){
            for(int j = 0; j< chessSet[0].length; j++){
                Chess c = chessSet[i][j];
                System.out.println("" + i +" "+ j+" "+ c.getCoordinate().getRow()+","+c.getCoordinate().getCol()+" "+ c.getType()+" "+c.getSide());
            }
        }
        //logger.info("Print success.");
    }

    public String getDefaultLayoutString(){
        return defaultLayout;
    }

}
