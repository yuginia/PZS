package com.glomozda.machinerepair;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.repository.client.ClientService;

import org.apache.log4j.Logger;

@Controller
public class ClientPageController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(ClientPageController.class.getName());
	
	@Autowired
	private ClientService clientSvc;
	
	private Client myClient;
	
	private MessageSource messageSource;	
	 
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/clientpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Client Page for " + principal.getName() + "...");
		
		myClient = clientSvc.getClientByLoginWithFetching(principal.getName());
		if (null == myClient) {
			return "redirect:/index";
		}		
		
		model.addAttribute("clientname", myClient.getClientName());		
		
		return "clientpage";
	}	
}
