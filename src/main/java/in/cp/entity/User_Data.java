package in.cp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class User_Data {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 20)
	private String firstName;

	@Column(nullable = false, length = 20)
	private String lastName;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 65)
	private String password; // Encrypted password will be stored here

	@Column(nullable = false, length = 32)
	private String salt;

	@Transient
	private String captcha;

	@Transient
	private String hiddenCaptcha;

	@Transient
	private String realCaptcha;
}
