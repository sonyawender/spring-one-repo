package ru.geekbrains.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ChatServerRunner {

    public static void main(String[] args) {
       // ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-config.xml");
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ChatServer chatServer = appContext.getBean("chatServer", ChatServer.class);
        chatServer.start(7777);

    }
}
