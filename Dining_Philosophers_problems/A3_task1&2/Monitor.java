package comp346A3;

import java.util.concurrent.locks.Condition;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
  * @modified by Yushan Ai
 */
public class Monitor
{
	private enum status{thinking, hungry,eating};
	private status [] state;
	//private Condition self[];
	private int nbOfPhilosophers;
	private boolean isTalking;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		nbOfPhilosophers = piNumberOfPhilosophers;
		state = new status[nbOfPhilosophers];
		//self = new Condition[nbOfPhilosophers];
		//Set all Philosophers status to thinking 
		for(int i=0; i<nbOfPhilosophers;i++){
			state[i]=status.thinking;
		}
		//All Philosophers is not talking when staring
		isTalking = false;
		// TODO: set appropriate number of chopsticks based on the # of philosophers
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	public synchronized void test(final int piTID) {
		//test if the current Philosopher is hungry and two neighbors are not eating
		if(state[(piTID+(nbOfPhilosophers-1)) % nbOfPhilosophers]!= status.eating && state[piTID] == status.hungry && state[(piTID+1) %nbOfPhilosophers]!= status.eating){
			//set itsel to eating
			state[piTID] = status.eating;
			notifyAll();
		}
		
	}
	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		//index number
		int index = piTID -1;
		state[index]= status.hungry;
		test(index);
		if(state[index] != status.eating) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		int index = piTID -1;
		state[index] = status.thinking;
		test((index+(nbOfPhilosophers-1))% nbOfPhilosophers);
		test((index+1)% nbOfPhilosophers);
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		//if istalking is true, then this philosopher must wait 
		if(isTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
		isTalking = true;
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		isTalking = false;
		//notify others who is waiting to talk can talk
		notifyAll();
		// ...
	}
}
// EOF
