package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class JsonLogger {
    private static String basePath;
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private static final OpLogEntry opLogEntry = new OpLogEntry(new ArrayList<>());
    private static final ProcLogEntry procLogEntry = new ProcLogEntry(new ArrayList<>());
    private static final AgentLogEntry agentLogEntry = new AgentLogEntry(new ArrayList<>());
    private static final VisitorLogEntry visLogEntry = new VisitorLogEntry(new ArrayList<>());

    public static void setBasePath(String path) {
        basePath = path;
    }

    public static void logOperations(long a, long b, long c, Date d, Date e, long f, long g) {
        opLogEntry.operation_log.add(new OpLog(a, b, c, d, e, f, g));
    }
    public static void logProcess(long a, long b, Date c, Date d, ArrayList<Long> e) {
        ProcLog procLog = new ProcLog(a, b, c, d, e);
        procLogEntry.process_log.add(procLog);
    }

    public static void logVisitor(String a, long b) {
        visLogEntry.visitor_log.add(new VisitorLog(a, b));
    }

    public static void logAgent(String text) {
        agentLogEntry.agent_log.add(text);
    }

    public static void saveToFile(String filename, String jstring) {
        try {
            File myObj = new File(basePath + filename);
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        try {
            FileWriter myWriter = new FileWriter(basePath + filename);
            myWriter.write(jstring);
            myWriter.close();
            System.out.println("Saved " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void saveAll() {
        saveToFile("operation_log.txt", gson.toJson(opLogEntry));
        saveToFile("process_log.txt", gson.toJson(procLogEntry));
        saveToFile("agent_log.txt", gson.toJson(agentLogEntry));
        saveToFile("visitor_log.txt", gson.toJson(visLogEntry));
    }

    static class OpLog {
        private long oper_id;
        private long oper_proc;
        private long oper_card;
        private Date oper_started;
        private Date oper_ended;
        private long oper_equip_id;
        private long oper_coocker_id;
        private boolean oper_active = false;

        public OpLog(long i, long p, long c, Date st, Date en, long qi, long ci) {
            oper_id = i;
            oper_proc = p;
            oper_card = c;
            oper_started = st;
            oper_ended = en;
            oper_equip_id = qi;
            oper_coocker_id = ci;
        }
    }
    static class OpLogEntry {
        public ArrayList<OpLog> operation_log;
        public OpLogEntry(ArrayList<OpLog> l) {
            this.operation_log = l;
        }
    }

    static class ProcLog {
        private long proc_id;
        private long ord_dish;
        private Date proc_started;
        private Date proc_ended;
        private boolean proc_active = false;
        private ArrayList<Long> proc_operations;

        public ProcLog(long a, long b, Date c, Date d, ArrayList<Long> e) {
            proc_id = a;
            ord_dish = b;
            proc_started = c;
            proc_ended = d;
            proc_operations = e;
        }
    }

    static class ProcLogEntry {
        public ArrayList<ProcLog> process_log;
        public ProcLogEntry(ArrayList<ProcLog> l) {
            this.process_log = l;
        }
    }

    static class AgentLogEntry {
        public ArrayList<String> agent_log;
        public AgentLogEntry(ArrayList<String> l) {
            this.agent_log = l;
        }
    }

    static class VisitorLog {
        private String name;
        private long money;

        public VisitorLog(String name, long money) {
            this.money = money;
            this.name = name;
        }
    }

    static class VisitorLogEntry {
        public ArrayList<VisitorLog> visitor_log;
        public VisitorLogEntry(ArrayList<VisitorLog> l) {this.visitor_log = l;}
    }
}
