/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class Main {

    private static final int SERVER_PORT = 5781;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("SERVER PORT REEGISTERING...");
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("LISTEN " + SERVER_PORT);

            while (true) {
                Socket accept = serverSocket.accept();

                String clientAddress = accept.getInetAddress().getHostAddress();

                System.out.println("CONNECTED: " + clientAddress);

                DataInputStream dis = new DataInputStream(accept.getInputStream());
                DataOutputStream dos = new DataOutputStream(accept.getOutputStream());

                String command = dis.readUTF();
                System.out.println("COMMAND: " + command);

                switch (command) {
                    case "PING":
                        dos.writeUTF("PONG");
                        dos.flush();
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
