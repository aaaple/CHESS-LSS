package ICS;


import java.util.Timer;
import java.util.TimerTask;

/**
 *Simple countdown timer demo of java.util.Timer facility.
 */

public class tests {
	static boolean contin = true;
    public static void main(final String args[]) {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int one = 10000;
            int two = 10000;
            public void run() {
            	if (contin == true)
            	{
	                System.out.println(one--);
	                if (one == 7000)
	                	contin = false;
	                if (one == 0)
	                	timer.cancel();
            	}
            	else if (contin == false)
            	{
            		System.out.println(two--);
            		if (two == 2000)
            			contin = true;
            		if (two == 0)
            			timer.cancel();
            	}
            }
        }, 0, 1);
    }
}