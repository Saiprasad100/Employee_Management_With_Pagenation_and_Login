package in.cp.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.cp.entity.User_Data;

public interface UserRepository extends JpaRepository<User_Data, Integer> {

	Optional<User_Data> findByEmail(String email);

	Page<User_Data> findByEmailContainingIgnoreCase(String email, Pageable pageable);

	Page<User_Data> findAll(Pageable paging);
	
	//Pending task Pdf Generate 
}
