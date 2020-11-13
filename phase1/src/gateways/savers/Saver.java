package gateways.savers;

import java.io.*;

public abstract class Saver<T> {
    public static final char parameterSeparationChar = '\u02AC';
    protected FileWriter output;

    public Saver(String filepath) throws IOException {
        output = new FileWriter(filepath);
    }

    public final void saveAll(Iterable<T> objects) throws IOException {
        for(T t: objects) {
            save(t);
        }
    }

    public final void close() throws IOException {
        output.close();
    }

    abstract void save(T t) throws IOException;
}
