package com.attilax.fulltxt;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.cnhis.cloudhealth.module.log.Cls1;

public class esClient {
	static final Logger logger = Logger.getLogger(Cls1.class);
	private static final String HOST = "localhost";
	private static final int PORT = 9200;

	public static void main(String[] args) throws UnknownHostException {
		
		
		
		
//		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
//		        .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
//		        .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));

	}

}
