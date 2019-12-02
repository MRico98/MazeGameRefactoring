package kernel;

import marcadores.HighScore;
import marcadores.ScoreGui;
import tiempo.TimeCalculator;
import tiempo.TimeKeeper;
import util.FileLoader;
import util.TheArchitect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.lang.System.*;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;

public class GameGui extends JFrame implements ActionListener
{
    private static HighScore hs;
    private int catFileName= 1;
    private Container cp;
    private static FileLoader fl = new FileLoader();
    private JLabel shagLabel;
    private int ix;
    private int jx;
    private int timeLeft;
    private JPanel progBarPanel;
    private JLabel[][] labelMatrix;
    private  JProgressBar progressBar;
    private JPanel newPanel;
    private TheArchitect theArc = new TheArchitect();
    private String[][] scrapMatrix;
    private  Timer timely;
    private static TimeKeeper tk;
    private  String playerName;
    private int levelNum=1;
    private static final String NUEVOJUEGO = "New Game";
    private static final String NUEVACARGA = "newLoad";

    public static void main(String[] args)
    {
        new GameGui();
    }

    private GameGui()
    {
        super("Maze, a game of wondering");
        configFondoMenu();
        configMenuBar();
        newPanel = new JPanel();
        hs = new HighScore();
        tk=new TimeKeeper();
        pack();
        setVisible (true);
    }

    private void configFondoMenu(){
        cp=getContentPane();
        shagLabel = new JLabel("",new ImageIcon("C:\\Users\\mrico\\Documents\\ProyectosJava\\MazeRefactor\\src\\imagenes\\yeababyyea.jpg"), LEFT);//GUI background for initial load
        cp.add(shagLabel);
    }

