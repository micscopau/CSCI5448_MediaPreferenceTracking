package com.mycu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mycu.dbhandler.HibernateUtil;
import com.mycu.model.AList;


public class RatingsDAO implements StrategyDAO
{

	
	public RatingsDAO() 
	{
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<AList> fetchMovies(long uID)
	{
		ArrayList<AList> movies = new ArrayList<AList>();
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Ratings R where R.uID = :uID");
		query.setParameter("uID",uID);
	    
		@SuppressWarnings("unchecked")
		List<AList> allmovies = query.list();
		for(AList movie: allmovies)
		{
			movies.add(movie);
		}
		
		session.getTransaction().commit();	
		
		return movies;
	}
	
	
	@Override
	public void addMovie(AList movie)
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(movie);
		session.getTransaction().commit();
	}
}
