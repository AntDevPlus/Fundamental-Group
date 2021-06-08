package plus.antdev.fundamentalgroup.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializationManager {

    private Gson gson;

    public SerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }
}