package ch.hszt.groupf.fallstudie.client.controller;

import ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer;


public interface IfcUserInterface extends IfcSocketClientConsumer {
	public void displayConnStatus(String connectionStatus);
	
}
