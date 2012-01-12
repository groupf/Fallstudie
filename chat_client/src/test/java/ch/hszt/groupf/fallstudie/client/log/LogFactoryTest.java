package ch.hszt.groupf.fallstudie.client.log;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogFactoryTest {

	private DateFormat dateFormatTest = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private LogFactory myLog;
	private StringWriter logOutput;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		logOutput = new StringWriter();
		myLog = new LogFactory(logOutput);

	}

	@Test
	public void testWriteFirstLogAfterTurnedOn() {
		myLog.writeFirstLogAfterTurnedOn();
		String s = logOutput.toString();

		String excpected = "The Log turned on:";
		String excpected2 = "*************************";

		assertTrue(s.contains(excpected));
		assertTrue(s.contains(excpected2));

	}

	@Test
	public void testWriteLogBeforeTurnOff() {
		myLog.writeLogBeforeTurnOff();
		String s = logOutput.toString();

		String excpected = "The Log turned off:";
		String excpected2 = "*************************";

		assertTrue(s.contains(excpected));
		assertTrue(s.contains(excpected2));
		fail("Demo-Test");
	}

	@Test
	public void testWriteLog() {
		String excpected = "Test my class";
		String excpected2 = "This is a second test";
		String excpected3 = "This is a second test";

		myLog.writeLog("Test my class");
		myLog.writeLog("This is a second test");
		myLog.writeLog("Test with special signs @!$/?");
		myLog.writeLog("Test with special signs @!$/?");

		assertTrue(logOutput.toString().contains(excpected));
		assertTrue(logOutput.toString().contains(excpected2));
		assertTrue(logOutput.toString().contains(excpected3));
		assertFalse(logOutput.toString().contentEquals("$"));
	}

	@Test
	public void testWriteLogWithDate() {
		Date dateTest = new Date();
		myLog.writeLog("Test my class");

		String s = dateFormatTest.format(dateTest);
		assertTrue(logOutput.toString().contains(s));
	}

	@Test
	public void testWriteLogJustANewLine() {
		myLog.writeLogJustANewLine();
		String excpected = "\r\n";

		assertTrue(logOutput.toString().contains(excpected));

	}

}
