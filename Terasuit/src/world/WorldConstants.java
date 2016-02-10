
package world;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final int MAINBUILDINGID = -2;
	public static final int BARRACKSID = 0;
	public static final int FORGEID = 8;
	public static final int ARMORYID = 16;
	public static final int HOSPITALID = 24;
	public static final int OUTPOSTID = 32;
	public static final int BANKID = 40;
	public static final int GENERATORID = 48;
	public static final int SPECIALOPERATIONSID = 52;

	public static Building getBuilding(int id, int position) {
		switch (id) {
		case MAINBUILDINGID:
			return new MainBuilding();
		case BARRACKSID:
			return new Barrack(position);
		case FORGEID:
			return new Forge(position);
		case ARMORYID:
			return new Armory(position);
		case HOSPITALID:
			return new Hospital(position);
		case OUTPOSTID:
			return new Outpost(position);
		case BANKID:
			return new Bank(position);
		case GENERATORID:
			return new Generator(position);
		case SPECIALOPERATIONSID:
			return new SpecialOperations(position);
		}
		return null;
	}
}