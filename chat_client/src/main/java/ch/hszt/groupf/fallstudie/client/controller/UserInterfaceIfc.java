package ch.hszt.groupf.fallstudie.client.controller;

import ch.hszt.groupf.fallstudie.client.socket.SocketClientConsumerIfc;



public interface UserInterfaceIfc extends SocketClientConsumerIfc {
	public void displayConnStatus(String connectionStatus);

	
}
