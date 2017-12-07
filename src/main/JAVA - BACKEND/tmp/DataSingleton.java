package tmp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Account;
import entity.Token;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class DataSingleton {
    private static DataSingleton instance = new DataSingleton();
    private DataSingleton() {
    }

    public static DataSingleton getInstance() {
        return instance;
    }

    public static Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create();
    }
}
