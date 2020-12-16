import java.io.*;
import java.net.Socket;

/**
 * Creates Calculator threads.
 * Receives client input, adds the two numbers, then outputs it to the client.
 */
public class Calculator extends Thread {
    private final Socket socket;

    /**
     * Creates new Calculator. Assigns socket variable.
     * @param socket Socket to be assigned
     */
    public Calculator(Socket socket){
        this.socket = socket;
    }

    /**
     * Overrides run method for the thread. New run method accepts
     * two Integers from the client, adds them together, and returns the result.
     * When method receives -999 as input it will close the thread;
     */
    @Override
    public void run() {
        try{
            //Creating in and output streams.
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            //Infinite loop until client enters -999
            while(true){
                Integer first = in.readInt();
                System.out.println("Received client input: " + first);

                //Breakout point to close thread.
                if(first == -999){
                    System.out.println("Client Disconnected");
                    throw new NullPointerException();
                }

                Integer second = in.readInt();
                System.out.println("Received client input: " + second);

                //Output to client
                out.write(first + second);

                //Making thread sleep to play nicely with other threads.
                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException e){
                    System.out.println("Thread interrupted");
                }

            }

        }
        catch(IOException e){
            //If message is null most likely the client disconnected
            if(e.getMessage() == null) {
                System.out.println("A client has disconnected from server");
            } else {
                System.out.println("Error: " + e.getMessage());
            }
        }
        finally {
            try{
                socket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
