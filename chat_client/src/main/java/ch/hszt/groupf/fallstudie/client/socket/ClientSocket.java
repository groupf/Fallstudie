package ch.hszt.groupf.fallstudie.client.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket implements IfcClientSocket, Runnable {

	private Socket _clientSocket;
	private DataInputStream _clientDataIn;
	private DataOutputStream _clientDataOut;
	private IfcSocketClientConsumer _sktClientConsumer;
	private String _socketUserName;

	private InetAddress _serverInetAddress;
	// TODO Change _serverPort to a Port-Class
	private int _serverPort;

	public ClientSocket(IfcSocketClientConsumer inSktClientConsumer) {
		if (inSktClientConsumer == null)
			throw new IllegalArgumentException("inSktClientConsumer must not be null");
		_sktClientConsumer = inSktClientConsumer;
		// public ClientSocket(String inServerAddress, int inServerPort) {
		// TODO ServerAddress and ServerPort Validation with Exception throw
	}

	public void connect(InetAddress inServerAddress, int inServerPort, String inUserName) throws UnknownHostException,
			IOException, IllegalArgumentException {

		_serverInetAddress = inServerAddress;
		_serverPort = inServerPort;
		_socketUserName = inUserName;

		_clientSocket = getNewSocket(_serverInetAddress, _serverPort);
		// TODO Appender to the Log, that the Socket was opened

		_clientDataIn = getNewDataInputStream(_clientSocket);
		_clientDataOut = getNewDataOutputStream(_clientSocket);
		_clientDataOut.writeUTF(_socketUserName);

		new Thread(this).start();

	}

	// void setClientSocket(Socket inclientSocket) {
	// _clientSocket = inclientSocket;
	// }
	//
	// void setClientDataOut(DataOutputStream inclientDataOut) {
	// _clientDataOut = inclientDataOut;
	// }

	DataOutputStream getNewDataOutputStream(Socket inSocket) throws IOException {
		return new DataOutputStream(inSocket.getOutputStream());
	}

	DataInputStream getNewDataInputStream(Socket inSocket) throws IOException {
		return new DataInputStream(inSocket.getInputStream());
	}

	Socket getNewSocket(InetAddress inServerAddress, int inServerPort) throws IOException {
		if (inServerPort < 0)
			throw new IllegalArgumentException("inServerPort must be greater or equals 0");
		return new Socket(inServerAddress, inServerPort);
	}

	public void sendMsg(String inMessage) throws IOException {
		// TODO implement the sendMsg Method
		// TODO Can this give a NullpointerExeption, when the socket is
		// disconnected (closed)?
		_clientDataOut.writeUTF(inMessage);

	}

	public void run() {

		boolean isConnected = true;

		while (isConnected) {
			String message = "";
			try {
				message = _clientDataIn.readUTF();

			} catch (IOException e) {
				if (_clientSocket.isClosed()) {
					_sktClientConsumer.onDisconnected(e);
					isConnected = false;
				}
			}

			_sktClientConsumer.onReceivedMsg(message);
		}

	}
}
