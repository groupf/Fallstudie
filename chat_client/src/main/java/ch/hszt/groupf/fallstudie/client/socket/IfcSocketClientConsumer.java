package ch.hszt.groupf.fallstudie.client.socket;


/**
 * 
 * This Interface decribes the perfomance of all classes implements this class.
 * It is used for the ClientController.
 * 
 * @author groupf
 */
public interface IfcSocketClientConsumer {

	public void onDisconnected(Exception inEx);
	public void onReceivedMsg(String inMessage);
	
	
}
