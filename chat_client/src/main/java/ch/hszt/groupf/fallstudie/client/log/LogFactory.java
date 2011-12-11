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

public class LogFactory {

	private Writer _writer;
	private FileWriter _fstream;
	private File _myFile;
	private DateFormat dateFormat;
	Date date;

	public LogFactory(File file) throws IOException {

		_myFile = file;
		_fstream = new FileWriter(_myFile);
		_writer = new BufferedWriter(_fstream);
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	}

	public LogFactory(StringWriter writer) throws IOException {

		// if (writer==null) throw new IOException();
		_writer = writer;
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// writeFirstLogAfterTurnedOn();
	}

	// }

	public void writeFirstLogAfterTurnedOn() {
		// dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// date = new Date();
		writeLog("The Log turned on:");
		writeLog("****************************************");
		writeLogJustANewLine();
	}

	public void writeLogBeforeTurnOff() {
		// dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// date = new Date();
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
