package com.glomozda.machinerepair;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.user.User;
import com.glomozda.machinerepair.domain.userauthorization.UserAuthorization;
import com.glomozda.machinerepair.repository.client.ClientService;
import com.glomozda.machinerepair.repository.user.UserService;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationService;

@Controller
public class AdminPageController implements MessageSourceAware {
	
	static Logger log = Logger.getLogger(AdminPageController.class.getName());

	@Autowired
	private UserService userSvc;
	
	@Autowired
	private UserAuthorizationService userAuthorizationSvc;

	@Autowired
	private ClientService clientSvc;	
	
	@Autowired
	private PasswordEncoder encoder;
	
	private User myUser;
	
	private MessageSource messageSource;
	
	private String messageUserAuthorizationUserId = "";
	private Integer selectedUserAuthorizationUserId = 0;

	private String messageClientUserId = "";
	private Integer selectedClientUserId = 0;	
	 
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/adminpage", method = RequestMethod.GET)
	public String activate(final Principal principal, final Model model) {
		
		log.info("Activating Admin Page for " + principal.getName() + "...");
		
		myUser = userSvc.getUserByLogin(principal.getName());
		if (null == myUser) {
			return "redirect:/index";
		}
		
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}
		if (!model.containsAttribute("userAuthorization")) {
			model.addAttribute("userAuthorization", new UserAuthorization());
		}
		if (!model.containsAttribute("client")) {
			model.addAttribute("client", new Client());
		}
				
		model.addAttribute("users", userSvc.getAll());
		model.addAttribute("user_authorizations", userAuthorizationSvc.getAllWithFetching());
		model.addAttribute("user_roles", userAuthorizationSvc.getAllRoles());
		model.addAttribute("clients", clientSvc.getAllWithFetching());
				
		model.addAttribute("message_user_authorization_user_id", messageUserAuthorizationUserId);
		messageUserAuthorizationUserId = "";		
		model.addAttribute("selected_user_authorization_user_id", selectedUserAuthorizationUserId);
		selectedUserAuthorizationUserId = 0;
		
		model.addAttribute("message_client_user_id", messageClientUserId);
		messageClientUserId = "";		
		model.addAttribute("selected_client_user_id", selectedClientUserId);
		selectedClientUserId = 0;	
				
		return "adminpage";
	}	
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(
			@ModelAttribute("user") @Valid final User user,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute
			("org.springframework.validation.BindingResult.user", bindingResult);
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/adminpage#users";
		}
		
		userSvc.add(new	User(user.getLogin(), user.getPasswordText(),
				encoder.encode(user.getPasswordText())));
		return "redirect:/adminpage#users";
	}
	
	@RequestMapping(value = "/addUserAuthorization", method = RequestMethod.POST)
	public String addUserAuthorization
		(@ModelAttribute("userAuthorization") @Valid final UserAuthorization userAuthorization,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Integer userId) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageUserAuthorizationUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								Locale.getDefault());			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.userAuthorization", bindingResult);
				redirectAttributes.addFlashAttribute("userAuthorization", userAuthorization);				
			}
			
			selectedUserAuthorizationUserId = userId;
			return "redirect:/adminpage#user_auths";
		}
		
		userAuthorizationSvc.add(new UserAuthorization(userAuthorization.getRole()),
				userId);
		return "redirect:/adminpage#user_auths";
	}

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	public String addClient(@ModelAttribute("client") @Valid final Client client,
			final BindingResult bindingResult,			
			final RedirectAttributes redirectAttributes,
			@RequestParam("userId") final Integer userId) {
		
		if (userId == 0 || bindingResult.hasErrors()) {
			if (userId == 0) {
				messageClientUserId = 
						messageSource.getMessage("error.adminpage.userId", null,
								Locale.getDefault());			
			}

			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute
				("org.springframework.validation.BindingResult.client", bindingResult);
				redirectAttributes.addFlashAttribute("client", client);				
			}
			
			selectedClientUserId = userId;
			return "redirect:/adminpage#clients";
		}
		
		clientSvc.add(client, userId);
		return "redirect:/adminpage#clients";
	}	
}