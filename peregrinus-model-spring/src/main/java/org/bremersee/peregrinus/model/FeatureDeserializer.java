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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;
import org.springframework.util.Assert;

/**
 * @author Christian Bremer
 */
public class FeatureDeserializer extends StdDeserializer<Feature> {

  @SuppressWarnings("WeakerAccess")
  public FeatureDeserializer() {
    super(Feature.class);
  }

  @Override
  public Feature deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {

    String id = null;
    double[] bbox = null;
    Geometry geometry = null;
    FeatureProperties<? extends FeatureSettings> properties = null;
    Map<String, Object> unknown = new LinkedHashMap<>();
    JsonToken currentToken;
    while ((currentToken = jp.nextValue()) != null) {
      if (JsonToken.VALUE_STRING.equals(currentToken)) {
        if ("type".equals(jp.getCurrentName())) {
          Assert.isTrue(
              "Feature".equalsIgnoreCase(jp.getText()), "Type must be 'Feature'.");
        } else if ("id".equals(jp.getCurrentName())) {
          id = jp.getText();
        } else {
          unknown.put(jp.getCurrentName(), jp.getText());
        }
      } else if (JsonToken.START_OBJECT.equals(currentToken)) {
        if ("geometry".equals(jp.getCurrentName())) {
          geometry = jp.getCodec().readValue(jp, Geometry.class);
        } else if ("properties".equals(jp.getCurrentName())) {
          //noinspection unchecked
          properties = jp.getCodec().readValue(jp, FeatureProperties.class);
        } else {
          unknown.put(jp.getCurrentName(), jp.getCodec().readValue(jp, Map.class));
        }
      } else if (JsonToken.START_ARRAY.equals(currentToken)) {
        if ("bbox".equals(jp.getCurrentName())) {
          bbox = jp.getCodec().readValue(jp, double[].class);
        } else {
          unknown.put(jp.getCurrentName(), jp.getCodec().readValue(jp, List.class));
        }
      } else if (JsonToken.VALUE_FALSE.equals(currentToken)
          || JsonToken.VALUE_TRUE.equals(currentToken)) {
        unknown.put(jp.getCurrentName(), jp.getBooleanValue());
      } else if (JsonToken.VALUE_NUMBER_FLOAT.equals(currentToken)) {
        unknown.put(jp.getCurrentName(), jp.getDecimalValue());
      } else if (JsonToken.VALUE_NUMBER_INT.equals(currentToken)) {
        unknown.put(jp.getCurrentName(), jp.getBigIntegerValue());
      } else if (JsonToken.END_OBJECT.equals(currentToken)) {
        break;
      }
    }
    return findFeature(properties, geometry, bbox, id, unknown);
  }

  private Feature findFeature(
      FeatureProperties<? extends FeatureSettings> properties,
      Geometry geometry,
      double[] bbox,
      String id,
      Map<String, Object> unknown) throws JsonProcessingException {

    Feature feature = null;
    if (properties instanceof RteProperties
        && (geometry == null || geometry instanceof MultiLineString)) {
      feature = new Rte();
    } else if (properties instanceof RtePtProperties
        && (geometry == null || geometry instanceof Point)) {
      feature = new RtePt();
    } else if (properties instanceof TrkProperties && (geometry == null
        || geometry instanceof MultiLineString)) {
      feature = new Trk();
    } else if (properties instanceof WptProperties && (geometry == null
        || geometry instanceof Point)) {
      feature = new Wpt();
    }
    if (feature == null) {
      throw new FeatureParserException("Illegal feature.");
    }
    feature.setBbox(bbox);
    feature.setGeometry(geometry);
    feature.setId(id);
    feature.setProperties(properties);
    feature.unknown(unknown);
    return feature;
  }

  public static class FeatureParserException extends JsonProcessingException {

    FeatureParserException(String msg) {
      super(msg);
    }
  }

  /*
  public static void main(String[] args) throws Exception {
    ObjectMapper om = new ObjectMapper();
    om.registerModules(
        new GeoJsonObjectMapperModule(),
        new PeregrinusObjectMapperModule(),
        new Jdk8Module(),
        new JavaTimeModule());
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    Point point = GeometryUtils.createPoint(10.5, 52.3);
    Wpt wpt = Wpt.builder()
        .id("123")
        .geometry(point)
        .bbox(GeometryUtils.getBoundingBox(point))
        .properties(WptProperties.builder().build())
        .build();

    String json = om.writeValueAsString(wpt);
    json = "{\"type\":\"Feature\",\"arr\": [\"str\", \"zwei\"],\"id\":\"123\",\"bbox\":[10.5,52.3,10.5,52.3],\"geometry\":{\"type\":\"Point\",\"coordinates\":[10.5,52.3]},\"properties\":{\"_type\":\"Wpt\",\"acl\":null,\"created\":null,\"createdBy\":null,\"modified\":null,\"modifiedBy\":null,\"name\":null,\"plainTextDescription\":null,\"markdownDescription\":null,\"internalComments\":null,\"links\":[],\"startTime\":null,\"stopTime\":null,\"settings\":null,\"internalType\":null,\"ele\":null,\"address\":null,\"phoneNumbers\":[]}}";
    System.out.println(json);
    System.out.println("---");

    Feature f = om.readValue(json, Feature.class);
    System.out.println(f);
    System.out.println("---");
    System.out.println(f.unknown());
  }
  */

}
