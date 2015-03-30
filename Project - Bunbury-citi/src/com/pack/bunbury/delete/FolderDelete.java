package com.pack.bunbury.delete;

import java.io.File;
import java.io.IOException;

public class FolderDelete {
	 public static void deleteFileOrDir(String dir)
		   {
		 
		 File file = new File( dir);
		 
		 if(file.exists()){
			 
		    	if(file.isDirectory()){
		 
		    		//directory is empty, then delete it
		    		if(file.list().length==0){
		 
		    		   file.delete();
		    		   System.out.println("Directory is deleted : "+ file.getAbsolutePath());
		 
		    		}else{
		 
		    		   //list all the directory contents
		        	   String files[] = file.list();
		 
		        	   for (String temp : files) {
		        	      //construct the file structure
		        	   
		 
		        	      //recursive delete
		        	     deleteFileOrDir(temp);
		        	   }
		 
		        	   //check the directory again, if empty then delete it
		        	   if(file.list().length==0){
		           	     file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		 
		    	}else{
		    		//if file, then delete it
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		 }
		    }


    public static void main(String[] args)
    {	
    
    	
    	deleteFileOrDir("C:\\test");
    	
    }
 
}