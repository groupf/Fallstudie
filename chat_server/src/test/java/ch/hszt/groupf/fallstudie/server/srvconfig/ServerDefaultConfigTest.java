package ch.hszt.groupf.fallstudie.server.srvconfig;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerDefaultConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testServerPort() {
		assertEquals(8090, ServerDefaultConfig.SERVERPORT);
	}

	@After
	public void tearDown() throws Exception {
	}

}
