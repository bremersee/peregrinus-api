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

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Christian Bremer
 */
@Getter
@Setter
public class RteCalculationRequest {

  private List<Pt> rtePts;

  private String language; // TODO move to RteCalculationProperties after renaming in service

  private OffsetDateTime time = OffsetDateTime.now(Clock.systemUTC());

  private Boolean timeIsDepartureTime = Boolean.TRUE;

}
