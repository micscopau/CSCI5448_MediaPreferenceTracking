package com.mycu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mycu.dbhandler.HibernateUtil;
import com.mycu.model.AList;


public class RecommendationDAO implements StrategyDAO
{

	public RecommendationDAO() 
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
		
		System.err.println("pre query build");
		
		Query query = session.createQuery("from Recommendation R where R.uID = :uID");
		query.setParameter("uID",uID);
		
		System.err.println("post query build");
	    
		@SuppressWarnings("unchecked")
		List<AList> allmovies = query.list();
		
		System.err.println("post query");
		
		for(AList movie: allmovies)
		{
			movies.add(movie);
		}
		
		session.getTransaction().commit();	
		
		return movies;
	}
	
	public ArrayList<AList> fetchMovies(long uID, SearchDAO searchDAO)
	{
		ArrayList<AList> movies = new ArrayList<AList>();
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		System.err.println("pre query build");
		
		Query query = session.createQuery("from Recommendation R where R.uID = :uID");
		query.setParameter("uID",uID);
		
		System.err.println("post query build");
	    
		@SuppressWarnings("unchecked")
		List<AList> allmovies = query.list();
		
		System.err.println("post query");
		
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
