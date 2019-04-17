package xyz.guhong.play.ufile.thread;

import xyz.guhong.play.ufile.upload.SSHConnection;


public class UploadThread implements Runnable {

	private final String sFile;
	private final String dFile;
	
	public UploadThread(String sFile,String dFile){
		this.sFile = sFile;
		this.dFile = dFile;
	}

	public void run() {
		SSHConnection.connectAndUpload(sFile, dFile);		
	}
	
	

}
