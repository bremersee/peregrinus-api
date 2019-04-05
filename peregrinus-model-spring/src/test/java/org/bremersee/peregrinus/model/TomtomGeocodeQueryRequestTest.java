package org.bremersee.peregrinus.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bremersee.common.model.HttpLanguageTag;
import org.bremersee.common.model.TwoLetterCountryCode;
import org.bremersee.common.model.TwoLetterCountryCodes;
import org.junit.Test;

/**
 * @author Christian Bremer
 */
public class TomtomGeocodeQueryRequestTest {

  @Test
  public void readWrite() throws Exception {
    ObjectMapper om = new ObjectMapper();
    TwoLetterCountryCodes countryCodes = new TwoLetterCountryCodes();
    countryCodes.add(TwoLetterCountryCode.DE);
    TomTomGeocodeQueryRequest r = TomTomGeocodeQueryRequest.builder()
        .boundingBox(new double[] {1., 2., 4., 5.})
        .countryCodes(countryCodes)
        .language(HttpLanguageTag.DE)
        .limit(3)
        .query("Hallo")
        .offset(3)
        .build();

    String json = om.writerWithDefaultPrettyPrinter().writeValueAsString(r);
    System.out.println(json);

    GeocodeQueryRequest qr = om.readValue(json, GeocodeQueryRequest.class);
    System.out.println(qr);
  }

}
