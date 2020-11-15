package gateways.loaders;

import gateways.savers.Saver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for a Loader for some entity type T
 *
 * @param <T> the type of object being loaded
 */
public abstract class Loader<T> {

    /**
     * Loads all objects saved in a text file with filepath <path>
     *
     * @param path - the filepath where object information is stored
     * @return a List of objects of type T, the objects loaded from the file
     * @throws IOException - file does not exist at path
     */
    public final List<T> loadAll(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        List<T> objs = new ArrayList<>();

        String line;
        String[] info;

        while ((line = reader.readLine()) != null) {
            info = line.split(Saver.parameterSeparationChar + "");
            objs.add(createInstance(info));
        }
        return objs;
    }


    /**
     * Creates an instance of T based off some parameters
     *
     * @param parameters parameters for creating an object of type T as strings
     * @return a T - the object created with the passed in parameters
     */
    abstract T createInstance(String[] parameters);
}
