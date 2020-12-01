import processing.core.*;

public class RunMe extends PApplet {
	protected int c;
	TurnOffTheLights game;
	Display display;
	protected int clickMode = 1;
	protected int counter = 0;
	double baseTime = -1;

	public void setup() {
		size(900, 940); // set the size of the screen.

		// Create a game object
		game = new TurnOffTheLights(9, 9);

		// Create the display
		// parameters: (10,10) is upper left of display
		// (300, 300) is the width and height
		display = new Display(this, 40, 110, 820, 820);

		display.setColor(0, 0xFF356043);
		display.setColor(1, 0xFF0061FF);
		display.setColor(2, 0xFFcb00FF);
		display.setColor(3, 0xFFD8FF00);
		display.setColor(4, 0xFFFF7B00);
		display.setColor(5, 0xFF00FF55);
		display.setColor(6, 0xFF864AB5);
		display.setColor(7, 0xFFABCDE6);
		display.setColor(8, 0xFFB75CD8);

		// You can use images instead if you'd like.
		// d.setImage(1, "c:/data/ball.jpg");
		// d.setImage(2, "c:/data/cone.jpg");

		display.initializeWithGame(game);
		c = 0;
	}

	@Override
	public void draw() {
		background(200);

		display.drawGrid(game.getInformationGrid(), game.getGrid(), game.getFlag(), baseTime); // display
		// the
		// game
	}

	public void mouseClicked() {
		Location loc = display.gridLocationAt(mouseX, mouseY);

		if (baseTime == -1) {
			baseTime = System.currentTimeMillis();
		}

		if (clickMode == 1) {
			game.move(loc.getRow(), loc.getCol());
			System.out.println("Current mode regular");
		} else {
			game.flag(loc.getRow(), loc.getCol());
			System.out.println("Current mode flagging");
		}

		if (game.isGameOver()) {
			System.out.println("Suck it, you win!");
			int num = (int) (System.currentTimeMillis() - baseTime) / 1000;
			System.out.println("You took " + num + " seconds.");
			System.exit(0);

		}

	}

	public void keyReleased() {
		if (key == 'f') {
			clickMode = -clickMode;
			if (clickMode == 1) {
				System.out.println("Current mode regular");
			} else {
				System.out.println("Current mode flagging");
			}
		}
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunMe" });
	}
}