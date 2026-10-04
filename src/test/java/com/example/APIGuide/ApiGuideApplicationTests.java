package com.example.APIGuide;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApiGuideApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void failingTestCase(){
        assertEquals(1,2);
    }

}
