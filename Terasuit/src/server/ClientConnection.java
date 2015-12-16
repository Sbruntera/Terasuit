package server;

public class ClientConnection implements Analyser {

	private String[] sl;
	private Server server;
	private int id;
	
	public ClientConnection(Server server, int id) {
		this.server = server;
		this.id = id;
	}

	@Override
	public void analyse(String in) {
		sl = in.split(",");
		String status = sl[0];
		if (status.equals("r")) {
			server.registerClient(sl[1], sl[2], sl[3], sl[4], id);
		} else {
			server.loginClient(sl[1], sl[2], id);
		}
	}
}
