package com.dislinkt.agentapi.service.account.payload;

import com.dislinkt.agentapi.web.rest.base.BaseDTO;

import lombok.Data;

@Data
public class AccountDTO extends BaseDTO{

	private String username;
	
	private String name;
}
