package com.example.virtual_campus;

import com.example.virtual_campus.ServerPages.*;
import com.example.virtual_campus.controller.UserController;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.controller.AuthController;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class Server{
    private final int PORT = 8080;
    private final int MAX_CONNECTIONS = 10;
    private ConcurrentHashMap<Socket, Thread> socketMap = new ConcurrentHashMap<>();

    @Autowired
    private LoginPage LoginPage;
    @Autowired
    private ManageUserPage ManageUserPage;
    @Autowired
    private ManageGoodsPage ManageGoodsPage;
    @Autowired
    private ManageBookPage ManageBookPage;
    @Autowired
    private ManageCoursePage ManageCoursePage;
    @Autowired
    private ManageStudentPage ManageStudentPage;
    @Autowired
    private StudentShoppingPage StudentShoppingPage;
    @Autowired
    private StudentSchoolPage StudentSchoolPage;
    @Autowired
    private StudentLessonPage StudentLessonPage;
    @Autowired
    private StudentBorrowPage StudentBorrowPage;

    public static void main(String[] args) {
        SpringApplication.run(Server.class);
    }

    @Bean
    public CommandLineRunner startServer(){
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (socketMap.size() >= MAX_CONNECTIONS) {
                    System.out.println("Max connections reached, rejecting new connection.");
                    clientSocket.close();
                    continue;
                }
                System.out.println("Client connected!");
                handleClient(clientSocket);
/*
                Thread clientThread = new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                socketMap.put(clientSocket, clientThread);
                clientThread.start();
*/

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (args) -> {
            System.out.println("In CommandLineRunnerImpl ");

            for (String arg : args) {
                System.out.println(arg);
            }
        };
    }

    public void handleClient(Socket clientSocket) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
            while (true) {
                String model = (String) in.readObject();
                if (model.equals("exit")) {
                    in.close();
                    out.flush();
                    out.close();
                    break;
                }
                String function = (String) in.readObject();
                System.out.println("读入第一段指向："+model);
                switch (model) {
                    case "1":
                        LoginPage.work(function, in, out);
                        break;
                    case "2":
                        ManageUserPage.work(function, in, out);
                        break;
                    case "3":
                        ManageGoodsPage.work(function, in, out);
                        break;
                    case "4":
                        ManageBookPage.work(function, in, out);
                        break;
                    case "5":
                        ManageCoursePage.work(function, in, out);
                        break;
                    case "6":
                        ManageStudentPage.work(function, in, out);
                        break;
                    case "10":
                        StudentShoppingPage.work(function, in, out);
                        break;
                    case "11":
                        StudentSchoolPage.work(function, in, out);
                        break;
                    case "12":
                        StudentLessonPage.work(function, in, out);
                        break;
                    case "13":
                        StudentBorrowPage.work(function, in, out);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socketMap.remove(clientSocket);
            clientSocket.close();
            System.out.println("Connection closed with " + clientSocket.getRemoteSocketAddress());
        }
    }
}