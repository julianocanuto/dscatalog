package com.julianocanuto.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.julianocanuto.dscatalog.dto.ProductDTO;
import com.julianocanuto.dscatalog.entities.Category;
import com.julianocanuto.dscatalog.entities.Product;
import com.julianocanuto.dscatalog.repositories.CategoryRepository;
import com.julianocanuto.dscatalog.repositories.ProductRepository;
import com.julianocanuto.dscatalog.services.exceptions.DatabaseException;
import com.julianocanuto.dscatalog.services.exceptions.ResourceNotFoundException;
import com.julianocanuto.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceUnitTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private PageImpl<Product> page;
	private Category category;
	private Product product;

	@BeforeEach
	private void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		category = Factory.createCategory();
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));

		// ProductRepository mock
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		// CategoryRepository mock
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);

	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdHasDependents() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAllPaged(pageable);

		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll(pageable);
	}

	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		ProductDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		Mockito.verify(repository).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		Mockito.verify(repository).findById(nonExistingId);
	}
	
	@Test
	public void updateShouldReturnProductDtoWhenIdExists() {
		ProductDTO productDTO = Factory.createProductDTO();
		
		ProductDTO result = service.update(existingId, productDTO);

		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		ProductDTO productDTO = Factory.createProductDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDTO);
		});
	}
}
