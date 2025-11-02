package Pages;

import Pages.view.LoginFrame;
import Pages.view.RegisterFrame;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainApp {
    private static Long currentUserId;
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void main(String[] args) {
        // 初始化Socket或其他必要的设置
        initializeSocket();

        // 检查用户状态并显示相应的页面
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(MainApp::close_source));
    }

    public static void initializeSocket() {
        try {
            socket = new Socket("localhost", 8080);//此处填入服务器ip地址
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close_source() {
        try {
            out.writeObject("exit");
            out.flush();
            System.out.println("exit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter and Setter for currentUser and socket

    public static Long getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(Long userId) {
        currentUserId = userId;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        MainApp.socket = socket;
    }

    public static ObjectInputStream getIn() {
        return in;
    }

    public static void setIn(ObjectInputStream in) {
        MainApp.in = in;
    }

    public static ObjectOutputStream getOut() {
        return out;
    }

    public static void setOut(ObjectOutputStream out) {
        MainApp.out = out;
    }
}
