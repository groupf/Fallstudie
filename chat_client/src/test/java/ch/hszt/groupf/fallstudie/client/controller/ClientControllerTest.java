package ch.hszt.groupf.fallstudie.client.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hszt.groupf.fallstudie.client.log.LogFactory;
import ch.hszt.groupf.fallstudie.client.socket.IfcClientSocket;
import ch.hszt.groupf.fallstudie.client.socket.IfcSocketClientConsumer;

public class ClientControllerTest {

	private IfcClientSocket _chatClient = mock(IfcClientSocket.class);
	private IfcUserInterface _userInterface = mock(IfcUserInterface.class);
	
	private ClientController clientControllerCLI = null;
	private ClientController clientControllerGUI = null;
	// müsste auch gemockt werden
	private LogFactory logger = null;
	private boolean _logisOn = false;
	StringWriter stringWriter = null;

	private static InetAddress _localhost;
	private final int _serverPort = 10000;
	private final String _userName = "TestUser";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		_localhost = Inet4Address.getLocalHost();

		// stringWriter = new StringWriter();
		logger = new LogFactory(stringWriter);
		
		clientControllerGUI = new ClientController(false) {
			@Override
			protected IfcClientSocket getNewClientSocket(IfcSocketClientConsumer inSktClientConsumer) {
				return _chatClient;
			}

//			protected IfcUserInterface getNewChatClientCLI(ClientController inCltController) {
//				return _userInterface;
//			}

			protected IfcUserInterface getNewChatClientGUI(ClientController inCltController) {
				return _userInterface;
			}
		};
		clientControllerCLI = new ClientController(true) {
			@Override
			protected IfcClientSocket getNewClientSocket(IfcSocketClientConsumer inSktClientConsumer) {
				return _chatClient;
			}

			protected IfcUserInterface getNewChatClientCLI(ClientController inCltController) {
				return _userInterface;
			}

//			protected IfcUserInterface getNewChatClientGUI(ClientController inCltController) {
//				return _userInterface;
//			}
		};

	}

	// Was willst du hier genau testen?
	// @Test
	// public void testClientController() {
	// clientControllerGUI = new ClientController(false, true);
	// clientControllerCLI = new ClientController(true, true);
	//
	// if
	// (clientControllerCLI.getUserInterface().getChatClientString().contentEquals("CLI"))
	// assertTrue(true);
	// else
	// assertTrue(false);
	//
	// if
	// (clientControllerGUI.getUserInterface().getChatClientString().contentEquals("GUI"))
	// assertTrue(true);
	// else
	// assertTrue(false);
	//
	// }

	/**
	 * Wenn der Controller mit dem Parameter '-c' gestartet wird, muss verifiziert werden, 
	 * dass die CLI instanziert wird. Ansonsten soll die GUI Klasse instanziert werden. 
	 */
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	/**
	 * Simuliertes Verhalten auf dem Socket: 
	 * 1. beim Verbindungsaufbau mit dem Socket wird eine Exception geworfen
	 * 
	 * Erwatetes Verhalten: die Exception wird an das Userinterface weitergeworfen.
	 * 
	 * evtl. zweiter Testfall: Exception wird vom Socket geworfen, nachdem die Verbindung erfolgreich aufgebaut wurde.
	 */
	@Test
	public void testOnDisconnected() {
		fail("Not yet implemented");
	}
	
