package com.dislinkt.agentapi.service;

import static com.dislinkt.agentapi.constants.CommentConstants.*;
import static com.dislinkt.agentapi.constants.AccountConstants.DB_ACCOUNT_USERNAME_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.agentapi.service.comment.CommentService;
import com.dislinkt.agentapi.web.rest.comment.payload.CommentDTO;
import com.dislinkt.agentapi.web.rest.comment.payload.request.NewCommentRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false
	    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentServiceIntegrationTest {

	@Autowired
	CommentService service;
	
	@BeforeAll
	public void init() {
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Set<? extends GrantedAuthority> auth = new HashSet<>(); 
		UserDetails principal = new User(DB_ACCOUNT_USERNAME_1, "aPassword", auth);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);	
		
	}
	
	@Test
	@Transactional
	public void testFindByCompany() {
		
		 Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		 Page<CommentDTO> found = service.findByCompany(DB_COMMENT_COMPANY_UUID, pageable);
		 
		 assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsertComment() {
		NewCommentRequest req = new NewCommentRequest();
		req.setCompanyUuid(NEW_COMMENT_COMPANY_UUID);
		req.setText(NEW_COMMENT_TEXT);
		
		
		//CommentDTO comment = service.insertComment(req);
		//assertEquals(NEW_COMMENT_TEXT, comment.getText());
		
		
	}
}
