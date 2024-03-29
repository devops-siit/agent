package com.dislinkt.agentapi.service.rate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.dislinkt.agentapi.domain.account.Account;
import com.dislinkt.agentapi.domain.company.Company;
import com.dislinkt.agentapi.domain.rate.Rate;
import com.dislinkt.agentapi.repository.RateRepository;
import com.dislinkt.agentapi.service.account.AccountService;
import com.dislinkt.agentapi.service.company.CompanyService;
import com.dislinkt.agentapi.service.account.payload.AccountDTO;
import com.dislinkt.agentapi.web.rest.company.payload.CompanyDTO;
import com.dislinkt.agentapi.web.rest.rate.payload.NewRateRequest;
import com.dislinkt.agentapi.web.rest.rate.payload.RateDTO;
import com.dislinkt.agentapi.exception.types.EntityAlreadyExistsException;

@Service
public class RateService {

	@Autowired
	private RateRepository rateRepository;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private AccountService accountService;
	
	public Double averageRateByCompany(String companyUuid) {

		Company company = companyService.findOneByUuidOrElseThrowException(companyUuid);
		int sum = 0;
		if(company.getRates() == null)
			return new Double (0);
        for (Rate r: company.getRates()) {
        	sum += r.getRate();
        }
        
        return new Double( sum/company.getRates().size());
    }
	
	public RateDTO insertRate(NewRateRequest rateRequest) {

        Company company = companyService.findOneByUuidOrElseThrowException(rateRequest.getCompanyUuid());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Account account = accountService.findOneByUsernameOrElseThrowException(user.getUsername());

        Optional<Rate> existing = rateRepository.findOneByAccountIdAndCompanyId(account.getId(), company.getId());
        
        if (existing.isPresent()) {
        	throw new EntityAlreadyExistsException("You alredy rate this company");
        }
        
        Rate rate = new Rate();
        rate.setAccount(account);
        rate.setCompany(company);
        rate.setRate(rateRequest.getRate());
        
        rateRepository.save(rate);
        
        RateDTO dto = new RateDTO();
        
        AccountDTO accDto = new AccountDTO();
        accDto.setName(account.getName());
        accDto.setUsername(account.getUsername());
        
        CompanyDTO compDto = new CompanyDTO();
        compDto.setName(company.getName());
        compDto.setAddress(company.getAddress());
        compDto.setPhone(company.getPhone());
        
        dto.setCompany(compDto);
        dto.setAccount(accDto);
        dto.setRate(rate.getRate());
        
        return dto;
    }
}
