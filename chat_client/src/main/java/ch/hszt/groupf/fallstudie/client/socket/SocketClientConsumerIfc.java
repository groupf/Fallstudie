package ch.hszt.groupf.fallstudie.client.socket;

public interface SocketClientConsumerIfc {

	public void onDisconnected(Exception inEx);
	public void onReceivedMsg(String inMessage);
	
}
