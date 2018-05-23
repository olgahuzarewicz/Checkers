package checkers;
public class Timer {
    long starttime, stoptime;
    double fulltime;
    public void start(){
        starttime = System.nanoTime();
    }
    public void stop(){
        stoptime = System.nanoTime();
        fulltime = stoptime - starttime;
    }
    public void show(){
        fulltime = (double) (stoptime - starttime)/1000000;
        System.out.println("Full time: " + fulltime);
    }
}
