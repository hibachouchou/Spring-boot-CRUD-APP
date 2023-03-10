package com.hiba.stage.controllers;

import java.util.ArrayList;
import java.util.List;


import javax.validation.Valid;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hiba.stage.entities.User;
import com.hiba.stage.security.SecurityConfig;
import com.hiba.stage.entities.Role;
import com.hiba.stage.service.RoleService;
import com.hiba.stage.service.UserService;



@Controller
public class UserController {
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@RequestMapping("/showCreateU")
	public String showCreate(ModelMap modelMap)
	{  
	    User user = new User();
       
		List<Role> roles = roleService.getAllRoles();
		modelMap.addAttribute("roles", roles);
	    modelMap.addAttribute("user",user);
	    modelMap.addAttribute("mode", "new");
	 
	return "formUser";
	}

	@RequestMapping("/saveUser")
	public String saveUser(@Valid User user, int s,
			 BindingResult bindingResult) 
	{
		if (bindingResult.hasErrors()) return "formUser";
		
		
		SecurityConfig sec = new SecurityConfig();
		PasswordEncoder passwordEncoder = sec.passwordEncoder();
     	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	user.setEnabled(true);
    	List<Role> All= roleService.getAllRoles();
  	   
     	Role r1=All.get(s -1);
     	List<Role> listR = new ArrayList<Role>();
     	listR.add(r1);
     	user.setRoles(listR);
  
     	List<Role> list=user.getRoles();
    	
		userService.saveUser(user);
	 return "redirect:/ListeUsers";
	}
	
	@RequestMapping("/ListeUsers")
	public String listeUsers(ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "10") int size)
	{
	Page<User> user = userService.getAllUsersParPage(page, size);
	modelMap.addAttribute("user", user);
	 modelMap.addAttribute("pages", new int[user.getTotalPages()]);
	modelMap.addAttribute("currentPage", page);
	return "listeUsers";
	}
	@RequestMapping("/supprimerUser")
	public String supprimerUser(@RequestParam("id") Long id, ModelMap modelMap,

			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {

			User user =userService.getUser(id);
			user.setRoles(null);
			userService.updateUser(user);
			userService.deleteUserById(id);
			Page<User> use = userService.getAllUsersParPage(page,
					size);
			modelMap.addAttribute("users", use);
			modelMap.addAttribute("pages", new int[use.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			modelMap.addAttribute("size", size);
			return "listeUsers";
	}
	@RequestMapping("/updateUser")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam("date") String date,int s,
			ModelMap modelMap) throws ParseException {
		SecurityConfig sec = new SecurityConfig();
		PasswordEncoder passwordEncoder = sec.passwordEncoder();
     	user.setPassword(passwordEncoder.encode(user.getPassword()));
     	user.setEnabled(true);
     	List<Role> All= roleService.getAllRoles();
     	Role r1=All.get(s -1);
    	List<Role> listR = new ArrayList<Role>();
     	listR.add(r1);
     	user.setRoles(listR);
     	List<Role> list=user.getRoles();
		userService.updateUser(user);
		List<User> use = userService.getAllUsers();
		modelMap.addAttribute("users", use);
		return "listeUsers";
	}

	@RequestMapping("/modifierUser")
	public String editerEtudiant(@RequestParam("id") Long id,ModelMap modelMap)
	{
	User u= userService.getUsere(id);
	modelMap.addAttribute("user", u);
	modelMap.addAttribute("mode", "edit");
	return "formUser";
	}
	
	
	
	
	
}
