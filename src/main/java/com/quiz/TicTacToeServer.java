package com.quiz;
import java.io.*;
import java.net.*;

public class TicTacToeServer {
    private static final int PORT = 12345;
    static String boardState = "- ";
    private static String[] board = {boardState,boardState,boardState,boardState,boardState,boardState,boardState,boardState,boardState};
    private static int currentPlayer = 0;
    private static Socket[] players = new Socket[2];

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Tic-Tac-Toe Server is running...");

        // Accept connections from two players
        for (int i = 0; i < 2; i++) {
            players[i] = serverSocket.accept();
            System.out.println("Player " + (i + 1) + " connected." );
            new PrintWriter(players[i].getOutputStream(), true).println("You are Player " + (i + 1));
        }

        // Start the game

        while (true) {
            Socket currentPlayerSocket = players[currentPlayer];
            PrintWriter out = new PrintWriter(currentPlayerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(currentPlayerSocket.getInputStream()));

            out.println("Your turn! Enter a position (1-9):");

            String input = in.readLine(); // Read input from the client
            if (input == null || input.trim().isEmpty()) {
                out.println("Invalid input. Please enter a valid position (1-9)." + "\n" + "Board:\n " + drawBoard());
                continue;
            }

            int position;
            try {
                position = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter a number between 1 and 9." + "\n" + "Board:\n " + drawBoard());
                continue;
            }

            if (position < 0 || position > 8 || !board[position].equals(boardState)) {
                out.println("Invalid move. Try again." + "\n" + "Board: \n" + drawBoard());
                continue;
            }

            board[position] = currentPlayer == 0 ? "X " : "O ";
            currentPlayer = (currentPlayer + 1) % 2;

            if (checkWin()) {
                broadcast("Game Over! Player " + (currentPlayer == 0 ? 2 : 1) + " wins!");
                break;
            }

            if (isDraw()) {
                broadcast("Game Over! It's a draw!");
                break;
            }

            broadcastBoard();
        }

        serverSocket.close();
    }

    private static void broadcast(String message) throws IOException {
        for (Socket player : players) {
            new PrintWriter(player.getOutputStream(), true).println(message);
        }
    }

    private static void broadcastBoard() throws IOException {
        String boardState = drawBoard();
        broadcast("Board:\n " + boardState);
    }

    private static boolean checkWin() {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };
        for (int[] pattern : winPatterns) {
            if (!board[pattern[0]].equals(boardState) &&
                    board[pattern[0]].equals(board[pattern[1]]) &&
                    board[pattern[1]].equals(board[pattern[2]])) {
                return true;
            }
        }
        return false;
    }
    public static String drawBoard() {
        StringBuilder totalBoard = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            totalBoard.append(board[i]).append(" ");
            if ((i + 1) % 3 == 0) {
                totalBoard.append("\n");
            }
        }
        return totalBoard.toString();
    }
    private static boolean isDraw() {
        for (String cell : board) {
            if (cell.equals(boardState)) {
                return false;
            }
        }
        return true;
    }
}
