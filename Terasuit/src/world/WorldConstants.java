
package world;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final int MAINBUILDINGID = 0;

	public static Building getBuilding(int id) {
		switch (id) {
		case MAINBUILDINGID:
			return new MainBuilding();
		}
		return null;
	}
}