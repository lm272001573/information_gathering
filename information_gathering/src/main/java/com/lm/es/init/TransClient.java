package com.lm.es.init;


import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransClient {
    
	public static TransportClient client = null;
	
	@Value("${es.ip}")
	private String ip;
	
	@Value("${es.port}")
	private String port;
	
	@SuppressWarnings("resource")
	@PostConstruct
	public void init(){
		try {
			Settings settings = Settings.builder().put("client.transport.sniff", true).build();
			
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(port)));
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
