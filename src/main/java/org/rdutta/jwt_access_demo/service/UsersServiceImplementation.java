package org.rdutta.jwt_access_demo.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.rdutta.jwt_access_demo.dao.UsersDao;
import org.rdutta.jwt_access_demo.dto.UsersDto;
import org.rdutta.jwt_access_demo.entity.Users;
import org.rdutta.jwt_access_demo.exceptions.UserExceptions;
import org.rdutta.jwt_access_demo.mapper.UsersMapper;
import org.rdutta.jwt_access_demo.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UsersServiceImplementation implements UsersDao{
    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;
    private final Logger log = LoggerFactory.getLogger(UsersServiceImplementation.class);

    @Autowired
    public UsersServiceImplementation(UsersRepository usersRepository, UsersMapper usersMapper){
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    @Override
    public Long signup(UsersDto usersDto) {
        if (usersDto.getUsername() == null || usersDto.getUsername().isEmpty()) {
            throw new UserExceptions("Username cannot be null or empty");
        }

        if (usersRepository.existsByEmail(usersDto.getEmail())) {
            throw new UserExceptions("Email already exists");
        }

        Users users = usersMapper.toUsers(usersDto);
        usersRepository.save(users);
        log.info("User got created with id:: {}", users.getUserId());
        return users.getUserId();
    }

    @Override
    public List<Users> listOfUsers() {
        List<Users> listOfUsers = usersRepository.findAll().stream().collect(Collectors.toList());
        if(listOfUsers.size()==0){
            throw new UserExceptions("There's no user present currently in database.");
        }
        log.info("Found users from database:: {}", listOfUsers);
        return listOfUsers==null ? null : listOfUsers;
    }

    @Override
    public Users singleUser(Long user_id) {
        Users users = usersRepository.findById(user_id).orElseThrow(()->new UserExceptions("User id not present in the database:: "+user_id));
        log.info("User found with id:: {}", user_id);
        return (users==null) ? null : users;
    }

    @Override
    public String existRecordUpdate(Long user_id, UsersDto usersDto) {
        var existingUser = usersRepository.findById(user_id).orElseThrow(()->new UserExceptions("User not present with the id:: "+user_id));

        String nameOfTheUser = usersDto.getUsername();
    
        if (!nameOfTheUser.equals(existingUser.getUsername())) {
            throw new UserExceptions("The choosen user "+usersDto.getUsername()+" is not matched with username"+existingUser.getUsername()+", present in database for the user_id:: "+existingUser.getUserId());
        }

        if (usersRepository.existsByEmail(usersDto.getEmail())) {
            throw new UserExceptions("Email already exists");
        }
        if(usersRepository.existsById(existingUser.getUserId())){
            Users users = usersMapper.toUsers(usersDto);
            existingUser.setUsername(users.getUsername());
            existingUser.setEmail(users.getEmail());
            existingUser.setUserModifiedAt(Date.from(Instant.now()));
            usersRepository.save(existingUser);
        return "User updated successfully!!";
        }
        return "You can't update the current data!";
    }

    @Override
    public void deleteSingleUserByUserId(Long user_id) {
        Users users = usersRepository.findById(user_id).orElseThrow(()->new UserExceptions("User not found:: "+user_id));
        log.info("User found for deletion:: {}", users);
        usersRepository.delete(users);
    }
}
