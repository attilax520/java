package com.attilax.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;

import ch.ethz.ssh2.StreamGobbler;

public class IOUtilsStreamUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static Map<InputStream, Map<String, Object>> inputStreamTable = Maps.newConcurrentMap();

	public synchronized static List<String> IOUtils_readLines(InputStream stdout, Consumer Consumer1) throws Exception {
		Map map = Maps.newConcurrentMap();

		if (!inputStreamTable.containsKey((stdout))) { // first
			List<String> result = new ArrayList<>();
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						InputStream is = new StreamGobbler(stdout);// 获得标准输出流
						BufferedReader brs = new BufferedReader(new InputStreamReader(is));
						map.put("br", brs);
						for (String line = brs.readLine(); line != null; line = brs.readLine()) {

							Consumer1.accept(line);
							result.add(line);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			thread.start();
			Map recMapLine = Maps.newConcurrentMap();
			recMapLine.put("thd", thread);
			recMapLine.put("rzt", result);
			inputStreamTable.put(stdout, recMapLine);
			Thread.sleep(500);
			return result;
		} else {
			Thread.sleep(500);
			List<String> result = (List<String>) inputStreamTable.get(stdout).get("rzt");
			return result;
		}

	}
	public static void execV2(OutputStream oStream, String get) throws IOException, InterruptedException {
		System.out.println(get);
		get = get + "\r\n";
		oStream.write(get.getBytes());
		oStream.flush();
		// Thread.sleep(500);
	}

	public static void exec(OutputStream oStream, String get) throws IOException, InterruptedException {
		System.out.println(get);
		get = get + "\r\n";
		oStream.write(get.getBytes());
		oStream.flush();
		Thread.sleep(500);
	}
	public synchronized static List<String> readLines(InputStream stdout) throws Exception {

		return IOUtils_readLines(stdout, new Consumer() {

			@Override
			public void accept(Object line) {
				System.out.println("xStream:>" + line);

			}
		});

//		BufferedReader brs=(BufferedReader) map.get("br");
//		brs.close();
		// return result;
	}
	
	
	

//	private static void out2li(List<String> result, Session session) throws IOException {
//		InputStream is = new StreamGobbler(session.getStdout());// 获得标准输出流
//		BufferedReader brs = new BufferedReader(new InputStreamReader(is));
//		for (String line = brs.readLine(); line != null; line = brs.readLine()) {
//			result.add(line);
//			System.out.println("stdStream:>" + line);
//		}
//	}
}
