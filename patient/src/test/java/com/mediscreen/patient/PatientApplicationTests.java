package com.mediscreen.patient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile("dev")
@ActiveProfiles("dev")
@SpringBootTest
class PatientApplicationTests {

	@Test
	void contextLoads() {
	}

}
