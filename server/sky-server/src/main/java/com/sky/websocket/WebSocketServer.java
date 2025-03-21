package com.sky.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * websocket服务端
 */
@Component
@ServerEndpoint("/ws/{sid}") // 配置websocket服务端
public class WebSocketServer {

    // 存放会话对象，存储所有在线用户的session
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * 连接建立成功调用的方法
     * @param session
     * @param sid 客户端id
     */
    @OnOpen // 监听连接打开事件
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "建立连接");
        sessionMap.put(sid, session);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param sid 客户端id
     */
    @OnMessage  // 监听消息事件
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息：" + message);
    }

    /**
     * 连接关闭调用的方法
     * @param sid 客户端id
     */
    @OnClose // 监听连接关闭事件
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("客户端：" + sid + "连接断开");
        sessionMap.remove(sid);
    }

    /**
     * 群发：给所有在线用户发送消息
     * @param message 消息
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
