package com.mycu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mycu.dao.UserDAO;
import com.mycu.dao.SearchDAO;
import com.mycu.lists.ContextLists;
import com.mycu.lists.IgnoreList;
import com.mycu.lists.MovieListWrapper;
import com.mycu.lists.RatingsList;
import com.mycu.lists.RecommendationLists;
import com.mycu.lists.SearchList;
import com.mycu.lists.WishList;
//import javax.persistence.Query;
import com.mycu.model.*;


@Controller
public class HomeController 
{
	
	private String Username,Password;
	private String email,fName,lName;
	public  long uID,mID;
	public String page;
	public String searchTitle;
	
	UserDAO userdao= new UserDAO();
	User usernew= new User(); 
	SearchDAO searchdao = new SearchDAO();
	ArrayList<Moviedisplayformat> movies= new ArrayList<Moviedisplayformat>();
	
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String Splashpage(@ModelAttribute("userForm") User user,ModelMap model) 
	 {
		    page=displaySplash();
	        return page;
	 }
    	
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView createAccount (@ModelAttribute("userForm") User user,ModelMap model)
    {
    	 return new ModelAndView("createaccount", "user", new User(fName,lName,Username,email,Password));
    }
	
	
	
	/*
	 @RequestMapping(value = "/Search" )
	 public String Search(ModelMap model) 
	 {
			
		 page=displaySearch(model,uID);
	     return page;
	 }
	 */
	
	
	
	// When user tries to log in
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(@ModelAttribute("userForm") User user,BindingResult result,ModelMap model) 
    {
		
    	uID=userdao.checkUser(user);
   
    	if(uID==0)
    		page=displaySplash();
    	else
    	   page=displayRecommendation(model,uID);
    	
    	 
    	usernew=userdao.getUser(uID);
    	 return page;

    }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Recommendation(ModelMap model) 
    {
		usernew=userdao.getUser(uID);
    	page=displayRecommendation(model,uID);
    	
    	return page;

    }
	
	// When user tries to log out
	@RequestMapping(value = "/logout")
    public String userLogout(@ModelAttribute("userForm") User user,ModelMap model)
    {
		page=displaySplash();
        return page;
    }
 	
	
	// When user created an account and clicked on submit
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public String addUseraccount(@ModelAttribute("userForm") User user,ModelMap model) throws SQLException
    {

		uID=userdao.addUser(user); 	
		usernew=user;
		
		page=displaySplash();
        return page;
    }
    

	
	// When user clicks on edit profile
	@RequestMapping(value = "/editprofile")
    public ModelAndView  viewProfile(@ModelAttribute("userForm") User user,ModelMap model)
    {
    	 return new ModelAndView("viewprofile", "user", new User(fName,lName,Username,email,Password));
    }
    
	

	
	

	 
	 
	 @RequestMapping(value = "/MyLists-Ratings")
	 public String myLists(ModelMap model)  //By default ratings page
	 {		
		
		 page=displayRatings(model,uID);
	     return page;
	
	 }
	 
	 
	 @RequestMapping(value = "/Ignore")
	 public String ignoreList(ModelMap model)  
	 {
		
		page=displayIgnore(model,uID);
	    return page; 
	 }
	
	 	 
	 
