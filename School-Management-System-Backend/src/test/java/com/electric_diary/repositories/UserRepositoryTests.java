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

import com.electric_diary.entities.UserEntity;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {
	@Mock
	private UserRepository userRepository;

	@Test
	public void UserRepository_SaveAll_ReturnSavedUser() {
		// Arrange
		UserEntity user = UserEntity.builder()
				.id(1)
				.name("Clark")
				.lastName("Kent")
				.password("ver7strong")
				.email("clark.kent@gmail.com")
				.build();
		
		Mockito.when(userRepository.save(user)).thenReturn(user);

		// Act
		UserEntity result = userRepository.save(user);

		// Assert
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getId()).isGreaterThan(0);
	}
	
	@Test
	public void UserRepository_FindAll_ReturnMoreThanOneUser() {
	    // Arrange
		UserEntity firstUser = UserEntity.builder()
				.name("Clark")
				.lastName("Kent")
				.password("ver7strong")
				.email("clark.kent@gmail.com")
				.build();
		
		UserEntity secondUser = UserEntity.builder()
				.name("Elon")
				.lastName("Musk")
				.password("genious442a")
				.email("elon7.musk@tesla.com")
				.build();
	    
	    List<UserEntity> userList = Arrays.asList(firstUser, secondUser);

	    Mockito.when(userRepository.findAll()).thenReturn(userList);
	    
	    // Act
	    Iterable<UserEntity> result = userRepository.findAll();
	    
	    // Assert
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat(result).hasSize(2);
	    Assertions.assertThat(result).containsExactlyInAnyOrder(firstUser, secondUser);
	}
	
	@Test
	public void UserRepository_FindById_ReturnUser() {
	    // Arrange
		UserEntity user = UserEntity.builder()
				.name("Elon")
				.lastName("Musk")
				.password("genious442a")
				.email("elon7.musk@tesla.com")
				.build();
	    
		Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		// Act
		Optional<UserEntity> result = userRepository.findById(user.getId());

		// Assert
		Assertions.assertThat(result).isPresent();
		Assertions.assertThat(result.get()).isEqualTo(user);
	}
	
	@Test
	public void UserRepository_UpdateUser_ReturnsUpdatedUser() {
	    // Arrange
		UserEntity user = UserEntity.builder()
				.name("Elon")
				.lastName("Musk")
				.password("genious442a")
				.email("elon7.musk@tesla.com")
				.build();
	    
	    Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
	    UserEntity resultSave = userRepository.findById(user.getId()).get();
	    
	    resultSave.setName("Ivan");
	    resultSave.setLastName("Tančik");
	    resultSave.setPassword("abAP46God!");
	    resultSave.setEmail("ivan.tancik@gmail.com");
	    
		Mockito.when(userRepository.save(resultSave)).thenReturn(resultSave);
	    
	    // Act
		UserEntity updatedUser = userRepository.save(resultSave);
	    
	    // Assert
	    Assertions.assertThat(updatedUser.getName()).isEqualTo("Ivan");
	    Assertions.assertThat(updatedUser.getLastName()).isEqualTo("Tančik");
	    Assertions.assertThat(updatedUser.getPassword()).isEqualTo("abAP46God!");
	    Assertions.assertThat(updatedUser.getEmail()).isEqualTo("ivan.tancik@gmail.com");
	}
	
	@Test
	public void UserRepository_DeleteUser_ReturnUserIsEmpty() {
	    // Arrange
		UserEntity user = UserEntity.builder()
				.id(1)
				.name("Clark")
				.lastName("Kent")
				.password("ver7strong")
				.email("clark.kent@gmail.com")
				.build();
	    
		userRepository.save(user);
	    
	    // Act
		userRepository.delete(user);
	    Optional<UserEntity> userReturn = userRepository.findById(user.getId());
	    
	    // Assert
	    Assertions.assertThat(userReturn).isEmpty();
	}
}
