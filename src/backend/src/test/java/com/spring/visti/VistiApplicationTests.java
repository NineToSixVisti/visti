package com.spring.visti;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@RequiredArgsConstructor
@Slf4j
@ActiveProfiles("test")
@TestPropertySource
class VistiApplicationTests {

	@Test
	void contextLoads() {
	}
}
