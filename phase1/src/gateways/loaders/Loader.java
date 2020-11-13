package gateways.loaders;

import entities.Event;
import gateways.savers.Saver;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Loader<T> {

    public final List<T> loadAll(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        List<T> objs = new ArrayList<>();

        String line;
        String[] info;

        while((line = reader.readLine()) != null) {
            info = line.split(Saver.parameterSeparationChar+"");
            objs.add(createInstance(info));
        }
        return objs;
    }

    abstract T createInstance(String[] parameters);
}
