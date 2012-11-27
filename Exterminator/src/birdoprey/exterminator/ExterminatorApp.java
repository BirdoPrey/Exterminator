package birdoprey.exterminator;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import birdoprey.exterminator.ui.charva.ExterminatorUI;

//Main application class - a most auspicious start
public class ExterminatorApp {

	//whether or not we're in debug mode
	public static boolean DEBUG = false;
	
	//home path, root of data and scripts and all that
	public static File PATH = null;
	
	/**
	 * When dealing with charva (default for now), we need to
	 * run and debug in a shell.
	 *
	 * @param args -debug:{address} 	toggle debug mode with the given address (and port)
	 * @param args -ide		run the bash script to launch safely from IDE
	 * @param args -path=path/to/home/exterminator/folder (necessary for now, but temporary?)
	 */
	public static void main(String[] args) {
		
		boolean ide = false;
		String address = null;
		
		for (String arg : args) {
			if (arg.equals("-ide")) {
				ide = true;
			}
			else if (arg.matches("-debug:\\S+")) {
				DEBUG = true;
				address = arg.substring(arg.indexOf(':') + 1);
			}
			else if (arg.startsWith("-path=")) {
				PATH = new File(arg.substring(arg.indexOf('=') + 1));
				if (!PATH.isDirectory()) {
					throw new IllegalArgumentException("given path " + PATH.getPath() + " is not an existing directory");
				}
			}
		}
		
		//if eclipse is launching this, run the launch script in a shell
		if (ide) {
			launchShell(DEBUG, address);
		}
		else{
			//create app instance
			ExterminatorApp.app = new ExterminatorApp();
			
			//launch ui
			final ExterminatorUI ui = new ExterminatorUI(app);
			try {
				EventQueue.invokeAndWait(new Runnable(){
					@Override
					public void run() {
						ui.setVisible(true);
					}
				});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//TODO - consider whether this is/should be really necessary
	public static ExterminatorApp getApplication() {
		
		return ExterminatorApp.app;
		
	}

	/**
	 * 	launch in bash shell for IDE mode
	 * 
	 * @param debug 	Debug mode flag
	 * @param address	address + port to attach debugger
	 */
	private static void launchShell(boolean debug, String address) {
		
		if (PATH == null) {
			//TODO - LATER, MIGHT NEED TO MOVE THIS CHECK UP THE CHAIN
			throw new IllegalStateException("eclipse mode needs path set");
		}
		
		Process process;
		try {
			//FIXME - this is os x + xterm specific 
			if (debug) {
				//FIXME - DEFAULT PORT BLAH BLAH
				if (address == null) {
					throw new IllegalArgumentException("Pr");
				}
				process = Runtime.getRuntime().exec(new String[]{
						"/bin/bash", "-c", "cd " + PATH.getPath() + " && /usr/X11/bin/xterm -e sh launch_debug.sh " + address	
					});
			}
			else {
				process = Runtime.getRuntime().exec(new String[]{
					"/bin/bash", "-c", "cd " + PATH.getPath() + " && /usr/X11/bin/xterm -e sh launch.sh"	
				});
			}
			
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();
			
			byte[] inputBytes = new byte[500];
			byte[] errorBytes = new byte[500];
			
			process.waitFor();
			
			inputStream.read(inputBytes);
			errorStream.read(errorBytes);
			
			String input = new String(inputBytes);
			String error = new String(errorBytes);
			
			System.out.println("LOG:\n" + input);
			System.out.println("ERROR:\n" + error);
			
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
			
	}
	
	
	private static ExterminatorApp app;
	
}
