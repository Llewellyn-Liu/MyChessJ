package core.gui;

import core.component.Chess;
import core.component.GameContext;
import core.controller.ChessBoardController;
import core.controller.GameController;
import core.run.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainGUI {

    private static Logger logger = LogManager.getLogger(MainGUI.class);

    public static JLabel infoLabel;

    private static JFrame fileDialog;

    public static JPanel chessBoardPanel;
//
//    private static MouseListener mouseListener;

    public static void prepareGUI(){

        JFrame frame = new JFrame();
        frame.setTitle("MyChessJ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Game");
        menuBar.add(menu1);
        JMenuItem item1 = new JMenuItem("Save");
        JMenuItem item2 = new JMenuItem("Load");
        menu1.add(item1);
        menu1.add(item2);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //Draw Chessboard
        chessBoardPanel = new JPanel();
        chessBoardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("ChessBoard"),
                BorderFactory.createEmptyBorder(0,50,30,50)));
        chessBoardPanel.setPreferredSize(new Dimension(400,400));
        System.out.println(GameController.getInstance().getCurrentGame().getChessBoard().getChessList().size());

        Resource.drawChessBoard(chessBoardPanel);


        //Draw Controller Panel
        JPanel controllerPanel = new JPanel();
        controllerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Controllers"),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        controllerPanel.setPreferredSize(new Dimension(200,400));
        JButton startBt = new JButton("Start a New Game");
        JLabel rowLabel = new JLabel("Row: ");
        JTextField rowTf = new JTextField(2);
        JLabel colLabel = new JLabel("Col: ");
        JTextField colTf = new JTextField(2);
        JButton moveBt = new JButton("move");
        JButton surrenderBt = new JButton("Surrender");
        JButton magicBt = new JButton("Magic");
        JLabel magicLabel = new JLabel("");
        controllerPanel.add(startBt);

        controllerPanel.add(rowLabel);
        controllerPanel.add(rowTf);
        controllerPanel.add(colLabel);
        controllerPanel.add(colTf);

        controllerPanel.add(moveBt);
        controllerPanel.add(surrenderBt);
        controllerPanel.add(magicBt);
        controllerPanel.add(magicLabel);

        //Draw the Conquered Chess Stack
        JPanel conqueredPanel = new JPanel();
        conqueredPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Captured"),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        conqueredPanel.setPreferredSize(new Dimension(200,100));
        stuffConqueredPanel(conqueredPanel);



        //Draw Other Decorations
        JPanel profilePanel = new JPanel();
        profilePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Other"),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        profilePanel.setPreferredSize(new Dimension(200,400));
        JLabel authorTag1 = new JLabel("Author:");
        JLabel authorTag2 = new JLabel("Runlin Liu");
        JLabel authorTag3 = new JLabel("INFO 5100");
        profilePanel.add(authorTag1);
        profilePanel.add(authorTag2);
        profilePanel.add(authorTag3);

        //Draw notification panel
        infoLabel = new JLabel("This is a notification.");
        //Listeners
        startBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Restart the game controller, all components are released before generating a new one.
                try {
                    GameController.getInstance().init();
                    GameController.getInstance().isMagicModeOn = false;
                    magicLabel.setText("");
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                chessBoardPanel.repaint();
            }
        });

        surrenderBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().end();
                chessBoardPanel.repaint();
            }
        });

        magicBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().isMagicModeOn = true;
                logger.info("Magic mode on");
                magicLabel.setText("Magic Mode ON");
            }
        });

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int X = e.getX(), Y = e.getY();

                if(X<Resource.observed_startX || Y<Resource.observed_startY
                        ||X>Resource.observed_startX+8.5*Resource.gridSize
                        ||Y>Resource.observed_startY+9.5*Resource.gridSize)
                    return;
                //Select a chess to enter the Confirmation stage
                //But in the code we need to detect if a chess is already selected first.
                if(Resource.requirePossibleMove()){
                    if(Resource.moveTargetDetected(X, Y)){
                        ChessBoardController.newMove(GameController.getInstance().getCurrentGame().getMoveStack(),
                                Resource.getDetectedChess().getCoordinate(),Resource.getDetectedMoveTarget());
                    }
                    GameController.getInstance().conductMove();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    Resource.clear();
                    chessBoardPanel.repaint();
                    stuffConqueredPanel(conqueredPanel);
                    return;
                }

                if(Resource.chessDetected(X, Y)){
                    if(!rowTf.isEditable()){
                        rowTf.setEditable(true);
                        colTf.setEditable(true);
                    }
                    logger.info("Mouse Event Clicked" + Resource.getChessState(X, Y));
                    infoLabel.setText(Resource.getChessState(X, Y));
                    rowTf.setText(""+Resource.getDetectedChess().getCoordinate().getRow());
                    colTf.setText(""+Resource.getDetectedChess().getCoordinate().getCol());

                    Resource.setRequirePossibleMove(true);
                    chessBoardPanel.repaint();

                }
                else{
                    infoLabel.setText("Not selectable");
                    rowTf.setEditable(false);
                    colTf.setEditable(false);
                    Resource.setRequirePossibleMove(false);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        chessBoardPanel.addMouseListener(mouseListener);

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.write(GameController.getInstance().getCurrentGame().state());
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int rev = fileChooser.showDialog(fileDialog, "Confirm");
                if(rev == JFileChooser.APPROVE_OPTION){
                    System.out.println(fileChooser.getSelectedFile().getName());
                    File f = fileChooser.getSelectedFile();
                    try {
                        GameController.getInstance().init(f);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }});


        //Final Steps
        contentPanel.add(infoLabel, BorderLayout.NORTH);
        contentPanel.add(chessBoardPanel, BorderLayout.WEST);
        contentPanel.add(controllerPanel, BorderLayout.CENTER);
        contentPanel.add(profilePanel, BorderLayout.EAST);
        contentPanel.add(conqueredPanel, BorderLayout.SOUTH);

        frame.setJMenuBar(menuBar);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setVisible(true);
    }


    private static void stuffConqueredPanel(JPanel conqueredPanel) {
        conqueredPanel.removeAll();
        for(Chess c: GameController.getInstance().getCurrentGame().getChessConquered()){
            JLabel currentLabel = new JLabel();
            currentLabel.setIcon(Resource.getIcon(c.getType(), c.getSide()));
            conqueredPanel.add(currentLabel);
        }

    }

    public static void switchOff(){
//        chessBoardPanel.removeMouseListener(mouseListener);
    }


}
