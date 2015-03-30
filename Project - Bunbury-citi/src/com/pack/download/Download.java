package com.pack.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.pack.bunbury.decryption.DecryptFile;
import com.pack.bunbury.delete.FolderDelete;
import com.pack.bunbury.property.ReadPropFile;
import com.pack.bunbury.server.FTPServerDirectory;
import com.pack.bunbury.server.MoveRemoteFile;
import com.pack.bunbury.uploadfile.UploadFile;
import com.pack.mail.EmailNotification;
//import com.pack.bunbury.property.ReadPropFile;
public class Download {
	static Logger logger=Logger.getLogger(Download.class);
	public static String localFilePath =null;
	public static String body ="";
	public static boolean status = true;
	public static int  countFiles = 0 ;
	public static String serverTargetDir=null;
	public static void downloadDirectoryAndDecrypt(FTPClient ftpClient, String parentDir,
			String currentDir, String saveDir,String outfile1) throws IOException {
		serverTargetDir=outfile1;
		//logger.info("Download started :"); 
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				
				body+="\n\r";
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String ftpRelativeFilePath = "/"+parentDir + "/" + currentDir + "/"
						+ currentFileName;
				if (currentDir.equals("")) {
					ftpRelativeFilePath = "/"+parentDir + "/" + currentFileName;
				}

				 localFilePath = saveDir + File.separator + parentDir + File.separator
						+ currentDir + File.separator + currentFileName;
				if (currentDir.equals("")) {
					localFilePath = saveDir + File.separator + parentDir + File.separator
							  + currentFileName;
				}

				if (aFile.isDirectory()) {
				
					continue;
				
				} else {
					// download the file
					boolean success = downloadSingleFile(ftpClient, ftpRelativeFilePath,
							localFilePath);
					countFiles ++ ;
					body+=ftpRelativeFilePath+" : ";
					if (success) {
						body+=" File Downloaded  ";
						logger.info("File Downloaded & Stored : "+ftpRelativeFilePath);
						//System.out.println(" File downloaded");
						// call decrypt 
						File f=new File(ftpRelativeFilePath);
						String fname=f.getName();
						logger.info("File downloaded : "+fname);
						StringBuffer sb=new StringBuffer();
						if(DecryptFile.decrypt(localFilePath,outfile1))
						{
							body+="Decrypted ";
							sb.append("//"+parentDir+"//completed//"+fname);
							MoveRemoteFile.moveRemoteFileToCompletedSource(ftpRelativeFilePath,sb.toString(),parentDir);
							logger.info("file moved to completed folder at server");
							
							
						}
						else
						{
							body+=" Decrypt Failed : ";
							status = false;
							sb.append("//"+parentDir+"//error//"+fname);
							MoveRemoteFile.moveRemoteFileToCompletedSource(ftpRelativeFilePath,sb.toString(),parentDir);
							logger.info("moving file to error folder at server");
						}
						
						// if fails move to error folder  
						
						
						//call upload 
						
						//delete the local file
						//deleting local file and folder
					    FolderDelete.deleteFileOrDir(ReadPropFile.savePath);
					    logger.info("Local folder deleted");
				      
				    	//System.out.println("Folder deleted");
						
						//move ftp file to completed
						
						
						
					} else {
						body+=" Download Failed : ";
						logger.error("Downloding file failed : "+ftpRelativeFilePath);
						//move file to error folder 
						//System.out.println("COULD NOT download the file: "+ filePath);
						//System.out.println("COULD NOT download the file");
					}
				}
			}
			
			
			
			
		}
		
	}
	public static File parentDir;
	public static boolean downloadSingleFile(FTPClient ftpClient,
			String remoteFilePath, String savePath) throws IOException {
		File downloadFile = new File(savePath);

		System.out.println("savePath= "+savePath);
		
		parentDir = downloadFile.getParentFile();
		System.out.println("parentDir= "+parentDir.getAbsolutePath());
		if (!parentDir.exists()) {
			
			System.out.println("creating local directory");
			parentDir.mkdirs();
		}

		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}


	/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadPropFile.readPropFile();
		FTPClient ftpClient = new FTPClient();
	try {

			ftpClient.enterLocalPassiveMode();
			ftpClient.connect(ReadPropFile.ftpUrl.trim(), 21);
			ftpClient.login(ReadPropFile.user, ReadPropFile.pass);
			
			System.out.println("Connected");
			System.out.println("ReadPropFile.target2= "+ReadPropFile.target2);
			//downloadDirectoryAndDecrypt(ftpClient,ReadPropFile.source1,"", ReadPropFile.savePath,ReadPropFile.target1) ;
		//	downloadDirectoryAndDecrypt(ftpClient,ReadPropFile.source2,"", ReadPropFile.savePath,ReadPropFile.target2) ;
		downloadDirectoryAndDecrypt(ftpClient,ReadPropFile.source3,"", ReadPropFile.savePath,ReadPropFile.target3) ;
			System.out.println("Disconnected");
		} 
catch(Exception ex) {
		ex.printStackTrace();
	}
			
	} */

}
