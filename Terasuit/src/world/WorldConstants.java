
package world;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final byte MAINBUILDINGID = 127;
	public static final byte OUTPOSTID = 0;
	public static final byte BARRACKSID = 1;
	public static final byte FORGEID = 4;
	public static final byte ARMORYID = 16;
	public static final byte HOSPITALID = 24;
	public static final byte BANKID = 40;
	public static final byte GENERATORID = 48;
	public static final byte SPECIALOPERATIONSID = 52;


	public static Building getBuilding(int id, byte position, byte player, boolean primaryBuilding) {
		switch (id) {
		case MAINBUILDINGID:
			return new MainBuilding(player);
		case OUTPOSTID:
			return new Outpost(position, player);
		case FORGEID:
			return new Forge(position, player);
		case HOSPITALID:
			return new Hospital(position, player);
		case BANKID:
			return new Bank(position, player);
		case ARMORYID:
			return new Armory(position, player);
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