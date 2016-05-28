/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class Main {

    private static final String SERVER_ADDRESS = "192.168.1.99";
    private static final int SERVER_PORT = 5781;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try (Socket client = new Socket()) {
            System.out.println("CONNECTING...");

            InetSocketAddress serverAddress = new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT);
            client.connect(serverAddress);

            System.out.println("CONNECTION ESTABLISHED");

            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());

            dos.writeUTF("PING");
            dos.flush();
            
            String response = dis.readUTF();
            
            System.out.println("RESPONSE: " + response);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("DISCONNECTED");

    }

}
