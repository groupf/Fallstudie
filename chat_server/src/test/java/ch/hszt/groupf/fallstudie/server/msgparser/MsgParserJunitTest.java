package ch.hszt.groupf.fallstudie.server.msgparser;

import junit.framework.TestCase;

/**
 * This Testclass tests the functionality of the Message-Parser. A massage can
 * be for a specific user and because of the reason that communication between
 * the server and the client is a simple String, there must be a reserved
 * Character to indicate this.Beside the recipients-name needs to be in the
 * message at a specified place.
 * 
 * @author esterren
 * 
 */
public class MsgParserJunitTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests if a message is for a specific User. If a Message starts with the
	 * Character ' / ' the function under test should return true, otherwise
	 * false.
	 */
	public void testIsForSecificUser() {

		assertTrue(MsgParser.isForSpecificUser("/asdfwwe"));
		assertTrue(MsgParser.isForSpecificUser("/ Hallo"));
		assertTrue(MsgParser.isForSpecificUser("/"));
		assertFalse(MsgParser.isForSpecificUser("Hallo Test"));
		assertFalse(MsgParser.isForSpecificUser("\\user hello"));
		assertFalse(MsgParser.isForSpecificUser("\\user"));
		assertFalse(MsgParser.isForSpecificUser(null));
	}

	/**
	 * Tests if the recipeint-name is returned from a message with the indicator
	 * ' / ' plus recipient-username plus message (and various other
	 * combinations) The separator between the recipient-name and the message is
	 * a blank.
	 */
	public void testGetRecipientUsername() {
		assertEquals("testUser", MsgParser.getRecipientFromMsg("/testUser Ab hier kommt die Meldung"));

		assertEquals("testUserAbhierkommtdieMeldung", MsgParser.getRecipientFromMsg("/testUserAbhierkommtdieMeldung"));

		assertEquals("testUser:AbhierkommtdieMeldung", MsgParser.getRecipientFromMsg("/testUser:AbhierkommtdieMeldung"));
		assertEquals("", MsgParser.getRecipientFromMsg("/"));

		assertEquals("", MsgParser.getRecipientFromMsg("\testUser Abhier kommt die Meldung"));
		assertEquals("", MsgParser.getRecipientFromMsg(null));
	}

	/**
	 * Tests, if the real message form the message with indicator +
	 * recipient-username + message is returned.
	 */
	public void testGetMsgPartFromMsg() {

		assertEquals("Ab hier kommt die Meldung", MsgParser.getMsgPartFromMsg("/testUser Ab hier kommt die Meldung"));

		assertEquals("", MsgParser.getMsgPartFromMsg("/testUserAbhierkommtdieMeldung"));

		assertEquals("", MsgParser.getMsgPartFromMsg("/testUser:AbhierkommtdieMeldung"));
		assertEquals("", MsgParser.getMsgPartFromMsg("/"));

		assertEquals("\testUser Abhier kommt die Meldung",
				MsgParser.getMsgPartFromMsg("\testUser Abhier kommt die Meldung"));
		assertEquals("", MsgParser.getMsgPartFromMsg(null));

	}
}
