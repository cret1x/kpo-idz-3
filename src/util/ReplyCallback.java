package util;

import java.io.Serializable;

public interface ReplyCallback<T> {
    void callback (T data);
}
