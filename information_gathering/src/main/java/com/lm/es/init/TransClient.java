package com.lm.es.init;


import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TransClient {
    
	public static TransportClient client = null;
	
	static{
		init();
	}
	
	@SuppressWarnings("resource")
	public static void init(){
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).build();
			
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static TransportClient getClient(){
		return client;
	}
	
	public void shutdown(){
		client.close();
	}
}	
