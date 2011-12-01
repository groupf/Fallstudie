package ch.hszt.groupf.fallstudie.server.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hszt.groupf.fallstudie.server.srvconfig.ServerDefaultConfig;

/**
 * The Class ServerStarter starts the SocketServer. To start the Server start
 * this compiled .JAR File with the preferred TCP-Port on which the Server
 * should listen.
 * <p>
 * e.g.: To start the Server on Listening-Port 10550 >java -jar
 * ServerStarter.jar 90100
 * 
 * @author Renato Estermann
 * 
 */
public class ServerStarter {
	final static Logger logger = LoggerFactory.getLogger(ServerStarter.class);

	/**
	 * The main Funktion starts the SocketServer (Server Applikation) with a
	 * specific portnumber.
	 * 
	 * @param args
	 *            actually only the argument of an Integer for a specific
	 *            serverport is provided.
	 * 
	 */
	public static void main(String[] args) {

		int serverPort = getServerPortToStart(args);

		new SocketServer(serverPort);

	}

	/**
	 * Parses and returns the port number, which can be passed to the
	 * main-methode by arguments.
	 * 
	 * @param args
	 *            the arguments from the main-methode
	 * @return the valid serverport-number parsed from args. If the Integer in
	 *         args is less than or equals 1024, the default serverport 8090
	 *         will be taken.
	 */
	protected static int getServerPortToStart(String[] args) {
		int serverPort = ServerDefaultConfig.SERVERPORT;

		// TODO eventually replace with BufferedReader from Config File
		if (args.length != 0) {
			try {

				serverPort = Integer.parseInt(args[0]);
				if (serverPort <= 1024) {
					serverPort = ServerDefaultConfig.SERVERPORT;
					logger.info("Serverports less than 1024 are not allowed. Default Port "
							+ ServerDefaultConfig.SERVERPORT + " will be used");
				}
			} catch (NumberFormatException e) {

				logger.info("Could not Parse Argument to Integer. Default Port " + ServerDefaultConfig.SERVERPORT
						+ " will be used");

			}
		}
		return serverPort;
	}
}
