package core.gui;

import core.component.Chess;
import core.component.ChessBoard;
import core.component.Coordinate;
import core.controller.ChessBoardController;
import core.controller.GameController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * To CHEAT! To SNEAK! 69 years old man!
 * Is it good? Not good!
 */
public class Resource {

    private static Logger logger = LogManager.getLogger(Resource.class);
    public static int gridSize = 35;

    public static int chessSize = 30;

    public static int row_start = 50;

    public static int col_start = 40;

    //The Observed position of very northwest of the chessboard
    public static int observed_startX = 35;

    public static int observed_startY = 48;



    public static ImageIcon jiang_red;
    public static ImageIcon jiang_black;
    public static ImageIcon shi_red;
    public static ImageIcon shi_black;
    public static ImageIcon xiang_red;
    public static ImageIcon xiang_black;
    public static ImageIcon ma_red;
    public static ImageIcon ma_black;
    public static ImageIcon ju_red;
    public static ImageIcon ju_black;
    public static ImageIcon pao_red;
    public static ImageIcon pao_black;
    public static ImageIcon zu_red;
    public static ImageIcon zu_black;

    //Attributes that temporarily used in programme
    private static Chess detectedChess;

    private static Coordinate detectedMoveTarget;

    private static boolean requirePossibleMove;



    static {
        jiang_red = new ImageIcon("./src/main/resources/jiang-red.png");
        jiang_red.setImage(jiang_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        jiang_black = new ImageIcon("./src/main/resources/jiang-black.png");
        jiang_black.setImage(jiang_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        shi_red = new ImageIcon("./src/main/resources/shi-red.png");
        shi_red.setImage(shi_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        shi_black = new ImageIcon("./src/main/resources/shi-black.png");
        shi_black.setImage(shi_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

        xiang_red = new ImageIcon("./src/main/resources/xiang-red.png");
        xiang_red.setImage(xiang_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        xiang_black = new ImageIcon("./src/main/resources/xiang-black.png");
        xiang_black.setImage(xiang_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

        ma_red = new ImageIcon("./src/main/resources/ma-red.png");
        ma_red.setImage(ma_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        ma_black = new ImageIcon("./src/main/resources/ma-black.png");
        ma_black.setImage(ma_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

        ju_red = new ImageIcon("./src/main/resources/ju-red.png");
        ju_red.setImage(ju_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        ju_black = new ImageIcon("./src/main/resources/ju-black.png");
        ju_black.setImage(ju_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

        pao_red = new ImageIcon("./src/main/resources/pao-red.png");
        pao_red.setImage(pao_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        pao_black = new ImageIcon("./src/main/resources/pao-black.png");
        pao_black.setImage(pao_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

        zu_red = new ImageIcon("./src/main/resources/zu-red.png");
        zu_red.setImage(zu_red.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));
        zu_black = new ImageIcon("./src/main/resources/zu-black.png");
        zu_black.setImage(zu_black.getImage().getScaledInstance(chessSize,chessSize,Image.SCALE_AREA_AVERAGING));

    }

    public static void drawChessBoard(JPanel p){
        ChessBoardPanel canvas = new ChessBoardPanel();
        canvas.setPreferredSize(new Dimension(400,400));
        p.add(canvas);
        logger.info("Draw Chessboard finished.");
    }


    public static ImageIcon getIcon(String type, String side){
        switch (type){
            case "Ma":
                if(side.equals("Red"))
                    return ma_red;
                else if(side.equals("Black"))
                    return ma_black;
                break;
            case "Jiang":
                if(side.equals("Red"))
                    return jiang_red;
                else if(side.equals("Black"))
                    return jiang_black;
                break;
            case "Shi":
                if(side.equals("Red"))
                    return shi_red;
                else if(side.equals("Black"))
                    return shi_black;
                break;
            case "Xiang":
                if(side.equals("Red"))
                    return xiang_red;
                else if(side.equals("Black"))
                    return xiang_black;
                break;
            case "Ju":
                if(side.equals("Red"))
                    return ju_red;
                else if(side.equals("Black"))
                    return ju_black;
                break;
            case "Pao":
                if(side.equals("Red"))
                    return pao_red;
                else if(side.equals("Black"))
                    return pao_black;
                break;
            case "Zu":
                if(side.equals("Red"))
                    return zu_red;
                else if(side.equals("Black"))
                    return zu_black;
                break;
            default: return null;
        }
        return null;
    }

    /**
     * Use the chessDetected and getChessState together
     * @param X
     * @param Y
     * @return
     */
    public static boolean chessDetected(int X, int Y){
        int realX = (X - observed_startX)/gridSize+1;
        int realY = (Y - observed_startY)/gridSize+1;

        detectedChess = GameController.getInstance().getCurrentGame().getChessBoard().getChess(new Coordinate(realX, realY));

        if(detectedChess.getType().equals("None"))
            return false;
        else return true;
    }

    public static boolean moveTargetDetected(int X, int Y){
        int realX = (X - observed_startX)/gridSize+1;
        int realY = (Y - observed_startY)/gridSize+1;

        detectedMoveTarget = new Coordinate(realX, realY);
        for(Coordinate co: requestPossibleMoveForDetectedChess(GameController.getInstance().getCurrentGame().getChessBoard())){
            if(co.getRow() == detectedMoveTarget.getRow() && co.getCol() == detectedMoveTarget.getCol()){
                if(ChessBoardController.checkPositionEnemy(detectedMoveTarget, GameController.getInstance().getCurrentGame().getChessBoard(), detectedChess)){
                    detectedMoveTarget = new Coordinate(detectedMoveTarget.getRow(), detectedMoveTarget.getCol(), true);
                }
                return true;
            }
        }
        return false;
    }

    public static Coordinate getDetectedMoveTarget(){
        return detectedMoveTarget;
    }
    public static String getChessState(int X, int Y) {
        return "Chess selected: "+detectedChess.getType()+" "+detectedChess.getSide()+" "+detectedChess.getIdentifier();
    }

    public static Chess getDetectedChess() {
        return detectedChess;

    }

    public static ArrayList<Coordinate> requestPossibleMoveForDetectedChess(ChessBoard cb){
        return ChessBoardController.getPossibleMove(cb, detectedChess.getCoordinate());
    }

    public static void setRequirePossibleMove(boolean b){
        requirePossibleMove = b;
    }

    public static boolean requirePossibleMove() {
        return requirePossibleMove;
    }

    public static void clear(){
        detectedChess = null;
        detectedMoveTarget = null;
        requirePossibleMove = false;
    }

}
