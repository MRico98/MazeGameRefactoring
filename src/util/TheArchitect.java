package util;

import javax.swing.*;

public class TheArchitect extends JFrame
{
    private String[][] updatedMatrix;
    private static int wallxcord;
    private static int wallycord;
    private int collected=0;
    private boolean level;
    private int globalTotalDimonds=0;

   public void setExit(int x, int y) {
       wallxcord=x;
       wallycord=y;
   }

   public void showWall() {
       updatedMatrix[wallxcord][wallycord]="E";
   }

    public void playerMove(int xScale, int yScale, String[][] currentMatrix,int totalDimonds)throws StupidAssMove
    {
       int x=0;
       int y=0;
       int found=0;
       globalTotalDimonds=totalDimonds;
       nextLevel(false);
       String[][] junkMatrix=currentMatrix;
        for (int i = 0; i < currentMatrix.length; i++)
        {
        for (int j = 0; j < currentMatrix[i].length; j++) 
        {
           if(currentMatrix[i][j].equals("P"))
           {
            x=i;
            y=j;
            found = 1;
            break;
           }
        }}
            if(currentMatrix[x+xScale][y+yScale].equals("H"))
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P";
                currentMatrix[x][y]="N";
                collected+=1;
            }
            else if(currentMatrix[x+xScale][y+yScale].equals("D"))
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P";
                collected+=1;
            }
            else if(currentMatrix[x+xScale][y+yScale].equals("M") && currentMatrix[x+(xScale*2)][y+(yScale*2)].equals("N"))
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
                currentMatrix[x+(xScale*2)][y+(yScale*2)]="M";
            }
            else if (currentMatrix[x+xScale][y+yScale].equals("N"))
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
            }
            else if (currentMatrix[x+xScale][y+yScale].equals("E"))
            {
                currentMatrix[x][y]="N";
                currentMatrix[x+xScale][y+yScale]="P"; 
                nextLevel(true);
            }
            else
               throw new StupidAssMove("Ass Hole hit wall!");
                
            if(collected==totalDimonds)
            showWall();
               
            updatedMatrix=currentMatrix;
        }

    public void nextLevel(boolean tOrF)
    {
        level=tOrF;
    }
    
    public boolean getLevel()
    {
        return level;
    }
        
    public int getDimondsLeft()
    {
        return globalTotalDimonds-collected;
    }
    
    public String[][] getUpdatedMatrix()
    {
        return updatedMatrix;    
    }
    
    private class StupidAssMove extends RuntimeException
    {
         public StupidAssMove(String event)
         {
             JFrame frame = new JFrame("Warning");
             JOptionPane.showMessageDialog(frame, "You Stupid Ass, Ran into something did you?");
         }
    }

}
