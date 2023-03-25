package entities;

import java.io.Serializable;

public class Equipment implements Serializable {
    private long equip_type;
    private String equip_name;
    private boolean equip_active;

    public boolean isActive() {
        return equip_active;
    }

    public long type() {
        return equip_type;
    }

    public String name() {
        return equip_name;
    }
}
