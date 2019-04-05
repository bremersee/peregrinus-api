/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.peregrinus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.common.model.HttpLanguageTag;
import org.bremersee.common.model.TwoLetterCountryCodes;

/**
 * @author Christian Bremer
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class NominatimGeocodeQueryRequest extends GeocodeQueryRequest {

  /**
   * Preferred language order for showing search results, overrides the value specified in the
   * "Accept-Language" HTTP header. Either uses standard rfc2616 accept-language string or a simple
   * comma separated list of language codes.
   *
   * <p>Query parameter name is {@code accept-language}.
   */
  //private String acceptLanguage = "en";

  /**
   * Include a breakdown of the address into elements.
   *
   * <p>addressDetails=[0|1]
   */
  private Boolean addressDetails = Boolean.TRUE;

  /**
   * If you are making large numbers of request please include a valid email address or
   * alternatively include your email address as part of the User-Agent string. This information
   * will be kept confidential and only used to contact you in the event of a problem, see Usage
   * Policy for more details.
   *
   * <p>email={@literal <valid email address>}
   */
  @Setter
  private String email;

  /**
   * Output geometry of results in geojson format.
   *
   * <p>polygon_geojson=1
   */
  private Boolean polygon = Boolean.TRUE;

  /**
   * Include additional information in the result if available, e.g. wikipedia link, opening hours.
   *
   * <p>extraTags=1
   */
  private Boolean extraTags = Boolean.TRUE;

  /**
   * Include a list of alternative names in the results. These may include language variants,
   * references, operator and brand.
   *
   * <p>nameDetails=1
   */
  private Boolean nameDetails = Boolean.TRUE;

  @Builder
  public NominatimGeocodeQueryRequest(
      HttpLanguageTag language,
      double[] boundingBox,
      TwoLetterCountryCodes countryCodes,
      Integer limit,
      String query,
      Boolean addressDetails,
      String email,
      Boolean polygon,
      Boolean extraTags,
      Boolean nameDetails) {

    super(language, boundingBox, countryCodes, limit, query);
    setAddressDetails(addressDetails);
    setEmail(email);
    setPolygon(polygon);
    setExtraTags(extraTags);
    setNameDetails(nameDetails);
  }

  @JsonProperty("addressDetails")
  public void setAddressDetails(Boolean addressDetails) {
    this.addressDetails = !Boolean.FALSE.equals(addressDetails);
  }

  @JsonProperty("polygon")
  public void setPolygon(Boolean polygon) {
    this.polygon = !Boolean.FALSE.equals(polygon);
  }

  @JsonProperty("extraTags")
  public void setExtraTags(Boolean extraTags) {
    this.extraTags = !Boolean.FALSE.equals(extraTags);
  }

  @JsonProperty("nameDetails")
  public void setNameDetails(Boolean nameDetails) {
    this.nameDetails = !Boolean.FALSE.equals(nameDetails);
  }
}
