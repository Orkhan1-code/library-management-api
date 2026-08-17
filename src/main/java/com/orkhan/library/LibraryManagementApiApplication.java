package com.orkhan.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;
import com.orkhan.library.config.FileStorageProperties;

@EnableCaching
@EnableConfigurationProperties(FileStorageProperties.class)
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class LibraryManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApiApplication.class, args);
	}

}
