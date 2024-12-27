# Tic-Tac-Toe Multiplayer Game Using Sockets in Java

## Overview
This is a simple multiplayer Tic-Tac-Toe game implemented in Java using sockets for client-server communication. The game supports two players who connect to a server, take turns making moves, and receive updates about the game state in real-time.
## How to Run the Game

### Prerequisites
- Java Development Kit (JDK) installed.
- Basic knowledge of Java programming.

### Steps
1. **Compile the Code**:
   Open a terminal or command prompt and compile the server and client files using:
   ```
   javac TicTacToeServer.java TicTacToeClient.java
   ```

2. **Start the Server**:
   Run the server first to allow clients to connect:
   ```
   java com.quiz.TicTacToeServer
   ```
   The server will start listening on port `12345`.

3. **Start the Clients**:
   Open two separate terminals or command prompts and run the client for each player:
   ```
   java com.quiz.TicTacToeClient
   ```

4. **Gameplay**:
   - Each client will receive a message indicating their player number (Player 1 or Player 2).
   - Players take turns entering a position (1-9) to place their mark (`X` for Player 1, `O` for Player 2).
   - The server validates moves, updates the board, and notifies players of the game state.

### Example Gameplay
https://github.com/user-attachments/assets/771f822a-4391-449b-9ff4-8299e55cae0f



## Author
This Tic-Tac-Toe implementation was created as a simple demonstration of socket programming in Java.


