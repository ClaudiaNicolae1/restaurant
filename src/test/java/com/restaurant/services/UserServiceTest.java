//package com.restaurant.services;
//
//import com.restaurant.converters.ObjectConverter;
//import com.restaurant.models.dtos.UserDTO;
//import com.restaurant.models.entities.User;
//import com.restaurant.repositories.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @InjectMocks
//    UserService service;
//
//    // We need a mock for userRepository as it is used within the userService
//    @Mock
//    UserRepository repository;
//
//    // We need a mock for objectConverter as it is used within the userService
//    @Mock
//    ObjectConverter<User, UserDTO> converter;
//
//    @Test
//    void testSaveUser() {
//        // given
//        long id = 1l;
//        UserDTO userDTO = buildUserDTO();
//        User user = buildUser();
//
//        // when
//        // we coordinate, what should happen when the repository is called
//        when(repository.getUserById(1l)).thenReturn(Optional.of(user));
//        // we coordinate, what should happen when the converter is called
//        when(converter.convertFirstToSecond(user)).thenReturn(userDTO);
//
//        // then
//        UserDTO result = service.getUserById(id);
//        // we validate that the result is equal to what we expected initially
//        Assertions.assertEquals(userDTO, result);
//
//        // we also verify if the methods were called during the flow execution
//        verify(repository).getUserById(anyLong());
//        verify(converter).convertFirstToSecond(any());
//    }
//
//    private static UserDTO buildUserDTO() {
//        return UserDTO.builder().age(1).name("name").build();
//    }
//
//    private static User buildUser() {
//        User user = new User();
//
//        user.setAge(1);
//        user.setName("name");
//
//        return user;
//    }//package com.restaurant.services;
////
////import com.restaurant.converters.ObjectConverter;
////import com.restaurant.models.dtos.UserDTO;
////import com.restaurant.models.entities.User;
////import com.restaurant.repositories.UserRepository;
////import org.junit.jupiter.api.Assertions;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.junit.jupiter.MockitoExtension;
////
////import java.util.Optional;
////
////import static org.mockito.Mockito.*;
////
////@ExtendWith(MockitoExtension.class)
////class UserServiceTest {
////
////    @InjectMocks
////    UserService service;
////
////    // We need a mock for userRepository as it is used within the userService
////    @Mock
////    UserRepository repository;
////
////    // We need a mock for objectConverter as it is used within the userService
////    @Mock
////    ObjectConverter<User, UserDTO> converter;
////
////    @Test
////    void testSaveUser() {
////        // given
////        long id = 1l;
////        UserDTO userDTO = buildUserDTO();
////        User user = buildUser();
////
////        // when
////        // we coordinate, what should happen when the repository is called
////        when(repository.getUserById(1l)).thenReturn(Optional.of(user));
////        // we coordinate, what should happen when the converter is called
////        when(converter.convertFirstToSecond(user)).thenReturn(userDTO);
////
////        // then
////        UserDTO result = service.getUserById(id);
////        // we validate that the result is equal to what we expected initially
////        Assertions.assertEquals(userDTO, result);
////
////        // we also verify if the methods were called during the flow execution
////        verify(repository).getUserById(anyLong());
////        verify(converter).convertFirstToSecond(any());
////    }
////
////    private static UserDTO buildUserDTO() {
////        return UserDTO.builder().age(1).name("name").build();
////    }
////
////    private static User buildUser() {
////        User user = new User();
////
////        user.setAge(1);
////        user.setName("name");
////
////        return user;
////    }
////}
////
//}
//
