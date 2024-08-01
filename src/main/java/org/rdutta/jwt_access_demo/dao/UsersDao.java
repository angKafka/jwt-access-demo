package org.rdutta.jwt_access_demo.dao;


import java.util.List;

import org.rdutta.jwt_access_demo.dto.UsersDto;
import org.rdutta.jwt_access_demo.entity.Users;

public interface UsersDao {
    Long signup(UsersDto usersDto);
    String existRecordUpdate(Long user_id, UsersDto usersDto);
    List<Users> listOfUsers();
    Users singleUser(Long user_id);
    void deleteSingleUserByUserId(Long user_id);
}
