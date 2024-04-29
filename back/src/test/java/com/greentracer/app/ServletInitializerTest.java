package com.greentracer.app;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.mockito.Mockito.*;

public class ServletInitializerTest {

    @Test
    public void configure_setsGreenTracerApplicationAsSource() {
        // Arrange
        ServletInitializer servletInitializer = new ServletInitializer();
        SpringApplicationBuilder applicationBuilder = mock(SpringApplicationBuilder.class);
        when(applicationBuilder.sources(GreenTracerApplication.class)).thenReturn(applicationBuilder);

        // Act
        SpringApplicationBuilder result = servletInitializer.configure(applicationBuilder);

        // Assert
        verify(applicationBuilder).sources(GreenTracerApplication.class);
    }
}