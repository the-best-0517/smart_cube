package cn.buu.on_way.common.websocket.webS;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebSocket {
	private Session session;
	/**���ͻ������ӳɹ���ִ�еķ���*/
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		System.out.println("�Ѿ���������");
	}
	@OnMessage
	public void onMessage(String msg) {
		System.out.println("msg:"+msg);
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			System.out.println("�����쳣");
			e.printStackTrace();
		}
	}
}
