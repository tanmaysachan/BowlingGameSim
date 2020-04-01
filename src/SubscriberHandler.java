import java.util.Iterator;
import java.util.Vector;

public class SubscriberHandler <T> {

    private final Vector subscribers;

    public SubscriberHandler(Vector subs) {
        subscribers = subs;
    }

    /**
     * Allows objects to subscribe as observers
     *
     * @param adding	the ControlDeskObserver that will be subscribed
     *
     */

    public void subscribe(T adding) {
        subscribers.add(adding);
    }

    /** unsubscribe
     *
     * Method that unsubscribes an observer from this object
     *
     * @param removing	The observer to be removed
     */

    public void unsubscribe( T removing ) {
        subscribers.remove( removing );
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param event	the ControlDeskEvent to broadcast
     *
     */

    public void publishControlDesk(ControlDeskEvent event) {
        for (Object subscriber : subscribers) {
            (
                    (ControlDeskObserver) subscriber)
                    .receiveControlDeskEvent(
                            event);
        }
    }

    /** publish
     *
     * Method that publishes an event to subscribers
     *
     * @param event	Event that is to be published
     */

    public void publishLane( LaneEvent event ) {
        if( subscribers.size() > 0 ) {

            for (Object subscriber : subscribers) {
                ((LaneObserver) subscriber).receiveLaneEvent(event);
            }
        }
    }
}
