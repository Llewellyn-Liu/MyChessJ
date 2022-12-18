package core.run;

import com.sun.tools.javac.Main;
import core.component.Chess;
import core.component.ChessBoard;
import core.component.Coordinate;
import core.component.GameContext;
import core.controller.ChessBoardController;
import core.controller.GameController;
import core.gui.MainGUI;
import core.gui.TestGUI;

import javax.swing.*;
import java.io.File;

public class Test {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                TestGUI.prepareGUI();
//            }
//        });

        ChessBoard cb = new ChessBoard(new File("./src/main/resourse/log.txt"));
        cb.state();
    }
}
