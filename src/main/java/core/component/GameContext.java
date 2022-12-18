package core.component;

import java.io.File;
import java.util.ArrayList;

/**
 * An abstract class which encapsulates all necessary instance in a game.
 */
public class GameContext {

    private int id;
    private ChessBoard chessBoard;
    private MoveStack moveStack;
    private ConqueredList chessConquered;

    private int round;


    private int side;

    public GameContext() {
        this.id = (int) (Math.random() * 1000);
        this.chessBoard = new ChessBoard();
        this.moveStack = new MoveStack();
        this.chessConquered = new ConqueredList();
        this.round = 0;
        this.side = 0;
    }

    public GameContext(File file) {
        this.id = (int) (Math.random() * 1000);
        this.chessBoard = new ChessBoard(file);
        this.moveStack = new MoveStack();
        this.chessConquered = new ConqueredList();
        this.round = 0;
        this.side = 0;
    }



    public int getId() {
        return id;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public MoveStack getMoveStack() {
        return moveStack;
    }

    public ArrayList<Chess> getChessConquered() {
        return chessConquered.getList();
    }

    public int getRoundNum() {
        return round;
    }

    public int getSide() {
        return side;
    }

    public String getSideStr() {
        return side == 0 ? "Red" : "Black";
    }

    public void switchSide(){
        side = 1 - side;
    }

    public void nextRound(){
        round++;
    }

    //Show current info on the board, including current side and other information(if needed)
    public void statePrint() {
        System.out.printf("Current round %d, side: %s\n", round, side);
        System.out.println(state());
    }

    public String state(){
        return getChessBoard().state();
    }
}
