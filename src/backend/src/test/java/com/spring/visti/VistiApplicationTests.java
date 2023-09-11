package com.spring.visti;

import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

@RequiredArgsConstructor
@Slf4j
@SpringBootTest
class VistiApplicationTests {

	MockHttpServletResponse response = new MockHttpServletResponse();

	@Autowired
	private MemberService memberService;
	private MemberJoinDTO member1;
	private MemberJoinDTO member2;
	private MemberJoinDTO member3;
	private MemberJoinDTO member4;


	private MemberLoginDTO loginMember1;
	private MemberLoginDTO loginMember2;

	private MemberLoginDTO failLoginMember1;
	private MemberLoginDTO failLoginMember2;

	@Test
	@Transactional
	void TestJoinService() {
		member1 = new MemberJoinDTO("test@test.com", "12345678", "테스트중");
		member2 = new MemberJoinDTO("whipbaek@gmail.com", "12345678", "ㅋㅋ 고생좀 했네");



		memberService.signUp(member1);

		memberService.signUp(member2);

		TestLogInService();
	}

	public void rollbackTest(){
		throw new RuntimeException("런타임 엑셉션");
	}

	@Test
	void TestLogInService() {
		loginMember1 = new MemberLoginDTO(member1.getEmail(), member1.getPassword());
		loginMember2 = new MemberLoginDTO(member1.getEmail(), member1.getPassword());
		failLoginMember1 = new MemberLoginDTO(member2.getEmail(), member1.getPassword());
		failLoginMember2 = new MemberLoginDTO("ttttt@qqqqq.com", "123aaaasssd");

		memberService.signIn(loginMember1, response);
		memberService.signIn(loginMember2, response);
//		memberService.signIn(failLoginMember1, response);
//		memberService.signIn(failLoginMember2, response);
	}

}
