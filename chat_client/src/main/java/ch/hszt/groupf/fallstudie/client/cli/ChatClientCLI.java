package ch.hszt.groupf.fallstudie.client.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ch.hszt.groupf.fallstudie.client.controller.ClientController;
import ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface;


public class ChatClientCLI implements IfcUserInterface {
	private boolean _exitCLI = false;
	private final ClientController _controller;

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
				inText = in.readLine();
				if (inText.contains("\\q")) {
					_exitCLI = true;
				}
				// out.write(strLine, 0, strLine.length());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

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
		// TODO evtl. use a write-buffer
		// System.out.println(connectionStatus);
	}

}
