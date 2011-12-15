package ch.hszt.groupf.fallstudie.server.srvconfig;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This Class holds default values for the chat server, until they are placed in
 * a config file.
 * 
 * @author rest
 * 
 */
public class ServerDefaultConfigTest {

	/**
	 * This Test is just needed for a better coverage in the emma-plugin. It
	 * tests, that the Class is instancable.
	 */
	@Test
	public void testServerDefaultConfig() {
		ServerDefaultConfig sdc = new ServerDefaultConfig();
	}

	/**
	 * Tests, if the default serverport is set to 8090.
	 */
	@Test
	public void testServerPort() {
		assertEquals(8090, ServerDefaultConfig.SERVERPORT);
	}

}
