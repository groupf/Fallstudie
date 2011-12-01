package ch.hszt.groupf.fallstudie.client.socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ClientSocketTest {
	private int c = 0;

	private SocketClientConsumerIfc clientConsumer = mock(SocketClientConsumerIfc.class);
	private ClientSocket _clientSocket;
	private final Socket _socket = mock(Socket.class);
	private final DataOutputStream _dos = mock(DataOutputStream.class);
	private final DataInputStream _dis = spy(new DataInputStream(mock(InputStream.class)));
	private static InetAddress _localhost;
	private final int _serverPort = 10000;
	private final String _userName = "TestUser";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_localhost = Inet4Address.getLocalHost();

		_clientSocket = new ClientSocket(clientConsumer) {
			@Override
			Socket getNewSocket(InetAddress inServerAddress, int inServerPort) throws IOException,
					IllegalArgumentException {
				if (inServerPort < 0)
					throw new IllegalArgumentException("inServerPort must be greater or equals 0");
				return _socket;
			}

			@Override
			DataOutputStream getNewDataOutputStream(Socket inSocket) throws IOException {
				return _dos;
			}

			@Override
			DataInputStream getNewDataInputStream(Socket inSocket) throws IOException {
				return _dis;
			}
		};

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClientSocketConstructor() {
		try {
			new ClientSocket(null);
			fail("not null param");
		} catch (IllegalArgumentException e) {
			// expected
			assertTrue(e.getMessage().contains("null"));

		}
		SocketClientConsumerIfc clientConsumer = mock(SocketClientConsumerIfc.class);
		@SuppressWarnings("unused")
		ClientSocket clientSocket = new ClientSocket(clientConsumer);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testConnectInvalidPort() throws Exception {

		_clientSocket.connect(_localhost, -1, _userName);
	}

	@Test
	public void testOnDisconnectedCallBack() throws Exception {
		final Object lock = new Object();
		when(_socket.isClosed()).thenReturn(true);

		when(_dis.readUTF()).then(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				synchronized (lock) {
					try {
						c++;
						throw new IOException();
					} finally {
						lock.notify();
					}
				}
			}
		});
		_clientSocket.connect(_localhost, _serverPort, _userName);
		// when(_clientSocket.getNewSocket((InetAddress) anyObject(),
		// anyInt())).thenReturn(_socket);
		// when(_clientSocket.getNewDataInputStream(_socket)).thenReturn(_dis);
		// when(_clientSocket.getNewDataOutputStream(_socket)).thenReturn(_dos);

		synchronized (lock) {
			while (c == 0) {
				lock.wait();
			}
		}
		assertEquals(1, c);
		verify(clientConsumer).onDisconnected(any(IOException.class));
		// when(clientConsumer.onDisconnected("first").thenReturn("first"));

	}

	@Test
	public void testSendMsg() throws Exception {
		// when(_clientSocket.getNewSocket((InetAddress) anyObject(),
		// anyInt())).thenReturn(_socket);
		// when(_clientSocket.getNewDataInputStream((Socket)
		// anyObject())).thenReturn(_dis);
		// when(_clientSocket.getNewDataOutputStream((Socket)
		// anyObject())).thenReturn(_dos);

		_clientSocket.connect(_localhost, _serverPort, _userName);
		// when(_clientSocket.getNewSocket(Inet4Address.getLocalHost(),
		// 10000)).thenReturn(_socket);
		// when(_clientSocket.getNewDataInputStream(_socket)).thenReturn(_dis);
		// when(_clientSocket.getNewDataOutputStream(_socket)).thenReturn(_dos);
		// when(_clientSocket.setClientDataOut(anyObject()));
		// DataOutputStream os = null;
		// when(_socket.getOutputStream()).thenReturn(os);
		_clientSocket.sendMsg("Test");
		// assertTrue(os.);
		verify(_dos).writeUTF("Test");
	}
}
