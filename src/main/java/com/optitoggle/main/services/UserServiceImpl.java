package com.optitoggle.main.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optitoggle.main.dao.UserDao;
import com.optitoggle.main.entities.User;
import com.optitoggle.main.exceptions.ResourceNotFoundException;
import com.optitoggle.main.payloads.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    // getAllUsers() method implementation
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userDao.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    // getUserById() method implementation
    @Override
    public UserDto getUserById(Integer userid) {
        User user = this.userDao.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userid", userid));
        return this.userToDto(user);

    }

    // addUser() method implementation
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setCreatedOn(new Date());
        User savedUser = this.userDao.save(user);
        return this.userToDto(savedUser);
    }

    // updateUser() method implementation
    @Override
    public UserDto updateUser(UserDto userDto, Integer userid) {
        User user = this.userDao.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userid", userid));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmailid(userDto.getEmailid());
        user.setPassword(userDto.getPassword());
        // user.setRoles(userDto.getRoles());

        User updatedUser = this.userDao.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    // deleteUser() method implementation
    @Override
    public void deleteUser(int userid) {
        User user = this.userDao.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userid", userid));
        this.userDao.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

}
