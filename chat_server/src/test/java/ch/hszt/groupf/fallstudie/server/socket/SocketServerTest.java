package ch.hszt.groupf.fallstudie.server.socket;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SocketServerTest {

	private SocketServer _socketServer;
	private final ServerSocket _serverSocket = mock(ServerSocket.class);
	private final ServerThread _serverThread = mock(ServerThread.class);
	private final Socket _socket = mock(Socket.class);
	private final DataOutputStream _dos = mock(DataOutputStream.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * In the setUp Method the newServerSocket Methode from the
	 * SocketServer-Class is overwritten, to return the Mock-Object
	 * _serverSocket. Beside the SecurityManager is set to NoExitSecurityManager
	 * for the test testSocketServerStopsOnIOEx.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.setSecurityManager(new NoExitSecurityManager());
		// when(_serverSocket.accept()).thenReturn(_socket);

		// _socketServer = new SocketServer(ServerDefaultConfig.SERVERPORT) {
		// @Override
		// protected ServerSocket newServerSocket(int inServerPort) throws
		// IOException {
		// return _serverSocket;
		// }
		//
		// @Override
		// protected String getIncomingSocketUserName(Socket inSocket) throws
		// IOException, IllegalArgumentException {
		// // TODO check the received username if it is in a legal
		// // pattern.
		// // otherwise throw IllegalArgumentException
		// return "testuser";
		// }
		//
		// @Override
		// protected DataOutputStream getDosFromSocket(Socket inSocket) throws
		// IOException {
		// return _dos;
		// }
		// };
	}

	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(null);
	}

	/**
	 * Tests if the SocketServer calls system.exit(0), when the newServerSockets
	 * throws an IOException
	 */
	// @Test
	// public void testSocketServerStopsOnIOEx() {
	// try {
	// new SocketServer(ServerDefaultConfig.SERVERPORT) {
	// @Override
	// protected ServerSocket newServerSocket(int inServerPort) throws
	// IOException {
	// throw new IOException();
	// }
	//
	// };
	// } catch (ExitException e) {
	// assertEquals("Exit status", 0, e.status);
	// }
	//
	// }

	@Test
	public void testSendJoinedMsg() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendToAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendToSpecificUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUserToMap() {
		fail("Not yet implemented");
	}

}
