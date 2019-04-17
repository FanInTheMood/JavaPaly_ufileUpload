package xyz.guhong.play.ufile;


import xyz.guhong.play.ufile.upload.SSHConnection;

public class TestUFile {

	
	public static void main(String[] args) {
		SSHConnection.connectAndUpload("C:/javac",
				"ufile");
	}

}
