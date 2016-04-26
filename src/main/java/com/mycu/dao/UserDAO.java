package com.mycu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mycu.dbhandler.HibernateUtil;
import com.mycu.model.Movie;
import com.mycu.model.User;

public class UserDAO 
{
	private String Username,password;
	private String email,fName,lName;
	public long uID;
	
	SessionFactory sessionFactory;
	Session session;
	
	public UserDAO()
	{
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void checkSession(){
		
		System.out.println("Session isopen? " + session.isOpen());
		
		//sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		
		System.out.println("Session isopen? " + session.isOpen());
		
	}

	public long checkUser(User user)
	{
	    try
	    {
	    	 session.beginTransaction();     	 
	    	 Query query = session.createQuery("from User U where U.userName = :userName");
	    	 query.setParameter("userName",user.getuserName());
	    	 
	         @SuppressWarnings("unchecked")
			 List<User> user2 = query.list();
	         for(User user3 : user2)
	         {
	             System.out.println("List of Users::"+user3.getuId()+","+user3.getfName());
	             if(user3.getpassword().equals(user.getpassword()) && user3.getuserName().equals(user.getuserName()))
	            	 uID=user3.getuId();
	             else
	            	 uID=0;
	         }
	     
	        session.getTransaction().commit();
	        
	    }catch (HibernateException e) 
	    {
	        e.printStackTrace();
	    }
	    return uID;
	}
	
	
	
	public long addUser(User user)
	{
 		session.beginTransaction();
 		// this would save the Student_Info object into the database
 		 
 		System.out.println("New User uID: " + user.getuId());
 		
 		session.save(user);	
 		
 		System.out.println("Save succesful");
 		
 		 session.getTransaction().commit();
 		 
		 
 		 uID=user.getuId();
 		 return uID;
	}
	
	
	public long save(User user)
	{
		
		/*
		session.beginTransaction();	
		
 		lName=user.getlName();
 		fName=user.getfName();
 		email=user.getEmail();
 		Username=user.getuserName();
 		password=user.getpassword();
 		uID=user.getuId();
 		
 		User user2=new User();
 		

 		user2= (User) session.get(User.class,uID+1);
 		
 		
 		user2.setfName(fName);
 		user2.setlName(lName);
 		user2.setEmail(email);
 		user2.setpassword(password);
 		user2.setuserName(Username);
 	
 		
 		session.update(user2);	
 		session.getTransaction().commit();
 		
 		
 		return uID;
		
		*/
		
 		session.beginTransaction();

 		lName=user.getlName();
 		fName=user.getfName();
 		email=user.getEmail();
 		Username=user.getuserName();
 		password=user.getpassword();
 		
 		System.out.println("SAVING uID: " + uID + " f: " + fName + " l: " + lName + " e: " + email);
 
 		String hql;
 		int result;
 		Query query;
 	 		
 		hql = "UPDATE User set fName = :fName WHERE uID = :uID";
 		query = session.createQuery(hql);
		query.setString("fName",  fName );
		query.setParameter("uID", uID);
 		result = query.executeUpdate();
 		System.out.println("Rows affected on userupdate table: " + result);
 		
 		hql = "UPDATE User set lName = :lName WHERE uID = :uID";
 		query = session.createQuery(hql);
 		query.setString("lName", lName );
 		query.setParameter("uID", uID);
 		result = query.executeUpdate();
 		System.out.println("Rows affected on userupdate table: " + result);
 		
 		hql = "UPDATE User set email = :email WHERE uID = :uID";
 		query = session.createQuery(hql);
		query.setString("email",  email );
 		query.setParameter("uID", uID);
 		result = query.executeUpdate();
 		System.out.println("Rows affected on userupdate table: " + result);
 		
 		hql = "UPDATE User set username = :userName WHERE uID = :uID";
 		query = session.createQuery(hql);
 		query.setString("userName", Username );
 		query.setParameter("uID", uID);
 		result = query.executeUpdate();
 		System.out.println("Rows affected on userupdate table: " + result);
 		
 		hql = "UPDATE User set password = :password WHERE uID = :uID";
 		query = session.createQuery(hql);
 		query.setString("password", password );
 		query.setParameter("uID", uID);
 		result = query.executeUpdate();
 		System.out.println("Rows affected on userupdate table: " + result);
 		
 		return uID;
 		
		
	}
	
	public User getUser(long uID)
	{
		User user =new User();
		session.beginTransaction();	
		user= (User) session.get(User.class,uID);
		session.getTransaction().commit();
 		
		return user;
	}
}
