package com.mycu.controller;

import org.hibernate.SessionFactory;
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
import com.mycu.dbhandler.HibernateUtil;
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
	
	private String userName,password;
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
    	 return new ModelAndView("createaccount", "user", new User(fName,lName,userName,email,password));
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
		//userdao.checkSession();

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
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		sessionFactory.close();
		
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
    public ModelAndView  viewProfile(@ModelAttribute("userForm") User user, ModelMap model)
    {
		
		System.out.println("User: " + usernew.getfName() + " email: " + usernew.getEmail());
    	return new ModelAndView("viewprofile", "user", usernew);
    	
    	//return new ModelAndView("viewprofile", "user", new User(fName,lName,userName,email,password));

    }
	
	 @RequestMapping(value = "/profile")
	    public String profile(@ModelAttribute("userForm") User user, ModelMap model, @RequestParam String action)
	    {
	    
		// System.out.println("Action: " + action.toString());
		 
	    	if(action.equals("Save"))  // Save changes to the database
	    	{
	    
	    		if (usernew.compareTo(user) == 1)
	    			System.out.println("No changes made");
	    		else
	    		{
	
	    			System.out.println("pre uID: " + uID + " pF: " + fName + " pF: " + lName + " pE: " + email + " pU: " + userName);
	    			System.out.println("preUser uID: " + user.getuId() + " pF: " + user.getfName() + 
	    					" pF: " + user.getlName() + " pE: " + user.getEmail() + " pU: " + user.getuserName());
	    			System.out.println("preUsernew uID: " + usernew.getuId() + " pF: " + usernew.getfName() + 
	    					" pF: " + usernew.getlName() + " pE: " + usernew.getEmail() + " pU: " + usernew.getuserName());
	    			 
	    			uID=userdao.save(user);
	    			
	    			System.out.println("post uID: " + uID + "pF: " + fName + "pF: " + lName + " pE: " + email + " pU: " + userName);System.out.println("post uID: " + uID);
	    			System.out.println("postUser uID: " + user.getuId() + " pF: " + user.getfName() + 
	    					" pF: " + user.getlName() + " pE: " + user.getEmail() + " pU: " + user.getuserName());
	    			System.out.println("postUsernew uID: " + usernew.getuId() + " pF: " + usernew.getfName() + 
	    					" pF: " + usernew.getlName() + " pE: " + usernew.getEmail() + " pU: " + usernew.getuserName());
	    			
	    			usernew=user;
	    			user=userdao.getUser(uID);
	    			
	    			System.out.println("postUsernew uID: " + usernew.getuId() + " pF: " + usernew.getfName() + 
	    					" pF: " + usernew.getlName() + " pE: " + usernew.getEmail() + " pU: " + usernew.getuserName());
	    			
	    		}
	    		   		
	    	}
	    	
	    	System.out.println("About to print users updated info");
	    	System.out.println("Usernew fName: " + usernew.getfName());
	
	    	System.out.println("User fName: " + user.getfName());
	    	System.out.println("User uID:" + user.getuId() + " User email: " + user.getEmail());
	    	
	    	page=displayRecommendation(model,uID);
	    	return page;
	    	
	    	/*else // cancel changes
	    	{
	    		user=userdao.getUser(uID);
	    		
	    		model.addAttribute("fName",usernew.getfName());
	    		return "userLoggedin";
	    	}*/
	    	
	    
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
		 public String Search(@ModelAttribute("userForm") SearchDAO search, ModelMap model) 
		 {
			// model.addAttribute("searchTitle", searchTitle);
			 searchdao = search;	
			 page=displaySearch(model,uID);
			 		 
		     return page;
		 }
		
	
		@RequestMapping(value = "/previousSearch", method = RequestMethod.GET)
		 public String previousSearch(@ModelAttribute("userForm") SearchDAO search, ModelMap model) 
		 {
			//if(action.equals("previous")) //MPauly this is the code you were looking for.
			
			int startRow = searchdao.getStartRow();
			
			if (startRow==0)
				 return page;
			else if(startRow <= 20)
					searchdao.setStartRow(0);				
			else
				searchdao.setStartRow(startRow -20);
					 	 
			 page=displaySearch(model,uID);
			 		 
		     return page;
		 }
		
		@RequestMapping(value = "/nextSearch", method = RequestMethod.GET)
		 public String nextSearch(@ModelAttribute("userForm") SearchDAO search, ModelMap model) 
		 {
			int startRow = searchdao.getStartRow();
			long endRow = searchdao.getMaxResults();
			
			if (startRow < (endRow-20)) //still before end of results
				searchdao.setStartRow(startRow+20);
			else if(startRow == (endRow - 20)) //exactly at last 20 of results
				return page;//already at end of results from last query, don't need to redo displaySearch call				
			else //near end but still some results might not be visible
				searchdao.setStartRow((int)endRow-20);	
					 	 
			 page=displaySearch(model,uID);
			 		 
		     return page;
		 }
		
	    public String displaySearch(ModelMap model, long uID)
	    {     
	    	 MovieListWrapper wrapper=new MovieListWrapper();
	    	 ContextLists context = new ContextLists(new SearchList());
	    	 
	    	 System.out.println("searchdao.getSearchTitle(): " + searchdao.getSearchTitle());
	    	 
	    	 if (searchdao.getSearchTitle() == null)
	    		 movies=context.executeFetchMovieStrategy(uID);
	    	 else
	    		 movies=context.executeFetchMovieStrategy(uID, searchdao);
			 
	    	 wrapper.setAllmovies(movies);
			 usernew=userdao.getUser(uID);
			 
			 model.addAttribute("fName",usernew.getfName());
			 model.addAttribute("movieListWrapper",wrapper);
			 model.addAttribute("searchTitle",searchTitle);
	
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
