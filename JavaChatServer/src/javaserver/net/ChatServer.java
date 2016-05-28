/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaserver.Main;

/**
 *
 * @author human
 */
public class ChatServer implements Runnable {

    private static final int SERVER_TIMEOUT = 1000;

    private int port;
    private ServerSocket serverSocket;
    private boolean listen;

    public ChatServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(SERVER_TIMEOUT);
    }

    @Override
    public void run() {
        listen = true;

        System.out.println("SERVER LISTEN ON " + port);
        while (listen) {
            try (Socket accept = serverSocket.accept()) {
                // CONFIG SOCKET
                accept.setSoLinger(true, 500); // Waiting before close connection
                accept.setSoTimeout(1000); // Waiting before throws SocketTimeoutException when read data.

                String clientAddress = accept.getInetAddress().getHostAddress();
                System.out.println("CONNECTED: " + clientAddress);

                // GET IO
                DataInputStream dis = new DataInputStream(accept.getInputStream());
                DataOutputStream dos = new DataOutputStream(accept.getOutputStream());

                // WORKING WITH SOCKET
                String command = dis.readUTF();
                System.out.println("COMMAND: " + command);

                switch (command) {
                    case "PING":
                        dos.writeUTF("PONG");
                        dos.flush();
                        break;
                    case "MSG":
                        String recipient = dis.readUTF();
                        String message = dis.readUTF();

                        System.out.printf("  Address: %s\n  Message: %s\n", recipient, message);

                        dos.writeUTF("OK");
                        dos.flush();
                        break;

                    default:
                        dos.writeUTF("ERROR:Unknown command!");
                        dos.flush();
                        break;
                }
            } catch (SocketTimeoutException ex) {
                // NOOP
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
        listen = false;
    }

}
