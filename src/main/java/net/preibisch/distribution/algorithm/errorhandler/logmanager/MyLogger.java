package net.preibisch.distribution.algorithm.errorhandler.logmanager;

import org.scijava.Context;
import org.scijava.log.LogService;
import org.scijava.log.Logger;

import net.imagej.ops.OpService;
import net.preibisch.distribution.gui.items.LogFrame;

public class MyLogger {
	
	private static Boolean sendLog = false;
	
	public static Boolean initialized = false;
	
	private static Logger log;
	
	private static OpService opService;
	private static LogService logService;
	
	public static void SubLogger(LogService logService) {
		log = logService.subLogger("Distribution");
		log.addLogListener(LogFrame.getLogPanel());
//		logPanel = new LoggingPanel(Tr2dContext.ops.context());
	}
	
	public static Logger log() {
		if(initialized) return log;
		else
		{
			initLogger();
			return log();
		}
	}

	public static void initLogger() {
		if(initialized) return ;
//		org.apache.log4j.BasicConfigurator.configure();
		final Context context = new Context( OpService.class, LogService.class );
		opService = context.getService( OpService.class );
		logService = context.getService( LogService.class );
		LogFrame logFrame = new LogFrame(opService.getContext());
		MyLogger.SubLogger(logService);
		logFrame.setVisible(true);
		initialized = true;
	}
}
