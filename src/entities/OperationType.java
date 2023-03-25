package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class OperationType implements Serializable {
    private long oper_type_id;
    private String oper_type_name;

    public long type() {
        return oper_type_id;
    }

    public String name() {
        return oper_type_name;
    }
}
