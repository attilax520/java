package resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

import com.attilax.util.HtmlUtilV2t33;

import emailPKg.emailMimeParse;
import officefile.wordutilV2t34;

public class resumeGrepper {

	public static void main(String[] args) throws IOException, XmlException, OpenXML4JException {
		String dirString = "C:\\Users\\zhoufeiyue\\Downloads\\resm415";
		Files.walkFileTree(Paths.get(dirString), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				// TODO Auto-generated method stub
				// return super.visitFile(file, attrs);
				System.out.println("\t正在访问" + file + "文件");
				if (file.endsWith(".rar") || file.endsWith(".zip")) {
					// System.out.println("******找到目标文件Test.java******");
					return FileVisitResult.CONTINUE; // 找到了就终止
				}
				if (file.toAbsolutePath().toString().endsWith(".pdf")  ) {
					// System.out.println("******找到目标文件Test.java******");
					try {
						PowerPointExtractor	extractor = new PowerPointExtractor(new FileInputStream(file.toFile()));
						 System.out.println( extractor.getText()); ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					return FileVisitResult.CONTINUE; // 找到了就终止
				}
				if("1"=="1")
				return FileVisitResult.CONTINUE; // 没找到继续找
				
				if( file.toAbsolutePath().toString().contains("智联")  )
				{
					
					System.out.println( HtmlUtilV2t33.html2txt(FileUtils.readFileToString(file.toFile()) ));
					return FileVisitResult.CONTINUE; // 没找到继续找
				}

				try {
					System.out.println(wordutilV2t34.readWordV2(file.toFile().getAbsolutePath()));

				} catch (Exception e) {
					try {
						System.out.println(emailMimeParse.eml2txt(file.toFile().getAbsolutePath()));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				return FileVisitResult.CONTINUE; // 没找到继续找
			}

		});

//		File[] faFiles=new File(dirString).listFiles();
//		for (File f : faFiles) {
//			try {
//				System.out.println(f);
//			    System.out.println(wordutilV2t34.readWord(f.getAbsolutePath()));	  
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//		}

	}

}
