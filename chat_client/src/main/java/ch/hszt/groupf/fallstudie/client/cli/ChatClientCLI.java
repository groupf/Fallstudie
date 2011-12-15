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


public class ChatClientCLI implements IfcUserInterface {
	private boolean _exitCLI = false;
	private final ClientController _controller;
	private String[] goodByeMessages = {"Good bye","See you soon","CYA", "Bye", "Peace"};

	public ChatClientCLI(ClientController inClientController) {
		_controller = inClientController;
		runSubshell();
	}

	private void runSubshell() {
		String inText = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

		welcomeMsg();
		while (!_exitCLI) {

			try {
				msgParser(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
//			} finally {
//
			}

		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
					System.out.println("connect");
					int port = Integer.parseInt(currentLine[2]);
					String username = currentLine[3];
					InetAddress ipAddress = getHostByName(currentLine[1]);
					System.out.println("" + ipAddress + port + username);
					//_controller.connect(ipAddress, port, username);
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
				System.out.println("currently under development");
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
			//_controller.send(inText);
		}	
	}
	
	private InetAddress getHostByName(String name) throws UnknownHostException{
			return InetAddress.getByName(name);
	}
	
	private void printHelpMsg() {
		System.out.println("currently supported commands");
		System.out.println("****************************");
		System.out.println("\\help\t\t\tDisplay this help message");
		System.out.println("\\connect <h> <p> <u>\tConnect as <u> to <h> <pp> ");
		System.out.println("\\quit\t\t\tExit Chat");
		System.out.println("\\logfile\t\tSet path to logfile. This will turn on logging as well");
		System.out.println("\\log:(on|off)\t\tTurn log on / off");
		System.out.println("\\status\t\t\tDisplays info about current connection status");
	}
	
	private void welcomeMsg() {
		System.out.println("Welcome to the CLI-Chat Client IRCv2" + System.getProperty("line.separator"));
		// TODO print out the help (possible commands)
	}

	public void onDisconnected(Exception ex) {
		// TODO evtl. use a write-buffer
		System.out.println("Connection lost!");

	}

	public void onReceivedMsg(String inMessage) {
		// TODO evtl. use a write-buffer
		System.out.println(inMessage);
	}

	public void displayConnStatus(String connectionStatus) {
		System.out.println("You are " + connectionStatus);
	}

	/**
	 * This method is just used for JUnit Tests
	 */
	@Override
	public void setLoggeronController(File file) throws IOException, NullPointerException{
		_controller.setLogger(file);
		
	}

	@Override
	public LogFactory getLoggeronController() {
		return _controller.getLogger();
	}

	@Override
	public String getChatClientString() {
		return "CLI";
	}

}
