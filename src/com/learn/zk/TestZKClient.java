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
	
	//���ӵ� ZkServer
	private String connectStr ="hadoop102:2181,hadoop103:2181,hadoop104:2181";
	//��ʱʱ��  ����
	private int sessionTimeout = 2000;

	private ZooKeeper zkClient = null;
	
	
	//������ZooKeeper�ͻ���
	@Before
	public void initClient() throws IOException {
		
	 zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType()+"\t"+event.getPath());
			}
		});
	}
	
	// �����ӽڵ�
	@Test
	public void create() throws Exception {
		// 4������
		// ���� 1��Ҫ�����Ľڵ��·����  ���� 2���ڵ����� ��  ���� 3���ڵ�Ȩ�� ��    ���� 4���ڵ������
		//����ֵ��һ�������ɹ�֮���·��
		String  nodeCreated  =  zkClient.create("/eclipse",  "hello  zk".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		System.out.println(nodeCreated);
		
	}
	
	// ��ȡ�ӽڵ�
	@Test
	public void getChildren() throws Exception {
		List<String> children = zkClient.getChildren("/", true);
		for (String child : children) {
		System.out.println(child);
		}
		// ��ʱ����
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// �ж� znode �Ƿ����
	@Test
	public void exist() throws Exception {
		Stat stat = zkClient.exists("/eclipse", false);
		System.out.println(stat == null ? "not exist" : "exist");
	}

	
	
	
	

}
