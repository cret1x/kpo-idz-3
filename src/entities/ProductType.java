package entities;

import java.io.Serializable;

public record ProductType(long typeId, String name, boolean isFood) implements Serializable {
}