/**
 * Der Socket hat eine neue Mitteilung erhalten, diese soll an das Userinterface weitergeleitet werden.
 */
	@Test
	public void testOnReceivedMsg() {
		fail("Not yet implemented");

		// assertFalse(false);
	}

	/**
	 * Testfall: erfollgreicher Connect (mit CLI instanziert). Die Methode connect wurde auf dem Socket-Objekt aufgerufen.
	 */
	@Test
	public void testConnectCLI() {
		clientControllerCLI.connect(_localhost, _serverPort, _userName);
		try {
			verify(_chatClient).connect(_localhost, _serverPort, _userName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Testfall: erfollgreicher Connect (mit GUI instanziert). Die Methode connect wurde auf dem Socket-Objekt aufgerufen.
	 */
	@Test
	public void testConnectGUI() {
		clientControllerGUI.connect(_localhost, _serverPort, _userName);
		try {
			verify(_chatClient).connect(_localhost, _serverPort, _userName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Auf dem Userinterface wurde die send-Methode aufgerufen. Es wird erwartet, dass die übergebene Mitteilung an den Socket übergeben wird. (send-Methode auf dem Socket)
	 */
	@Test
	public void testSend() {
		fail("Not yet implemented");
	}

/**
 * was wird hier getestet? -->Micha
 */
	@Test
	public void testGetLoggerNotNull() {

		/*
		 * as we are currently developing on different operating systems
		 * java.io.tmpdir should be used as temporary directory instead of 'c:\'
		 */
		// File myFile = new File("C:\test");
		File myFile = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "test");

		/**
		 * Test GUI Controller with Logger != null;
		 */
		try {
			try {
				clientControllerGUI.setLogger(myFile);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(true);
		}

		if (clientControllerGUI.getLogger() == null)
			assertFalse(true);
		else {
			assertTrue(true);
		}

		/**
		 * Test CLI Controller with Logger != null;
		 */
		try {
			try {
				clientControllerCLI.setLogger(myFile);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(true);
		}

		if (clientControllerCLI.getLogger() == null)
			assertFalse(true);
		else {
			assertTrue(true);
		}

	}

	@Test
	public void testGetLoggerWithNull() {
		// ClientController clientControllerGUI2 = new ClientController(true,
		// true);
		// ClientController clientControllerCLI2 = new ClientController(true,
		// true);
		/**
		 * Test GUI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerGUI.setLogger(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
		}
		if (clientControllerGUI.getLogger() == null)
			assertTrue(true);
		else
			assertTrue(false);

		/**
		 * Test CLI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerCLI.setLogger(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
		}
		if (clientControllerCLI.getLogger() == null)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testSetLoggerWithNull() {

		/**
		 * Test GUI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerGUI.setLogger(null);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(false);
		}

		/**
		 * Test CLI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerCLI.setLogger(null);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(false);
		}

	}

	@Test
	public void testSetLoggerNotNull() {
		/*
		 * as we are currently developing on different operating systems
		 * java.io.tmpdir should be used as temporary directory instead of 'c:\'
		 */
		// File myFile = new File("C:\test");
		File myFile = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "test");

		/**
		 * Test GUI Controller with Logger != null;
		 */
		try {
			try {
				clientControllerGUI.setLogger(myFile);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(true);
		}

		assertTrue(true);

		/**
		 * Test CLI Controller with Logger != null;
		 */
		try {
			try {
				clientControllerCLI.setLogger(myFile);
			} catch (IOException e) {
				assertFalse(true);
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
			assertFalse(true);
		}
		assertTrue(true);

	}

	@Test
	public void testTurnLogOff() {
		clientControllerGUI.turnLogOff();
		clientControllerCLI.turnLogOff();
		assertFalse(clientControllerCLI.isLogOn());
		assertFalse(clientControllerGUI.isLogOn());
	}

	@Test
	public void testTurnLogOn() {
		clientControllerGUI.turnLogOn();
		clientControllerCLI.turnLogOn();
		assertTrue(clientControllerCLI.isLogOn());
		assertTrue(clientControllerGUI.isLogOn());

	}

	@Test
	public void testIsLogOn() {

//		ClientController clientControllerGUI2 = new ClientController(true, true);
//		ClientController clientControllerCLI2 = new ClientController(true, true);
//
//		clientControllerGUI.turnLogOn();
//		clientControllerGUI2.turnLogOff();
//		clientControllerCLI.turnLogOn();
//		clientControllerCLI2.turnLogOff();
//		assertTrue(clientControllerGUI.isLogOn());
//		assertFalse(clientControllerGUI2.isLogOn());
//		assertTrue(clientControllerCLI.isLogOn());
//		assertFalse(clientControllerCLI2.isLogOn());

	}

}
