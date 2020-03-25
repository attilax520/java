package org.chwin.firefighting.apiserver.net;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NetUtil {


    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public void testUploadFile2(@RequestParam MultipartFile file, @RequestParam String saveDir) throws IOException {
        //保存文件
        file.transferTo(new File(saveDir + "\\" + file.getOriginalFilename()));

    }






    @RequestMapping("/show")
    public void home(@RequestParam String saveDir, HttpServletResponse response) throws IOException {
        File dir = new File(saveDir);

        response.setContentType("application/image");
     //   response.addHeader("Content-Disposition", "attachment;fileName=" + f.getName());// 设置文件名
        // 输出到下载刘
        IOUtils.copy(new FileInputStream(dir), response.getOutputStream());
        response.flushBuffer();

    }
}
