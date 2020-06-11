package com.attilax.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.attilax.compress.ZipUtil;
import com.attilax.io.pathx;
import com.attilax.util.ExUtil;
import com.attilax.util.Global;
import com.attilax.util.linuxUtil;
import com.attilax.util.cli.SSHHelper;
import com.attilax.util.cli.cliRet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jcraft.jsch.SftpProgressMonitor;

// 
@WebServlet(name = "UploadServlet1", urlPatterns = { "/UploadServlet1", "/dafdafsss22" }, loadOnStartup = 1)
public class UploadServlet1 extends HttpServlet {
	protected static Logger logger = LoggerFactory.getLogger(UploadServlet1.class);

	public static Object upProcessInfo() {

		Object upPct = Global.getVarVal("vals626_upProcessInfo");
		Object putPct = Global.getVarVal("vals626_sftpTransPutProcessInfo");
		List li = Lists.newArrayList();
		li.add(upPct);
		li.add(putPct);
		return li;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Global.setVarVal("vals626_sftpTransPutProcessInfo", new ProcessInfo()); // reset
		response.setContentType("text/html");
		// 创建工厂 common fileup 1.3 jar
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 解决上传文件名的中文乱码
		// upload.setHeaderEncoding("UTF-8");
		// 通过工厂创建解析器
		ServletFileUpload upload = new ServletFileUpload(factory);

		// {"itemNum":3,"path":"","rate":100,"readSize":57815489,"show":"57815489/57815489
		// byte","totalSize":57815489}
		upload.setProgressListener(new ProgressListener() {
			public void update(long pBytesRead, long pContentLength, int pItems) {
				ProcessInfo pri = new ProcessInfo();
				pri.itemNum = pItems;
				pri.readSize = pBytesRead;
				pri.totalSize = pContentLength;
				pri.show = pBytesRead + "/" + pContentLength + " byte";
				pri.rate = Math.round(new Float(pBytesRead) / new Float(pContentLength) * 100);
				System.out.println(JSON.toJSONString(pri));
				Global.setVarVal("vals626_upProcessInfo", pri);
				// hs.setAttribute("proInfo", pri);
			}
		});

		Map<String, FileItem> m = Maps.newConcurrentMap();

		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fileItem : list) {
				m.put(fileItem.getFieldName(), fileItem);
			}

		//	String sx=JSON.toJSONString(m); // if big file the heap full
			final FileItem fileItem = m.get("upfile");

			String path = m.get("serverSavePath").getString("utf-8");// getvalue
			FileUtils.forceMkdir(new File(path));
			String pathname = path + File.separator + fileItem.getName();

			String svrinfo = m.get("svrinfo").getString("utf-8");// getvalue
			if (svrinfo.trim().length() == 0) {
				// localmode
				String truepath = gettoOsPath(pathname);
				fileItem.write(new File(pathname));

			} else {
				// rmt mode
				logger.info(" doget will inovke rmt_linux_put ,pathname:" + pathname);
				pathname = rmt_linux_put(fileItem, path, svrinfo);
			}

			respService.out_astoStr(response, pathname);

		} catch (Exception e) {
			ExUtil.throwExV2(e);
		}

		//// System.out.println("普通表单项："+item1.getName()+"="+path);
		// System.out.println("文件表单项:");
		// System.out.println("Content-Type"+item2.getContentType());
		// System.out.println("filename="+item2.getName());
		// System.out.println("size = "+item2.getSize());
	}

	private String rmt_linux_put(final FileItem fileItem, String path, String svrinfo) throws IOException, Exception {
		
		String[] a = svrinfo.split(",");
		SSHHelper helper = new SSHHelper(a[0], 22, a[1], a[2]);
	    cliRet cr=	 (cliRet) new linuxUtil(helper).   mkdir(path);
	    logger.info( "mkdir "+path+",cli ret:"+JSON.toJSONString(cr));
		String pathname;
		InputStream is = fileItem.getInputStream();
	
		
		pathname = path + "/" + fileItem.getName();
		FileUtils.forceMkdir(new File(path));
		// helper.uploadLocalFileToRemote(is, pathname);
		final ProcessInfo pri = new ProcessInfo();
		Global.setVarVal("vals626_sftpTransPutProcessInfo", new ProcessInfo()); // reset
		logger.info(" rmt_linux_put ,pathname:" + pathname);
		helper.uploadLocalFileToRemote(is, pathname, new SftpProgressMonitor() {

			@Override
			public void init(int arg0, String arg1, String arg2, long arg3) {
				// init(int op, String src, String dest, long max)
				// jeig haosyo ma invoke
				Map m = Maps.newConcurrentMap();
				m.put("a0", arg0);
				m.put("a1", arg1);
				m.put("a2", arg2);
				m.put("a3", arg3);
				System.out.println(m);

			}

			@Override
			public void end() {
				System.out.println("--end");
				pri.readSize = fileItem.getSize();
				pri.totalSize = fileItem.getSize();
				pri.rate = 100;
				Global.setVarVal("vals626_sftpTransPutProcessInfo", pri);

			}

			@Override
			public boolean count(long arg0) {
				// 32673 mybe 32kb is cache file block per time trans,must ret
				// true to next trans..
				// Will be called periodically as more data is transfered.
				// System.out.println(arg0);

				pri.readSize = pri.readSize + arg0;
				pri.totalSize = fileItem.getSize();
				pri.show = pri.readSize + "/" + pri.totalSize + " byte";
				pri.rate = Math.round(new Float(pri.readSize) / new Float(pri.totalSize) * 100);
				Global.setVarVal("vals626_sftpTransPutProcessInfo", pri);
				return true;
			}
		});
		return pathname;
	}

	private String gettoOsPath(String pathname) {
		if (!pathname.trim().toLowerCase().contains(":")) // rlt path
			if (pathx.isWindows()) {

				pathname = pathx.toOsPath(pathname);

			}
		return pathname;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
