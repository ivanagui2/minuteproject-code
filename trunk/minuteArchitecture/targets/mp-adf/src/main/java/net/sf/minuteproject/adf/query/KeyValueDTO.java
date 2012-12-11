package eu.adf.fwk.query;

import java.io.Serializable;

public class KeyValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String key;
    private String value;

    public KeyValueDTO() {
    }

    public KeyValueDTO(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
