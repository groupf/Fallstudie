package ch.hszt.groupf.fallstudie.server.msgparser;

public class MsgParser {

	public static final String _sendToIndicator = "/";

	/**
	 * Checks if a message is for a specific user.
	 * 
	 * @param inMessage
	 *            message from the chat-client
	 * @return true, if the inMessage starts with the character ' / ', otherwise
	 *         false.
	 */
	public static boolean isForSpecificUser(String inMessage) {
		return inMessage.startsWith(_sendToIndicator);
	}

	/**
	 * Returns the recipient's username from a chat-client message, which is for
	 * a specific user (recipient).
	 * 
	 * @param inMessage
	 *            message from the chat-client
	 * @return the recipient, which must be placed after the indicator (' / ')
	 *         and the first blank in the inMessage String. Otherwise it returns
	 *         an empty String.
	 */
	public static String getRecipientFromMsg(String inMessage) {
		if (isForSpecificUser(inMessage)) {
			String recipientUsername = "";

			recipientUsername += inMessage.replaceFirst("^" + _sendToIndicator, "");
			recipientUsername = recipientUsername.replaceFirst("\\p{Blank}.*", "");
			return recipientUsername;

		}
		return "";
	}

	/**
	 * Returns the real message out from an unparsed chat-client message.
	 * 
	 * @param inMessage
	 *            message from the chat-client
	 * @return the real message, which starts after the pattern ' / ' +
	 *         recipient + {blank}
	 */
	public static String getMsgPartFromMsg(String inMessage) {
		if (!isForSpecificUser(inMessage)) {
			return inMessage;
		}

		String msgPart = "";
		if (!inMessage.contains(" ")) {
			return msgPart;
		}

		msgPart += inMessage.replaceFirst("^" + _sendToIndicator, "");

		msgPart = msgPart.replaceFirst("\\S*\\p{Blank}", "");

		return msgPart;
	}
}
