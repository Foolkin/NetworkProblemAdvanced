package anotheria.bootcamp.fileapp.commands.pojos;

import anotheria.bootcamp.fileapp.commands.Constants.Commands;

/**
 * Created by admin on 4/30/14.
 */
public class GetCommand extends Command{

    /**
     * File name that will be transfer.
     */
    private String fileName;

    /**
     * fileName getter.
     *
     * @return fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * fileName setter
     *
     * @param fileName file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Commands getType() {
        return Commands.GET;
    }
}
