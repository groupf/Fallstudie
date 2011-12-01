package ch.hszt.groupf.fallstudie.server.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hszt.groupf.fallstudie.server.msgparser.MsgParser;

/**
 * The ServerThread Class contains the interactions with the Chat-Client and the
 * SocketServer.
 * 
 * @author esterren
 * 
 */
public class ServerThread extends Thread {

	private SocketServer _server;
	private Socket _socket;
	private String _socketUserName;
	final static Logger logger = LoggerFactory.getLogger(SocketServer.class);

	/**
	 * 
	 * @param inServer
	 *            an Instance of the SocketServer. This is needed to exchange
	 *            messages and oth. between the userspecific ServerThread and
	 *            the SocketServer.
	 * @param inSocket
	 * @param inSocketUserName
	 */
	public ServerThread(SocketServer inServer, Socket inSocket, String inSocketUserName)
			throws IllegalArgumentException {

		if ((inServer == null) || (inSocket == null) || (inSocketUserName == null)) {
			throw new IllegalArgumentException(
					"null reference in new ServerThread(SocketServer inServer, Socket inSocket, String inSocketUserName) is not allowed!");
		}
		_server = inServer;
		_socket = inSocket;
		_socketUserName = inSocketUserName;

	}

	public void run() {

		_server.sendJoinedMsg(_socketUserName);

		try {
			DataInputStream dInStream = new DataInputStream(_socket.getInputStream());

			while (true) {

				String message = dInStream.readUTF();

				// TODO Appender for Message-Logger with Unknown User Exception
				// Handler

				// TODO Message Parser: Return only Message Part without
				// Recipient
				if (MsgParser.isForSpecificUser(message)) {
					String recipient = MsgParser.getRecipientFromMsg(message);
					String msgPart = MsgParser.getMsgPartFromMsg(message);

					_server.sendToSpecificUser(_socketUserName, recipient, msgPart);

				} else {
					_server.sendToAll(message, _socketUserName);

				}
			}
		} catch (IOException e) {
			// TODO Handle Exception for DataInputStream use a slf4j logger
			_server.removeConnection(_socketUserName, _socket);
			// return;
		}

	}

}
