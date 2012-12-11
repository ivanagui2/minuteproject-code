package eu.adf.fwk.query;

import java.io.Serializable;

/**
 * Used in comboboxes where key and internationalized representation of the object is needed.
 */
public class ReferenceDTO implements Serializable {

    private static final long serialVersionUID = 2544808066485664403L;

    private String key;
    private transient String value;

    public ReferenceDTO() {

    }

    public ReferenceDTO(String key) {
        setKey(key);

    }

    public ReferenceDTO(String key, String value) {
        setKey(key);
        setValue(value);
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


  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ReferenceDTO)) {
      return false;
    }
    final ReferenceDTO other = (ReferenceDTO)object;
    if (!(key == null ? other.key == null : key.equals(other.key))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int PRIME = 37;
    int result = 1;
    result = PRIME * result + ((key == null) ? 0 : key.hashCode());
    return result;
  }
}
