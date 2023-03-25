package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonLogger {
    private static String basePath;
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private static OpLogEntry opLogEntry = new OpLogEntry(new ArrayList<>());

    public static void setBasePath(String path) {
        basePath = path;
    }

    public static void logOperations(long i, long p, long c) {
        OpLog opLog = new OpLog(i, p, c);
        opLogEntry.operation_log.add(opLog);
    }

    public static void saveAll() {
        var jstr = gson.toJson(opLogEntry);
        try {
            File myObj = new File(basePath + "operation_log.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(basePath + "operation_log.txt");
            myWriter.write(jstr);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static class OpLog {
        private long oper_id;
        private long oper_proc;
        private long oper_card;

        private boolean oper_active = false;

        public OpLog(long i, long p, long c) {
            oper_id = i;
            oper_proc = p;
            oper_card = c;
        }
    }

    static class OpLogEntry {
        public ArrayList<OpLog> operation_log;
        public OpLogEntry(ArrayList<OpLog> l) {
            this.operation_log = l;
        }
    }
}
