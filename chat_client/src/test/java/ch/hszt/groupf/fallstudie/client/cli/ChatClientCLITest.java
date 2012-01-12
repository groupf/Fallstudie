package ch.hszt.groupf.fallstudie.client.cli;

import org.junit.Test;

import ch.hszt.groupf.fallstudie.client.controller.ClientController;
import ch.hszt.groupf.fallstudie.client.controller.IfcUserInterface;
import ch.hszt.groupf.fallstudie.client.socket.IfcClientSocket;
import ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ChatClientCLITest {
	private IfcClientSocket _chatClient = mock(IfcClientSocket.class);
	private IfcUserInterface _userInterface = mock(IfcUserInterface.class);
	
	private ClientController clientControllerCLI = null;

	
	private ChatClientCLI cli;

	public void setUp() {
		clientControllerCLI = new ClientController(true) {
			protected IfcClientSocket getNewClientSocket(IfcSocketClientConsumer inSktClientConsumer) {
				return _chatClient;
			}
	
			protected IfcUserInterface getNewChatClientGUI(ClientController inCltController) {
				return _userInterface;
			}
		};		
	}

	@Test
	public void testHelpMsg() {
		
	}

}
