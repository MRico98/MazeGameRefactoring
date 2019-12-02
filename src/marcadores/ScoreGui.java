package marcadores;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;

import static javax.swing.SwingConstants.LEFT;

public class ScoreGui extends JDialog implements ActionListener
{

    public ScoreGui()
    {
        super();
    }

    public void ScoreGui()
    {
        Container cp = getContentPane();
        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
        cp.add(ok,BorderLayout.SOUTH);
        try{
            String line = "";
            String[] myScoreArray = new String[100];
            Arrays.fill(myScoreArray, " ");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("marcadores/scores.txt")));
            int recordsCount=0;
            while(br1.readLine() != null) {
                line = br1.readLine();
                if(!line.equals("")) {
                    recordsCount+=1;
                    int tempPOS = line.indexOf('*');
                    String pos=line.substring(tempPOS+1);
                    int index = Integer.parseInt(pos);
                    if(myScoreArray[index].equals(" ")) {
                        myScoreArray[index]=line;
                    }
                    else {
                        for(int i=0; i<myScoreArray.length;i++) {
                            if(index+i<myScoreArray.length) {
                                if (myScoreArray[index + i].equals(" ")) {
                                    myScoreArray[index + 1] = line;
                                }
                            }
                        }
                    }
                    JPanel scorePanel = new JPanel();
                    scorePanel.setLayout(new GridLayout(recordsCount,recordsCount));
                    for (String s : myScoreArray) {
                        if (!s.equals(" ")) {
                            JLabel mainLabel = new JLabel(s, LEFT);//display the score on the screen
                            scorePanel.add(mainLabel);
                        }
                    }
                    cp.add(scorePanel);
                }
            }
        }
        catch(IOException ex) {
            JFrame frame = new JFrame("Alert");
            JOptionPane.showMessageDialog(frame, "Problem with scores.txt file.  Cant load high Scores");
        }
        pack();
        setVisible (true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}