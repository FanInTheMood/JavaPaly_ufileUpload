package xyz.guhong.play.ufile.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties; 

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;



/**
 * 远程连接类
 * 
 * @author 李双凯
 *
 */
public class SSHConnection {

	public static void connectAndUpload(String sFile,String dFile){
		Properties prop = new Properties();
		InputStream is = SSHConnection.class.getClassLoader().
				getResourceAsStream("ssh.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			System.out.println("配置文件出错");
			e.printStackTrace();
			return;
		}
		
		String ip = prop.getProperty("ip");
		String user = prop.getProperty("user"); 
		String pwd = prop.getProperty("pwd");
		String portStr = prop.getProperty("port");
		
		if("".equals(ip) || "".equals(user) || "".equals(pwd) || "".equals(portStr)){
			System.out.println("检测到配置文件为空");
			return ;
		}
		int port = Integer.parseInt(portStr);
		sshSftp(ip,user,pwd,port, sFile, dFile);
	}
	
	/**
	 * sshSftp上传
	 * @param ip
	 * @param user
	 * @param pwd
	 * @param port
	 */
	public static void sshSftp(String ip, String user, String pwd, int port,
			String sFile,String dFile) {
        Session session = null;
        JSch jsch = new JSch();
        try {
            if (port <= 0) {
                session = jsch.getSession(user, ip);
            } else {
                session = jsch.getSession(user, ip, port);
            }
            if (session == null) {
                throw new Exception("session is null");
            }
            session.setPassword(pwd);// 设置密码
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(300000);
            System.out.println("Connection Success");
            UploadFile.upLoadFile(session, sFile, dFile);
        } catch (Exception e) {
        	System.out.println("连接错误");
            e.printStackTrace();
           
        }
 
        
    }

}
