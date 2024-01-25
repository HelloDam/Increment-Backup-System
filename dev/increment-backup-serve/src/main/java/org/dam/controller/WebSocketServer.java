package org.dam.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author dam
 * @create 2024/1/24 14:32
 */
@ServerEndpoint(value = "/websocket/{username}")
//将WebSocketServer注册为spring的一个bean
@Component
@Slf4j
public class WebSocketServer {
    /**
     * 记录当前在线连接的客户端的session
     */
    public static final Map<String, Session> usernameAndSessionMap = new ConcurrentHashMap<>();
    /**
     * 记录正在进行的聊天的发出者和接收者
     */
    public static final Map<String, Integer> fromToMap = new ConcurrentHashMap<>();
    /**
     * 用户Session保留时间，如果超过该时间，用户还没有给服务端发送消息，认为用户下线，删除其Session
     * 注意：该时间需要比客户端的心跳时间更长
     */
    private static final long expire = 6000;


    /**
     * 浏览器和服务端连接建立成功之后会调用这个方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        usernameAndSessionMap.put(username, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, usernameAndSessionMap.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        usernameAndSessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, usernameAndSessionMap.size());
    }

    /**
     * 发生错误的时候会调用这个方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }


    /**
     * onMessage方法是一个消息的中转站
     * 1、首先接受浏览器端socket.send发送过来的json数据
     * 2、然后解析其数据，找到消息要发送给谁
     * 3、最后将数据发送给相应的人
     *
     * @param message 客户端发送过来的消息 数据格式：{"from":"user1","to":"admin","text":"你好呀"}
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {

    }

}