package com.electric_diary.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.electric_diary.entities.RoleEntity;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTests {
	@Mock
	private RoleRepository roleRepository;

	@Test
	public void RoleRepository_SaveAll_ReturnSavedRole() {
		// Arrange
		RoleEntity role = RoleEntity.builder()
				.id(1)
				.name("TEACHER")
				.build();
		
		Mockito.when(roleRepository.save(role)).thenReturn(role);

		// Act
		RoleEntity result = roleRepository.save(role);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void RoleRepository_FindAll_ReturnMoreThanOneRole() {
	    // Arrange
		RoleEntity firstRole = RoleEntity.builder()
				.name("TEACHER")
				.build();
	    
		RoleEntity secondRole = RoleEntity.builder()
				.name("ADMIN")
				.build();
	    
	    List<RoleEntity> roleList = Arrays.asList(firstRole, secondRole);

	    Mockito.when(roleRepository.findAll()).thenReturn(roleList);
	    
	    // Act
	    Iterable<RoleEntity> result = roleRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstRole, secondRole);
	}
	
	@Test
	public void RoleRepository_FindById_ReturnRole() {
	    // Arrange
		RoleEntity role = RoleEntity.builder()
				.name("TEACHER")
				.build();
	    
	    Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
	    
	    // Act
	    Optional<RoleEntity> result = roleRepository.findById(role.getId());
	    
	    // Assert
	    Assertions.assertThat(result).isPresent();
	    Assertions.assertThat(result.get()).isEqualTo(role);
	}
	
	@Test
	public void RoleRepository_UpdateRole_ReturnsUpdatedRole() {
	    // Arrange
		RoleEntity role = RoleEntity.builder()
				.id(1)
				.name("TEACHER")
				.build();
	    
	    Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
	    RoleEntity resultSave = roleRepository.findById(role.getId()).get();
	    
	    resultSave.setName("ADMIN");
	    
		Mockito.when(roleRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		RoleEntity updatedRole = roleRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedRole.getName()).isEqualTo("ADMIN");
	}
	
	@Test
	public void RoleRepository_DeleteRole_ReturnRoleIsEmpty() {
	    // Arrange
		RoleEntity role = RoleEntity.builder()
				.id(1)
				.name("TEACHER")
				.build();
	    
		roleRepository.save(role);
	    
	    // Act
		roleRepository.delete(role);
	    Optional<RoleEntity> roleReturn = roleRepository.findById(role.getId());
	    
	    // Assert
	    Assertions.assertThat(roleReturn).isEmpty();
	}
}
