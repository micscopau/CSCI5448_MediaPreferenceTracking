package com.mycu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mycu.dbhandler.HibernateUtil;

import com.mycu.model.Movie;
import com.mycu.model.AList;


public class SearchDAO implements StrategyDAO

{
	
	public String searchTitle;

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public SearchDAO() 
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<AList> fetchMovies(long uID)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		int start, maxRows;
		
		start = 0;
		maxRows = 10;
		
		//Query query = session.createQuery("from Movie M"); //ORI
		
		Query query = session.createQuery("from Movie M where M.movieTitle like :searchTitle ");
		//query.setString("searchTitle", "%" + searchTitle + "%");
		query.setParameter("searchTitle", "%" + searchTitle + "%");
		
		 System.out.println("this.getSearchTitle: " + this.getSearchTitle());
		 System.out.println("searchTitle: " + searchTitle);
		
		
		query.setFirstResult(start);
		query.setMaxResults(maxRows);	
		
		@SuppressWarnings("unchecked")		
		List<Movie> allmovies = query.list();
		
		ArrayList<AList> aList = new ArrayList<AList>();
	
		for(Movie movie: allmovies)
		{
			AList singleAList = new AList();
			singleAList.setmID(movie.getmID());
			singleAList.setuID(uID);
			singleAList.setRatings(0);
			
			movies.add(movie);
			
			aList.add(singleAList);
		
		}
		session.getTransaction().commit();	
		
		return aList;
	}
	
	public ArrayList<AList> fetchMovies(long uID, SearchDAO searchDAO)
	{
		ArrayList<Movie> movies = new ArrayList<Movie>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		int start, maxRows;
		
		start = 0;
		maxRows = 10;
		
		//Query query = session.createQuery("from Movie M"); //ORI
		
		Query query = session.createQuery("from Movie M where M.movieTitle like :searchTitle ");
		query.setString("searchTitle", "%" +  searchDAO.getSearchTitle() + "%");
		
		System.out.println("Searching for movies with title like: " + searchDAO.getSearchTitle());
		
		query.setFirstResult(start);
		query.setMaxResults(maxRows);	
		
		@SuppressWarnings("unchecked")		
		List<Movie> allmovies = query.list();
		
		ArrayList<AList> aList = new ArrayList<AList>();
	
		for(Movie movie: allmovies)
		{
			System.out.println("mID: " + movie.getmID());
			
			AList singleAList = new AList();
			singleAList.setmID(movie.getmID());
			singleAList.setuID(uID);
			singleAList.setRatings(0);
			
			movies.add(movie);
			
			aList.add(singleAList);
		
		}
		session.getTransaction().commit();	
		
		for(AList mov: aList)
		{
			System.out.println(mov.getmID());
		}
		
		
		return aList;
	}
	
	
	public void addMovie(AList movie)
	{
	}
	
}

