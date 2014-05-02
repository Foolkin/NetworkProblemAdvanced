package anotheria.bootcamp.fileapp.commands.pojos;

import anotheria.bootcamp.fileapp.commands.Constants.Commands;

import java.io.Serializable;

/**
 * Java pojo that contains command from client to server
 *
 * @author Yevgenii Nikonchuk.
 */
public abstract class Command implements Serializable {

    /**
     * Command that will should be send.
     */
    private Commands command;

    /**
     * Command getter.
     *
     * @return command.
     */
    public abstract Commands getType();

}
