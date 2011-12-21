package ch.hszt.groupf.fallstudie.client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class descibes the Log (its a LogFactory). It can be used from all
 * ChatClient's to write into the Log. The Log must be written into a .txt file.
 * It is not allowed to print the log on the CLI or into the GUI directely.
 * 
 * At the moment we just can turn on or turn off the log. It is not possible for
 * some different log levels like normal, debug...
 * 
 * @author groupf
 */

public class LogFactory {

	private Writer _writer;
	private FileWriter _fstream;
	private File _myFile;
	private DateFormat dateFormat;
	Date date;

	/**
	 * Create a new LogFactory with a file. The file is a .txt file on the local
	 * machine.
	 * 
	 * @param file
	 *            This is the file on the local machine for writing the Log into
	 * @throws IOException
	 *             If the log cannot be created (wrong permission) IOException
	 *             will be thrown
	 */
	public LogFactory(File file) throws IOException {
		_myFile = file;
		_fstream = new FileWriter(_myFile);
		_writer = new BufferedWriter(_fstream);
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	}

	/**
	 * This constructor ist just used for the JUnit/Mock Test. We use String
	 * Writer instead of Buffered Writer. In Java StringWriter and
	 * BufferedWriter both extends Writer. So they have the same
	 * 
	 * @param writer
	 * @throws IOException
	 */
	public LogFactory(StringWriter writer) throws IOException {

		_writer = writer;
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * After turning the log on, this method write the first log into the
	 * logfile. So we can see later on what time the log has turned on.
	 */
	public void writeFirstLogAfterTurnedOn() {
		writeLog("The Log turned on:");
		writeLog("****************************************");
		writeLogJustANewLine();
	}

	/**
	 * Before turning the log off, this method write the last log into the
	 * logfile. So we can see later on what time the log has turned off.
	 */
	public void writeLogBeforeTurnOff() {
		writeLog("The Log turned off:");
		writeLog("****************************************");
		writeLogJustANewLine();
	}

	/**
	 * The log has the format: YYYYY/MM/DD: <logtext> e.g. 2011/12/01 20:55:33:
	 * test log
	 * 
	 * @param string
	 *            This is the logging string
	 */
	public void writeLog(String string) {
		date = new Date();
		try {
			_writer.write(dateFormat.format(date) + ": ");
			_writer.write(string);
			writeLogJustANewLine();
			_writer.flush();
		} catch (IOException e) {
			System.out.println("Could not write into the Log File the Log: "
					+ string);
			e.printStackTrace();
		}
	}

	/**
	 * It's just used to create a new line in the log.
	 * So we don't have to duplicate code.
	 */
	public void writeLogJustANewLine() {

		try {
			_writer.write("\r\n");
			_writer.flush();
		} catch (IOException e) {
			System.out.println("Could not write into the Log File the Log: ");
			e.printStackTrace();
		}
	}

}