	 @RequestMapping(value = "/Wish")
	 public String myWishlist(ModelMap model) 
	 {
		//uID=usernew.getuId();	
		page=displayWish(model,uID);
	    return page;  
	 }
	 	 
	 
	 @RequestMapping(value = "/dbupdate", method = RequestMethod.POST)
	 public String dbUpdate(@ModelAttribute("movieListWrapper") MovieListWrapper movies,ModelMap model) 
	 {
		   MovieController moviecontroller= new MovieController();
		   System.out.println("Saving to the database ...");
		    /*if(action.equals("Save"))  // Save changes to the database
	    	{
			    uID=usernew.getuId();  //IMPROVE THIS
			    System.out.println("Saving to the database ...");
		  		
	    	}*/
		    ArrayList<Moviedisplayformat> moviesondisplay = movies.getAllmovies(); 
		    for(Moviedisplayformat mdf: moviesondisplay)
		    {
		    	mID=moviecontroller.getMID(mdf.getMovieTitle());
		    	if(mdf.isIgnore()==true)
		    	{
		    		//Contact ignore dao  
		    		AList alist= new Ignore();
		    		ContextLists context = new ContextLists(new IgnoreList());
		    		alist.setmID(mID);
		    		alist.setuID(uID);
		    		alist.setRatings(0);
		    		context.executeAddMovieStrategy(alist);
		    		
		    	}
		    	if(mdf.isWish()==true)
		    	{
		    		//Contact wish dao
		    		AList alist= new Wish();
		    		ContextLists context = new ContextLists(new WishList());
		    		alist.setmID(mID);
		    		alist.setuID(uID);
		    		alist.setRatings(0);
		    		context.executeAddMovieStrategy(alist);
		    		
		    	}
		    	if(mdf.ratings>0)
		    	{
		    		//Contact ratings list
		    		AList alist= new Ratings();
		    		ContextLists context = new ContextLists(new RatingsList());
		    		alist.setmID(mID);
		    		alist.setuID(uID);
		    		alist.setRatings(mdf.getRatings());
		    		context.executeAddMovieStrategy(alist);
		    		
		    	}
		    	
		    	
	    		
		    	System.out.println("Title is "+ mdf.getMovieTitle());
		    	System.out.println("Ignore value is "+ mdf.isIgnore());
		    	System.out.println("Wish value is "+ mdf.isWish());
		    	System.out.println("Ratings is "+ mdf.getRatings());
		    }
	    	
		    
		    page=displayRecommendation(model,uID);
	    	return page;
	    	
		 
	 }
	 
		
	    @RequestMapping(value = "/profile")
	    public String profile(@ModelAttribute("userForm") User user,ModelMap model,@RequestParam String action)
	    {
	    	
	    	
	    	
	    	if(action.equals("Save"))  // Save changes to the database
	    	{
	    		System.out.println("User name is "+user.getuserName());
	    		uID=userdao.save(user);
	    		user=userdao.getUser(uID);
	    		usernew=user;
	    		   		
	    	}
	    	page=displayRecommendation(model,uID);
	    	return page;
	    	
	    	/*else // cancel changes
	    	{
	    		user=userdao.getUser(uID);
	    		
	    		model.addAttribute("fName",usernew.getfName());
	    		return "userLoggedin";
	    	}*/
	    	
	    
	    }

	    
	        
	    public String displayRecommendation(ModelMap model, long uID)
	    {
	    	 ContextLists context = new ContextLists(new RecommendationLists());
			 movies=context.executeFetchMovieStrategy(uID);
			
			 
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movies", movies);
	    	 return "userLoggedin"; //recommendations page
	    }
	    
	    
	    
	    public String displayRatings(ModelMap model, long uID)
	    {
	    	 ContextLists context = new ContextLists(new RatingsList());
			 movies=context.executeFetchMovieStrategy(uID);
						
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movies", movies);
	    	 return "MyLists"; //ratings page
	    }
	    
	    
	    
	    public String displayIgnore(ModelMap model, long uID)
	    {
	    	 ContextLists context = new ContextLists(new IgnoreList());
			 movies=context.executeFetchMovieStrategy(uID);
			 
			 
			 usernew=userdao.getUser(uID);
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movies", movies);
	    	 return "ignoreList"; //ignore list page
	    }
	    
	    
	    public String displayWish(ModelMap model, long uID)
	    {
	    	
	    	 ContextLists context = new ContextLists(new WishList());
			 movies=context.executeFetchMovieStrategy(uID);
			 usernew=userdao.getUser(uID);
			 
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movies", movies);
	    	 return "Wishlist"; //wish list page
	    }
	    

		@RequestMapping(value = "/Search", method = {RequestMethod.GET, RequestMethod.POST})
		 public String Search(@ModelAttribute("userForm") SearchDAO search, BindingResult result, ModelMap model) 
		 {
			
			 model.addAttribute("searchTitle", searchTitle);
					
			 page=displaySearch(model,uID);
			 searchdao = search;

		     return page;
		 }
		
	    public String displaySearch(ModelMap model, long uID)
	    {    
	    	 ContextLists context = new ContextLists(new SearchList());
	    	 
			 movies=context.executeFetchMovieStrategy(uID, searchdao);
			 usernew=userdao.getUser(uID);
			 
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movies", movies);
			// model.addAttribute("searchTitle",searchTitle);
	
			 /*for (Moviedisplayformat mov: movies)
			 {
				 System.out.println("movie title: " + mov.getMovieTitle());
				 
			 }*/
			 
	    	 return "Search"; //Search page
	    	 	 
	    }
	    
	    
	    public String displaySplash()
	    {
	    	return "index";
	    }
	
	    
}
