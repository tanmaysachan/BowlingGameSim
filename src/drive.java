// TODO: add to report, unused import statements

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty = 5;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subhandler.subscribe( cdv );

	}
}
