package anotheria.bootcamp.fileapp.commands.pojos;

import anotheria.bootcamp.fileapp.commands.Constants.Commands;

/**
 * Created by admin on 4/30/14.
 */
public class ByeCommand extends Command {
    @Override
    public Commands getType() {
        return Commands.BYE;
    }
}
