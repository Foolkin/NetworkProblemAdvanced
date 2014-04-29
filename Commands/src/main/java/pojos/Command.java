package pojos;

import Constants.Commands;

import java.io.Serializable;

/**
 * Java pojo that contains command from client to server
 *
 *  @author Yevgenii Nikonchuk.
 */
public class Command implements Serializable{

    /**
     * pojos.Command that will should be send.
     */
    private Commands command;

    /**
     * File name that will be transfer.
     */
    private String fileName;

    /**
     * Constructor.
     */
    public Command(){}

    /**
     * pojos.Command getter.
     * @return command.
     */
    public Commands getCommandName(){
        return command;
    }

    /**
     * pojos.Command setter.
     * @param command command.
     */
    public void setCommandName(Commands command){
        this.command = command;
    }

    /**
     * fileName getter.
     * @return fileName.
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * fileName setter
     * @param fileName file name.
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command=" + command +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
