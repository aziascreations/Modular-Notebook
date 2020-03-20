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
			formatter.printHelp("Arguments List:", launchOptions);
			System.exit(0);
		}
		
		logger.info("Java: {} - {}", System.getProperty("java.vendor"), System.getProperty("java.version"));
		logger.info("Operating System: {} - {}", System.getProperty("os.name"), System.getProperty("os.arch"));
		
		// This part has been separated here to be able to potentially run 1+ instance(s) of Notebook at the same time
		//  more easily.
		logger.info("- - - - - - - - - - - - - - - -");
		mainNotebook = new Notebook(launchArgs);
		logger.info("- - - - - - - - - - - - - - - -");
		mainNotebook.initialize(true);
		logger.info("- - - - - - - - - - - - - - - -");
		while(!mainNotebook.update());
		//logger.info("- - - - - - - - - - - - - - - -");
		mainNotebook.finish();
	}
	
	private static boolean parseLaunchArgs(String[] args) {
		launchOptions = new Options();
		launchOptions.addOption("a", "addons", true, "usage: -a mod1;mod2;...");
		launchOptions.addOption("h", "help", false, "Show help.");
		launchOptions.addOption("f", "force", false, "Bypass some checks at launch.");
		
		try {
			launchArgs = new DefaultParser().parse(launchOptions, args);
		} catch (ParseException e) {
			logger.error("Failed to parse command line properties " + e);
			return false;
		}
		
		return true;
	}
}
