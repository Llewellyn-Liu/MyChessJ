package core.controller;

import core.component.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;

public class ChessBoardController {

    private static Logger logger = LogManager.getLogger(ChessBoardController.class);
    /**
     * Manipulation of Chess 1 - Conduct a move
     *
     */

    public static void move(ChessBoard cb, ArrayList<Chess> cs, Move m){
        if(m.withConquer()){
            Chess conquered = cb.getChess(m.getTarget());
            cs.add(conquered);

            //Set original position with a Empty instance
            if(!conquered.isConquered()){
                setConquered(conquered);
            }
            else {
                System.out.println("Error: the chess is already conquered.");
            }

            moveChessTo(cb,m);

            if(conquered.getType().equals("Jiang")){
                logger.info("Game End.");
                GameController.getInstance().end();
            }
        }
        else{
            Chess target = cb.getChess(m.getTarget());
            if(target.isEmpty()){
                moveChessTo(cb, m);
            }
            else {
                System.out.println("Error: the target is not empty.");
            }
        }

        logger.info("Move Successfully.");

    }

    private static void moveChessTo(ChessBoard cb, Move m) {
        Chess c = cb.getChess(m.getFrom());
        cb.setChessSetPosition(m.getTarget().getRow(),m.getTarget().getCol(),c);
        cb.setChessSetPosition(m.getFrom().getRow(),m.getFrom().getCol(),new Chess());
        c.setCoordinate(m.getTarget());
        //chessList
    }

    private static void setConquered(Chess conquered) {
        conquered.setConquered(true);
        conquered.setCoordinate(0,0);
        conquered.setEmpty(true);
    }

    public static void undoLastMove(ChessBoard cb, Move m){

    }

    public static ArrayList<Coordinate> getPossibleMove(ChessBoard cb, Coordinate from) {
        return Rule.getPossibleMove(cb, from);
    }

    //Check if a target coordinate is available for a move
    public static boolean checkMove(ArrayList<Coordinate> possibleTarget, Coordinate unconfirmedTargetCoordinate) {
        boolean rev = false;
        for(Coordinate c: possibleTarget){
            if(c.getRow() == unconfirmedTargetCoordinate.getRow() && c.getCol() == unconfirmedTargetCoordinate.getCol())
                rev = true;
        }

        return rev;
    }


    public static void newMove(MoveStack ms, Coordinate from, Coordinate target) {
        if(target.hasConqueringMark()){
            ms.put(new Move(from, target, true));
        }
        else
            ms.put(new Move(from, target));
    }

    public static boolean checkPositionEnemy(Coordinate coordinate, ChessBoard chessBoard, Chess chess)  {
        return Rule.checkPositionEnemy(coordinate.getRow(), coordinate.getCol(), chessBoard, chess);
    }


}



class Rule {

    //Define the coordinate, (Row, Column). Eg, (5,1) is the red Shuai of start,
    // (5,10) is the black Shuai on the contrary.

    private final static int ROW_START = 1;

    private final static int ROW_END = 9;

    private final static int COLUMN_START = 1;

    private final static int COLUMN_END = 10;

    //Red side plays at downside by default.
    //private final boolean BOTTOM_BE_RED = true;


