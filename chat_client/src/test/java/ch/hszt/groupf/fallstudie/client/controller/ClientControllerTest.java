package ch.hszt.groupf.fallstudie.client.controller;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.hszt.groupf.fallstudie.client.log.LogFactory;
import ch.hszt.groupf.fallstudie.client.socket.IfcClientSocket;

public class ClientControllerTest {

	private IfcClientSocket _chatClient;
	private IfcUserInterface _userInterface;
	private ClientController clientControllerCLI = null;
	private ClientController clientControllerGUI = null;
	private LogFactory logger = null;
	private boolean _logisOn = false;
	StringWriter stringWriter = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		// stringWriter = new StringWriter();
		logger = new LogFactory(stringWriter);
		clientControllerGUI = new ClientController(true, true);
		clientControllerCLI = new ClientController(true, true);

	}

	@Test
	public void testClientController() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnDisconnected() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnReceivedMsg() {
		fail("Not yet implemented");

		// assertFalse(false);
	}

	@Test
	public void testConnect() {
		fail("Not yet implemented");
	}

	@Test
	public void testSend() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLogger() {
		ClientController clientControllerGUI2 = new ClientController(true, true);
		ClientController clientControllerCLI2 = new ClientController(true, true);
		File myFile = new File("C:\test");

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

		/**
		 * Test GUI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerGUI2.setLogger(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
		}
		if (clientControllerGUI2.getLogger() == null)
			assertTrue(true);
		else
			assertTrue(false);

		/**
		 * Test CLI Controller with Logger == null;
		 */
		try {
			try {
				clientControllerCLI2.setLogger(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e1) {
		}
		if (clientControllerCLI2.getLogger() == null)
			assertTrue(true);
		else
			assertTrue(false);

	}

	@Test
	public void testSetLogger() {
		File myFile = new File("C:\test");

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

		ClientController clientControllerGUI2 = new ClientController(true, true);
		ClientController clientControllerCLI2 = new ClientController(true, true);

		clientControllerGUI.turnLogOn();
		clientControllerGUI2.turnLogOff();
		clientControllerCLI.turnLogOn();
		clientControllerCLI2.turnLogOff();
		assertTrue(clientControllerGUI.isLogOn());
		assertFalse(clientControllerGUI2.isLogOn());
		assertTrue(clientControllerCLI.isLogOn());
		assertFalse(clientControllerCLI2.isLogOn());

	}

}
