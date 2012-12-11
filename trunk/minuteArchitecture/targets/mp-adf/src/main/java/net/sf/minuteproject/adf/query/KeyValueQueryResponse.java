package eu.adf.fwk.query;

import eu.adf.fwk.query.KeyValueDTO;

import java.util.ArrayList;
import java.util.List;

public class KeyValueQueryResponse extends QueryResponse {
    
    protected List<KeyValueDTO> keyValues;

        /**
     * Gets the value of the directive property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directive property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     * getKeyValueDTOs().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValueDTO}
     *
     *
     */
        public List<KeyValueDTO> getKeyValueDTOs() {
            if (keyValues == null) {
                keyValues = new ArrayList<KeyValueDTO>();
            }
            return this.keyValues;
        }
}
