package core.controller;

import core.component.Coordinate;
import core.component.GameContext;
import core.component.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import core.gui.MainGUI;
import core.run.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class GameController {

    private static Logger logger = LogManager.getLogger(GameController.class);
    private static GameController instance = new GameController();

    private GameContext currentGame;

    private boolean isGUIOn = false;

    /**
     * The program will generate a log.txt to record every operation in the game.
     */
    private static File logURL;


    private int status = 0;

    public int getStatus() {
        return status;
    }

    public boolean isGUIOn() {
        return isGUIOn;
    }

    public boolean isMagicModeOn = false;

    //Life Cycle 1 - Initialization
    public void init() throws InterruptedException {
        logger.info("GameController Life Cycle: Initializing");
        currentGame = new GameContext();
        //needConfirm()
        //prepareGUI();
        currentGame.state();

        if(isGUIOn){

        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainGUI.prepareGUI();
                }
            });
            isGUIOn = true;
            Thread.sleep(200);
        }
        //Start GUI

        logURL = new File("./src/main/resources/auto-log.txt");
        write(getCurrentGame().getChessBoard().getDefaultLayoutString());
        status = 1;
    }


    public void init(File file) throws InterruptedException {
        logger.info("GameController Life Cycle: Initializing");
        currentGame = new GameContext(file);
        //needConfirm()
        //prepareGUI();
        currentGame.state();

        if(isGUIOn){
            MainGUI.chessBoardPanel.repaint();
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainGUI.prepareGUI();
                }
            });
            isGUIOn = true;
            Thread.sleep(500);
        }
        //Start GUI

        status = 1;
    }


    //Life Cycle 2 - Run
    public void run(){
        logger.info("GameController Life Cycle: Running");
        while (status < 2){
            listenChoice();
            conductMove();
            getCurrentGame().state();
            if(getCurrentGame().getSide()== 1)
                getCurrentGame().nextRound();
            getCurrentGame().switchSide();
        }

    }


    //Life Cycle 3 - End
    public void end(){
        logger.info("GameController Life Cycle: Game End");
//        freeMemory();
        MainGUI.infoLabel.setText("Game End.");
//        MainGUI.switchOff();
        status = 2;
    }


    public void listenChoice() {
        logger.info("GameController Listening user's choice");
        Scanner input = new Scanner(System.in);

        GameController.getInstance().getCurrentGame().state();

        //Read a chess selection
        System.out.println("Please select a chess: ");
        Coordinate unconfirmedFromCoordinate = new Coordinate(input.next());

        if(getCurrentGame().getChessBoard().getChess(unconfirmedFromCoordinate).isEmpty()){
            System.out.println("The position selected is Empty.");
            return;
        }

        ArrayList<Coordinate> possibleTarget = ChessBoardController.getPossibleMove(getCurrentGame().getChessBoard(), unconfirmedFromCoordinate);
        //Read a chess movement
        System.out.println("Please select a target: ");
        Coordinate unconfirmedTargetCoordinate = new Coordinate(input.next());

        //if a target coordinate is not available, prompt user to enter a available one
        while(!ChessBoardController.checkMove(possibleTarget, unconfirmedTargetCoordinate)){
            System.out.println("Please select a target: ");
            unconfirmedTargetCoordinate = new Coordinate(input.next());
        }

        ChessBoardController.newMove(getCurrentGame().getMoveStack(), unconfirmedFromCoordinate, unconfirmedTargetCoordinate);
        logger.info("GameController Listening finished.");
    }


    public void conductMove() {
        logger.info("GameController Conduct Move");
        if(getCurrentGame().getMoveStack().isEmpty()){
            System.out.println("Error, no move in the stack");
            return;
        }

        ChessBoardController.move(getCurrentGame().getChessBoard(), getCurrentGame().getChessConquered(), getCurrentGame().getMoveStack().get());
        logger.info("GameController Move Conduct Finished.");
    }

    public GameContext getCurrentGame() {
        return currentGame;
    }

    public static GameController getInstance() {
        return instance;
    }

    public static String state(){
        return getInstance().getCurrentGame().state();
    }


    public static void write(String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logURL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
