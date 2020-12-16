import java.io.IOException;
import java.net.ServerSocket;

/**
 * ServerStartup listens to port 5000 and creates Calculator instances
 * when clients connect.
 */
public class ServerStartup {
    /**
     * Main method to start server socket and create calculator.
     * The method listens to port 5000 and creates new Calculator instances
     * whenever a client connects.
     * @param args
     */
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server Running");
            while(true) {
                new Calculator(serverSocket.accept()).start();
                System.out.println("Client Connected");
            }
        }
        catch(IOException e){
            System.out.println("Server exception " + e.getMessage());
        }

    }
}
