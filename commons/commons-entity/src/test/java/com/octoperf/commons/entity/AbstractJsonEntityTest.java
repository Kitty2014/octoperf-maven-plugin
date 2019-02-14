package com.octoperf.commons.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.google.common.testing.NullPointerTester;
import com.octoperf.tools.jackson.mapper.JacksonConfig;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

import static com.google.common.testing.NullPointerTester.Visibility.PACKAGE;
import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JacksonConfig.class)
public abstract class AbstractJsonEntityTest<T> {

  @Autowired
  private JsonMapperService jsonService;

  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final T dto = entity();

    final String json = jsonService.toJson(dto);
    final T fromJson = jsonService.fromJson(json, typeReference());
    assertEquals(dto, fromJson);
  }

  @Test
  public void shouldPassNPETester() {
    final NullPointerTester tester = new NullPointerTester();
    getDefaults().forEach(tester::setDefault);
    tester.testConstructors(entity().getClass(), PACKAGE);
  }

  @Test
  public void shouldPassEqualsVerifier() {
    EqualsVerifier.forClass(entity().getClass()).verify();
  }

  @Test
  public void shouldHaveNonStandardToString() {
    final T entity = entity();
    assertTrue(entity.toString().startsWith(entity.getClass().getSimpleName() + "("));
  }

  @Test
  public void shouldCreate() {
    assertNotNull(entity());
  }

  protected Map<Class, Object> getDefaults() {
    return ImmutableMap.of();
  }

  protected abstract T entity();

  protected abstract TypeReference<T> typeReference();

}
