package core.gui;

import core.component.Chess;
import core.component.Coordinate;
import core.controller.ChessBoardController;
import core.controller.GameController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.StringLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessBoardPanel extends JPanel {

    private static Logger logger = LogManager.getLogger(ChessBoardPanel.class);

    private static int count = 1;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (GameController.getInstance().getStatus() == 0) {
            return;
        } else if (GameController.getInstance().getStatus() == 1) {

            Graphics2D canvas = (Graphics2D) g;
            //Draw the canvas outline
            canvas.setColor(Color.PINK);
            canvas.fill3DRect(0, 0, 400, 380, true);
            canvas.setColor(Color.BLACK);
            canvas.drawRect(0, 0, 400, 375);

            //Draw the grid
            int row_start = Resource.row_start;
            int col_start = Resource.col_start;

            canvas.setColor(Color.BLACK);
            canvas.setStroke(new BasicStroke(2));
            for (int i = 0; i < 10; i++) {
                canvas.drawLine(row_start, col_start + i * Resource.gridSize, row_start + 8 * Resource.gridSize, col_start + i * Resource.gridSize);
            }
            for (int i = 0; i < 9; i++) {
                if (i == 0 || i == 8)
                    canvas.drawLine(row_start + i * Resource.gridSize, col_start, row_start + i * Resource.gridSize, col_start + 5 * Resource.gridSize);
                else
                    canvas.drawLine(row_start + i * Resource.gridSize, col_start, row_start + i * Resource.gridSize, col_start + 4 * Resource.gridSize);
                canvas.drawLine(row_start + i * Resource.gridSize, col_start + 5 * Resource.gridSize, row_start + i * Resource.gridSize, col_start + 9 * Resource.gridSize);
            }

            //Draw Decorations
            //Don't know why they should minus 1
            int[] drawDecoAtRow = {2, 8}, drawDecoAtCol = {3, 8};
            for (int i : drawDecoAtRow) {
                for (int j : drawDecoAtCol) {
                    //Vertical Stroke
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize - 9, row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize - 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 9, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize + 9, row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize + 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 9, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 4);
                    //Horizontal Stroke
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 9, col_start + (j - 1) * Resource.gridSize - 3, row_start + (i - 1) * Resource.gridSize - 4, col_start + (j - 1) * Resource.gridSize - 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 9, col_start + (j - 1) * Resource.gridSize - 3, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 9, col_start + (j - 1) * Resource.gridSize + 3, row_start + (i - 1) * Resource.gridSize - 4, col_start + (j - 1) * Resource.gridSize + 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 9, col_start + (j - 1) * Resource.gridSize + 3, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 3);
                }
            }

            canvas.drawLine(row_start + 3 * Resource.gridSize, col_start, row_start + 5 * Resource.gridSize, col_start + 2 * Resource.gridSize);
            canvas.drawLine(row_start + 3 * Resource.gridSize, col_start + 7 * Resource.gridSize, row_start + 5 * Resource.gridSize, col_start + 9 * Resource.gridSize);
            canvas.drawLine(row_start + 5 * Resource.gridSize, col_start, row_start + 3 * Resource.gridSize, col_start + 2 * Resource.gridSize);
            canvas.drawLine(row_start + 5 * Resource.gridSize, col_start + 7 * Resource.gridSize, row_start + 3 * Resource.gridSize, col_start + 9 * Resource.gridSize);

            //Draw Chess
            logger.info("Drawing Chess");
            Chess chess = GameController.getInstance().getCurrentGame().getChessBoard().getChessList().get(0);

            for (Chess c : GameController.getInstance().getCurrentGame().getChessBoard().getChessList()) {
                if (!c.isConquered())
                    canvas.drawImage(Resource.getIcon(c.getType(), c.getSide()).getImage(), row_start + (int) ((c.getCoordinate().getRow() - 1.5) * Resource.gridSize), col_start + (int) ((c.getCoordinate().getCol() - 1.5) * Resource.gridSize), 30, 30, null);
            }


            //Draw Possible target positions
            if (Resource.requirePossibleMove()) {
                logger.info("Drawing possible moves");
                ArrayList<Coordinate> list = Resource.requestPossibleMoveForDetectedChess(GameController.getInstance().getCurrentGame().getChessBoard());
                logger.info("Possible move list size: " + list.size());
                for (Coordinate c : list) {

                    int startX = row_start + (c.getRow() - 1) * Resource.gridSize;
                    int startY = col_start + (c.getCol() - 1) * Resource.gridSize;
                    canvas.setColor(Color.GREEN);
                    canvas.draw3DRect(startX - 5, startY - 5, 10, 10, true);
//                    logger.info("Current possible move painting range: "+(startX-5)+" "+(startX +5)+" "+(startY-5)+" "+(startY+5));
                }

            }

        } else if (GameController.getInstance().getStatus() == 2) {

            Graphics2D canvas = (Graphics2D) g;
            //Draw the canvas outline
            canvas.setColor(Color.PINK);
            canvas.fill3DRect(0, 0, 400, 380, true);
            canvas.setColor(Color.BLACK);
            canvas.drawRect(0, 0, 400, 375);

            //Draw the grid
            int row_start = Resource.row_start;
            int col_start = Resource.col_start;

            canvas.setColor(Color.BLACK);
            canvas.setStroke(new BasicStroke(2));
            for (int i = 0; i < 10; i++) {
                canvas.drawLine(row_start, col_start + i * Resource.gridSize, row_start + 8 * Resource.gridSize, col_start + i * Resource.gridSize);
            }
            for (int i = 0; i < 9; i++) {
                if (i == 0 || i == 8)
                    canvas.drawLine(row_start + i * Resource.gridSize, col_start, row_start + i * Resource.gridSize, col_start + 5 * Resource.gridSize);
                else
                    canvas.drawLine(row_start + i * Resource.gridSize, col_start, row_start + i * Resource.gridSize, col_start + 4 * Resource.gridSize);
                canvas.drawLine(row_start + i * Resource.gridSize, col_start + 5 * Resource.gridSize, row_start + i * Resource.gridSize, col_start + 9 * Resource.gridSize);
            }

            //Draw Decorations
            //Don't know why they should minus 1
            int[] drawDecoAtRow = {2, 8}, drawDecoAtCol = {3, 8};
            for (int i : drawDecoAtRow) {
                for (int j : drawDecoAtCol) {
                    //Vertical Stroke
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize - 9, row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize - 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 9, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize + 9, row_start + (i - 1) * Resource.gridSize - 3, col_start + (j - 1) * Resource.gridSize + 4);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 9, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 4);
                    //Horizontal Stroke
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 9, col_start + (j - 1) * Resource.gridSize - 3, row_start + (i - 1) * Resource.gridSize - 4, col_start + (j - 1) * Resource.gridSize - 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 9, col_start + (j - 1) * Resource.gridSize - 3, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize - 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize - 9, col_start + (j - 1) * Resource.gridSize + 3, row_start + (i - 1) * Resource.gridSize - 4, col_start + (j - 1) * Resource.gridSize + 3);
                    canvas.drawLine(row_start + (i - 1) * Resource.gridSize + 9, col_start + (j - 1) * Resource.gridSize + 3, row_start + (i - 1) * Resource.gridSize + 4, col_start + (j - 1) * Resource.gridSize + 3);
                }
            }

            canvas.drawLine(row_start + 3 * Resource.gridSize, col_start, row_start + 5 * Resource.gridSize, col_start + 2 * Resource.gridSize);
            canvas.drawLine(row_start + 3 * Resource.gridSize, col_start + 7 * Resource.gridSize, row_start + 5 * Resource.gridSize, col_start + 9 * Resource.gridSize);
            canvas.drawLine(row_start + 5 * Resource.gridSize, col_start, row_start + 3 * Resource.gridSize, col_start + 2 * Resource.gridSize);
            canvas.drawLine(row_start + 5 * Resource.gridSize, col_start + 7 * Resource.gridSize, row_start + 3 * Resource.gridSize, col_start + 9 * Resource.gridSize);

            //Draw Chess
            logger.info("Drawing Chess");
            Chess chess = GameController.getInstance().getCurrentGame().getChessBoard().getChessList().get(0);

            for (Chess c : GameController.getInstance().getCurrentGame().getChessBoard().getChessList()) {
                if (!c.isConquered())
                    canvas.drawImage(Resource.getIcon(c.getType(), c.getSide()).getImage(), row_start + (int) ((c.getCoordinate().getRow() - 1.5) * Resource.gridSize), col_start + (int) ((c.getCoordinate().getCol() - 1.5) * Resource.gridSize), 30, 30, null);
            }

            String s = "Game Over.";
            canvas.setFont(new Font( "SansSerif", Font.PLAIN, 25 ));
            canvas.drawChars(s.toCharArray(), 0,s.length(),130,200);


        }

    }

}


