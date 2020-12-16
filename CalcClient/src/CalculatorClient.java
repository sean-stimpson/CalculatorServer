
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * CalculatorClient connects to Calculator server through port 5000.
 * It then prompts the user for two numbers to add, sends those numbers
 * to the Calculator server and prints out the result when the server sends
 * it back. Entering -999 will close the Client instance.
 */
public class CalculatorClient {
    public static void main(String[] args) {
        //Try block attempts connection to port 5000 through local host.
        try(Socket socket = new Socket("localhost",5000)){
            socket.setSoTimeout(5000);
            // Creates output stream from server
            OutputStream output = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(output);
            //User input scanner
            Scanner scanner = new Scanner(System.in);
            //User input variables
            Integer first;
            Integer second;
            //Infinite loop until user enters -999
            do {
                System.out.println("Enter first number to add or -999 to end");
                first = scanner.nextInt();

                if(first == -999) {
                    break;
                }

                System.out.println("Enter second number to add");
                second = scanner.nextInt();

                out.writeInt(first);
                out.writeInt(second);

                InputStream in = socket.getInputStream();
                DataInputStream serverResponse = new DataInputStream(in);
                System.out.println("= " + serverResponse.read());
            } while (first != -999);

        }

        catch(SocketTimeoutException e){
            System.out.println("The socket timed out");
        }

        catch(IOException e){
            System.out.println("Client Error: "+ e.getMessage());
        }
    }
}
