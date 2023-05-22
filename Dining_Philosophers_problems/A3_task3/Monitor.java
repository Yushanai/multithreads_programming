import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
     private enum State {THINKING, HUNGRY, EATING,TALKING};
     private State[] state;
     private int piNumberOfPhilosophers;

     
	/*
	 * ------------
	 * Data members
	 * ------------
	 */


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		this.piNumberOfPhilosophers=piNumberOfPhilosophers;
		state=new State[piNumberOfPhilosophers];
        for (int i = 0; i < piNumberOfPhilosophers; i++) {
            state[i] = State.THINKING;
          }
        
		// TODO: set appropriate number of chopsticks based on the # of philosophers
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	public synchronized void test(final int piTID) throws InterruptedException{

	    if ((state[(piTID + piNumberOfPhilosophers-1) % piNumberOfPhilosophers] != State.EATING) &&
	    (state[piTID] == State.HUNGRY) &&
	    (state[(piTID + 1) % piNumberOfPhilosophers] != State.EATING)) {
	        state[piTID] = State.EATING;
	        notifyAll();
	    }
	}

	
 

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 * @throws InterruptedException 
	 */
	public synchronized void pickUp(final int piTID) throws InterruptedException
	{
		state[piTID]= State.HUNGRY;
		test(piTID);
		if(state[piTID]!=State.EATING)
			 wait();
		// ...
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 * @throws InterruptedException 
	 */
	public synchronized void putDown(final int piTID) throws InterruptedException
	{
	    state[piTID] = State.THINKING;
	    test((piTID + piNumberOfPhilosophers-1) % piNumberOfPhilosophers);
	    test((piTID + 1) % piNumberOfPhilosophers);
		// ...
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 * @throws InterruptedException 
	 */
	public synchronized void requestTalk(final int piTID) throws InterruptedException
	{
		if(state[piTID]!=State.EATING)
			state[piTID]=State.TALKING;
		else
			wait();
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk(final int piTID)
	{
		if(state[piTID]!=State.EATING)
		{
			state[piTID]=State.THINKING;
		}
		// ...
	}
}

// EOF
