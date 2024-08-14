package rainbownlp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemUtil {
	/**
	 * Run synchronous shell command and wait till it finishes 
	 * @param command
	 */
	public static void runShellCommand(String command) {
		try {
			System.out.println(command);
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);

			BufferedReader in