package cn.buu.on_way.common.websocket.webS;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebSocket {
	private Session session;
	/**当客户端连接成功后执行的方法*/
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		System.out.println("已经建立连接");
	}
	@OnMessage
	public void onMessage(String msg) {
		System.out.println("msg:"+msg);
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			System.out.println("链接异常");
			e.printStackTrace();
		}
	}
}
