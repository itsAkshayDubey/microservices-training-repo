package demo.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TransactionIdGeneratorTest {

	@MockBean
	TransactionIdGenerator transactionIdGenerator;

	@Test
	void testGenerateTransactionId() {
		
		when(transactionIdGenerator.generateTransactionId()).thenReturn("ABC123");
		assertEquals("ABC123", transactionIdGenerator.generateTransactionId());
	}

}
