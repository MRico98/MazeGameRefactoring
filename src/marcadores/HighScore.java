package marcadores;

import java.io.*;

import static java.lang.System.*;

public class HighScore
{

    public void addHighScore(String name, int min, int sec,int level)
    {
        String outData="PlayerName: "+name+" Total Time for Levels:"+min+":"+sec+ "(Minutes:Seconds)"+ "Level Reached:*" + level;
        try(PrintWriter out = new PrintWriter(new FileOutputStream("marcadores/scores.txt",true))){
            out.println("");
            out.println(outData);
        }
        catch(Exception ex){
            out.println(ex.getMessage());
        }
        finally {
            out.println("Se ha escrito en el documento");
        }
    }

}