    public static ArrayList<Coordinate> getPossibleMove(ChessBoard chessBoard, Coordinate coordinate){

        Chess chess = chessBoard.getChess(coordinate);

        String chessType = chess.getType();
        Coordinate chessCoordinate = chess.getCoordinate();
        int row = chessCoordinate.getRow();
        int col = chessCoordinate.getCol();

        ArrayList<Coordinate> returnList = new ArrayList<>();

        switch (chess.getType()){
            case "Ju":
                //Ju dashes for 4 direction

                //To left
                for(int i = row - 1; i >= ROW_START; i--){
                    //If empty, move forward, if enemy, register and break, else break
                    if(checkPositionEmpty(i, col, chessBoard)) {
                        returnList.add(new Coordinate(i, col));
                        continue;
                    }
                    else {
                        boolean positionHasEnemy = checkPositionEnemy(i, col, chessBoard, chess);
                        if (positionHasEnemy)
                            returnList.add(new Coordinate(i, col, true));

                        break;
                    }
                }

                for(int i = row + 1; i <= ROW_END; i++){
                    if(checkPositionEmpty(i, col, chessBoard)){
                        returnList.add(new Coordinate(i, col));
                        continue;
                    }
                    else {
                        boolean positionHasEnemy = checkPositionEnemy(i, col, chessBoard, chess);
                        if (positionHasEnemy)
                            returnList.add(new Coordinate(i, col, true));

                        break;
                    }
                }
                for(int i = col - 1; i>= COLUMN_START; i--){
                    if(checkPositionEmpty(row, i, chessBoard)){
                        returnList.add(new Coordinate(row, i));
                        continue;
                    }
                    else {
                        boolean positionHasEnemy = checkPositionEnemy(row, i, chessBoard, chess);
                        if (positionHasEnemy)
                            returnList.add(new Coordinate(row, i, true));

                        break;
                    }
                }

                for(int i = col + 1; i <= COLUMN_END; i++){
                    if(checkPositionEmpty(row, i, chessBoard)){
                        returnList.add(new Coordinate(row, i));
                        continue;
                    }
                    else {
                        boolean positionHasEnemy = checkPositionEnemy(row, i, chessBoard, chess);
                        if (positionHasEnemy)
                            returnList.add(new Coordinate(row, i, true));

                        break;
                    }
                }
                break;

            case "Ma":
                if(row + 2 <= ROW_END){
                    //Check hurdling chess
                    if(checkPositionEmpty(row + 1, col, chessBoard)){
                        if(col + 1 <= COLUMN_END && checkPositionEmpty(row + 2, col + 1,chessBoard)){
                            returnList.add(new Coordinate(row + 2, col + 1));
                        } else if (col + 1 <= COLUMN_END && checkPositionEnemy(row + 2, col +1, chessBoard,chess)) {
                            returnList.add(new Coordinate(row + 2, col + 1));
                        }
                        else;
                        if(col - 1 >= COLUMN_START && checkPositionEmpty(row + 2, col -1, chessBoard)){
                            returnList.add(new Coordinate(row + 2, col - 1));
                        }
                        else if (col - 1 >= COLUMN_START && checkPositionEnemy(row + 2, col - 1, chessBoard,chess)) {
                            returnList.add(new Coordinate(row + 2, col - 1));
                        }
                        else;
                    }
                    else ;
                }
                if(row - 2 >= ROW_START){
                    if(checkPositionEmpty(row - 1, col, chessBoard)){
                        if(col + 1 <= COLUMN_END && checkPositionEmpty(row - 2, col +1,chessBoard)){
                            returnList.add(new Coordinate(row - 2, col + 1));
                        } else if (col + 1 <= COLUMN_END && checkPositionEnemy(row - 2, col +1, chessBoard, chess)) {
                            returnList.add(new Coordinate(row - 2, col + 1));
                        }
                        if(col - 1 >= COLUMN_START && checkPositionEmpty(row - 2, col - 1,chessBoard)){
                            returnList.add(new Coordinate(row - 2, col - 1));
                        } else if (col - 1 >= COLUMN_START && checkPositionEnemy(row - 2, col - 1, chessBoard, chess)) {
                            returnList.add(new Coordinate(row - 2, col - 1));
                        }
                    }
                    else ;
                }
                if(col + 2 <= COLUMN_END){
                    if(checkPositionEmpty(row, col + 1, chessBoard)){
                        if(row + 1 <= ROW_END && checkPositionEmpty(row+1, col + 2,chessBoard)){
                            returnList.add(new Coordinate(row + 1, col + 2));
                        } else if (row + 1 <= ROW_END && checkPositionEnemy(row + 1, col + 2, chessBoard, chess)) {
                            returnList.add(new Coordinate(row + 1, col + 2));
                        }
                        else;
                        if(row - 1 >= ROW_START && checkPositionEmpty(row - 1, col + 2,chessBoard)){
                            returnList.add(new Coordinate(row - 1, col + 2));
                        } else if (row - 1 >= ROW_START && checkPositionEnemy(row - 1, col + 2, chessBoard, chess)) {
                            returnList.add(new Coordinate(row - 1, col + 2));
                        }
                        else;
                    }
                    else ;

                }
                if(col - 2 >= COLUMN_START){
                    if(checkPositionEmpty(row, col - 1, chessBoard)){
                        if(row + 1 <= ROW_END && checkPositionEmpty(row+1, col - 2,chessBoard)){
                            returnList.add(new Coordinate(row + 1, col - 2));
                        } else if (row + 1 <= ROW_END && checkPositionEnemy(row + 1, col - 2, chessBoard, chess)) {
                            returnList.add(new Coordinate(row + 1, col - 2));
                        }
                        else;
                        if(row - 1 >= ROW_START && checkPositionEmpty(row - 1, col - 2,chessBoard)){
                            returnList.add(new Coordinate(row - 1, col - 2));
                        } else if (row - 1 >= ROW_START && checkPositionEnemy(row - 1, col - 2, chessBoard, chess)) {
                            returnList.add(new Coordinate(row - 1, col - 2));
                        }
                        else;
                    }
                    else ;

                }
                break;
            case "Xiang":
                if(row + 2 <= ROW_END){
                    if(col + 2 <= COLUMN_END){
                        //Limits for Red side
                        if(chess.getSide().equals("Red") && col + 2 > 5);
                        else{
                            //Check hurdling chess
                            if(!checkPositionEmpty(row + 1, col + 1, chessBoard));
                            else {
                                if(checkPositionEmpty(row + 2, col + 2, chessBoard))
                                    returnList.add(new Coordinate(row + 2, col + 2));
                                else{
                                    if(checkPositionEnemy(row + 2, col + 2, chessBoard, chess))
                                        returnList.add(new Coordinate(row + 2, col +2,true));
                                    else ;
                                }
                            }
                        }
                    }
                    if(col - 2 >= COLUMN_START){
                        //Limit for Black side
                        if(chess.getType().equals("Black")&& col - 2 < 6);
                        else{
                            if(!checkPositionEmpty(row + 1, col - 1, chessBoard));
                            else {
                                if(checkPositionEmpty(row + 2, col - 2, chessBoard)){
                                    returnList.add(new Coordinate(row + 2, col - 2));
                                }
                                else {
                                    if(checkPositionEnemy(row + 2, col - 2, chessBoard, chess))
                                        returnList.add(new Coordinate(row + 2, col - 2,true));
                                    else;
                                }
                            }
                        }
                    }
                }
                if(row - 2 <= ROW_END){
                    if(col + 2 <= COLUMN_END){
                        //Limits for Red side
                        if(chess.getSide().equals("Red") && col + 2 > 5);
                        else{
                            //Check hurdling chess
                            if(!checkPositionEmpty(row - 1, col + 1, chessBoard));
                            else {
                                if(checkPositionEmpty(row - 2, col + 2, chessBoard))
                                    returnList.add(new Coordinate(row - 2, col + 2));
                                else{
                                    if(checkPositionEnemy(row - 2, col + 2, chessBoard, chess))
                                        returnList.add(new Coordinate(row - 2, col +2,true));
                                    else ;
                                }
                            }
                        }
                    }
                    if(col - 2 >= COLUMN_START){
                        //Limit for Black side
                        if(chess.getType().equals("Black")&& col - 2 < 6);
                        else{
                            if(!checkPositionEmpty(row - 1, col - 1, chessBoard));
                            else {
                                if(checkPositionEmpty(row - 2, col - 2, chessBoard)){
                                    returnList.add(new Coordinate(row - 2, col - 2));
                                }
                                else {
                                    if(checkPositionEnemy(row - 2, col - 2, chessBoard, chess))
                                        returnList.add(new Coordinate(row - 2, col - 2,true));
                                    else;
                                }
                            }
                        }
                    }
                }
                break;
            case "Shi":
                // Possible location:
                // Red side: (4, 1), (5, 2), (6, 1), (4, 3), (6, 3)
                // Black side: (4, 10), (5, 9), (6, 10), (4, 8), (6, 8)
                if ((row == 4 && col == 1) || (row == 5 && col == 2) || (row == 4 && col == 8) || (row == 5 && col == 9) ){
                    if(checkPositionEmpty(row + 1,col + 1,chessBoard)){
                        returnList.add(new Coordinate(row + 1,col + 1));
                    }
                    else if(checkPositionEnemy(row + 1,col + 1, chessBoard, chess)){
                        returnList.add(new Coordinate(row + 1, col + 1,true));
                    }
                    else;
                }
                if((row == 4 && col == 3) || (row == 5 && col == 2) || (row == 4 && col == 10 ) || (row == 5 && col == 9 )){
                    if(checkPositionEmpty(row + 1,col - 1,chessBoard)){
                        returnList.add(new Coordinate(row + 1,col - 1));
                    }
                    else if(checkPositionEnemy(row + 1,col - 1, chessBoard, chess)){
                        returnList.add(new Coordinate(row + 1, col - 1,true));
                    }
                    else;
                }
                if((row == 6 && col == 3) || (row == 5 && col == 2) || (row == 6 && col == 10 ) || (row == 5 && col == 9 )){
                    if(checkPositionEmpty(row - 1,col - 1,chessBoard)){
                        returnList.add(new Coordinate(row - 1,col - 1));
                    }
                    else if(checkPositionEnemy(row - 1,col - 1, chessBoard, chess)){
                        returnList.add(new Coordinate(row - 1, col - 1,true));
                    }
                    else;
                }
                if((row == 6 && col == 1) || (row == 5 && col == 2) || (row == 6 && col == 8 ) || (row == 5 && col == 9 )){
                    if(checkPositionEmpty(row - 1,col + 1,chessBoard)){
                        returnList.add(new Coordinate(row - 1,col + 1));
                    }
                    else if(checkPositionEnemy(row - 1,col + 1, chessBoard, chess)){
                        returnList.add(new Coordinate(row - 1, col + 1,true));
                    }
                    else;
                }
                break;

            case "Jiang":

                if(GameController.getInstance().isMagicModeOn){
                    for(int i = 1; i < GameController.getInstance().getCurrentGame().getChessBoard().getChessSet().length; i++){
                        for(int j = 1; j < GameController.getInstance().getCurrentGame().getChessBoard().getChessSet()[0].length; j++){
                            returnList.add(new Coordinate(i, j));
                        }
                    }
                }
                else{

                    if(chess.getSide().equals("Red")){
                        if (row + 1 > 6);
                        else {
                            if (checkPositionEmpty(row + 1, col, chessBoard))
                                returnList.add(new Coordinate(row + 1, col));
                            else if (checkPositionEnemy(row + 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row + 1, col,true));
                            }
                        }
                        if(row - 1 < 4);
                        else {
                            if (checkPositionEmpty(row - 1, col, chessBoard))
                                returnList.add(new Coordinate(row - 1, col));
                            else if (checkPositionEnemy(row - 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row - 1, col,true));
                            }
                        }
                        if(col + 1 > 3);
                        else {
                            if (checkPositionEmpty(row, col + 1, chessBoard))
                                returnList.add(new Coordinate(row, col + 1));
                            else if (checkPositionEnemy(row, col + 1, chessBoard, chess)) {
                                returnList.add(new Coordinate(row, col + 1,true));
                            }
                        }
                        if(col - 1 < 1);
                        else {
                            if (checkPositionEmpty(row, col - 1, chessBoard))
                                returnList.add(new Coordinate(row, col - 1));
                            else if (checkPositionEnemy(row, col - 1, chessBoard, chess)) {
                                returnList.add(new Coordinate(row, col - 1,true));
                            }
                        }
                    }
                    else if(chess.getSide().equals("Black")){
                        if (row + 1 > 6);
                        else {
                            if (checkPositionEmpty(row + 1, col, chessBoard))
                                returnList.add(new Coordinate(row + 1, col));
                            else if (checkPositionEnemy(row + 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row + 1, col,true));
                            }
                        }
                        if(row - 1 < 4);
                        else {
                            if (checkPositionEmpty(row - 1, col, chessBoard))
                                returnList.add(new Coordinate(row - 1, col));
                            else if (checkPositionEnemy(row - 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row - 1, col,true));
                            }
                        }
                        if(col + 1 > 10);
                        else {
                            if (checkPositionEmpty(row, col + 1, chessBoard))
                                returnList.add(new Coordinate(row, col + 1));
                            else if (checkPositionEnemy(row, col + 1, chessBoard, chess)) {
                                returnList.add(new Coordinate(row, col + 1,true));
                            }
                        }
                        if(col - 1 < 8);
                        else {
                            if (checkPositionEmpty(row, col - 1, chessBoard))
                                returnList.add(new Coordinate(row, col - 1));
                            else if (checkPositionEnemy(row, col - 1, chessBoard, chess)) {
                                returnList.add(new Coordinate(row, col - 1,true));
                            }
                        }
                    }

                }

                break;
            case "Zu":
                if(chess.getSide().equals("Red")){
                    //Horizontally move permitted over the river
                    if(col > 5){
                        if (row + 1 > 10);
                        else {
                            if (checkPositionEmpty(row + 1, col, chessBoard))
                                returnList.add(new Coordinate(row + 1, col));
                            else if (checkPositionEnemy(row + 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row + 1, col,true));
                            }
                        }
                        if(row - 1 < 1);
                        else {
                            if (checkPositionEmpty(row - 1, col, chessBoard))
                                returnList.add(new Coordinate(row - 1, col));
                            else if (checkPositionEnemy(row - 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row - 1, col,true));
                            }
                        }
                    }

                    if(col + 1 > 10);
                    else {
                        if (checkPositionEmpty(row, col + 1, chessBoard))
                            returnList.add(new Coordinate(row, col + 1));
                        else if (checkPositionEnemy(row, col + 1, chessBoard, chess)) {
                            returnList.add(new Coordinate(row, col + 1,true));
                        }
                    }
                    break;
                }
                else if (chess.getSide().equals("Black")){
                    //Horizontally move permitted over the river
                    if(col < 6){
                        if (row + 1 > 10);
                        else {
                            if (checkPositionEmpty(row + 1, col, chessBoard))
                                returnList.add(new Coordinate(row + 1, col));
                            else if (checkPositionEnemy(row + 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row + 1, col,true));
                            }
                        }
                        if(row - 1 < 1);
                        else {
                            if (checkPositionEmpty(row - 1, col, chessBoard))
                                returnList.add(new Coordinate(row - 1, col));
                            else if (checkPositionEnemy(row - 1, col, chessBoard, chess)) {
                                returnList.add(new Coordinate(row - 1, col,true));
                            }
                        }
                    }

                    if(col - 1 < 1);
                    else {
                        if (checkPositionEmpty(row, col - 1, chessBoard))
                            returnList.add(new Coordinate(row, col - 1));
                        else if (checkPositionEnemy(row, col - 1, chessBoard, chess)) {
                            returnList.add(new Coordinate(row, col - 1,true));
                        }
                    }
                    break;
                }
                break;

            case "Pao":
                //Non-conquering move
                for(int i = row - 1; i >= ROW_START; i--){
                    //If empty, move forward, else break
                    if(checkPositionEmpty(i, col, chessBoard)) {
                        returnList.add(new Coordinate(i, col));
                        continue;
                    }
                    else break;
                }

                for(int i = row + 1; i <= ROW_END; i++){
                    if(checkPositionEmpty(i, col, chessBoard)){
                        returnList.add(new Coordinate(i, col));
                        continue;
                    }
                    else break ;
                }
                for(int i = col - 1; i>= COLUMN_START; i--){
                    if(checkPositionEmpty(row, i, chessBoard)){
                        returnList.add(new Coordinate(row, i));
                        continue;
                    }
                    else break;
                }

                for(int i = col + 1; i <= COLUMN_END; i++){
                    if(checkPositionEmpty(row, i, chessBoard)){
                        returnList.add(new Coordinate(row, i));
                        continue;
                    }
                    else break;
                }

                //Conquering move
                boolean hasPaoRack = false;
                for(int i = row - 1; i >= ROW_START; i--){
                    if(checkPositionEmpty(i, col, chessBoard))
                        continue;
                    else {
                        if(!hasPaoRack) hasPaoRack = true;
                        else {
                            if(checkPositionEnemy(i, col, chessBoard, chess)){
                                returnList.add(new Coordinate(i, col,true));
                                break;
                            }
                            else break;
                        }
                    }
                }

                hasPaoRack = false;
                for(int i = row + 1; i <= ROW_END; i++){
                    if(checkPositionEmpty(i, col, chessBoard))
                        continue;
                    else {
                        if(!hasPaoRack) hasPaoRack = true;
                        else {
                            if(checkPositionEnemy(i, col, chessBoard, chess)){
                                returnList.add(new Coordinate(i, col,true));
                                break;
                            }
                            else;
                        }
                    }
                }

                hasPaoRack = false;
                for(int i = col - 1; i >= COLUMN_START; i--){
                    if(checkPositionEmpty(row, i, chessBoard))
                        continue;
                    else {
                        if(!hasPaoRack) hasPaoRack = true;
                        else {
                            if(checkPositionEnemy(row, i, chessBoard, chess)){
                                returnList.add(new Coordinate(row, i,true));
                                break;
                            }
                            else;
                        }
                    }
                }

                hasPaoRack = false;
                for(int i = col + 1; i <= COLUMN_END; i++){
                    if(checkPositionEmpty(row, i, chessBoard))
                        continue;
                    else {
                        if(!hasPaoRack) hasPaoRack = true;
                        else {
                            if(checkPositionEnemy(row, i, chessBoard, chess)){
                                returnList.add(new Coordinate(row, i,true));
                                break;
                            }
                            else;
                        }
                    }
                }
                break;
        }

        return returnList;
    }

    private static boolean checkPositionEmpty(Coordinate coordinate, ChessBoard chessBoard){
        return checkPositionEmpty(coordinate.getRow(), coordinate.getCol(), chessBoard);
    }
    private static boolean checkPositionEmpty(int row, int col, ChessBoard chessBoard){
        return chessBoard.getChessSet()[row][col].isEmpty();
    }

    public static boolean checkPositionEnemy(Coordinate coordinate, ChessBoard chessBoard, Chess chess)  {
        return checkPositionEnemy(coordinate.getRow(), coordinate.getCol(), chessBoard, chess);
    }
    public static boolean checkPositionEnemy(int row, int col, ChessBoard chessBoard, Chess chess) {
        //call the method to prepare to  conquer
        //whatever.conquerPrepare(int row, int col);

        if(chess.getSide().equals("Red")){
            return chessBoard.getChessSet()[row][col].getSide().equals("Black");
        }
        else if(chess.getSide().equals("Black")) {
            return chessBoard.getChessSet()[row][col].getSide().equals("Red");
        }

        //Potential risk in this line
        return false;
    }



}
