package ch.hszt.groupf.fallstudie.client.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import ch.hszt.groupf.fallstudie.client.controller.ClientController;
import ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface;
import ch.hszt.groupf.fallstudie.client.log.LogFactory;

/**
 * ChatClientCLI will provide the user a command line interface (cli) chat client. As it will not take any command line arguments,
 * all operations (e.g. connect to server) will be done in the cli itself. 
 * 
 * @author groupf
 */
public class ChatClientCLI implements IfcUserInterface {
	private boolean _exitCLI = false;
	private final ClientController _controller;
	private String[] goodByeMessages = {"Good bye","See you soon","CYA", "Bye", "Peace"};

	
	
	/**
	 * @param inClientController
	 */
	public ChatClientCLI(ClientController inClientController) {
		_controller = inClientController;
		runSubshell();
	}
	
	
	/**
	 * This method will read the user's input line-by-line.  
	 */
	private void runSubshell() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		welcomeMsg();
		while (!_exitCLI) {

			try {
				msgParser(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * msgParser will parse any input line-by-line. It is able to differ between sending a command, a private message or a public message. 
	 * If a command is typed msgParser will call a subroutine 
	 * 
	 * @param user input line
	 */
	private void msgParser(String inText)  {
		// check whether a command is executed
		String[] currentLine = inText.split(" ");

		if (currentLine[0].startsWith("\\")) {
			currentLine[0] = currentLine[0].replaceFirst("\\\\", "");
			String command = currentLine[0];
			
			if (command.equals("quit")) {
				System.out.println(goodByeMessages[new Random().nextInt(goodByeMessages.length)]);
				_exitCLI = true;
			} else if (command.equals("connect")){
				try {
					int port = Integer.parseInt(currentLine[2]);
					String username = currentLine[3];
					InetAddress ipAddress = getHostByName(currentLine[1]);
					System.out.println("connecting to " + ipAddress + " on port " + port +  " as user " + username);
					_controller.connect(ipAddress, port, username);
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else if (command.equals("logfile")) {
				try {
					_controller.setLogger(new File(currentLine[1]));
					_controller.turnLogOn();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (command.equals("log:on")) {
				_controller.turnLogOn();
			} else if (command.equals("log:off" )) {
				_controller.turnLogOff();
			} else if (command.equals("help")) {
				printHelpMsg();
			} else if (command.equals("status")) {
				displayConnStatus();
			} else {
				System.out.println("command not found. See \\help for further information");
			}
		
		// check whether the message is private nor not		
		} else if (currentLine[0].startsWith("/")) {
			currentLine[0] = currentLine[0].replaceFirst("/", "");
			String receipt = currentLine[0];
			System.out.println("sending msg to user " + receipt);
		} else if (currentLine[0].matches("\\w+")){
			//System.out.println("sending msg to all users");
			_controller.send(inText);
		}	
	}
	
	/**
	 * Resolves a given fqdn. If your host is not resolvable by a name server an "UnknownHostException" will be thrown.
	 *   
	 * @param fqdn of remote server
	 * @return	ip address of remote server
	 * @throws UnknownHostException
	 */
	private InetAddress getHostByName(String name) throws UnknownHostException{
			return InetAddress.getByName(name);
	}
	
	/**
	 * Displays a short help page containing all supported commands
	 */
	private void printHelpMsg() {
		System.out.println("currently supported commands");
		System.out.println("****************************");
		System.out.println("\\help\t\t\tDisplay this help message");
		System.out.println("\\connect <h> <p> <u>\tConnect as <u> to host <h> on tcp/<p> ");
		System.out.println("\\quit\t\t\tExit Chat");
		System.out.println("\\logfile\t\tSet path to logfile. This will turn on logging as well");
		System.out.println("\\log:(on|off)\t\tTurn log on / off");
		System.out.println("\\status\t\t\tDisplays info about current connection status");
	}
	
	
	/**
	 * This is the Title of the Frame in the GUI.
	 */
	private void welcomeMsg() {
		System.out.println("Welcome to the CLI-Chat Client IRCv2" + System.getProperty("line.separator"));
		// TODO print out the help (possible commands)
	}

	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer#onDisconnected(java.lang.Exception)
	 */
	public void onDisconnected(Exception ex) {
		// TODO evtl. use a write-buffer
		System.out.println("Connection lost!");

	}

	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer#onReceivedMsg(java.lang.String)
	 */
	public void onReceivedMsg(String inMessage) {
		// TODO evtl. use a write-buffer
		System.out.println(inMessage);
	}

	
	
	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface#displayConnStatus()
	 */
	public void displayConnStatus() {
		String status = "disconnected"; 
		if (_controller.isConnected())
			 status = "connected";
		System.out.println("You are " + status);
	}

	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface#setLoggeronController(java.io.File)
	 */
	@Override
	public void setLoggeronController(File file) throws IOException, NullPointerException{
		_controller.setLogger(file);
		
	}

	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface#getLoggeronController()
	 */
	@Override
	public LogFactory getLoggeronController() {
		return _controller.getLogger();
	}

	/* (non-Javadoc)
	 * @see ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface#getChatClientString()
	 */
	@Override
	public String getChatClientString() {
		return "CLI";
	}

}
