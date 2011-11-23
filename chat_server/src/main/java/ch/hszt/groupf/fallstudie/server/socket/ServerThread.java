package ch.hszt.groupf.fallstudie.server.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import ch.hszt.groupf.fallstudie.server.msgparser.MsgParser;

public class ServerThread extends Thread {

	private SocketServer _server;
	private Socket _socket;
	private String _socketUserName;

	public ServerThread(SocketServer inServer, Socket inSocket, String inSocketUserName) {
		// TODO Auto-generated constructor stub
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
			return;
		}

	}

}
