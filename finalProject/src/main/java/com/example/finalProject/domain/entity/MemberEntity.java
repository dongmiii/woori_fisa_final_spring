package com.example.finalProject.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * model - 회원 정보 데이터를 저장할 회원 엔티티
 * db 설계
 */

@Entity
@Data
//@Builder
@NoArgsConstructor
@Table(name="member")
public class MemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(nullable = true, length = 255)
	private String agegroup; // varchar(255)

	@Column(nullable = true, length = 255)
	private String category; // varchar(255)

	@Column(nullable = true, length = 255, unique = true)
	private String email; // varchar(255)

	@Column(nullable = true, length = 255)
	private String gender; // varchar(255)

	@Column(nullable = false, length = 255)
	private String password; // varchar(255)

	@Column(nullable = false, length = 255)
	private String username; // varchar(255)

	@Column(columnDefinition = "TEXT")
	private String prompt; // text

	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image; // mediumblob

	@Column(nullable = true, length = 1000)
	private String imageUrl; // varchar(1000)

    @Column(columnDefinition = "INT DEFAULT 5")
    private Integer honey = 5; // Java 기본값
    
    @PrePersist
    protected void onCreate() {
        if (honey == null) {
            honey = 5;
        }
    }
	
//	@Enumerated(EnumType.STRING)
//	private Role role;
	
	@Builder
	public MemberEntity(Long id, String username, String password,String email, String agegroup, String gender, String category, Integer honey) {
	      this.id = id;
	        this.username = username;
	        this.password = password;
	        this.email = email;
	        this.agegroup = agegroup;
	        this.gender = gender;
	        this.category = category;
	        this.honey  = honey;
	}

}
