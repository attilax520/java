package com.attilax.net.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.attilax.io.FileService;
import com.google.common.collect.Maps;

/**
 * Ftp工具类 需要导入commons-net-3.4.jar这个包
 */
public class FtpUtil {
    
    private static Logger logger=Logger.getLogger(FtpUtil.class);
    
    private static FTPClient ftp;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	String ftpf = "ftp://192.168.1.18/home/cnhis/managerweb/webapps/webcon/cpu.htm";
    	 ftpf = "ftp://192.168.1.77:5221/0logs555/cpu2.htm";
		FTPFile ftpfile=new FTPFile();
		  //  连接ftp server
		byte[] data_bytearr = ftpurl2bytearr(ftpf,7000);
		
		System.out.println("get data_bytearr ");
		 String pathname = "c:\\0logs555\\cpu9.htm";
		FileService.writeByteArrayToFile(pathname,data_bytearr);
		
		
		//	FileOutputStream fileOutputStream = new FileOutputStream(new File(pathname));
    	
	 
    	System.out.println("000");
	}
	private static byte[] ftpurl2bytearr(String ftpurl, int timeout) throws IOException {
		Map m=parseFtpurl(ftpurl);
		
		FtpUtil fu=new FtpUtil();
		fu.host=(String) m.get("host");
		fu.port=  (int) m.get("port");
		System.out.println(JSON.toJSONString(fu));
        FTPClient ftpClient =fu. connect(timeout);
        System.out.println("connect finished ");
		ByteArrayOutputStream ByteArrayOutputStream1 = new ByteArrayOutputStream();
		String reltPath = "/home/cnhis/managerweb/webapps/webcon/cpu.htm";
		reltPath=(String) m.get("path");
		ftpClient.retrieveFile(reltPath, ByteArrayOutputStream1);
		byte[] data_bytearr=ByteArrayOutputStream1.toByteArray();
		return data_bytearr;
	}
    @SuppressWarnings("all")
	private static Map parseFtpurl(String ftpurl) {
		int doubleslash_protocalstart_Index=ftpurl.indexOf("//");
		int host_port_endIndex=ftpurl.indexOf("/", doubleslash_protocalstart_Index+2);
		int maohaorIdx_hostport_splitor=ftpurl.indexOf(":",doubleslash_protocalstart_Index);
		Map m=Maps.newConcurrentMap();
		if(maohaorIdx_hostport_splitor>0 )
			m.put("host", ftpurl.substring(doubleslash_protocalstart_Index+2, maohaorIdx_hostport_splitor));
		else
			m.put("host", ftpurl.substring(doubleslash_protocalstart_Index+2, host_port_endIndex));
		m.put("path", ftpurl.substring(host_port_endIndex, ftpurl.length()));
		if(maohaorIdx_hostport_splitor>0 )
			m.put("port", Integer.parseInt( ftpurl.substring(maohaorIdx_hostport_splitor+1, host_port_endIndex)));
		
		return m;
	}
	/** 
     * 
     * 下载FTP文件 
     * 当你需要下载FTP文件的时候，调用此方法 
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
     * 
     * @param ftpFile 
     * @param relativeLocalPath 
     * @param relativeRemotePath 
     */ 
    private  static void downloadFileV2(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
  
    }
    
    
    /**
     * 连接FTP Server
     * @throws IOException
     */
    public static final String ANONYMOUS_USER="anonymous";

	private String ftpPath;

	private String host;

	private int port=21;

	private String user="anonymous";

	private String password;
    private FTPClient connect(int connTimeoutMills){        
    	if(this.host==null || this.host.trim().length()==0)
    		throw new RuntimeException("host is empty");
        FTPClient client = new FTPClient();
        try{
            //连接FTP Server
        	// int connTimeoutMills = 5000;
			client.setConnectTimeout(connTimeoutMills);
            client.connect(this.host, this.port);
           
            //登陆
            if(this.user==null||"".equals(this.user)){
                //使用匿名登陆
                client.login(ANONYMOUS_USER, ANONYMOUS_USER);
            }else{
                client.login(this.user, this.password);
            }
            //设置文件格式
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            //获取FTP Server 应答
            int reply = client.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                client.disconnect();
                throw new RuntimeException("  FTPReply.isPositiveCompletion(reply) ： replycode is "+reply );
            }
            //切换工作目录  if defin workdir,if not difin nothing todo
            changeWorkingDirectory(client);
            System.out.println("===连接到FTP："+host+":"+port);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return client;
    }
    
    
    public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
     * 上传文件
     * @param targetName 上传到ftp文件名
     * @param localFile 本地文件路径
     * @return
     */
    public boolean upload(String targetName,String localFile){
        //连接ftp server
        FTPClient ftpClient = connect();

        if(ftpClient==null){
            System.out.println("连接FTP服务器["+host+":"+port+"]失败！");
            return false;
        }
        File file = new File(localFile);
        //设置上传后文件名
        if(targetName==null||"".equals(targetName))
            targetName = file.getName();
        FileInputStream fis = null;
        try{
            long now = System.currentTimeMillis();
            //开始上传文件
            fis = new FileInputStream(file);
            System.out.println(">>>开始上传文件："+file.getName());
            boolean ok = ftpClient.storeFile(targetName, fis);
            if(ok){//上传成功
                long times = System.currentTimeMillis() - now;
                System.out.println(String.format(">>>上传成功：大小：%s,上传时间：%d秒", formatSize(file.length()),times/1000));
            }else//上传失败
                System.out.println(String.format(">>>上传失败：大小：%s", formatSize(file.length())));
        }catch(IOException e){
            System.err.println(String.format(">>>上传失败：大小：%s", formatSize(file.length())));
            e.printStackTrace();
            return false;
        }finally{
            try{
                if(fis!=null)
                    fis.close();
                close(ftpClient);
            }catch(Exception e){}
        }
        return true;
    }
    private FTPClient connect() {
		// TODO Auto-generated method stub
		return null;
	}
	private Object formatSize(long length) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * 切换工作目录，远程目录不存在时，创建目录
     * @param client
     * @throws IOException
     */
    private void changeWorkingDirectory(FTPClient client) throws IOException{
        if(this.ftpPath!=null&&!"".equals(this.ftpPath)){
            boolean ok = client.changeWorkingDirectory((String) this.ftpPath);
            if(!ok){
                //ftpPath 不存在，手动创建目录
                StringTokenizer token = new StringTokenizer(this.ftpPath,"\\//");
                while(token.hasMoreTokens()){
                    String path = token.nextToken();
                    client.makeDirectory(path);
                    client.changeWorkingDirectory(path);
                }
            }
        }
    }
    /**
     * 断开FTP连接
     * @param ftpClient
     * @throws IOException
     */
    public void close(FTPClient ftpClient) throws IOException{
        if(ftpClient!=null && ftpClient.isConnected()){
            ftpClient.logout();
            ftpClient.disconnect();
        }
        System.out.println("!!!断开FTP连接："+host+":"+port);
    }
    
    
    /** 
     * 
     * 下载FTP文件 
     * 当你需要下载FTP文件的时候，调用此方法 
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
     * 
     * @param ftpFile 
     * @param relativeLocalPath 
     * @param relativeRemotePath 
     */ 
    private  static void downloadFileV3(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
        if (ftpFile.isFile()) {
           
                OutputStream outputStream = null; 
                try { 
                	File entryDir = new File(relativeLocalPath);
    				//如果文件夹路径不存在，则创建文件夹
    				if (!entryDir.exists() || !entryDir.isDirectory())
    				{
    					entryDir.mkdirs();
    				}
                    File locaFile= new File(relativeLocalPath+ ftpFile.getName()); 
                    //判断文件是否存在，存在则返回 
                    if(locaFile.exists()){ 
                        return; 
                    }else{ 
                        outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName()); 
                        ftp.retrieveFile(ftpFile.getName(), outputStream); 
                        outputStream.flush(); 
                        outputStream.close(); 
                    } 
                } catch (Exception e) { 
                    logger.error(e);
                } finally { 
                    try { 
                        if (outputStream != null){ 
                            outputStream.close(); 
                        }
                    } catch (IOException e) { 
                       logger.error("输出文件流异常"); 
                    } 
                } 
            
        } else { 
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName(); 
            String newRemote = new String(relativeRemotePath+ ftpFile.getName().toString()); 
            File fl = new File(newlocalRelatePath); 
            if (!fl.exists()) { 
                fl.mkdirs(); 
            } 
            try { 
                newlocalRelatePath = newlocalRelatePath + '/'; 
                newRemote = newRemote + "/"; 
                String currentWorkDir = ftpFile.getName().toString(); 
                boolean changedir = ftp.changeWorkingDirectory(currentWorkDir); 
                if (changedir) { 
                    FTPFile[] files = null; 
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                        downloadFile(files[i], newlocalRelatePath, newRemote); 
                    } 
                } 
                if (changedir){
                    ftp.changeToParentDirectory(); 
                } 
            } catch (Exception e) { 
                logger.error(e);
            } 
        } 
    } 
    
    /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(Ftp f) throws Exception{
        ftp=new FTPClient();
        boolean flag=false;
        int reply;
        try{
	        if (f.getPort()==null) {
	            ftp.connect(f.getIpAddr(),21);
	        }else{
	            ftp.connect(f.getIpAddr(),f.getPort());
	        }
	        //ftp登陆
	        ftp.login(f.getUserName(), f.getPwd());
	        //设置文件传输类型
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	        //检查延时
	        reply = ftp.getReplyCode(); 
	        //如果延时不在200到300之间，就关闭连接
	        if (!FTPReply.isPositiveCompletion(reply)) {      
	              ftp.disconnect();      
	              return flag;      
	        }      
	        ftp.changeWorkingDirectory(f.getPath());      
	        flag = true;      
	        return flag;
        }catch(Exception e){
        	throw new Exception(e.getMessage());
        }
    }
    
    /**
     * 关闭ftp连接
     */
    public static void closeFtp(){
        if (ftp!=null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * ftp上传文件
     * @param f
     * @throws Exception
     */
    public static void upload(File f) throws Exception{
        if (f.isDirectory()) {
            ftp.makeDirectory(f.getName());
            ftp.changeWorkingDirectory(f.getName());
            //返回目录名和文件名组成的字符串数组
            String[] files=f.list();
            for(String fstr : files){
                File file1=new File(f.getPath()+"/"+fstr);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                }else{
                    File file2=new File(f.getPath()+"/"+fstr);
                    FileInputStream input=new FileInputStream(file2);
                    ftp.storeFile(file2.getName(),input);
                    input.close();
                }
            }
        }else{
            File file2=new File(f.getPath());
            FileInputStream input=new FileInputStream(file2);
            ftp.storeFile(file2.getName(),input);
            input.close();
        }
    }
    
    /**
     * 下载链接配置
     * @param f
     * @param localBaseDir 本地目录
     * @param remoteBaseDir 远程目录
     * @throws Exception
     */
    public static boolean startDown(Ftp f,String filename,String localBaseDir,String remoteBaseDir ) throws Exception{
        if (FtpUtil.connectFtp(f)) {
            
            try { 
                FTPFile[] files = null; 
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir); 
                if (changedir) { 
                    ftp.setControlEncoding("GBK"); 
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                        try{ 
                        	if(filename.equals(files[i].getName())){
                        		downloadFile(files[i], localBaseDir, remoteBaseDir); 
                        		return true;
                        	}
                        }catch(Exception e){ 
                            logger.error(e); 
                            logger.error("<"+files[i].getName()+">下载失败"); 
                        } 
                    } 
                } 
            } catch (Exception e) { 
                logger.error(e); 
                logger.error("下载过程中出现异常"); 
            } 
        }else{
            logger.error("链接失败！");
        }
        return false;
    }
    
    
    /** 
     * 
     * 下载FTP文件 
     * 当你需要下载FTP文件的时候，调用此方法 
     * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
     * 
     * @param ftpFile 
     * @param relativeLocalPath 
     * @param relativeRemotePath 
     */ 
    private  static void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) { 
                OutputStream outputStream = null; 
                try { 
                	File entryDir = new File(relativeLocalPath);
    				//如果文件夹路径不存在，则创建文件夹
    				if (!entryDir.exists() || !entryDir.isDirectory())
    				{
    					entryDir.mkdirs();
    				}
                    File locaFile= new File(relativeLocalPath+ ftpFile.getName()); 
                    //判断文件是否存在，存在则返回 
                    if(locaFile.exists()){ 
                        return; 
                    }else{ 
                        outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName()); 
                        ftp.retrieveFile(ftpFile.getName(), outputStream); 
                        outputStream.flush(); 
                        outputStream.close(); 
                    } 
                } catch (Exception e) { 
                    logger.error(e);
                } finally { 
                    try { 
                        if (outputStream != null){ 
                            outputStream.close(); 
                        }
                    } catch (IOException e) { 
                       logger.error("输出文件流异常"); 
                    } 
                } 
            } 
        } else { 
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName(); 
            String newRemote = new String(relativeRemotePath+ ftpFile.getName().toString()); 
            File fl = new File(newlocalRelatePath); 
            if (!fl.exists()) { 
                fl.mkdirs(); 
            } 
            try { 
                newlocalRelatePath = newlocalRelatePath + '/'; 
                newRemote = newRemote + "/"; 
                String currentWorkDir = ftpFile.getName().toString(); 
                boolean changedir = ftp.changeWorkingDirectory(currentWorkDir); 
                if (changedir) { 
                    FTPFile[] files = null; 
                    files = ftp.listFiles(); 
                    for (int i = 0; i < files.length; i++) { 
                        downloadFile(files[i], newlocalRelatePath, newRemote); 
                    } 
                } 
                if (changedir){
                    ftp.changeToParentDirectory(); 
                } 
            } catch (Exception e) { 
                logger.error(e);
            } 
        } 
    } 

    /**
     * 先配置下载链接，在下载文件。 调用了startDown和downloadFile写的方法
     * @param f
     * @param savepath
     * @param filename
     * @return
     * @throws Exception
     */
    public static boolean downfile(Ftp f,String savepath,String filename) throws Exception{  
    	try{
            
            FtpUtil.connectFtp(f);
            //File file = new File("F:/test/com/test/Testng.java");  
            //FtpUtil.upload(file);//把文件上传在ftp上
            return FtpUtil.startDown(f, filename ,savepath,  f.getPath());//下载ftp文件测试
            
    	}catch(Exception e){
    		throw new Exception(e.getMessage());
    	}
          
       } 
}
