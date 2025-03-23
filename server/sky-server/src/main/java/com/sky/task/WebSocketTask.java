package com.sky.task;

import com.sky.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebSocketTask {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 定时任务，通过webSocket每隔5秒发送一条消息到客户端
     */
//    @Scheduled(cron = "0/5 * * * * ?") // 每5秒执行一次
    public void sendMessageToClient() {
        webSocketServer.sendToAllClient("这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }
}
