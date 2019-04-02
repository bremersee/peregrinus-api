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

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.common.model.AccessControlList;
import org.bremersee.common.model.Address;
import org.bremersee.common.model.Link;
import org.bremersee.common.model.PhoneNumber;

/**
 * @author Christian Bremer
 */
@ApiModel(
    value = "PtProperties",
    description = "Common properties of a point.",
    parent = FeatureProperties.class,
    subTypes = {
        WptProperties.class,
        RtePtProperties.class
    })
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PtProperties<S extends PtSettings> extends FeatureProperties<S> {

  private String internalType; // photo, video or not // TODO

  /**
   * Elevation in meters
   */
  private BigDecimal ele;

  /**
   * Address
   */
  private Address address; // index?

  /**
   * Phone numbers
   */
  private List<PhoneNumber> phoneNumbers;

  public PtProperties() {
    phoneNumbers = new ArrayList<>();
  }

  public PtProperties(
      AccessControlList acl,
      OffsetDateTime created,
      String createdBy,
      OffsetDateTime modified,
      String modifiedBy,
      String name,
      String plainTextDescription,
      String markdownDescription,
      String internalComments,
      List<Link> links,
      OffsetDateTime startTime,
      OffsetDateTime stopTime,
      S settings,
      String internalType,
      BigDecimal ele,
      Address address,
      List<PhoneNumber> phoneNumbers) {

    super(acl, created, createdBy, modified, modifiedBy, name, plainTextDescription,
        markdownDescription, internalComments, links, startTime, stopTime, settings);
    setInternalType(internalType);
    setEle(ele);
    setAddress(address);
    setPhoneNumbers(phoneNumbers);
  }

  public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
    if (phoneNumbers == null) {
      this.phoneNumbers = new ArrayList<>();
    } else {
      this.phoneNumbers = phoneNumbers;
    }
  }
}
