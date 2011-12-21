package ch.hszt.groupf.fallstudie.client.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.hszt.groupf.fallstudie.client.cli.ChatClientCLI;
import ch.hszt.groupf.fallstudie.client.gui.ChatClientGUI;
import ch.hszt.groupf.fallstudie.client.log.LogFactory;
import ch.hszt.groupf.fallstudie.client.socket.ClientSocket;
import ch.hszt.groupf.fallstudie.client.socket.IfcClientSocket;
import ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer;

/**
 * The ClientController is the controller of the Client. It handels the Log
 * File, knows if a CLI or a GUI is active and knows what to do with possilby
 * Exceptions.
 * 
 * @author groupf
 */
public class ClientController implements IfcSocketClientConsumer {

	private IfcClientSocket _chatClient;
	private IfcUserInterface _userInterface;

	private LogFactory logger = null;
	private boolean _logisOn = false;

	public ClientController(boolean startCLI) {
		_chatClient = getNewClientSocket(this);

		if (startCLI) {
			_userInterface = getNewChatClientCLI(this);

		} else {
			_userInterface = getNewChatClientGUI(this);
		}

	}

	/**
	 * This Method will create a new ClientSocket.
	 * 
	 * @param inSktClientConsumer
	 * @return returns the ClientSocket-Reference, which was created in this
	 *         method.
	 */
	protected IfcClientSocket getNewClientSocket(
			IfcSocketClientConsumer inSktClientConsumer) {
		return new ClientSocket(inSktClientConsumer);
	}

	/**
	 * @param inCltController
	 * @return gives the reference of a new created Chat Client CLI back
	 */
	protected IfcUserInterface getNewChatClientCLI(
			ClientController inCltController) {
		return new ChatClientCLI(inCltController);
	}

	/**
	 * @param inCltController
	 * @return gives the reference of a new created Chat Client CLI back
	 */

	protected IfcUserInterface getNewChatClientGUI(
			ClientController inCltController) {
		return new ChatClientGUI(inCltController);
	}

	/**
	 * This is the Main method of the Controller. So for starting the
	 * ChatClient, the controller will be created with this method and all the
	 * other object such as ClientSocket, Log... will be initalizied from this
	 * class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO check wether cmd-line or swing gui
		if (isCLIinStartParam(args)) {
			new ClientController(true);
		} else {
			new ClientController(false);
		}

	}

	/**
	 * 
	 * @return gives the refernece of the Interface IfcUserInterface back.
	 */
	public IfcUserInterface getUserInterface() {
		return _userInterface;

	}

	/**
	 * This method will decide if a CLI or a GUI will be used for the chat
	 * client.
	 * 
	 * @param args
	 * @return return true if a CLI should be started. If false, a GUI will be
	 *         started.
	 */
	private static boolean isCLIinStartParam(String[] args) {
		boolean startCLI = false;

		if (args.length != 0) {

			if (args[0].contains("-c")) {
				startCLI = true;
			}
		}
		return startCLI;
	}

	public void onDisconnected(Exception inEx) {
		


	}

	/**
	 * This method describes what to to with received messages. The Controller
	 * will just pass down the received message to the user interface
	 */
	public void onReceivedMsg(String inMessage) {
		_userInterface.onReceivedMsg(inMessage);

	}

	/**
	 * This method will connect the client to the server. Follwing parameters
	 * are nessessary for connecting.
	 * 
	 * @param serverAddress		This is the Server's IP- or DNS Adress
	 * @param serverPort		This is the Port of the Server where the server-services is running on.
	 * @param username			This is the Client's username. It has to be unique
	 */
	public void connect(InetAddress serverAddress, int serverPort,
			String username) {

		try {
			_chatClient.connect(serverAddress, serverPort, username);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sends a message. It does not matter if the massage will be sent to one
	 * person or to all. This method just gives the String to the _chatClient.
	 * If the log is on, the sended message will be written into the log file,
	 * if not, the mesage will just be sent.
	 * 
	 * @param message
	 */
	public void send(String message) {
		try {
			if (_logisOn)
				logger.writeLog(message);
			_chatClient.sendMsg(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return gives the reference of the logger back. So the one who asked for
	 *         the logger can directely write a log.
	 */
	public LogFactory getLogger() {
		return logger;
	}

	/**
	 * 
	 * @param file
	 *            This file is the file whre the log we will written into.
	 * @throws IOException
	 *             If the file is not usable the Exception will be thrown
	 * @throws NullPointerException
	 *             If the file is "null" the Exception will be thrown
	 */
	public void setLogger(File file) throws IOException, NullPointerException {
		logger = new LogFactory(file);
	}

	/**
	 * Will turn off the log. Before turning off, a log will be written that the
	 * log will turn off now.
	 */
	public void turnLogOff() {
		if (_logisOn)
			logger.writeLogBeforeTurnOff();
		_logisOn = false;

	}

	/**
	 * Will turn on the log. After turning off, a log will be written that the
	 * has now turned on.
	 */
	public void turnLogOn() {
		_logisOn = true;

	}

	/**
	 * @return It just return if the log is in that moment turned on (true) or
	 *         turned off (false).
	 */
	public boolean isLogOn() {
		return _logisOn;

	}

	/**
	 * @return Return true if the Client is connected to the server, if not, the
	 *         return false. This method is used among other things from the
	 *         ChatClient(GUI/CLI) for asking of the conneting status.
	 */
	public boolean isConnected() {
		return _chatClient.isConnected();
	}

}
