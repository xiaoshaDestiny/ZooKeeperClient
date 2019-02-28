package com.learn.zkcase;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

//�������˴���
public class DistributeServer {
	private static String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private static int sessionTimeout = 2000;
	private ZooKeeper zk = null;
	private String parentNode = "/servers";

	// ������ zk �Ŀͻ�������
	public void getConnect() throws IOException {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
	}

	// ע�������
	public void registServer(String hostname) throws Exception {
		String create = zk.create(parentNode + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is noline " + create);
	}

	// ҵ����
	public void business(String hostname) throws Exception {
		System.out.println(hostname + " is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		
		DistributeServer server = new DistributeServer();	// ��ȡ zk ����
		server.getConnect();
		server.registServer(args[0]);						// ���� zk ����ע���������Ϣ
		server.business(args[0]);							//����ҵ����
	}
}