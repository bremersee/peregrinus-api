/*
 * Copyright 2018 the original author or authors.
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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import org.bremersee.geojson.AbstractGeoJsonFeature;
import org.bremersee.geojson.GeometryDeserializer;
import org.bremersee.geojson.GeometrySerializer;
import org.bremersee.geojson.utils.GeometryUtils;
import org.locationtech.jts.geom.Geometry;

/**
 * @author Christian Bremer
 */
@ApiModel(value = "Feature", description = "A GeoJSON feature with well known properties.")
@NoArgsConstructor
@SuppressWarnings("WeakerAccess")
public abstract class Feature
    extends AbstractGeoJsonFeature<Geometry, FeatureProperties<? extends FeatureSettings>> {

  public static final String WPT_TYPE = "Wpt";

  public static final String TRK_TYPE = "Trk";

  public static final String RTE_TYPE = "Rte";

  public static final String RTE_PT_TYPE = "RtePt";

  @JsonIgnore
  private String id;

  @JsonIgnore
  private Geometry geometry;

  public Feature(
      String id,
      Geometry geometry,
      double[] bbox,
      FeatureProperties<? extends FeatureSettings> properties) {
    setId(id);
    setGeometry(geometry);
    setBbox(bbox);
    setProperties(properties);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @ApiModelProperty(value = "GeoJSON", dataType = "org.bremersee.geojson.model.Geometry")
  @JsonSerialize(using = GeometrySerializer.class)
  @Override
  public Geometry getGeometry() {
    return geometry;
  }

  @ApiModelProperty(value = "GeoJSON", dataType = "org.bremersee.geojson.model.Geometry")
  @JsonDeserialize(using = GeometryDeserializer.class)
  @Override
  public void setGeometry(final Geometry geometry) {
    this.geometry = geometry;
  }

  @Override
  protected boolean equals(final Geometry g1, final Object g2) {
    if (g1 == g2) {
      return true;
    } else if (g1 != null && g2 instanceof Geometry) {
      return GeometryUtils.equals(g1, (Geometry) g2);
    } else {
      return false;
    }
  }

}
