package com.learn.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZKClient {
	
	//连接的 ZkServer
	private String connectStr ="hadoop102:2181,hadoop103:2181,hadoop104:2181";
	//超时时间  毫秒
	private int sessionTimeout = 2000;

	private ZooKeeper zkClient = null;
	
	
	//创建化ZooKeeper客户端
	@Before
	public void initClient() throws IOException {
		
	 zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType()+"\t"+event.getPath());
			}
		});
	}
	
	// 创建子节点
	@Test
	public void create() throws Exception {
		// 4个参数
		// 参数 1：要创建的节点的路径；  参数 2：节点数据 ；  参数 3：节点权限 ；    参数 4：节点的类型
		//返回值是一个创建成功之后的路径
		String  nodeCreated  =  zkClient.create("/eclipse",  "hello  zk".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		System.out.println(nodeCreated);
		
	}
	
	// 获取子节点
	@Test
	public void getChildren() throws Exception {
		List<String> children = zkClient.getChildren("/", true);
		for (String child : children) {
		System.out.println(child);
		}
		// 延时阻塞
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// 判断 znode 是否存在
	@Test
	public void exist() throws Exception {
		Stat stat = zkClient.exists("/eclipse", false);
		System.out.println(stat == null ? "not exist" : "exist");
	}

	
	
	
	

}
