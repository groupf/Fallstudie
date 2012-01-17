package ch.hszt.groupf.fallstudie.client.cli;

import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hszt.groupf.fallstudie.client.controller.ClientController;
import ch.hszt.groupf.fallstudie.client.socket.IfcClientSocket;

public class ChatClientCLITest {
	private IfcClientSocket _chatClient = mock(IfcClientSocket.class);
	// private IfcUserInterface _userInterface = mock(IfcUserInterface.class);
	// private ClientController _clientController =
	// mock(ClientController.class);
	private ClientController _clientController = mock(ClientController.class);
	private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	// private static ByteArrayInputStream inContent;
	private static ByteArrayInputStream inContent;

	private ChatClientCLI _chatClientCLI = null;

	@BeforeClass
	public static void setUpStreams() {
		System.setOut(new PrintStream(outContent));

		// InputStream bais = new ByteArrayInputStream(str.getBytes());
	}

	@AfterClass
	public static void cleanUpStreams() {
		System.setOut(null);

	}

	@Before
	public void setUp() {
		// _chatClientCLI = new ChatClientCLI(_clientController);

	}

	// @Test
	public void testWelcomeMsg() {
		// assertTrue(outContent.toString().contains("Welcome"));
	}

	@Test
	public void testHelpMsg() {

	}

	// @After
	public void tearDown() {
		String quit = "\\quit" + System.getProperty("line.separator");
		inContent = new ByteArrayInputStream(quit.getBytes());
		System.setIn(inContent);
		// _chatClientCLI = null;
	}

}
