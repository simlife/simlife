/*
 * Copyright 2016-2018 the original author or authors from the Simlife project.
 *
 * This file is part of the Simlife project, see https://www.simlife.tech/
 * for more information.
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

package io.github.simlife.service.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IntegerFilterTest {

    private IntegerFilter filter;

    private Integer value = 42;

    @Before
    public void setup() {
        filter = new IntegerFilter();
    }

    @Test
    public void testConstructor() {
        assertThat(filter.getEquals()).isNull();
        assertThat(filter.getGreaterThan()).isNull();
        assertThat(filter.getGreaterOrEqualThan()).isNull();
        assertThat(filter.getLessThan()).isNull();
        assertThat(filter.getLessOrEqualThan()).isNull();
        assertThat(filter.getSpecified()).isNull();
        assertThat(filter.getIn()).isNull();
        assertThat(filter.toString()).isEqualTo("IntegerFilter []");
    }

    @Test
    public void testSetEquals() {
        Filter<Integer> chain = filter.setEquals(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getEquals()).isEqualTo(value);
    }

    @Test
    public void testSetLessThan() {
        Filter<Integer> chain = filter.setLessThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getLessThan()).isEqualTo(value);
    }

    @Test
    public void testSetLessOrEqualThan() {
        Filter<Integer> chain = filter.setLessOrEqualThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getLessOrEqualThan()).isEqualTo(value);
    }

    @Test
    public void testSetGreaterThan() {
        Filter<Integer> chain = filter.setGreaterThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getGreaterThan()).isEqualTo(value);
    }

    @Test
    public void testSetGreaterOrEqualThan() {
        Filter<Integer> chain = filter.setGreaterOrEqualThan(value);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getGreaterOrEqualThan()).isEqualTo(value);
    }

    @Test
    public void testSetSpecified() {
        Filter<Integer> chain = filter.setSpecified(true);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getSpecified()).isEqualTo(true);
    }

    @Test
    public void testSetIn() {
        List<Integer> list = new LinkedList<>();
        Filter<Integer> chain = filter.setIn(list);
        assertThat(chain).isEqualTo(filter);
        assertThat(filter.getIn()).isEqualTo(list);
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setLessThan(value);
        filter.setLessOrEqualThan(value);
        filter.setGreaterThan(value);
        filter.setGreaterOrEqualThan(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        String str = value.toString();
        assertThat(filter.toString()).isEqualTo("IntegerFilter "
            + "[greaterThan=" + str + ", greaterOrEqualThan=" + str + ", lessThan=" + str + ", "
            + "lessOrEqualThan=" + str + ", equals=" + str + ", specified=true, in=[]]");
    }
}
