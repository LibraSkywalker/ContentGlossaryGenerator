package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Kernel.Kernel.*;

/**
 * Created by Bill on 2016/4/26.
 */
public class myWindows {
    public static void setUI() {
        Frame frame = new Frame("词汇表自动生成软件");

        JButton[] button = new JButton[4];
        TextField[] text = new TextField[3];

        button[0] = new JButton("Generate");

        button[1] = new JButton("Learned");

        button[2] = new JButton("Learning");

        button[3] = new JButton("Fix");

        button[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text[0] != null && !text[0].getText().equals("")) {
                    generate(text[0].getText());
                    text[0].setText("");
                }
            }
        });

        button[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text[1] != null && !text[1].getText().equals("")) {
                    know(text[1].getText());
                    text[1].setText("");
                }
            }
        });


        button[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text[2] != null && !text[2].getText().equals("")) {
                    learn(text[2].getText());
                    text[2].setText("");
                }
            }
        });

        button[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fix();
            }
        });

        frame.setLayout(new FlowLayout());
        frame.setSize(350, 350);
        frame.setVisible(true);

        Panel[] panel = new Panel[3];

        for (int i = 0; i < 3; i++) {
            panel[i] = new Panel();
            panel[i].add(button[i]);

            text[i] = new TextField();
            text[i].setColumns(10);
            panel[i].add(text[i]);


            if (i == 0) {
                JButton loadButton = new JButton("...");

                loadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        text[0].setText(setUI2());
                    }
                });

                panel[i].add(loadButton);
            }
            frame.add(panel[i]);
        }
        frame.add(button[3]);
        frame.addWindowListener(new myWindowsListener());
    }

    public static String setUI2(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        String now = fileChooser.getSelectedFile().toString();
        return now;
    }

    public static void fix(){
        for (String word : newWords.data) {
            int response = JOptionPane.showConfirmDialog(null, "Do you know the word " + word + " ?", "WordList Fix", JOptionPane.YES_NO_OPTION);
            if (response == 0){
                known.add(word);
            }
        }
        known.save();
        JOptionPane.showMessageDialog(null,"You have fixed the word list");
    }
}
