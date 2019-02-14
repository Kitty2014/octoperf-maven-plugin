package com.octoperf.entity.analysis.report.graph;

import com.octoperf.tools.jackson.mapper.JacksonConfig;
import com.octoperf.tools.jackson.mapper.JsonMapperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link MetricValues}.
 * 
 * @author jerome
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JacksonConfig.class})
public class MetricValuesJacksonTest {

  @Autowired
  private JsonMapperService jsonService;
  
  @Test
  public void shouldJacksonSerializeCorrectly() throws IOException {
    final MetricValues bean = MetricValuesTest.newInstance();
    
    final String json = jsonService.toJson(bean);
    final MetricValues fromJson = jsonService.fromJson(json, MetricValues.class);
    assertEquals(bean, fromJson);
  }
}
