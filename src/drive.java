public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty = 6;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subhandler.subscribe( cdv );

	}
}
