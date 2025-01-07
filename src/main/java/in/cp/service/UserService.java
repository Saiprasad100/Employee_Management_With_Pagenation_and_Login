package in.cp.service;

import java.util.List;
import java.util.Optional;

import in.cp.entity.User_Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void createUser(User_Data user);

    List<User_Data> getAllUsers();

    Optional<User_Data> getOneUser(Integer id);
    
    Page<User_Data> getUsersWithPagination(Pageable pageable);

	Page<User_Data> getUsers(int i, int size, String keyword);
    

}
