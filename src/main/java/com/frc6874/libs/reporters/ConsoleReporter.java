package com.frc6874.libs.reporters;

import edu.wpi.first.wpilibj.DriverStation;
import com.frc6874.robot2019.Constants;
import com.frc6874.libs.ThreadRateControl;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A class to report messages to the console or DriverStation. Messages with a level of DEFCON1 will always be reported
 * whether reporting is enabled or not and will be reported both to the console and the DriverStation.
 */
public class ConsoleReporter extends Thread {

	private static final int MIN_CONSOLE_SEND_RATE_MS = 250;
	private static MessageLevel reportingLevel = MessageLevel.ERROR;
	private static LinkedHashSet<Message> sendMessageSet = new LinkedHashSet<>();
	private static ReentrantLock _reporterMutex = new ReentrantLock();
	private static ConsoleReporter instance = null;
	private boolean runThread;
	private ThreadRateControl threadRateControl = new ThreadRateControl();

	private PrintWriter logWriter;

	private Thread logThread;

	private ConsoleReporter() throws Exception {
		super();
		super.setPriority(Constants.ConsoleReporterThreadPriority);
		runThread = false;
	}

	public static ConsoleReporter getInstance() {
		if(instance == null) {
			try {
				instance = new ConsoleReporter();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return instance;
	}

	public static void setReportingLevel(MessageLevel messageLevel) {
		ConsoleReporter.reportingLevel = messageLevel;
	}

	public static void report(Throwable t) { report(t, MessageLevel.ERROR); }

	public static void report(Throwable t, MessageLevel messageLevel) {
		StringWriter s = new StringWriter();
		t.printStackTrace(new PrintWriter(s));
		report(s.toString(), messageLevel);
	}

	public static void report(Object message) {
		report(String.valueOf(message));
	}

	public static void report(Object message, MessageLevel msgLvl) {
		report(String.valueOf(message), msgLvl);
	}

	public static void report(String message) {
		report(message, MessageLevel.WARNING);
	}

	public static void report(String message, MessageLevel msgLvl) {
		if (msgLvl == MessageLevel.DEFAULT || (Constants.ENABLE_REPORTING && (msgLvl.ordinal() <= reportingLevel.ordinal()))) {
			_reporterMutex.lock();
			try {
				sendMessageSet.add(new Message(message, msgLvl));
			} finally {
				_reporterMutex.unlock();
			}
		}
	}

	@Override
	public void start() {
		runThread = true;
		threadRateControl.start();
//		createLog();
		super.start();
	}

	public void terminate() {
		runThread = false;
	}

	@Override
	public void run() {
		while (runThread) {
			try {
				_reporterMutex.lock();
				try {
					for (Iterator<Message> i = sendMessageSet.iterator(); i.hasNext();) {
						Message ckm = i.next();
						if (ckm.messageLevel == MessageLevel.DEFAULT || (Constants.ENABLE_REPORTING && (ckm.messageLevel.ordinal() <= reportingLevel.ordinal()))) {
							String s = ckm.toString();
							if (Constants.REPORT_TO_DRIVERSTATION_INSTEAD_OF_CONSOLE) {
								switch (ckm.messageLevel) {
									case DEFAULT:
										System.out.println(s);
									case ERROR:
										DriverStation.reportError(s, false);
										break;
									case WARNING:
									case INFO:
										DriverStation.reportWarning(s, false);
										break;
									default:
										break;
								}
							} else {
								System.out.println(s);
								if (ckm.messageLevel == MessageLevel.DEFAULT)
									DriverStation.reportError(s, false);
							}
//							writeData(s);
							i.remove();
						}
					}

//					flushData();
				} finally {
					_reporterMutex.unlock();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			threadRateControl.doRateControl(MIN_CONSOLE_SEND_RATE_MS);
		}
	}

	private void createLog() {
		try {
			logWriter = new PrintWriter(new FileWriter("/home/lvuser/ConsoleLog.txt", true));
			logWriter.print(new Date().toString());
			logWriter.println();
		} catch (Exception e) {

		}
	}

	private void writeData(String data) {
		if (logWriter != null) {
			try {
				logWriter.println(data);
			} catch (Exception ex) {

			}
		}
	}

	private void flushData() {
		if (logWriter != null) {
			try {
				logWriter.flush();
			} catch (Exception ex) {

			}
		}
	}

	private void closeLog() {
		if (logWriter != null) {
			try {
				logWriter.flush();
				logWriter.close();
				logWriter = null;
			} catch (Exception ex) {

			}
		}
	}

}
