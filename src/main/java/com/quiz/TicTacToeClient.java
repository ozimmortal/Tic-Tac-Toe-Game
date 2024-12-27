package com.quiz;

import java.io.*;
import java.net.*;

public class TicTacToeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the Tic-Tac-Toe Server!");

            String serverMessage;
            while (true) {
                try {
                    serverMessage = in.readLine();
                    if (serverMessage == null) {
                        System.out.println("Server has closed the connection.");
                        break;
                    }
                    System.out.println(serverMessage);

                    if (serverMessage.startsWith("Your turn!")) {
                        System.out.print("Enter your move: ");
                        String move = userInput.readLine();
                        if (move == null || move.trim().isEmpty()) {
                            System.out.println("Invalid move. Try again.");
                        } else {
                            out.println(move);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Connection lost: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Unable to connect to the server: " + e.getMessage());
        }
    }
}
