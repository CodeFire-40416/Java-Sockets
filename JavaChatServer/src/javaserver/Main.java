/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaserver.net.ChatServer;

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

        ChatServer chatServer = null;

        try {
            chatServer = new ChatServer(SERVER_PORT);
            Thread thread = new Thread(chatServer);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (chatServer != null) {
            try (Scanner scanner = new Scanner(System.in)) {
                boolean work = true;

                while (work) {
                    System.out.println("ACTIONS:");
                    System.out.println("0. [E]xit");

                    String input = scanner.nextLine();

                    switch (input.toLowerCase()) {
                        case "0":
                        case "e":
                            chatServer.stop();
                            work = false;
                            continue;
                        default:
                            System.out.println("UNKNOWN ACTION");
                            break;
                    }
                }
            }
        }

    }

}
