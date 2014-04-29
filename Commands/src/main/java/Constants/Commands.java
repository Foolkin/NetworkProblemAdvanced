package Constants;

/**
 * This class contains set of command for server.
 *
 * @author Yevgenii Nikonchuk.
 */
public enum Commands {
    /**
     * DIR - Returns a list of files.
     */
    DIR,
    /**
     * PUT - Uploads a file with given name to the server.
     */
    PUT,
    /**
     * GET - Downloads a file with given name from the server.
     */
    GET,
    /**
     * BYE - Ends session between client end server.
     */
    BYE;
}
