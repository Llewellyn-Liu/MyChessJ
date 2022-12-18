package core.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Template{

    private static int count = 0;

    private static ArrayList<JTextField> inputTextFieldList = new ArrayList<>();

    public static void prepareGUI(){
        /**
         * Structuring the GUI.
         * The workspace is divided into 2 sections, Work Space(workPanel)
         * and Profile(profilePanel, for other or future use)
         *
         */
        JFrame frame = new JFrame();
        frame.setTitle("Grade Calculator for Single Assignment");
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JPanel workPanel = new JPanel();
        workPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Work Space"), BorderFactory.createEmptyBorder(20,50,30,50)));
        workPanel.setPreferredSize(new Dimension(400,200));

        JPanel profilePanel = new JPanel();
        SpringLayout lLayout = new SpringLayout();

        profilePanel.setBorder(BorderFactory.createTitledBorder("Author"));
        profilePanel.setPreferredSize(new Dimension(200,200));
        contentPanel.add(workPanel, BorderLayout.WEST);
        contentPanel.add(profilePanel, BorderLayout.EAST);


        /**
         * Adding the components into the workspaces.
         */
        JLabel attr1 = new JLabel("Category");
        JLabel attr2 = new JLabel("Input");
        JLabel attr3 = new JLabel("Comment");
        attr1.setPreferredSize(new Dimension(100,20));
        attr2.setPreferredSize(new Dimension(100,20));
        attr3.setPreferredSize(new Dimension(100,20));
        workPanel.add(attr1);
        workPanel.add(attr2);
        workPanel.add(attr3);
        lLayout.putConstraint(SpringLayout.WEST, attr1, 5,SpringLayout.WEST,workPanel);
        lLayout.putConstraint(SpringLayout.NORTH, attr1, 5,SpringLayout.NORTH, workPanel);
        lLayout.putConstraint(SpringLayout.WEST, attr2, 5,SpringLayout.EAST, attr1);
        lLayout.putConstraint(SpringLayout.NORTH, attr2, 5,SpringLayout.NORTH, workPanel);
        lLayout.putConstraint(SpringLayout.WEST, attr3, 5,SpringLayout.EAST, attr2);
        lLayout.putConstraint(SpringLayout.NORTH, attr3, 5,SpringLayout.NORTH, workPanel);

        String[] attrList = {"Total Score:", "Eared Score:", "Percentage(%):"};
        Component formerCol1 = attr1;
        Component formerCol2 = attr2;
        Component formerCol3 = attr3;

        for(int i = 0; i< attrList.length; i++){
            //Components and relationships
            JTextField texti = new JTextField(""+i);
            JLabel labeli = new JLabel(attrList[i]);
            JLabel commenti = new JLabel("for assignment 0");
            texti.setColumns(5);
            labeli.setPreferredSize(new Dimension(100,20));
            texti.setPreferredSize(new Dimension(100,20));
            commenti.setPreferredSize(new Dimension(100,20));
            inputTextFieldList.add(texti);
            workPanel.add(labeli);
            workPanel.add(texti);
            workPanel.add(commenti);

            labeli.setLabelFor(texti);
            commenti.setLabelFor(labeli);

            //Layout
            lLayout.putConstraint(SpringLayout.WEST, labeli, 5,SpringLayout.WEST,workPanel);
            lLayout.putConstraint(SpringLayout.NORTH, labeli, 5,SpringLayout.SOUTH, formerCol1);
            lLayout.putConstraint(SpringLayout.WEST, texti, 5,SpringLayout.EAST, labeli);
            lLayout.putConstraint(SpringLayout.NORTH, texti, 5,SpringLayout.SOUTH, formerCol2);
            lLayout.putConstraint(SpringLayout.WEST, commenti, 5,SpringLayout.EAST, texti);
            lLayout.putConstraint(SpringLayout.NORTH, commenti, 5,SpringLayout.SOUTH, formerCol3);
            formerCol1 = labeli;
            formerCol2 = texti;
            formerCol3 = commenti;

        }

        /**
         * Result area
         */
        JButton confirmBt = new JButton("Calculate");
        JLabel resultLabel = new JLabel("Defaut Output.");
        workPanel.add(confirmBt);
        workPanel.add(resultLabel);
        lLayout.putConstraint(SpringLayout.WEST, confirmBt, 5,SpringLayout.WEST,workPanel);
        lLayout.putConstraint(SpringLayout.NORTH, confirmBt, 5,SpringLayout.SOUTH, formerCol1);
        lLayout.putConstraint(SpringLayout.WEST, resultLabel, 5,SpringLayout.EAST, confirmBt);
        lLayout.putConstraint(SpringLayout.NORTH, resultLabel, 5,SpringLayout.SOUTH, formerCol2);

        JButton refreshBt = new JButton("Refresh");
        workPanel.add(refreshBt);
        lLayout.putConstraint(SpringLayout.WEST, refreshBt, 5,SpringLayout.EAST,resultLabel);
        lLayout.putConstraint(SpringLayout.NORTH, refreshBt, 0,SpringLayout.NORTH, confirmBt);

        /**
         * Profile Panel(for author info and other usage)
         */
        profilePanel = generateProfilePanel(profilePanel);

        workPanel.setLayout(lLayout);

        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setVisible(true);

        /**
         * Registering the function
         */

        refreshBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JTextField tf: getInputTextFieldList()){
                    tf.setText("0");
                }
            }
        });
    }

    private static JPanel generateProfilePanel(JPanel profilePanel) {
        String[] attrList = {"Runlin Liu", "INFO 5100","Section 8",
                String.format("%s", DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US).format(new Date()))};

        SpringLayout layout = new SpringLayout();

        JLabel emptyLabel = new JLabel();
        profilePanel.add(emptyLabel);
        emptyLabel.setPreferredSize(new Dimension(100,20));

        for(int i = 0; i< attrList.length; i++){
            //Components and relationships
            JLabel itemi = new JLabel(attrList[i]);

            itemi.setPreferredSize(new Dimension(100,20));

            profilePanel.add(itemi);

            itemi.setHorizontalAlignment(SwingConstants.CENTER);
        }

        return profilePanel;
    }

    private static int count() {
        return count++;
    }

    public static ArrayList<JTextField> getInputTextFieldList() {
        return inputTextFieldList;
    }

}
