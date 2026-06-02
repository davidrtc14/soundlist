package com.p7.soundlist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SoundList API",
                version = "1.0",
                description = "API para gerenciamento de playlists e músicas",
                contact = @Contact(
                        name = "CC 7ºP - UNIPE"
                )
        )
)
public class OpenApiConfig {
}