package gateways.savers;

import java.io.FileWriter;
import java.io.IOException;

/**
 * A Saver for some type of object
 *
 * @param <T> the type of object being saved
 */
public abstract class Saver<T> {
    /**
     * The character used to separate parameter strings when saving
     * object information
     */
    public static final char parameterSeparationChar = '\u02AC';
    protected FileWriter output;

    /**
     * Creates a Saver that saves at the given file path
     *
     * @param filepath - the path of the file to be used to save
     *                 object information
     * @throws IOException if the file does not exist at the filepath
     */
    public Saver(String filepath) throws IOException {
        output = new FileWriter(filepath);
    }

    /**
     * Saves a number of objects
     *
     * @param objects - the Iterable object containing the objects to be saved
     * @throws IOException if the file does not exist at the filepath
     */
    public final void saveAll(Iterable<T> objects) throws IOException {
        for (T t : objects) {
            save(t);
        }
    }

    /**
     * Save an individual object
     *
     * @param t - the object to be saved
     * @throws IOException if the file does not exist at the filepath
     */
    abstract void save(T t) throws IOException;
}
