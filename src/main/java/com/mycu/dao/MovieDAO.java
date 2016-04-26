package com.mycu.dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mycu.dbhandler.HibernateUtil;

import com.mycu.model.Movie;
public class MovieDAO 
{
	public String movieTitle;
	public long mID;
	Movie movie = new Movie();
	
	
	public MovieDAO()
	{
		
	}

	
	public String getMovie(long mID)
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
 		movie=(Movie) session.get(Movie.class,mID);
 		movieTitle=movie.getMovieTitle();
 		
		session.getTransaction().commit();
		//session.close();
		
		return movieTitle;
	}
	
	public long getMID(String movieTitle)
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Movie M where M.movieTitle = :movieTitle");
		query.setParameter("movieTitle",movieTitle);
		
		@SuppressWarnings("unchecked")
		List<Movie> allmovies = query.list();
		for(Movie movie: allmovies)
		{
			mID=movie.getmID();
		}
		
		session.getTransaction().commit();
		//session.close();
		
		return mID;
	}
}
