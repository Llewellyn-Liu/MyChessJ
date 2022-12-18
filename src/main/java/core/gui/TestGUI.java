package core.gui;

import javax.swing.*;
import java.awt.*;

public class TestGUI {

    public static void prepareGUI(){

        JFrame frame = new JFrame();
        frame.setTitle("MyChessJ");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();

        ChessBoardPanel cbp = new ChessBoardPanel();
        cbp.setPreferredSize(new Dimension(100,100));
        contentPanel.add(cbp);
        
        frame.setContentPane(contentPanel);
//frame.pack();
        frame.setVisible(true);
    }

    private static void drawChessBoard(JPanel chessBoardPanel) {
        chessBoardPanel.add(new ChessBoardPanel());
        System.out.println("drawChessboard test");
    }

    private static void stuffConqueredPanel(JPanel conqueredPanel) {
        JLabel jiang_red = new JLabel();
        jiang_red.setIcon(Resource.jiang_red);
        conqueredPanel.add(jiang_red);

        JLabel jiang_black = new JLabel();
        jiang_black.setIcon(Resource.jiang_black);
        conqueredPanel.add(jiang_black);
        JLabel shi_red = new JLabel();
        shi_red.setIcon(Resource.shi_red);
        conqueredPanel.add(shi_red);

        JLabel xiang_black = new JLabel();
        xiang_black.setIcon(Resource.xiang_black);
        conqueredPanel.add(xiang_black);
        JLabel ma_red = new JLabel();
        ma_red.setIcon(Resource.ma_red);
        conqueredPanel.add(ma_red);

        JLabel ma_black = new JLabel();
        ma_black.setIcon(Resource.ma_black);
        conqueredPanel.add(ma_black);
        JLabel ju_red = new JLabel();
        ju_red.setIcon(Resource.ju_red);
        conqueredPanel.add(ju_red);

        JLabel ju_black = new JLabel();
        ju_black.setIcon(Resource.ju_black);
        conqueredPanel.add(ju_black);
        JLabel pao_red = new JLabel();
        pao_red.setIcon(Resource.pao_red);
        conqueredPanel.add(pao_red);

        JLabel pao_black = new JLabel();
        pao_black.setIcon(Resource.pao_black);
        conqueredPanel.add(pao_black);
        JLabel zu_red = new JLabel();
        zu_red.setIcon(Resource.zu_red);
        conqueredPanel.add(zu_red);

        JLabel zu_black = new JLabel();
        zu_black.setIcon(Resource.zu_black);
        conqueredPanel.add(zu_black);


    }


}
