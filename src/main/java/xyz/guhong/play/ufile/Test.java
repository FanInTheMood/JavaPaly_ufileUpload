package xyz.guhong.play.ufile;

import xyz.guhong.play.ufile.check.CheckDisk;


public class Test {

	public static void main(String[] args) {
		CheckDisk cd = new CheckDisk();
		cd.initDisk();
		cd.showCurrDisk();
		cd.checkU();
	}

}
