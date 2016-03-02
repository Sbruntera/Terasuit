package de.szut.server.connection;

/**
 * 
 * @author Simeon
 *
 */
public interface Analyser {

	/**
	 * Analysiert eine Nachricht für den Server
	 * 
	 * @param input
	 *            Nachricht
	 */
	public void analyse(byte[] bs);
}
