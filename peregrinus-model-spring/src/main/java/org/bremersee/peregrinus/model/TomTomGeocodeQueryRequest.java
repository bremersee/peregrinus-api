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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bremersee.common.model.HttpLanguageTag;
import org.bremersee.common.model.TwoLetterCountryCodes;
import org.locationtech.jts.geom.Point;

/**
 * @author Christian Bremer
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class TomTomGeocodeQueryRequest extends GeocodeQueryRequest {

  /**
   * Starting offset of the returned results within the full result set.
   */
  private Integer offset;

  /**
   * Comma separated string of country codes. This will limit the search to the specified
   * countries.
   */
  //private List<Locale> countrySet;

  /**
   * Latitude and longitude where results should be biased. NOTE: supplying a lat/lon without a
   * radius will return search results biased to that point.
   */
  //private LatLonAware latLon;

  /**
   * If radius and position are set, the results will be constrained to the defined area. The radius
   * parameter is specified in meters.
   */
  //private Integer radius; // calculate from boundingBox

  /**
   * Top left and bottom right position of the bounding box.
   */
  //private BoundingBox boundingBox; // calculate from boundingBox

  @Builder
  @SuppressWarnings("unused")
  public TomTomGeocodeQueryRequest(
      HttpLanguageTag language,
      double[] boundingBox,
      TwoLetterCountryCodes countryCodes,
      Integer limit,
      String query,
      Integer offset) {

    super(language, boundingBox, countryCodes, limit, query);
    setOffset(offset);
  }

  @JsonProperty(value = "offset")
  public void setOffset(Integer offset) {
    if (offset != null && offset >= 0) {
      this.offset = offset;
    }
  }

  @ApiModelProperty(hidden = true)
  @JsonIgnore
  public Point getCenter() {
    // TODO
    return null;
  }

  @ApiModelProperty(hidden = true)
  @JsonIgnore
  public Integer getRadius() {
    // TODO
    return null;
  }

}
