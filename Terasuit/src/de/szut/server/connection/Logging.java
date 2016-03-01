package de.szut.server.connection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Logging {

	private static SimpleDateFormat format = new SimpleDateFormat(
			"dd.MM.yyyy hh:mm:ss.SSS");
	private static Timestamp time = new Timestamp(System.currentTimeMillis());
	private static String dateTime = format.format(time);
	private static FileWriter fWriter;
	public static boolean loggingOn = true;

	public static void log(String log, String flag) {
		if (loggingOn) {
			try {
				fWriter = new FileWriter("log.txt", true);
				BufferedWriter bWriter = new BufferedWriter(fWriter);
				PrintWriter pWriter = new PrintWriter(bWriter);
				pWriter.println(dateTime + ": (" + flag + ") " + log);
				bWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
