package ch.hszt.groupf.fallstudie.server.socket;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerThreadTest {

	private final ChatServer _socketServer = mock(ChatServer.class);
	private ServerThread _serverThread;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
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
	public void testRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testServerThread() {
		fail("Not yet implemented");
	}

}
