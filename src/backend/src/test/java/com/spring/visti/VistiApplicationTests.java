package com.spring.visti;

import com.spring.visti.api.member.service.MemberService;
import com.spring.visti.domain.member.dto.RequestDTO.MemberJoinDTO;
import com.spring.visti.domain.member.dto.RequestDTO.MemberLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@RequiredArgsConstructor
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource
class VistiApplicationTests {

	@Test
	void contextLoads() {
	}
}
