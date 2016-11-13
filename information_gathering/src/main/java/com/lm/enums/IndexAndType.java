package com.lm.enums;

public enum IndexAndType {
	DISTRIBUTE("分布式", "分布式"),
	SPRING("Spring", "Spring"),
	JAVA("java", "java"),
	HIBERNATE("Hibernate", "Hibernate"),
	LINUX("linux", "linux"),
	MYSQL("mysql", "mysql"),
	UML("UML", "UML"),
	BUSINESS("电商", "电商"),
	J2EE("J2EE", "J2EE"),
	INTERNET("互联网", "互联网"),
	MYBATIS("mybatis", "mybatis"),
	NOSQL("NoSQL", "NoSQL"),
	REDIS("redis", "redis"),
	MEMCACHE("memcache", "memcache"),
	QUEUE("queue", "queue"),
	COCURRENT("并发", "并发"),
	COLLECTION("集合", "集合"),
	OPERATOR("运算符", "运算符"),
	IO("IO", "IO"),
	MULTI_THREAD("多线程", "多线程"),
	REFLECT("反射", "反射"),
	IOC("IOC", "IOC"),
	AOP("AOP", "AOP"),
	TOMCAT("Tomcat", "Tomcat"),
	QUARTZ("Quart", "Quartz"),
	APACHE("apache", "apache"),
	DESIGN_PATTERN("设计模式 ", "设计模式"),
	SHELL("shell", "shell"),
	ELASTICSEARCH("elasticsearch", "elasticsearch"),
	BIG_DATA("大数据", "大数据"),
	HDFS("HDFS", "HDFS"),
	Kafka("Kafka", "Kafka"),
	SPARK("Spark", "Spark"),
	LUCENE("Lucene", "Lucene"),
	TCP("TCP", "TCP"),
	SQL("SQL", "SQL"),
	ORACLE("Oracle", "Oracle"),
	OPTIMISED("优化", "优化"),
	ActiveMQ("ActiveMQ", "ActiveMQ"),
	DUBBO("Dubbo", "Dubbo"),
	ZOOKEEPER("Zookeeper", "Zookeeper"),
	RESTFUL("Restful", "Restful"),
	TABLE_SPLIT("分表", "分表"),
	DATABASE_SPLIT("分库", "分库"),
	HIGH_PERFORM("高性能", "高性能"),
	LOAD("负载", "负载"),
	
	//代码管理
	MAVEN("MAVEN ", "MAVEN"),
	GIT("Git", "Git"),
	ANT("Ant", "Ant"),
	
	//前端技术
	JQUERY("jquery", "jquery"),
	JSON("JSON", "JSON"),
	JAVASCRIPT("Javascript", "Javascript"),
	HTTP("HTTP", "HTTP"),
	AJAX("AJAX", "AJAX"),
	JSTL("JSTL", "JSTL"),
	NETTY("Netty", "Netty");

	
	
	private IndexAndType(String index, String type) {
		this.index = index;
		this.type = type;
	}
	
	private String index;
	private String type;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static void main(String[] args) {
		for(IndexAndType types:IndexAndType.values()){
			System.out.println(types.index + types.type);
		}
	}
	
}
