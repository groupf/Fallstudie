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


	protected IfcClientSocket getNewClientSocket(IfcSocketClientConsumer inSktClientConsumer) {
		return new ClientSocket(inSktClientConsumer);
	}

	/**
	 * @param inCltController This is 
	 * @return gives the reference of a new created Chat Client CLI back
	 */protected IfcUserInterface getNewChatClientCLI(ClientController inCltController) {
		return new ChatClientCLI(inCltController);
	}

	/**
	 * @param inCltController
	 * @return gives the reference of a new created Chat Client CLI back
	 */
	
	protected IfcUserInterface getNewChatClientGUI(ClientController inCltController) {
		return new ChatClientGUI(inCltController);
	}

	/**
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

	public IfcUserInterface getUserInterface() {

		return _userInterface;

	}

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
		// TODO Auto-generated method stub

	}

	public void onReceivedMsg(String inMessage) {
		_userInterface.onReceivedMsg(inMessage);

	}

	public void connect(InetAddress serverAddress, int serverPort, String username) {

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

	public LogFactory getLogger() {
		return logger;
	}

	public void setLogger(File file) throws IOException, NullPointerException {
		logger = new LogFactory(file);
	}

	public void turnLogOff() {
		if (_logisOn)
			logger.writeLogBeforeTurnOff();
		_logisOn = false;

	}

	public void turnLogOn() {
		_logisOn = true;

	}

	public boolean isLogOn() {
		return _logisOn;

	}

	public boolean isConnected() {
		return _chatClient.isConnected();
	}
	
}
