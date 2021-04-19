package com.example.simpleaccounting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.simpleaccounting.web.MainController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimpleAccountingApplicationTests {

	@Autowired
	private MainController mainController;
	
	@Test
	void contextLoads() throws Exception {
		assertThat(mainController).isNotNull();
	}

}
