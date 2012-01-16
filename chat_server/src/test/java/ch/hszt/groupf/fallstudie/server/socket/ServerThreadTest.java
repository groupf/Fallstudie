package ch.hszt.groupf.fallstudie.server.socket;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerThreadTest {

	private final ChatServer _socketServer = mock(ChatServer.class);
	private final Socket _socket = mock(Socket.class);
	private final DataInputStream _dInStream = mock(DataInputStream.class);
	private String _username = "TestUser";
	private ServerThread _serverThread;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		_serverThread = new ServerThread(_socketServer, _socket, _username) {
			@Override
			protected DataInputStream getNewDinStream(Socket inSocket) throws IOException {
				return _dInStream;
			}
		};
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testServerThreadConstructor() {
		try {
			new ServerThread(null, null, null);
			fail("not null param");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("null"));
		}

	}

	@Test
	public void testRun() throws IOException {

		_serverThread.start();
		// when(_dInStream.readUTF()).then(new Answer<Object>() {
		// public Object answer(InvocationOnMock invocation) throws Throwable {
		//
		// throw new IOException();
		// }
		// });

		verify(_socketServer, times(1)).sendJoinedMsg(_username);
		// verify(_socketServer, times(1)).removeConnection(_username, _socket);
	}

	// @Test
	public void testServerThread() {
		fail("Not yet implemented");
	}

}
