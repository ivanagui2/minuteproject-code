package eu.adf.fwk.query;

import eu.adf.fwk.query.ReferenceDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ADF does not go well with templatization
 */
public class ReferenceListQueryResponse extends QueryResponse<ReferenceDTO> {
  protected List<ReferenceDTO> referenceDTOs;


  public List<ReferenceDTO> getReferenceDTOs() {
    if (referenceDTOs == null) {
      referenceDTOs = new ArrayList<ReferenceDTO>();
    }
    return referenceDTOs;
  }

}