    private void configMenuBar(){
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, CTRL_DOWN_MASK));
        JMenuItem itemSaveScore = new JMenuItem("Save High Score");
        itemSaveScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, CTRL_DOWN_MASK));
        JMenuItem itemHighScore = new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, CTRL_DOWN_MASK));
        JMenuItem itemEnterName = new JMenuItem("Enter Player Name");
        itemEnterName.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, CTRL_DOWN_MASK));
        JMenuItem newGameItem = new JMenuItem(NUEVOJUEGO);
        JMenuItem openFileItem = new JMenuItem("Open Maze File.");
        openFileItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_O, CTRL_DOWN_MASK));
        configMenuItemsActions(newGameItem,itemEnterName,itemSaveScore,itemHighScore,itemExit,openFileItem);
        addJMenuBar(addToJMenuBar(addToJMenu(newGameItem,itemEnterName,openFileItem,itemHighScore,itemSaveScore,itemExit)));
    }

    private void configMenuItemsActions(JMenuItem newGameItem, JMenuItem itemEnterName, JMenuItem itemSaveScore, JMenuItem itemHighScore, JMenuItem itemExit, JMenuItem openFileItem){
        newGameItem.setActionCommand(NUEVOJUEGO);
        newGameItem.addActionListener(this);
        itemEnterName.setActionCommand("EnterName");
        itemEnterName.addActionListener(this);
        itemSaveScore.setActionCommand("SaveScore");
        itemSaveScore.addActionListener(this);
        itemHighScore.setActionCommand("marcadores.HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        openFileItem.setActionCommand("Open");
        openFileItem.addActionListener(this);
    }

    private JMenu addToJMenu(JMenuItem newGameItem,JMenuItem itemEnterName,JMenuItem openFileItem,JMenuItem itemHighScore,JMenuItem itemSaveScore,JMenuItem itemExit){
        JMenu newMenu = new JMenu("File");
        newMenu.add(newGameItem);
        newMenu.add(itemEnterName);
        newMenu.add(openFileItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemSaveScore);
        newMenu.add(itemExit);
        return newMenu;
    }

    private JMenuBar addToJMenuBar(JMenu newMenu){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(newMenu);
        return menuBar;
    }

    private void addJMenuBar(JMenuBar menuBar){
        setJMenuBar(menuBar);
    }

    private class MyKeyHandler extends KeyAdapter
    {
        @Override
        public void keyPressed (KeyEvent theEvent)
       {         
           switch (theEvent.getKeyCode())
           {
               case KeyEvent.VK_UP:
                   playerMove(-1,0);
                   break;
               case KeyEvent.VK_DOWN:
                  playerMove(1,0);
                  break;
               case KeyEvent.VK_LEFT:
                 playerMove(0,-1);
                 break;
               case KeyEvent.VK_RIGHT:
                 playerMove(0,1);
                 break;
               default:
                   return;
           }
           JLabel mainLabel=new JLabel("Total Dimonds Left to Collect"+theArc.getDimondsLeft()+"", CENTER);
           JPanel dimondsPanel = new JPanel();
           dimondsPanel.add(mainLabel);
           cp.add(dimondsPanel,BorderLayout.SOUTH);
       }

        private void playerMove(int xscale,int yscale){
            theArc.playerMove(xscale,yscale,scrapMatrix,fl.dimondCount());
            loadMatrixGui("updateLoad");
            if (theArc.getLevel()) {
                nextLevelLoad();
            }
        }

        private void nextLevelLoad()
        {
            levelNum+=1;
            tk.TimeKeeper(timeLeft,ix);
            timely.stop();
            theArc = new TheArchitect();
            catFileName+=1;
            String fileName="level"+catFileName+".maz";
            fl.loadFile(fileName);
            scrapMatrix=fl.getGameMatrix();
            theArc.setExit(fl.ExitXCord(),fl.ExitYCord());
            loadMatrixGui("newLoad");
        }

    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                new Timer(1000, updateCursorAction).stop();
                exit(0);
                return;
            case NUEVOJUEGO:
                return;
            case "EnterName":
                playerName = JOptionPane.showInputDialog("Please Enter your Earth Name");
                break;
            case "marcadores.HighScore":
                ScoreGui sg = new ScoreGui();
                sg.ScoreGui();
                break;
            case "SaveScore":
                hs.addHighScore(playerName, tk.getMinutes(), tk.getSeconds(), levelNum);
                break;
            case "Open":
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = "C:\\Users\\mrico\\Documents\\ProyectosJava\\MazeRefactor\\src\\" +
                            "niveles\\";
                    fl.loadFile(path + chooser.getSelectedFile().getName());
                    theArc.setExit(fl.ExitXCord(), fl.ExitYCord());
                    loadMatrixGui(NUEVACARGA);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }

     private void loadMatrixGui(String event)
     {
        if (event.equals(NUEVACARGA))
         {       
             remove(newPanel);
             if(progBarPanel !=null){
                 remove(progBarPanel);
             }
             String[][] temp = fl.getGameMatrix();
             scrapMatrix = new String[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];
             for (int i = 0; i < scrapMatrix.length; i++){
                 for (int j = 0; j < scrapMatrix[i].length; j++){
                     scrapMatrix[i][j]= temp[i][j];
                     scrapMatrix[i] = Arrays.copyOf(temp[i],scrapMatrix[i].length);
                 }
             }
             TimeCalculator timeCalc = new TimeCalculator();
             timeCalc.calcTimeforMaze(fl.dimondCount(),fl.getMatrixSizeRow(),fl.getMatrixSizeColumn());
             timeLeft= timeCalc.getMinutes();
             ix= timeCalc.getSeconds();
             jx=0;
             timely = new Timer(1000,updateCursorAction);
             timely.start();
             progBarPanel = new JPanel();
             progressBar = new JProgressBar(0, timeCalc.getMinutes()*100);
             progressBar.setStringPainted(true);
             progBarPanel.add(progressBar);
             cp.add(progBarPanel,BorderLayout.NORTH);
             newPanel = new JPanel();
             newPanel.setLayout(new GridLayout(fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()));
             labelMatrix=new JLabel[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];
             newPanel.addKeyListener( new MyKeyHandler() );
        }
        else if(event.equals("updateLoad"))
        {
            scrapMatrix = theArc.getUpdatedMatrix();
            remove(newPanel);
            newPanel = new JPanel();
            newPanel.setLayout(new GridLayout(fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()));
            newPanel.addKeyListener( new MyKeyHandler() );
            newPanel.grabFocus();        
        }
          for (int i = 0; i < labelMatrix.length; i++){
              for (int j = 0; j < labelMatrix[i].length; j++){
                  labelMatrix[i][j] = new MazeObject(scrapMatrix[i][j]);
              }
          }
         cp.add(newPanel);
         remove(shagLabel);
         gc();
         pack();
         setVisible (true);
         newPanel.grabFocus();  
     }
 
    public class MazeObject extends JLabel
    {
        MazeObject(String fileName)
        {
            //No sirvio la ruta relativa, no se por que :(
            fileName = "C:\\Users\\mrico\\Documents\\ProyectosJava\\MazeRefactor\\src\\imagenes\\" + fileName;
            fileName+=".png";
            JLabel fancyLabel;
            out.println(fileName);
            fancyLabel = new JLabel("",new ImageIcon(fileName), LEFT);
            newPanel.add(fancyLabel);
        }
    }


    private Action updateCursorAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e)//this inner class generates an exeption if the player takes to long to finish a level
    {
        ix-=1;
        jx+=1;
        if(ix<0){
            ix=60;
            timeLeft-=1;
        }
    if(timeLeft==0 && ix==0) {
        timely.stop();
        JLabel yousuckLabel = new JLabel("",new ImageIcon("imagenes/yousuck.jpg"), LEFT);
        cp.add(yousuckLabel);
        remove(newPanel);
        remove(progBarPanel);
        pack();
        setVisible (true);
        timely.stop();
        catFileName-=1;
    if(catFileName<1)
        throw new SlowAssPlayer();
    else
        loadMatrixGui("newLoad");
    }
        progressBar.setValue(jx);
        progressBar.setString(timeLeft+":"+ix);
    }//end actionPerformed
};

    private class SlowAssPlayer extends RuntimeException
    {
        SlowAssPlayer()
        {
            hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
            JFrame frame = new JFrame("Warning");
            JOptionPane.showMessageDialog(frame, "You Stupid Ass, Did you eat to much for dinner?  Move Faster!");
        }
    }

}