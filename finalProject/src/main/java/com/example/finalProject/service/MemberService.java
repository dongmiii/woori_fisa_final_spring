package com.example.finalProject.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.example.finalProject.domain.Role;
import com.example.finalProject.domain.entity.MemberEntity;
import com.example.finalProject.domain.repository.MemberRepository;
import com.example.finalProject.dto.MemberDTO;
import com.example.finalProject.dto.MemberResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
@AllArgsConstructor
@Getter
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 이메일로 사용자 조회
    public MemberEntity findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
    }

    // 회원가입을 처리하는 메소드
    @Transactional
    public Long join(MemberDTO memberdto) {
        MemberEntity user = MemberEntity.builder()
                .username(memberdto.getUsername())
                .password(passwordEncoder.encode(memberdto.getPassword())) // 비밀번호 암호화
                .email(memberdto.getEmail())
                .agegroup(memberdto.getAgegroup())
                .gender(memberdto.getGender())
                .category(memberdto.getCategory())
                .build();

        return memberRepository.save(user).getId(); // 생성된 user의 id 반환
    }

    public MemberEntity getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
    }

    // userId로 이메일 조회
    public String getEmailById(Long userId) {
        MemberEntity member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        return member.getEmail();
    }

    public MemberEntity getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
    }

    public int getAutoIncrementIdByEmail(String email) {
        System.out.println("Email to search: " + email);
        int id = memberRepository.findIdByEmail(email);
        if(id == 0) {
            throw new IllegalArgumentException("No user found with eamil:" + email);
        }

        return id;
    }


    // 이메일로 사용자 이름(username) 조회
    public String getUsernameByEmail(String email) {
        String username = memberRepository.findUsernameByEmail(email);
        if (username == null) {
            throw new IllegalArgumentException("No user found with email: " + email);
        }
        return username;
    }

    public String getPromptByEmail(String email) {
        String prompt = memberRepository.findPromptByEmail(email);
        if (prompt == null) {
            return null;
        }
        return prompt;
    }

    public int getHoneyByEmail(String email) {
        // MemberRepository에서 이메일로 Member 엔티티를 조회
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email for honey: " + email));

        // honey 값 반환
        return member.getHoney();
    }
    
    public int getHoneyById(Integer userId) {
        Long userIdAsLong = userId.longValue();
        MemberEntity member = memberRepository.findById(userIdAsLong)
                .orElseThrow(() -> new IllegalArgumentException("getHoneyById :: 사용자를 찾을 수 없습니다."));
        return member.getHoney();
    }
    
    public void addHoney(Integer memberId, int points) {
    	Long memberIdAsLong = memberId.longValue();
    	MemberEntity member = memberRepository.findById(memberIdAsLong)
    			.orElseThrow(() -> new IllegalArgumentException("addHoney :: 사용자를 찾을 수 없습니다."));
    	
    	member.setHoney(member.getHoney() + points);
    	memberRepository.save(member);
    }
    
    
    public void subtractHoney(Integer memberId, int points) {
    	Long memberIdAsLong = memberId.longValue();
    	MemberEntity member = memberRepository.findById(memberIdAsLong)
    			.orElseThrow(() -> new IllegalArgumentException("subtractHoney :: 사용자를 찾을 수 없습니다."));
    	
    	member.setHoney(member.getHoney() - points);
    	memberRepository.save(member);
    }
    
//    @Transactional
//    public void subtractHoney(String email, int points) {
//        MemberEntity member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
//
//        if (member.getHoney() < points) {
//            throw new IllegalArgumentException("꿀이 부족합니다.");
//        }
//
//        member.setHoney(member.getHoney() - points);
//        memberRepository.save(member);
//    }
    
    
    
    // 하루에 한 번만 점수 추가
//    public boolean updateHoneyForDate(String email, String date) {
//    	MemberEntity member = memberRepository.findByEmail(email)
//    			.orElseThrow(() -> new IllegalStateException("honey :: 회원 정보가 없습니다."));
//    	
//    	// 특정 날짜에 이미 점수가 쌓였는지 확인
//    	boolean isAlreadyUpdated = memberRepository.checkHoneyUpdateForDate(email, date);
//    	if(!isAlreadyUpdated) {
//    		member.setHoney(member.getHoney() +1);
//    		memberRepository.save(member);
////    		memberRepository.recordHoneyUpdate(email,date);
//    		return true;
//    	}
//    	
//    	return false;
//    }


    // 상세 정보를 조회하는 메소드
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
//		Optional<MemberEntity> member = memberRepository.findByEmail(email);

        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));


        return toUserDetails(member);

//		if(member == null) {
//			throw new UsernameNotFoundException(email);
//		}
//
//		return User.builder()
//				.username(new MemberDTO().getEmail())
//				.password(new MemberDTO().getPassword())
//				.roles(Role.MEMBER.getValue())
//				.build();

//		MemberEntity memberEntity = member.orElse(null);
//
//		List<GrantedAuthority> authorities = new ArrayList<>();
//
//		authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
//
//		return new User(memberEntity.getEmail(), memberEntity.getPassword(),authorities);
    }

    // 사용자 정보를 제공하는 역할
    private UserDetails toUserDetails(MemberEntity member) {
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(new SimpleGrantedAuthority(Role.MEMBER.getValue()))
                .build();
    }

//    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
//        MemberEntity user = memberRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. email = " + email));
//        return new CustomUserDetails(user);
//    }

    // 회원 목록 조회
    public List<MemberResponseDTO> findMembers(){
        List<MemberEntity> all = memberRepository.findAll();
        List<MemberResponseDTO> members = new ArrayList<>();

        for(MemberEntity member : all) {
            MemberResponseDTO build = MemberResponseDTO.builder()
                    .member(member)
                    .build();
            members.add(build);
        }

        return members;
    }

    // 회원가입 시 유효성 체크
//	@Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        // 유효성 검사에 실패한 필드 목록을 받음
        // 유효성 검사에 실패한 필드들은 Map 자료구조를 통해 키값과 에러 메시지를 응답함
        // key : valid_{dto 필드명} , Message : dto에서 작성한 message 값
        // 유효성 검사에 실패한 필드 목록을 받아 미리 정의된 메시지를 가져와 Map에 넣어줌
        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    // 이메일 중복 체크
    @Transactional(readOnly = true)
    public void checkEmailDuplication(MemberDTO dto) {
        boolean emailDuplicate = memberRepository.existsByEmail(dto.getEmail());
        if(emailDuplicate) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

}