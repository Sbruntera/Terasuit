
package world;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final int MAINBUILDINGID = -2;
	public static final int FORGEID = 8;
	public static final int ARMORYID = 16;
	public static final int HOSPITALID = 24;
	public static final int OUTPOSTID = 32;
	public static final int BANKID = 40;
	public static final int GENERATORID = 48;
	public static final int SPECIALOPERATIONSID = 52;

	public static Building getBuilding(int id, byte position, byte player) {
		switch (id) {
		case MAINBUILDINGID:
			return new MainBuilding(player);
		case OUTPOSTID:
			return new Outpost(position, player);
		case FORGEID:
			return new Forge(position, player);
		case ARMORYID:
			return new Armory(position, player);
		case HOSPITALID:
			return new Hospital(position, player);
		case BANKID:
			return new Bank(position, player);
		case GENERATORID:
			return new Generator(position, player);
		case SPECIALOPERATIONSID:
			return new SpecialOperations(position, player);
		}
		return null;
	}

	public static Unit getUnit(byte type, short id, short position) {
		// TODO Auto-generated method stub
		return null;
	}
}