
package de.szut.server.world;

import java.awt.Point;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final byte MAINBUILDINGID = 127;
	public static final byte OUTPOSTID = 1;
	public static final byte BARRACKSID = 2;
	public static final byte ARSENALID = 3;
	public static final byte FORGEID = 4;
	public static final byte MANUFACTUREID = 5;
	public static final byte MECHANICSTERMINALID = 6;
	public static final byte HOSPITALID = 7;
	public static final byte WARSANCTUMID = 8;
	public static final byte BANKID = 9;
	public static final byte TREASURYID = 10;
	public static final byte ARMORYID = 11;
	public static final byte GENERATORID = 12;
	public static final byte SOLARGRIDID = 13;
	public static final byte SPECIALOPERATIONSID = 14;

	public static final byte MARINEID = 1;
	public static final byte CHRONITETANKID = 2;
	public static final byte SNIPERID = 3;
	public static final byte GRÖDITZID = 4;
	public static final byte HOVERTANKID = 5;
	public static final byte BLACKQUEENID = 6;
	public static final byte A25ROMANID = 7;
	public static final byte SCOUTID = 8;
	public static final byte PHANTOMID = 9;
	public static final byte SAKATAMK2ID = 10;
	public static final byte SAKATASPIDERID = 11;
	public static final byte GLADIATORID = 12;
	public static final byte MEDITECID = 13;
	public static final byte SAINTID = 14;
	public static final byte SPHINXID = 15;
	public static final byte MODIFIEDPHANTOMID = 16;
	public static final byte MODIFIEDSAKATAID = 17;


	public static Building getBuilding(int id, byte position, byte player) {
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
		default:
			return null;
		}
	}

	public static Unit getNewUnit(byte type, short id, Point position, byte player) {
		switch (type) {
		case MARINEID:
			return new Marine(id, position, player);
		case CHRONITETANKID:
			return new ChroniteTank(id, position, player);
		case SNIPERID:
			return new Sniper(id, position, player);
		case GRÖDITZID:
			return new Gröditz(id, position, player);
		case HOVERTANKID:
			return new HoverTank(id, position, player);
		case BLACKQUEENID:
			return new BlackQueen(id, position, player);
		case A25ROMANID:
			return new A25Roman(id, position, player);
		case SCOUTID:
			return new Scout(id, position, player);
		case PHANTOMID:
			return new Phantom(id, position, player);
		case SAKATAMK2ID:
			return new SakataMK2(id, position, player);
		case SAKATASPIDERID:
			return new SakataSpider(id, position, player);
		case MEDITECID:
			return new Meditec(id, position, player);
		case SAINTID:
			return new Saint(id, position, player);
		case SPHINXID:
			return new Sphinx(id, position, player);
		case MODIFIEDPHANTOMID:
			return new ModifiedPhantom(id, position, player);
		case MODIFIEDSAKATAID:
			return new ModifiedSakata(id, position, player);
		default:
			return null;
		}
	}

	public static boolean isFlying(byte id) {
		switch (id) {
		case MARINEID:
			return Marine.FLYING;
		case CHRONITETANKID:
			return ChroniteTank.FLYING;
		case SNIPERID:
			return Sniper.FLYING;
		case GRÖDITZID:
			return Gröditz.FLYING;
		case HOVERTANKID:
			return HoverTank.FLYING;
		case BLACKQUEENID:
			return BlackQueen.FLYING;
		case A25ROMANID:
			return A25Roman.FLYING;
		case SCOUTID:
			return Scout.FLYING;
		case PHANTOMID:
			return Phantom.FLYING;
		case SAKATAMK2ID:
			return SakataMK2.FLYING;
		case SAKATASPIDERID:
			return SakataSpider.FLYING;
		case GLADIATORID:
			return Gladiator.FLYING;
		case MEDITECID:
			return Meditec.FLYING;
		case SAINTID:
			return Saint.FLYING;
		case SPHINXID:
			return Sphinx.FLYING;
		case MODIFIEDPHANTOMID:
			return ModifiedPhantom.FLYING;
		case MODIFIEDSAKATAID:
			return ModifiedSakata.FLYING;
		default:
			return false;
		}
	}

	public static double[] getStartResources() {
		return new double[] {50, 50, 50, 0};
	}

	public static double[] getResources() {
		return new double[] {0.04, 0.025, 0.025, 0.01};
	}

	public static int[] getUnitPrice(byte id) {
		switch (id) {
		case MARINEID:
			return Marine.PRICE;
		case CHRONITETANKID:
			return ChroniteTank.PRICE;
		case SNIPERID:
			return Sniper.PRICE;
		case GRÖDITZID:
			return Gröditz.PRICE;
		case HOVERTANKID:
			return HoverTank.PRICE;
		case BLACKQUEENID:
			return BlackQueen.PRICE;
		case A25ROMANID:
			return A25Roman.PRICE;
		case SCOUTID:
			return Scout.PRICE;
		case PHANTOMID:
			return Phantom.PRICE;
		case SAKATAMK2ID:
			return SakataMK2.PRICE;
		case SAKATASPIDERID:
			return SakataSpider.PRICE;
		case MEDITECID:
			return Meditec.PRICE;
		case SAINTID:
			return Saint.PRICE;
		case SPHINXID:
			return Sphinx.PRICE;
		case MODIFIEDPHANTOMID:
			return ModifiedPhantom.PRICE;
		case MODIFIEDSAKATAID:
			return ModifiedSakata.PRICE;
		default:
			return null;
		}
	}
}