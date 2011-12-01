package ch.hszt.groupf.fallstudie.client.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public interface IfcClientSocket {
	public void connect(InetAddress inServerAddress, int inServerPort,
			String inUserName) throws UnknownHostException, IOException;

	public void sendMsg(String message) throws IOException;

}
