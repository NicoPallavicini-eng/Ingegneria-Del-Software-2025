package it.polimi.ingsw.galaxytrucker.View.VirtualModel.GamePackage;

//todo fix
//the Timer class implementation is kept (in comments) for possible future use
public class Hourglass {
    private int FlipNumber = 0;
//    private Boolean hasEnded;
//    private Timer timer = new Timer();
    private long startTime;

    public void flip(){
//        hasEnded = false;
        startTime = System.currentTimeMillis();
        FlipNumber++;
        //task that is executed when 60 seconds have passed
/*        TimerTask task = new TimerTask() {
            public void run() {
                hasEnded = true;
                timer.cancel();
            }
        };
        timer.schedule(task, 60000);
*/
    }

    public int getFlipNumber() {
        return FlipNumber;
    }

/*    public Boolean getHasEnded() {
        return hasEnded;
    }
*/
    public long getElapsedTime(){
        return System.currentTimeMillis() - startTime;
    }

}
