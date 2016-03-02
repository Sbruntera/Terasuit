package de.szut.server.logic;

/**
 * 
 * @author Simeon
 *
 */
public class Filter {

	private boolean noPassword;
	private String nameContains;
	private Map map;
	private int minPlayers;
	private int maxPlayers;

	public Filter(boolean noPassword, String nameContains, Map map,
			int minPlayers, int maxPlayers) {
		this.noPassword = noPassword;
		this.nameContains = nameContains;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	/**
	 * Gibt zurück ob es erwünscht ist das Passwortgeschützte Server
	 * rausgefiltert werden
	 * 
	 * @return
	 */
	public boolean isNoPassword() {
		return noPassword;
	}

	/**
	 * Gibt den String zurück der im Namen enthalten sein soll
	 * 
	 * @return
	 */
	public String getNameContains() {
		return nameContains;
	}

	/**
	 * Gibt die Map zurück die gewünscht ist
	 * 
	 * @return
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gibt die minimalzahl an Spielern an die gewünscht sind
	 * 
	 * @return
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * Gibt die maximalzahl an Spielern an die gewünscht sind
	 * 
	 * @return
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}
}
