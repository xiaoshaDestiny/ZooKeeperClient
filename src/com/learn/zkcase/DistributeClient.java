package com.learn.zkcase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

//�ͻ��˴���
public class DistributeClient {
	private static String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private static int sessionTimeout = 2000;
	private ZooKeeper zk = null;
	private String parentNode = "/servers";
	private volatile ArrayList<String> serversList = new ArrayList<>();

	// ������ zk �Ŀͻ�������
	public void getConnect() throws IOException {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// �ٴ���������
				try {
					getServerList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getServerList() throws Exception {
		List<String> children = zk.getChildren(parentNode, true);		// ��ȡ�������ӽڵ���Ϣ�����ҶԸ��ڵ���м���
		ArrayList<String> servers = new ArrayList<>();
		for (String child : children) {
			byte[] data = zk.getData(parentNode + "/" + child, false, null);
			servers.add(new String(data));
		}
		// �� servers ��ֵ����Ա serverList�����ṩ����ҵ���߳�ʹ��
		serversList = servers;
		System.out.println(serversList);
	}

	// ҵ����
	public void business() throws Exception {
		System.out.println("client is working ...");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		
		DistributeClient client = new DistributeClient();	// ��ȡ zk ����
		client.getConnect();
		client.getServerList();								// ��ȡ servers ���ӽڵ���Ϣ�����л�ȡ��������Ϣ�б�
		client.business();									// ҵ���������
	}
}