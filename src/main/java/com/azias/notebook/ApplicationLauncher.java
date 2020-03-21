package com.azias.notebook;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLauncher {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationLauncher.class);
	
	private static Notebook mainNotebook;
	private static Options launchOptions;
	private static CommandLine launchArgs;
	
	public static void main(String[] args) {
		if(!parseLaunchArgs(args)) {
			logger.error("The launch arguments parsing process has failed, now quitting...");
			System.exit(1);
		}
		
		if(launchArgs.hasOption("h") && !launchArgs.hasOption("f")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar xxx.jar <args>", launchOptions);
			System.exit(0);
		}
		
		logger.info("Azias's Modular Notebook v0.0.1");
		logger.debug("Java: {} - {}", System.getProperty("java.vendor"), System.getProperty("java.version"));
		logger.debug("Operating System: {} - {}", System.getProperty("os.name"), System.getProperty("os.arch"));
		logger.info("- - - - - - - - - - - - - - - -");
		
		try {
			mainNotebook = new Notebook(launchArgs);
			logger.info("- - - - - - - - - - - - - - - -");
			mainNotebook.initialize(true);
			logger.info("- - - - - - - - - - - - - - - -");
			while(!mainNotebook.update());
			logger.info("- - - - - - - - - - - - - - - -");
			mainNotebook.finish();
		} catch(NotebookException e) {
			//e.printStackTrace();
			
			switch(e.getErrorCode()) {
				case ERROR_GENERIC:
					logger.error("A generic error occurred, we cannot give you much more information, sorry.");
					break;
				case ERROR_NO_ADDONS_IN_PARAMETERS:
					logger.error("No addons were given in the launch arguments, please use --help for more info.");
					break;
				case ERROR_SUPER:
					logger.error("The error cannot be handled here, please refer to the error message for more info !");
					break;
				default:
					logger.error("An unknown error occurred, you're on your own now.");
					break;
			}
			logger.error("$> {}", e.getMessage());
			
			System.exit(2);
		}
	}
	
	private static boolean parseLaunchArgs(String[] args) {
		launchOptions = new Options();
		launchOptions.addOption("a", "addons", true, "usage: -a addon-1;addon-2;...");
		launchOptions.addOption("h", "help", false, "Show this help.");
		launchOptions.addOption("f", "force", false, "Bypass some checks at launch.");
		
		try {
			launchArgs = new DefaultParser().parse(launchOptions, args);
		} catch (ParseException e) {
			// TODO: format this properly.
			logger.error("Failed to parse command line properties " + e);
			return false;
		}
		
		return true;
	}
}
