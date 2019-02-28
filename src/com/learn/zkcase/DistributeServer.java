package com.learn.zkcase;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

//服务器端代码
public class DistributeServer {
	private static String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private static int sessionTimeout = 2000;
	private ZooKeeper zk = null;
	private String parentNode = "/servers";

	// 创建到 zk 的客户端连接
	public void getConnect() throws IOException {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
	}

	// 注册服务器
	public void registServer(String hostname) throws Exception {
		String create = zk.create(parentNode + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is noline " + create);
	}

	// 业务功能
	public void business(String hostname) throws Exception {
		System.out.println(hostname + " is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		
		DistributeServer server = new DistributeServer();	// 获取 zk 连接
		server.getConnect();
		server.registServer(args[0]);						// 利用 zk 连接注册服务器信息
		server.business(args[0]);							//启动业务功能
	}
}