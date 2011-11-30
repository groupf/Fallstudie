package ch.hszt.groupf.fallstudie.server.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hszt.groupf.fallstudie.server.srvconfig.ServerDefaultConfig;

/**
 * The SocketServer Class contains the most important Part of the Chat-Server.
 * It listens for incoming Chat-Client connections, opens a new Socket due to
 * the that request and starts a new ServerThread which corresponds to that new
 * Chat-Client connection. The open connections are kept in a Map with the
 * username as a Key and the DataOutputStream as the value from the
 * user-corresponding Socket.
 * 
 * @author rest
 * 
 */
public class SocketServer {

	final static Logger logger = LoggerFactory.getLogger(SocketServer.class);
	private ServerSocket _serverSocket;
	private Map<String, DataOutputStream> _openOutputStreams = new Hashtable<String, DataOutputStream>();

	/**
	 * The Constructor starts the SocketServer.
	 * 
	 * @param inServerPort
	 *            the port on which the java.net.ServerSocket starts to listen.
	 */
	public SocketServer(int inServerPort) {

		listen(inServerPort);
	}

	/**
	 * 
	 * @param inServerPort
	 */
	private void listen(int inServerPort) {

		try {
			_serverSocket = newServerSocket(inServerPort);

		} catch (IllegalArgumentException e) {
			logger.info("Tried to start the ServerSocket on a illegal portnumber! Server will be started on the default port "
					+ ServerDefaultConfig.SERVERPORT);

			try {
				_serverSocket = newServerSocket(ServerDefaultConfig.SERVERPORT);
			} catch (IOException e1) {
				logger.error("Unable to start the Server on Port " + inServerPort + ". Server will be stopped!");
				System.exit(0);
			}
		} catch (IOException e2) {

			logger.error("Unable to start the Server on Port " + inServerPort + ". Server will be stopped!");
			// e.printStackTrace();
			System.exit(0);
		}

		while (true) {
			// BufferedReader reader = null;
			// PrintWriter writer = null;

			Socket singleSocket;
			try {
				singleSocket = _serverSocket.accept();

				// DataInputStream dInStream = new
				// DataInputStream(_socket.getInputStream());
				// String message = dInStream.readUTF();
				// reader = new BufferedReader(new
				// InputStreamReader(singleSocket.getInputStream()));

				// String socketUserName = reader.readLine();
				String socketUserName = (new DataInputStream(singleSocket.getInputStream())).readUTF();
				DataOutputStream doutStream = new DataOutputStream(singleSocket.getOutputStream());

				addUserToMap(socketUserName, doutStream);
				// _openOutputStreams.put(socketUserName, doutStream);

				(new ServerThread(this, singleSocket, socketUserName)).start();

			} catch (IOException e) {
				logger.warn("IO Exception occured during the opening of an incoming Connection.");

			} catch (IllegalArgumentException e) {

				logger.info("IllegalArgumentException: " + e.getMessage());
			}

		}
	}

	private void addUserToMap(String inUserName, DataOutputStream dos) throws IllegalArgumentException {
		synchronized (_openOutputStreams) {
			if (_openOutputStreams.containsKey(inUserName)) {
				throw new IllegalArgumentException("Username allready exists in the Map.");
			}
			// TODO Parse Usernames, if they ar allowed -> needs a
			// UserNameParser

			_openOutputStreams.put(inUserName, dos);
		}
	}

	protected ServerSocket newServerSocket(int inServerPort) throws IOException {
		return new ServerSocket(inServerPort);
	}

	protected void sendJoinedMsg(String inUserName) {
		synchronized (_openOutputStreams) {
			for (Map.Entry<String, DataOutputStream> entry : _openOutputStreams.entrySet()) {
				if (!entry.getKey().equals(inUserName)) {
					try {
						entry.getValue().writeUTF("User " + inUserName + " joined the chatroom");
					} catch (IOException e) {
						logger.info("Could not send Message to User" + entry.getKey());
					}
				}
			}
		}
	}

	protected void sendToAll(String inMessage, String inSenderUser) {
		synchronized (_openOutputStreams) {
			for (Map.Entry<String, DataOutputStream> entry : _openOutputStreams.entrySet()) {
				try {
					entry.getValue().writeUTF(inSenderUser + ": " + inMessage);

				} catch (IOException e) {
					logger.info("Could not send Message to User" + entry.getKey());
				}

			}

		}
	}

	protected void sendToSpecificUser(String inSender, String inRecipient, String inMessage) {
		synchronized (_openOutputStreams) {
			try {
				_openOutputStreams.get(inRecipient).writeUTF("from " + inSender + ": " + inMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					_openOutputStreams.get(inSender).writeUTF("Could not send spezific Message to User" + inRecipient);
					logger.info("Could not send spezific Message from User " + inSender + " to User" + inRecipient);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.info("Could not send Message to User" + inSender);
				}
			}
		}
	}

	protected void removeConnection(Socket inSocket) {
		synchronized (_openOutputStreams) {
			// TODO Appender to logger that a Connection is removed
			_openOutputStreams.remove(inSocket);

			try {
				inSocket.close();
			} catch (IOException e) {
				// TODO: Append to logger that Socket couldn't be closed
				e.printStackTrace();
			}
		}
	}
}
