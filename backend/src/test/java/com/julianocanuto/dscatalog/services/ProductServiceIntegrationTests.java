package com.julianocanuto.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.julianocanuto.dscatalog.repositories.ProductRepository;
import com.julianocanuto.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
public class ProductServiceIntegrationTests {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private Long existingId = 1L;
	private Long nonExistingId = 1000L;
	private Long countTotalProducts = 25L;

	@BeforeEach
	private void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}

	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {

		service.delete(existingId);

		Assertions.assertEquals(countTotalProducts - 1, repository.count());
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
}
