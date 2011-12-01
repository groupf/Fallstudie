package ch.hszt.groupf.fallstudie.server.msgparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MsgParserTest {
	/**
	 * This Testclass tests the functionality of the Message-Parser. A massage
	 * can be for a specific user and because of the reason that communication
	 * between the server and the client is a simple String, there must be a
	 * reserved Character to indicate this.Beside the recipients-name needs to
	 * be in the message at a specified place.
	 * 
	 * @author esterren
	 * 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests if a message is for a specific User. If a Message starts with the
	 * Character ' / ' the function under test should return true, otherwise
	 * false.
	 */
	@Test
	public void testIsForSpecificUser() {
		assertTrue(MsgParser.isForSpecificUser("/asdfwwe"));
		assertTrue(MsgParser.isForSpecificUser("/ Hallo"));
		assertTrue(MsgParser.isForSpecificUser("/"));
		assertFalse(MsgParser.isForSpecificUser("Hallo Test"));
		assertFalse(MsgParser.isForSpecificUser("\\user hello"));
		assertFalse(MsgParser.isForSpecificUser("\\user"));
	}

	/**
	 * If a message is null the test should fail with a
	 * IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsForSpecificUserNull() {
		boolean test = MsgParser.isForSpecificUser(null);
	}

	/**
	 * Tests if the recipeint-name is returned from a message with the indicator
	 * ' / ' plus recipient-username plus message (and various other
	 * combinations) The separator between the recipient-name and the message is
	 * a blank.
	 */
	@Test
	public void testGetRecipientFromMsg() {
		assertEquals("testUser", MsgParser.getRecipientFromMsg("/testUser Ab hier kommt die Meldung"));

		assertEquals("testUserAbhierkommtdieMeldung", MsgParser.getRecipientFromMsg("/testUserAbhierkommtdieMeldung"));

		assertEquals("testUser:AbhierkommtdieMeldung", MsgParser.getRecipientFromMsg("/testUser:AbhierkommtdieMeldung"));
		assertEquals("", MsgParser.getRecipientFromMsg("/"));

		assertEquals("", MsgParser.getRecipientFromMsg("\testUser Abhier kommt die Meldung"));
	}

	/**
	 * If a message is null the test should fail with a
	 * IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetRecipientFromMsgNull() {
		String test = MsgParser.getRecipientFromMsg(null);
	}

	/**
	 * Tests, if the real message out of the message with indicator +
	 * recipient-username + message is returned.
	 */
	@Test
	public void testGetMsgPartFromMsg() {
		assertEquals("Ab hier kommt die Meldung", MsgParser.getMsgPartFromMsg("/testUser Ab hier kommt die Meldung"));

		assertEquals("", MsgParser.getMsgPartFromMsg("/testUserAbhierkommtdieMeldung"));

		assertEquals("", MsgParser.getMsgPartFromMsg("/testUser:AbhierkommtdieMeldung"));
		assertEquals("", MsgParser.getMsgPartFromMsg("/"));

		assertEquals("\testUser Abhier kommt die Meldung",
				MsgParser.getMsgPartFromMsg("\testUser Abhier kommt die Meldung"));
	}

	/**
	 * If a message is null the test should fail with a
	 * IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetMsgPartFromMsgNull() {

		String test = MsgParser.getMsgPartFromMsg(null);
	}

}
