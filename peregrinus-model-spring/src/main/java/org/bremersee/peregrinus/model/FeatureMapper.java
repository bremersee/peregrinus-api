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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * @author Christian Bremer
 */
@Validated
public abstract class FeatureMapper {

  private FeatureMapper() {
  }

  public static @NotNull List<Feature> ensureCorrectModels(
      @Nullable Collection<? extends Feature> features) {
    if (features == null) {
      return Collections.emptyList();
    }
    return features
        .stream()
        .filter(Objects::nonNull)
        .filter(feature -> feature.getGeometry() != null)
        .filter(FeatureMapper::isValidFeature)
        .map(FeatureMapper::ensureCorrectModel)
        .collect(Collectors.toList());
  }

  private static boolean isValidFeature(@Nullable Feature feature) {
    if (feature == null
        || feature.getGeometry() == null
        || feature.getProperties() == null) {
      return false;
    }
    Geometry geometry = feature.getGeometry();
    FeatureProperties properties = feature.getProperties();
    return (properties instanceof RteProperties && geometry instanceof MultiLineString)
        || (properties instanceof TrkProperties && geometry instanceof MultiLineString)
        || (properties instanceof WptProperties && geometry instanceof Point);
  }

  @Nullable
  public static Feature ensureCorrectModel(@Nullable Feature feature) {
    if (!isValidFeature(feature)) {
      return null;
    }
    final FeatureProperties properties = feature.getProperties();
    if (properties instanceof RteProperties
        && !(feature instanceof Rte)) {
      return new Rte(
          feature.getId(),
          (MultiLineString) feature.getGeometry(),
          feature.getBbox(),
          (RteProperties) feature.getProperties());
    }
    if (properties instanceof TrkProperties
        && !(feature instanceof Trk)) {
      return new Trk(
          feature.getId(),
          (MultiLineString) feature.getGeometry(),
          feature.getBbox(),
          (TrkProperties) feature.getProperties());
    }
    if (properties instanceof WptProperties
        && !(feature instanceof Wpt)) {
      return new Wpt(
          feature.getId(),
          (Point) feature.getGeometry(),
          feature.getBbox(),
          (WptProperties) feature.getProperties());
    }
    return feature;
  }

}
