package ch.hszt.groupf.fallstudie.client.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFactory {

//	private FileOutputStream _fos;
	private BufferedWriter _bufferedWriter;
	private FileWriter _fstream;
	private File _myFile;

	public LogFactory(File file) throws IOException {
//		JFileChooser _fileChooser = new JFileChooser();

//		int retval = _fileChooser.showOpenDialog(_fileChooser);
//		if (retval == JFileChooser.APPROVE_OPTION) {
			// ... The user selected a file, get it, use it.
			_myFile = file;
			
			//_myFile = _fileChooser.getSelectedFile();
			_fstream = new FileWriter(_myFile);
			_bufferedWriter = new BufferedWriter(_fstream);
			writeFirstLogAfterTurnedOn();
		}

//	}

	public void writeFirstLogAfterTurnedOn() {

		writeLog("The Log has turned on:");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		writeLog(dateFormat.format(date));

		writeLog("****************************************");
		writeLogJustANewLine();
	}

	public void writeLog(String string) {

		try {
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
