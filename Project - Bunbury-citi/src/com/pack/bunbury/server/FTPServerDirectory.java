package com.pack.bunbury.server;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.pack.bunbury.property.ReadPropFile;

public class FTPServerDirectory {
	public static void createRemoteFolderSource(String folderName)
	{
		ReadPropFile.readPropFile();
		String server = ReadPropFile.ftpUrl.trim();
		int port = 21;
		String user = ReadPropFile.user.trim();
		String pass = ReadPropFile.pass.trim();
		FTPClient ftpClient = new FTPClient();
		try {
		    ftpClient.connect(server, port);
		    int replyCode = ftpClient.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(replyCode)) {
		        System.out.println("Operation failed. Server reply code: " + replyCode);
		        return;
		    }
		    boolean success = ftpClient.login(user, pass);
		    if (!success) {
		        System.out.println("Could not login to the server");
		        return;
		    }
		    // Creates a directory
		    
		    String dirToCreate = "/"+folderName+"/"+"completed";
		    String dirToCreate1="/"+folderName+"/"+"error";
		    success = ftpClient.makeDirectory(dirToCreate);
		    		  ftpClient.makeDirectory(dirToCreate1);
		    if (success) {
		        System.out.println("Successfully created directory: " + dirToCreate);
		        System.out.println("Successfully created directory: " + dirToCreate1);
		    } else {
		        System.out.println("Failed to create directory. See server's reply.");
		    }
		    // logs out
		    ftpClient.logout();
		    ftpClient.disconnect();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		 String[] replies = ftpClient.getReplyStrings();
	        if (replies != null && replies.length > 0) {
	            for (String aReply : replies) {
	                System.out.println("SERVER: " + aReply);
	            }
	        }
	}
public static void main(String args[]){

	createRemoteFolderSource("source1");
}
}