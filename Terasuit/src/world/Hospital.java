package world;

public class Hospital implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 100;

	private int lvl = 1;
	private int buildTime;
	private int position;
	
	public Hospital(int position) {
		this.position = position;
	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasUpgrade() {
		return lvl < MAXLVL;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void build() {
		buildTime--;
	}
}
