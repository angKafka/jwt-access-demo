package org.rdutta.jwt_access_demo.rest;

import java.util.List;

import org.rdutta.jwt_access_demo.dto.UsersDto;
import org.rdutta.jwt_access_demo.entity.Users;
import org.rdutta.jwt_access_demo.service.UsersServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    private final UsersServiceImplementation userService;

    @Autowired
    public UsersController(UsersServiceImplementation userService){
        this.userService = userService;
    }
    
    @PostMapping(path = "/signup")
    public ResponseEntity<Long> signup(@RequestBody @Valid UsersDto usersDto){
        return new ResponseEntity<>(userService.signup(usersDto), HttpStatus.CREATED);
    }


    @GetMapping(path = "/list")
    public ResponseEntity<List<Users>> getAllUsersExists(){
        List<Users> usersFound = userService.listOfUsers();
        return new ResponseEntity<>(usersFound, HttpStatus.OK);
    }


    @GetMapping(path = "/{user_id}")
    public ResponseEntity<Users> getUsers(@PathVariable("user_id") @Valid Long user_id){
        Users users = userService.singleUser(user_id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    
    @PutMapping(path = "/{user_id}")
    public ResponseEntity<String> updateUsers(@PathVariable("user_id") @Valid Long user_id, @RequestBody @Valid UsersDto usersDto){
       return new ResponseEntity<>(userService.existRecordUpdate(user_id, usersDto), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{user_id}")
    public ResponseEntity<Void> removeUsers(@PathVariable("user_id") @Valid Long user_id){
        userService.deleteSingleUserByUserId(user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
