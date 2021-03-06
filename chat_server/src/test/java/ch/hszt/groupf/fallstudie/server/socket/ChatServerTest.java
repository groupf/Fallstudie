package ch.hszt.groupf.fallstudie.server.socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.hszt.groupf.fallstudie.server.srvconfig.ServerDefaultConfig;

public class ChatServerTest {
	private ChatServer _chatServer;
	private final ServerSocket _serverSocket = mock(ServerSocket.class);
	private final ServerThread _serverThread = mock(ServerThread.class);
	private final Socket _socket = mock(Socket.class);
	private final DataOutputStream _dos = mock(DataOutputStream.class);

	private final static int maxClientConn = 10;
	private int counter = 0;

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

		// _socketServer = new ChatServer(ServerDefaultConfig.SERVERPORT) {
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
		// // throw new IOException();
		// // return "testuser";
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
	 * 
	 * @throws IOException
	 */
	// TODO Needs refactoring!!
	@Test(expected = ExitException.class)
	public void testChatServerStopsOnIOExInNewServerSocket() throws Exception {
		final Object lock = new Object();
		counter = 0;

		_chatServer = new ChatServer(ServerDefaultConfig.SERVERPORT) {
			@Override
			protected ServerSocket newServerSocket(int inServerPort) throws IOException {

				synchronized (lock) {
					try {
						counter++;
						throw new IOException();
					} finally {
						lock.notify();
					}
				}

			}

			@Override
			protected String getFirstIncomingString(Socket inSocket) throws IOException {
				// TODO check the received username if it is in a legal
				// pattern.
				// otherwise throw IllegalArgumentException
				throw new IOException();
				// return "testuser";
			}

			@Override
			protected DataOutputStream getDosFromSocket(Socket inSocket) throws IOException {
				return _dos;
			}
		};
		synchronized (lock) {
			while (counter == 0) {
				lock.wait();
			}
		}
		assertEquals(1, counter);
	}

	/**
	 * Tests if the amount of connections defined in maxClientConn are opened.
	 * This means that the ChatServer only has to open this number of Sockets.
	 * 
	 * 
	 */
	@Test
	public void testChatServerClientConnections() throws Exception {
		final Object lock = new Object();
		counter = 0;
		// when(_serverSocket.accept()).thenReturn(_socket);
		when(_serverSocket.isClosed()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				synchronized (lock) {
					try {
						if (counter < maxClientConn) {
							return false;
						} else {
							return true;
						}

					} finally {
						lock.notify();
					}
				}
			}
		});

		when(_serverSocket.accept()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				synchronized (lock) {
					try {
						if (counter < maxClientConn) {
							counter++;
							return _socket;
						} else {
							throw new IOException();
						}

					} finally {
						lock.notify();
					}
				}
			}
		});

		_chatServer = new ChatServer(ServerDefaultConfig.SERVERPORT) {
			@Override
			protected ServerSocket newServerSocket(int inServerPort) throws IOException {

				return _serverSocket;
			}

			@Override
			protected String getFirstIncomingString(Socket inSocket) throws IOException {

				double rnd = Math.random() * maxClientConn * 10;
				int rrnd = (int) rnd;
				return "TestUser" + rrnd;

			}

			@Override
			protected DataOutputStream getDosFromSocket(Socket inSocket) throws IOException {
				return _dos;
			}

			@Override
			protected void startNewServerThread(ChatServer inChatServer, Socket inSocket, String inSocketUserName) {

			}
		};
		synchronized (lock) {
			while (counter < maxClientConn) {
				lock.wait();
			}
		}

		assertEquals(maxClientConn, counter);
	}

	// TODO test sendJoindMsg storie
	// @Test
	public void testSendJoinedMsg() throws Exception {
		final Object lock = new Object();
		counter = 0;
		// when(_serverSocket.accept()).thenReturn(_socket);
		when(_serverSocket.isClosed()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {

				synchronized (lock) {

					try {
						if (counter < maxClientConn) {
							return false;
						} else {
							return true;
						}

					} finally {
						lock.notify();
					}
				}
			}
		});

		when(_serverSocket.accept()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {

				synchronized (lock) {
					// while (counter == 0) {
					// lock.notify();
					// }
					try {
						if (counter < maxClientConn) {
							counter++;
							return _socket;
						} else {
							throw new IOException();
						}

					} finally {
						lock.notify();
					}
				}
			}
		});

		_chatServer = new ChatServer(ServerDefaultConfig.SERVERPORT) {
			@Override
			protected ServerSocket newServerSocket(int inServerPort) throws IOException {

				return _serverSocket;
			}

			@Override
			protected String getFirstIncomingString(Socket inSocket) throws IOException {

				return "TestUser1";

			}

			@Override
			protected DataOutputStream getDosFromSocket(Socket inSocket) throws IOException {
				return _dos;
			}

			@Override
			protected void startNewServerThread(ChatServer inChatServer, Socket inSocket, String inSocketUserName) {

			}
		};
		synchronized (lock) {
			while (counter < maxClientConn) {
				lock.wait();
			}
		}

		assertEquals(maxClientConn, counter);
	}

	// @Test
	public void testSendToAll() {
		fail("Not yet implemented");
	}

	// @Test
	public void testSendToSpecificUser() {
		fail("Not yet implemented");
	}

	// @Test
	public void testRemoveConnection() {
		fail("Not yet implemented");
	}

	// @Test
	public void testAddUserToMap() {
		fail("Not yet implemented");
	}

}
