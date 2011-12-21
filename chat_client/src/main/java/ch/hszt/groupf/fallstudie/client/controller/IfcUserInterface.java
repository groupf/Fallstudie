package ch.hszt.groupf.fallstudie.client.controller;

import java.io.File;
import java.io.IOException;

import ch.hszt.groupf.fallstudie.client.log.LogFactory;
import ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer;

/**
 * Describes the stategy of the Client User Interfaces like ChatClientGUI or ChatClientCLI
 * 
 * @author groupf
 */
public interface IfcUserInterface extends IfcSocketClientConsumer {
	
	/**
	 * Shows on the Client Interface the status of the connection. Connected or not connected.
	 */
	public void displayConnStatus();
	
	/**
	 * This method is just used for JUnit Tests
	 */
	public void setLoggeronController(File file) throws IOException;
	
	/**
	 * This method is just used for JUnit Tests
	 */
	public LogFactory getLoggeronController();
	
	public String getChatClientString();
	
	
	
}
