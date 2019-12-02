package tiempo;

public class TimeCalculator
{

    private int minutes=0;
    private int seconds=0;

    public void calcTimeforMaze(int totalDimonds, int xSize, int ySize)
    {
        minutes+= (ySize / xSize) +1;
        if(totalDimonds >6 && totalDimonds*.10 + seconds <= 60) {
            minutes += (ySize / xSize) + 1;
        }
        else {
            minutes+=1;          
        }
        if (minutes == 0) {
            minutes = 2;
        }
     }
    
    public int getMinutes()
    {
        return minutes;    
    }

    public int getSeconds()
    {
        return seconds;
    }
}