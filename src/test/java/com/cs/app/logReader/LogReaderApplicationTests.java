package com.cs.app.logReader;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogReaderApplicationTests {

	@Test
	void contextLoads() {
	}
	
	// Test class added ONLY to cover main() invocation not covered by application tests.
	@Test
	public void mainTest(){
		LogReaderApplication.main(new String[] {});
	}

}
