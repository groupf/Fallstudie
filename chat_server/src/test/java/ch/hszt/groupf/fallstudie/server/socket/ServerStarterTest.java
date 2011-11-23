package ch.hszt.groupf.fallstudie.server.socket;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hszt.groupf.fallstudie.server.srvconfig.ServerDefaultConfig;

public class ServerStarterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test, if the ServerStarter Class starts with the user-specified port, if
	 * it is valid. Valid is every Integer greater than 1024.
	 */
	@Test
	public void testGetServerPortToStart() {

		assertEquals(ServerDefaultConfig.SERVERPORT, ServerStarter.getServerPortToStart(new String[] { "" }));
		assertEquals(ServerDefaultConfig.SERVERPORT, ServerStarter.getServerPortToStart(new String[] { "1024" }));
		assertEquals(12345, ServerStarter.getServerPortToStart(new String[] { "12345" }));
		assertEquals(ServerDefaultConfig.SERVERPORT, ServerStarter.getServerPortToStart(new String[] { null }));

	}
}
