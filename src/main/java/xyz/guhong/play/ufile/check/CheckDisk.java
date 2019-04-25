package xyz.guhong.play.ufile.check;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import xyz.guhong.play.ufile.thread.UploadThread;


/**
 * 检查u盘
 * @author 李双凯
 *
 */
public class CheckDisk {

	// 记录当前存在的磁盘
	private Map<String, Boolean> currDisk = new HashMap<String,Boolean>();
	// 盘符数组
	private  final String[] drives = new String[]{
		"C","W","E","R","T","Y","U","I","O","P","A","S","D","F"
		,"G","H","J","K","L","Z","X","Q","V","B","N","M"
	};
	
	
	public CheckDisk(String path){
		initDisk();
		showCurrDisk();
		checkU(path);
	}
	
	public void showCurrDisk(){
		System.out.print("当前存在磁盘：");
		for(String s : currDisk.keySet()){
			System.out.print(s+" ");
		}
	}
	
	
	/**
	 * 获得当前存在的磁盘
	 */
	public void initDisk(){
		for(String s : drives){
			File f = new File(s+"://");
			if(f.exists()){
				// 将当前存在的磁盘存入集合
				currDisk.put(s, true);
			}
		}
	}
	
	/**
	 * 检查u盘插入
	 */
	public void checkU(String path){
		// 死循环检测
		while(true){
			for(String s : drives){
				File f = new File(s+"://");
				if(f.exists() && currDisk.get(s) == null){
					currDisk.put(s, true);
					System.out.println("检测到u盘:"+s);
					Thread t = new Thread(new UploadThread(s+"://", path),"UploadThread");
					t.start();
				}
				// 如果不存在这个盘，则移除它
				if(!f.exists()){
					currDisk.remove(s);
				}
				
			}
		}
	}
	
}
