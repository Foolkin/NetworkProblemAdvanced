package listeners;

import Constants.Commands;
import session.Session;
import pojos.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;


/**
 * This class receive commands from client and run required action
 */
public class CommandListener implements Runnable {

    /**
     * Socket that used to send command to the server.
     */
    private Socket commandSocket;

    /**
     * Session object.
     */
    private Session session;

    /**
     * Constructor. Initialize commandSocket and session objects.
     * @param commandSocket Socket that used to send command to the server.
     * @param session session.
     */
    public CommandListener(Socket commandSocket, Session session){
        this.commandSocket = commandSocket;
        this.session = session;
    }

    @Override
    public void run() {
        try (InputStream inStream = commandSocket.getInputStream();
             ObjectInputStream objectIn = new ObjectInputStream(inStream);
        ){
            boolean done = false;

            while (!done) {
                Command command = (Command) objectIn.readObject();

                if(command.getCommandName() == Commands.GET && !session.fileExist(command.getFileName())) {
                    continue;
                }
                session.setCommand(command);
                session.notifyDataListener();

                if(command.getCommandName() == Commands.BYE) {
                    done = true;
                    commandSocket.shutdownInput();
                }
            }

        } catch (IOException e){
            throw new RuntimeException("Can't get input stream from command socket", e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Can't cast object from socket to Command", e);
        }
    }
}
