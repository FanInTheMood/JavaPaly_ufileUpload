package xyz.guhong.play.ufile.upload;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



import xyz.guhong.play.ufile.util.Tool;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
public class UploadFile {

    public static void upLoadFile(Session session, String sPath, String dPath) {
 
        Channel channel = null;
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
            try {
            	//上传
                sftp.cd(dPath);
 
            } catch (SftpException e) {
 
                sftp.mkdir(dPath);
                sftp.cd(dPath);
 
            }
            String newCatalog = new String(Tool.getRandom(11).toString());
            sftp.mkdir(newCatalog);
            sftp.cd(newCatalog);
            File file = new File(sPath);
            copyFile(sftp, file, sftp.pwd());
            System.out.println("Upload End");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }
 
    private static void copyFile(ChannelSftp sftp, File file, String pwd) {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            try {
                try {
                    String fileName = file.getName();
                    sftp.cd(pwd);
                    System.out.println("正在创建目录:" + sftp.pwd() + "/" + fileName);
                    sftp.mkdir(fileName);
                    System.out.println("目录创建成功:" + sftp.pwd() + "/" + fileName);
                } catch (Exception e) {
                	e.printStackTrace();
                }
                pwd = pwd + "/" + file.getName();
                try {
 
                    sftp.cd(file.getName());
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < list.length; i++) {
                copyFile(sftp, list[i], pwd);
            }
        } else {
 
            try {
                sftp.cd(pwd);
            } catch (SftpException e1) {
                e1.printStackTrace();
            }
            System.out.println("正在复制文件:" + file.getAbsolutePath());
            InputStream instream = null;
            OutputStream outstream = null;
            try {
                outstream = sftp.put(file.getName());
                instream = new FileInputStream(file);
                byte b[] = new byte[1024];
                int n;
                try {
                    while ((n = instream.read(b)) != -1) {
                        outstream.write(b, 0, n);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SftpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                    instream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
