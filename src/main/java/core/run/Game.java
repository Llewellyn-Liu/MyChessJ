package core.run;

import core.controller.GameController;
import core.gui.MainGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class Game {

    private static Logger logger = LogManager.getLogger(Game.class);

    public static void run() throws InterruptedException {
        logger.info("Game Start.");
        GameController game = GameController.getInstance();
        game.init();
        game.run();
        game.end();
    }

    public static void main(String[] args) throws InterruptedException {
        run();

    }
}
