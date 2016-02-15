package inGame;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class ProgressbarLogic {
	
	// buildingLocation
	
	int X;
	int Y;
	int primID;
	int slotID;
	int time;
	int ID;
	JPanel field;
	String description;
	String buildingName;
	String buildingLocation;
	Game game;
	Timer timer;
	
	public ProgressbarLogic(int ID, int x, int y, Game game, int slotID, int primID, int time, JPanel field, String description, String buildingName, String buildingLocation) {
		this.X = x;
		this.Y = y;
		this.primID = primID;
		this.slotID = slotID;
		this.time = time;
		this.field = field;
		this.description = description;
		this.buildingName = buildingName;
		this.buildingLocation = buildingLocation;
		this.game = game;
		this.ID = ID;
	}
	
	public void tryCancel(){
		timer.cancel();
	}

	
	public void init(int time){
		
	    Timer timer = new Timer();
	    new ReminderBeep(time, timer);
	}
	
	public class ReminderBeep {
		  Toolkit toolkit;


		public ReminderBeep(int time, Timer timer) {
			toolkit = Toolkit.getDefaultToolkit();
			//int	splitration = 10;
			//int callTimer = splitration/time;
			//int counterFor = splitration*callTimer;
			for (int i = 1; i != 12; i++){
			    timer.schedule(new RemindTask(), ((1000)*i));
			}
		}

		class RemindTask extends TimerTask {
		    public void run() {
		     	game.setProgressbar(ID, X, Y, buildingLocation, description, buildingName, ID, slotID, primID, time);
		    }
		}
	}
}
