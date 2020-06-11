package com.attilax.util.cli;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpProgressMonitor;

/**
 * java ssh登录linux以后的一些操作方式
 * @author liuxy
 *
 */
public class SchUnitJsch extends SchUnit{
    private final static Log logger =LogFactory.getLog(SchUnitJsch.class);
 public SchUnitJsch() {
 super();
 }



 public SchUnitJsch(String username, String password, String host) {
 super(username, password, host);
 }
 /**
 * 开启session
 * @return
 * @throws JSchException
 */
 private  Session openSession() throws JSchException{
    JSch jsch=new JSch();
 Session session=null;    
 session=jsch.getSession(username, host);
     Properties sshConfig = new Properties();  
    sshConfig.put(&quot;StrictHostKeyChecking&quot;, &quot;no&quot;);  
    session.setConfig(sshConfig); 
    session.setPassword(password);
    session.connect(3000);
 return session;
 }
 /**
 * 上传本地文件到远程linux上
 * 使用sftp上传
 */
// @Override
// public boolean uploadLocalFileToRemote(String localFile, String remoteDir) {
//   Session session=null;
// try {
// session = openSession();
// } catch (JSchException e) {
// logger.error(e.getMessage());
// if(session!=null) session.disconnect();
// return false;
// }
// ChannelSftp channel=null;
// try {
// channel=(ChannelSftp) session.openChannel(&quot;sftp&quot;);
// channel.connect();
// SftpProgressMonitorImpl sftpProgressMonitorImpl=new SftpProgressMonitorImpl();
// channel.put(localFile, remoteDir,sftpProgressMonitorImpl);
// 
// return sftpProgressMonitorImpl.isSuccess();
// }catch (JSchException e) {
// if(channel!=null){
// channel.disconnect();
//             session.disconnect();
// }
//    return  false;
// } catch (SftpException e) {
// logger.error(e.getMessage());
// }
// return false;
// }
 /**
 * 上传镜像映射检测
 * @author liuxy
 *
 */
//   static class  SftpProgressMonitorImpl implements SftpProgressMonitor{
// private  long size;
// private  long currentSize=0;
// private  boolean endFlag=false;
//// @Override
//// public void init(int op, String srcFile, String dstDir, long size) {
//// logger.debug(&quot;文件开始上传：【&quot;+srcFile+&quot;】--&gt;【&quot;+dstDir+&quot;】&quot;+&quot;，文件大小:&quot;+size+&quot;,参数&quot;+op);
//// this.size=size; 
//// }
//// 
//// @Override
//// public void end() {
//// logger.debug(&quot;文件上传结束&quot;);
//// endFlag=true;
//// }
//// 
//// @Override
//// public boolean count(long count){
//// currentSize+=count;
//// logger.debug(&quot;上传数量:&quot;+currentSize); 
//// return true;
//// }
//// public boolean isSuccess(){
//// return endFlag&amp;&amp;currentSize==size;
//// }
//// }
// 
// /**
// * 执行指令
// * @param commands
// */
// public StringBuffer executeCommands(String commands){
// return executeCmd(commands).getOutRes();
// }
// /**
// * 执行shell指令并且返回结果对象ResInfo
// * @param commands
// * @return
// */
// public ResInfo executeCmd(String commands){
//     ResInfo resInfo=new ResInfo();
//     Session session=null;
// try {
// session = openSession();
// } catch (JSchException e) {
// logger.debug(e.getMessage());
// if(session!=null) session.disconnect();
// return null;
// }
// ChannelExec channel=null;
// StringBuffer result=new StringBuffer();
// StringBuffer errResult=new StringBuffer();
// try {
//     channel=(ChannelExec) session.openChannel(&quot;exec&quot;);
//     channel.setCommand(commands);
//     channel.setInputStream(null);
//       ((ChannelExec)channel).setErrStream(null);
//       InputStream in=channel.getInputStream();
//       InputStream err=channel.getErrStream();
//       channel.connect();
//       byte[] bytes=new byte[1024];
//       byte[] bytesErr=new byte[1024];
//       while(true){
//       while(in.available()&gt;0){
//       int i=in.read(bytes, 0, 1024);
//       if(i0){
//       int i=err.read(bytesErr, 0, 1024);
//       if(i0||err.available()&gt;0) continue;
//       logger.debug(&quot;exit-status: &quot;+channel.getExitStatus());
//       resInfo.setExitStuts(channel.getExitStatus());
//       resInfo.setOutRes(result);
//       resInfo.setErrRes(errResult);
//               break;
//       }
//       Thread.sleep(1000);
//       }
//     return resInfo;
// } catch (JSchException e) {
// logger.error(e.getMessage());
//     return null;
// } catch (Exception e) {
// logger.error(e.getMessage());
// return null;
// }finally{
// channel.disconnect();
// session.disconnect();
// }
// } 
// 
// //exec command 结果返回对象
//     public static class ResInfo{
//   int exitStuts;//返回状态码 （在linux中可以通过 echo $? 可知每步执行令执行的状态码）
//    StringBuffer outRes;//标准正确输出流内容
//    StringBuffer errRes;//标准错误输出流内容
//    public int getExitStuts() {
//     return exitStuts;
//     }
// public void setExitStuts(int exitStuts) {
// this.exitStuts = exitStuts;
// }
// public StringBuffer getOutRes() {
// return outRes;
// }
// public void setOutRes(StringBuffer outRes) {
// this.outRes = outRes;
// }
// public StringBuffer getErrRes() {
// return errRes;
// }
// public void setErrRes(StringBuffer errRes) {
// this.errRes = errRes;
// }
//   
// public void clear(){
// exitStuts=0;
// outRes=errRes=null;
// }
// }
// 
// 
// 
// public static abstract class MyUserInfo
//     implements UserInfo, UIKeyboardInteractive{
// @Override
// public String getPassword(){ return null; }
// @Override
// public boolean promptYesNo(String str){ return false; }
// @Override
// public String getPassphrase(){ return null; }
// @Override
// public boolean promptPassphrase(String message){ return false; }
// @Override
// public boolean promptPassword(String message){ return false; }
// @Override
// public void showMessage(String message){ }
// @Override
// public String[] promptKeyboardInteractive(String destination,
// String name, String instruction, String[] prompt, boolean[] echo) {
// return null;
// }
// } 
//    /**
//     * 删除远程linux下的文件
////     */
//// @Override
//// public boolean deleteRemoteFileorDir(String remoteFile) {
////   Session session=null;
//// try {
//// session = openSession();
//// } catch (JSchException e) {
//// logger.info(e.getMessage());
//// if(session!=null) session.disconnect();
//// return false;
//// }
//// ChannelSftp channel=null;
//// try {
//// channel=(ChannelSftp) session.openChannel(&quot;sftp&quot;);
//// channel.connect();
//// SftpATTRS sftpATTRS= channel.lstat(remoteFile);
//// if(sftpATTRS.isDir()){
//// //目录
//// logger.debug(&quot;remote File:dir&quot;);
//// channel.rmdir(remoteFile);
//// return true;
//// }else if(sftpATTRS.isReg()){
//// //文件
//// logger.debug(&quot;remote File:file&quot;);
//// channel.rm(remoteFile);
//// return true;
//// }else{
//// logger.debug(&quot;remote File:unkown&quot;);
//// return false;
//// }
//// }catch (JSchException e) {
//// if(channel!=null){
//// channel.disconnect();
////             session.disconnect();
//// }
////    return  false;
//// } catch (SftpException e) {
//// logger.error(e.getMessage());
//// }
//// return false;
//// }
////    /**
////     * 判断linux下 某文件是否存在
////     */
//// @Override
//// public boolean detectedFileExist(String remoteFile) {
////   Session session=null;
//// try {
//// session = openSession();
//// } catch (JSchException e) {
//// logger.info(e.getMessage());
//// if(session!=null) session.disconnect();
//// return false;
//// }
//// ChannelSftp channel=null;
//// try {
//// channel=(ChannelSftp) session.openChannel(&quot;sftp&quot;);
//// channel.connect();
//// SftpATTRS sftpATTRS= channel.lstat(remoteFile);
//// if(sftpATTRS.isDir()||sftpATTRS.isReg()){
//// //目录 和文件
//// logger.info(&quot;remote File:dir&quot;);
//// return true;
//// }else{
//// logger.info(&quot;remote File:unkown&quot;);
//// return false;
//// }
//// }catch (JSchException e) {
//// if(channel!=null){
//// channel.disconnect();
////             session.disconnect();
//// }
////    return  false;
//// } catch (SftpException e) {
//// logger.error(e.getMessage());
//// }
//// return false;
//// }
//
//
//}
//@Override
//public boolean count(long arg0) {
//	// TODO Auto-generated method stub
//	return false;
//}
//@Override
//public void end() {
//	// TODO Auto-generated method stub
//	
//}
//@Override
//public void init(int arg0, String arg1, String arg2, long arg3) {
//	// TODO Auto-generated method stub
//	
//}
//
