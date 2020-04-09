/* ControlDesk.java
 *
 *  Version:
 *  		$Id$
 * 
 *  Revisions:
 * 		$Log: ControlDesk.java,v $
 * 		Revision 1.13  2003/02/02 23:26:32  ???
 * 		ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 * 		
 * 		Revision 1.12  2003/02/02 20:46:13  ???
 * 		Added " 's Party" to party names.
 * 		
 * 		Revision 1.11  2003/02/02 20:43:25  ???
 * 		misc cleanup
 * 		
 * 		Revision 1.10  2003/02/02 17:49:10  ???
 * 		Fixed problem in getPartyQueue that was returning the first element as every element.
 * 		
 * 		Revision 1.9  2003/02/02 17:39:48  ???
 * 		Added accessor for lanes.
 * 		
 * 		Revision 1.8  2003/02/02 16:53:59  ???
 * 		Updated comments to match javadoc format.
 * 		
 * 		Revision 1.7  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 		Revision 1.6  2003/02/02 06:09:39  ???
 * 		Updated many classes to support the ControlDeskView.
 * 		
 * 		Revision 1.5  2003/01/26 23:16:10  ???
 * 		Improved thread handeling in lane/controldesk
 * 		
 * 
 */

/**
 * Class that represents control desk
 *
 */

import java.util.*;
import java.io.*;

class ControlDesk extends Thread implements BowlerFileHandler {

	/** The collection of Lanes */
	private HashSet lanes;

	/** The party wait queue */
	private Queue partyQueue;

	/** The number of lanes represented */
	private int numLanes;

	/** The Subscriber handler class */
	public SubscriberHandler <ControlDeskObserver> subhandler;

	private static String PAUSEFILE = "PAUSE.bin";

    /**
     * Constructor for the ControlDesk class
     *
     * @param numLanes	the number of lanes to be represented
     *
     */

	public ControlDesk(int numLanes) {
		this.numLanes = numLanes;
		lanes = new HashSet(numLanes);
		partyQueue = new Queue();

		/** The collection of subscribers */
		Vector subscribers = new Vector();
		subhandler = new SubscriberHandler(subscribers);

		for (int i = 0; i < numLanes; i++) {
			lanes.add(new Lane());
		}
		
		this.start();

	}
	
	/**
	 * Main loop for ControlDesk's thread
	 * 
	 */
	public void run() {
		while (true) {
			
			assignLane();
			
			try {
				sleep(250);
			} catch (Exception e) {}
		}
	}
		
    /**
     * Iterate through the available lanes and assign the paties in the wait queue if lanes are available.
     *
     */

    public void pause(){

    	Iterator it = lanes.iterator();
    	while(it.hasNext()) {
    		Lane curLane = (Lane) it.next();
    		curLane.pauseAndDump();
		}
	}

	public void res(){
    	try {
    		File f = new File(PAUSEFILE);
    		if(f.length() == 0){
    			return;
			}
			FileInputStream fin = new FileInputStream(PAUSEFILE);
			ObjectInputStream oin = new ObjectInputStream(fin);
			Vector V = (Vector) oin.readObject();
			Iterator it = V.iterator();
			Iterator lane = lanes.iterator();
			Vector v = new Vector();
			v.add(-1);
			while(lane.hasNext()) {
			    while(it.hasNext()) {
					v = (Vector) it.next();
					if((Boolean)v.get(0) == false){
						break;
					}
				}
				if((int)v.get(0) == -1) return;
				Lane curLane = (Lane) lane.next();
				curLane.assignLaneObjToLane(v);
			}

		} catch(Exception e){};
	}

	public void assignLane() {
		Iterator it = lanes.iterator();

		while (it.hasNext() && partyQueue.hasMoreElements()) {
			Lane curLane = (Lane) it.next();

			if (curLane.isPartyAssigned() == false) {
				System.out.println("ok... assigning this party");
				curLane.assignParty(((Party) partyQueue.next()));
			}
		}
		subhandler.publishControlDesk(new ControlDeskEvent(getPartyQueue()));
	}

    /**
     */

	public void viewScores(Lane ln) {
		// TODO: attach a LaneScoreView object to that lane
	}

    /**
     * Creates a party from a Vector of nickNAmes and adds them to the wait queue.
     *
     * @param partyNicks	A Vector of NickNames
     *
     */

	public void addPartyQueue(Vector partyNicks) {
		Vector partyBowlers = new Vector();
		for (int i = 0; i < partyNicks.size(); i++) {
			Bowler newBowler = registerPatron(((String) partyNicks.get(i)));
			partyBowlers.add(newBowler);
		}
		Party newParty = new Party(partyBowlers);
		partyQueue.add(newParty);
		subhandler.publishControlDesk(new ControlDeskEvent(getPartyQueue()));
	}

    /**
     * Returns a Vector of party names to be displayed in the GUI representation of the wait queue.
	 *
     * @return a Vecotr of Strings
     *
     */

	public Vector getPartyQueue() {
		Vector displayPartyQueue = new Vector();
		for ( int i=0; i < ( (Vector)partyQueue.asVector()).size(); i++ ) {
			String nextParty =
				((Bowler) ((Vector) ((Party) partyQueue.asVector().get( i ) ).getMembers())
					.get(0))
					.getNickName() + "'s Party";
			displayPartyQueue.addElement(nextParty);
		}
		return displayPartyQueue;
	}

    /**
     * Accessor for the number of lanes represented by the ControlDesk
     * 
     * @return an int containing the number of lanes represented
     *
     */

	public int getNumLanes() {
		return numLanes;
	}

    /**
     * Accessor method for lanes
     * 
     * @return a HashSet of Lanes
     *
     */

	public HashSet getLanes() {
		return lanes;
	}
}
