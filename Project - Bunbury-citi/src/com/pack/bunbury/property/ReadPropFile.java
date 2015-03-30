package com.pack.bunbury.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Properties;


public class ReadPropFile {

	static FileOutputStream outputStream;
	static FileInputStream fis;
	static Properties p;
	static InputStream inputStream;
	public static String ftpUrl;
	public static String savePath;
	public static String user;
	public static String pass;
	public static String cmdPath;
	public static String passphrase;
	public static String reciver;
	public static String sender;
	public static String senderpass;
	public static String logloc;
	public static String target1;
	public static String target2;
	public static String target3;
	public static String source1;
	public static String source2;
	public static String source3;
	
	public static String[] readPropFile()  {

		try {
		/*	CodeSource codeSource = ReadPropFile.class.getProtectionDomain().getCodeSource();
			File jarFile=null;
			try{
			jarFile= new File(codeSource.getLocation().toURI().getPath());
		}catch(Exception e){}
		
			File jarDir = jarFile.getParentFile();
			File propFile=null;

			if (jarDir != null && jarDir.isDirectory()) {
			   propFile = new File(jarDir, "./connect.properties");
			}
			*/
			fis = new FileInputStream(".//connect.properties");
			p = new Properties();
			p.load(fis);
			ftpUrl = p.getProperty("url");
			savePath = p.getProperty("downloadpath");
			user = p.getProperty("username");
			pass = p.getProperty("password");
			cmdPath = p.getProperty("cmdpath");
			passphrase = p.getProperty("passphrase");
			reciver=p.getProperty("reciver");
			sender=p.getProperty("sender");
			senderpass=p.getProperty("sender_password");
			logloc=p.getProperty("logfileloc");
			target1=p.getProperty("target1");
			target2=p.getProperty("target2");
			target3=p.getProperty("target3");
			source1=p.getProperty("source1");
			source2=p.getProperty("source2");
			source3=p.getProperty("source3");
		} catch (FileNotFoundException fnfe) {
		
		} catch (IOException ioe) {
		

		} finally {
			try {
				fis.close();

			} catch (Exception e) {
				System.out.println("Error is :" + e);
			}

		}

		return new String[] { ftpUrl, savePath, user, pass, cmdPath, passphrase,target1,target2,target3 };

	}
	
	
	
}
