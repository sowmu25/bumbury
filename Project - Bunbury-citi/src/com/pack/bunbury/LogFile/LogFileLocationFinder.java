package com.pack.bunbury.LogFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
 
public class LogFileLocationFinder {
	// configuration for logfile folder name and filename
		
	
		public static void getValueForLogFile() {
			// Assign runtime names for log folder and file.
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH-mm-ss");
			String logfolderName = sdf1.format(Calendar.getInstance().getTime())
					+ ".log";
			String logSubFolderName = sdf2.format(Calendar.getInstance()
					.getTime());
			SimpleDateFormat sdf3 = new SimpleDateFormat("dd MMM yyyy HH-mm-ss");
			String logfileName = sdf3.format(Calendar.getInstance()
					.getTime()) + "-LOG.log";

			String fullFilePath="LOG"+"/"+logfolderName+"/"+logSubFolderName+"/"+logfileName;
			System.setProperty("logfilename", fullFilePath);
			
		}
		
		public static void main(String args[])
		{
			
			
		}
}
