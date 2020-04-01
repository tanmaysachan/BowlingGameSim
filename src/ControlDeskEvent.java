/* ControlDeskEvent.java
 *
 *  Version:
 *  		$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class that represents control desk event
 *
 */

import java.util.*;

public class ControlDeskEvent {

	/** A representation of the wait queue, containing party names */
	private Vector partyQueue;

    /**
     * Contstructor for the ControlDeskEvent
     *
     * @param partyQueue	a Vector of Strings containing the names of the parties in the wait queue
     *
     */

	public ControlDeskEvent( Vector partyQueue ) {
		this.partyQueue = partyQueue;
	}

	public Vector getPartyQueue() {
		return partyQueue;
	}

}
