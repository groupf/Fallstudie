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

import ch.hszt.groupf.fallstudie.server.msgparser.MsgParser;
import ch.hszt.groupf.fallstudie.server.srvconfig.ServerDefaultConfig;

/**
 * The ChatServer Class contains the most important Part of the Chat-Server. It
 * listens for incoming Chat-Client connections, opens a new Socket due to the
 * that request and starts a new ServerThread which corresponds to that new
 * Chat-Client connection. The open connections are kept in a Map with the
 * username as a Key and the DataOutputStream as the value from the
 * user-corresponding Socket.
 * 
 * @author rest
 * 
 */
public class ChatServer {

	final static Logger logger = LoggerFactory.getLogger(ChatServer.class);
	private ServerSocket _serverSocket;
	private Map<String, DataOutputStream> _openOutputStreams = new Hashtable<String, DataOutputStream>();

	/**
	 * The Constructor starts the SocketServer.
	 * 
	 * @param inServerPort
	 *            the port on which the java.net.ServerSocket starts to listen.
	 */
	public ChatServer(int inServerPort) {

		listen(inServerPort);
	}

	/**
	 * The listen method starts the ServerSocket on the given Port an loops in a
	 * while until the ServerSocket is closed due to an Exception.
	 * 
	 * @param inServerPort
	 *            Port on which the Server listens.
	 */
	private void listen(int inServerPort) {

		/*
		 * Opens a new ServerSocket If inServerPort is an IllegalArgument, the
		 * ChatServer tries to start on the default port.
		 */
		try {
			_serverSocket = newServerSocket(inServerPort);

			/*
			 */
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
			System.exit(0);
		}

		/*
		 * Loop until the ServerSocket is closed due to an Exception.
		 */
		while (!(_serverSocket.isClosed())) {
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

				/*
				 * Due to the specification the User first sends, on an
				 * established connection, the username.
				 */
				String socketUserName = getFirstIncomingString(singleSocket);
				DataOutputStream doutStream = getDosFromSocket(singleSocket);

				addUserToMap(socketUserName, doutStream);
				// _openOutputStreams.put(socketUserName, doutStream);

				startNewServerThread(this, singleSocket, socketUserName);

			} catch (IOException e) {
				logger.warn("IO Exception occured during the opening of an incoming Connection.");

			} catch (IllegalArgumentException e) {
				// TODO Send Error Message to User (doutStream)
				logger.info("IllegalArgumentException: " + e.getMessage());
			}

		}
	}

	/**
	 * The method returns the String received by the Socket and should be used
	 * only the first time, when the Connection established
	 * 
	 * @param inSocket
	 *            Socket on which the ChatServer listens
	 * @return a String with the first received stream-message.
	 * @throws IOException
	 *             when the Stream on the Socket could not be opened.
	 */
	protected String getFirstIncomingString(Socket inSocket) throws IOException {
		return (new DataInputStream(inSocket.getInputStream())).readUTF();
	}

	/**
	 * Returns the DataOutputStream from the Socket in the Arguments. This
	 * method is especially used for testing due to return the mock-object.
	 * 
	 * @param inSocket
	 *            Socket on which the ChatServer listens
	 * @return is a DataOutputStream which results out of the method-call
	 *         getOutputStream()
	 * @throws IOException
	 *             if the method-call getOutputStream() on the inSocket throws
	 *             an IOException, the Exception is rethrown.
	 */
	protected DataOutputStream getDosFromSocket(Socket inSocket) throws IOException {
		return new DataOutputStream(inSocket.getOutputStream());
	}

	/**
	 * Returns the new ServerSocket Object. This method is especially used for
	 * testing due to return mock-objects.
	 * 
	 * @param inServerPort
	 *            the portnummber on which the ServerSocket will be started.
	 * @return a new ServerSocket
	 * @throws IOException
	 *             when the ServerSocket could not be established.
	 */
	protected ServerSocket newServerSocket(int inServerPort) throws IOException {
		return new ServerSocket(inServerPort);
	}

	private void addUserToMap(String inUserName, DataOutputStream dos) throws IllegalArgumentException {
		synchronized (_openOutputStreams) {
			if (_openOutputStreams.containsKey(inUserName)) {
				throw new IllegalArgumentException("Username allready exists!");
			}

			if (!MsgParser.isValidUserName(inUserName)) {
				throw new IllegalArgumentException("Username doesn't matches the pattern '[a-zA-Z0-9_-].*' !");
			}

			_openOutputStreams.put(inUserName, dos);
			if (logger.isDebugEnabled()) {
				logger.debug("User added to Map: " + inUserName);
			}
		}
	}

	/**
	 * When an new user is connected to the Chatserver, the Chatserver sends a
	 * Welcome-message to this user.
	 * 
	 * @param inUserName
	 *            the username of newly connected user
	 */
	protected void sendWelcomeMsg(String inUserName) {
		synchronized (_openOutputStreams) {
			for (Map.Entry<String, DataOutputStream> entry : _openOutputStreams.entrySet()) {
				if (entry.getKey().equals(inUserName)) {
					try {
						entry.getValue().writeUTF("Welcome to the chat-room. Your username: " + inUserName);
					} catch (IOException e) {
						logger.info("Could not send Welcome-message to User: " + entry.getKey());
					}
				}
			}
		}
	}

	/**
	 * When an new user is connected to the Chatserver, the Chatserver sends a
	 * 'Joind'-Message to all other active users.
	 * 
	 * @param inUserName
	 *            the username of newly connected user
	 */
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

	/**
	 * The default behavior of the Chatserver is to send every received message
	 * to every connected user.
	 * 
	 * @param inMessage
	 *            the received message String to deliver
	 * @param inSenderUser
	 *            the senders name
	 */
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

	/**
	 * If the Chatserver receives a message which indicates, that it is for a
	 * specific user, the Chatserver delivers the received message only to the
	 * specified recipient.
	 * 
	 * @param inSender
	 *            the senders name
	 * @param inRecipient
	 *            the recipients name
	 * @param inMessage
	 *            the message text
	 */
	protected void sendToSpecificUser(String inSender, String inRecipient, String inMessage) {
		synchronized (_openOutputStreams) {
			try {
				_openOutputStreams.get(inRecipient).writeUTF("from " + inSender + ": " + inMessage);
				_openOutputStreams.get(inSender).writeUTF("sent to " + inRecipient + ": " + inMessage);
			} catch (IOException e) {

				try {
					_openOutputStreams.get(inSender).writeUTF("Could not send spezific Message to User" + inRecipient);
					logger.info("Could not send spezific Message from User " + inSender + " to User" + inRecipient);
				} catch (IOException e1) {
					logger.info("Could not send Message to User" + inSender);
				}
			}
		}
	}

	/**
	 * If a user closes a connection, the corresponding ServerThread removes the
	 * entry for that user in the Map.
	 * 
	 * @param inUserName
	 *            username whoes connection had been closed
	 * @param inSocket
	 *            the Socket corresponding to the username which will be closed.
	 */
	protected void removeConnection(String inUserName, Socket inSocket) {
		synchronized (_openOutputStreams) {
			// TODO Appender to logger that a Connection is removed
			_openOutputStreams.remove(inUserName);
			if (logger.isDebugEnabled()) {
				logger.debug("User removed from Map: " + inUserName);
			}

			try {
				inSocket.close();
			} catch (IOException e) {
				// TODO: Append to logger that Socket couldn't be closed
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts a new ServerThread for every new established connection.
	 * 
	 * @param inChatServer
	 *            the reference to the ChatServer
	 * @param inSocket
	 *            the Socket on which the client-connection established
	 * @param inSocketUserName
	 *            the corresponding username to the socket (client-connection)
	 */
	protected void startNewServerThread(ChatServer inChatServer, Socket inSocket, String inSocketUserName) {
		(new ServerThread(this, inSocket, inSocketUserName)).start();
	}
}
