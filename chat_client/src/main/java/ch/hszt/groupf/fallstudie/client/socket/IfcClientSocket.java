package ch.hszt.groupf.fallstudie.client.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Describes the strategy of the client-socket.
 */
public interface IfcClientSocket {
	public void connect(InetAddress inServerAddress, int inServerPort,
			String inUserName) throws UnknownHostException, IOException;

	public void sendMsg(String message) throws IOException;

	public boolean isConnected();
}
