package ch.hszt.groupf.fallstudie.client.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Describes the strategy of the client-socket.
 * 
 * @author groupf
 * 
 */
public interface IfcClientSocket {
	public void connect(InetAddress inServerAddress, int inServerPort,
			String inUserName) throws UnknownHostException, IOException;

	
	/**
	 * The message will be sent as a normal String from the Client to the Server.
	 * 
	 * @param message This is the message String.
	 * @throws IOException if the message couldn't be sent (no connection, Nullpointer... ) the IOException will be thrown.
	 */
	public void sendMsg(String message) throws IOException;

	
	/**
	 * @return return true if the Client is connected to the server. Otherwhise return false.
	 */
	public boolean isConnected();
}
