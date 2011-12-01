package ch.hszt.groupf.fallstudie.client.socket;

public interface IfcSocketClientConsumer {

	public void onDisconnected(Exception inEx);
	public void onReceivedMsg(String inMessage);
	
	
}
