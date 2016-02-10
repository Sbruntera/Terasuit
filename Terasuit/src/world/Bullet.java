package world;

public class Bullet {

	private Unit target;

	public Bullet(Unit marine, Unit target) {
		this.target = target;
	}

	public boolean move() {
		return false;
	}

	public int getDamage() {
		return 0;
	}

	public Unit getTarget() {
		return target;
	}
}
