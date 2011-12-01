package ch.hszt.groupf.fallstudie.client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFactory {

	private BufferedWriter _bufferedWriter;
	private FileWriter _fstream;
	private File _myFile;
	private DateFormat dateFormat;
	Date date;

	public LogFactory(File file) throws IOException {
//		JFileChooser _fileChooser = new JFileChooser();

//		int retval = _fileChooser.showOpenDialog(_fileChooser);
//		if (retval == JFileChooser.APPROVE_OPTION) {
			// ... The user selected a file, get it, use it.
			_myFile = file;
			
			//_myFile = _fileChooser.getSelectedFile();
			_fstream = new FileWriter(_myFile);
			_bufferedWriter = new BufferedWriter(_fstream);
			dateFormat 	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			date = new Date();
			writeFirstLogAfterTurnedOn();
		}

//	}

	public void writeFirstLogAfterTurnedOn() {
//		dateFormat 	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		date = new Date();
		writeLog("The Log has turned on:");
		writeLog("****************************************");
	}
	
	public void writeLogBeforeTurnOff() {
//		dateFormat 	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		date = new Date();
		writeLog("The Log turned off:");
		writeLog("****************************************");
	}

	
	/**
	 * The log has the format: YYYYY/MM/DD: <logtext>
	 * e.g. 2011/12/01 20:55:33: test log
	 * 
	 * @param string This is the logging string
	 */
	public void writeLog(String string) {
//		dateFormat 	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		try {
		
			_bufferedWriter.write(dateFormat.format(date) + ": ");
			_bufferedWriter.write(string);
			_bufferedWriter.newLine();
			_bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println("Could not write into the Log File the Log: "
					+ string);
			e.printStackTrace();
		}
	}

	public void writeLogJustANewLine() {

		try {
			_bufferedWriter.newLine();
			_bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println("Could not write into the Log File the Log: ");
			e.printStackTrace();
		}
	}

}
