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
	public int startRow;
	long maxResults;
	

	public long getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(long maxResults) {
		this.maxResults = maxResults;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

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
		maxRows = 20;
		
		//Query query = session.createQuery("from Movie M"); //ORI
		
		Query query = session.createQuery("from Movie M");		
		
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
		
		int start, totalRows; 
		long maxSize;
		
		start = searchDAO.getStartRow();
		totalRows = 20;
		
		//Query query = session.createQuery("from Movie M"); //ORI
		
		maxSize = searchDAO.getMaxResults();
		
		if (maxSize == 0)
		{
			Query qSize = session.createQuery("select count(*) from Movie M where M.movieTitle like :searchTitle");
			qSize.setString("searchTitle", "%" +  searchDAO.getSearchTitle() + "%");
			maxSize = (Long) qSize.uniqueResult();
			System.out.println("Max Size: " + maxSize);
			searchDAO.setMaxResults(maxSize-1);
			
			//if (maxSize == 0) //still empty set, return aList immediately along with message of empty set
			
		}
		
		
		Query query = session.createQuery("from Movie M where M.movieTitle like :searchTitle ");
		query.setString("searchTitle", "%" +  searchDAO.getSearchTitle() + "%");
		
		System.out.println("Searching for movies with title like: " + searchDAO.getSearchTitle());
		
		query.setFirstResult(start);
		query.setMaxResults(totalRows);	
		
		System.out.println("Current Search Row: " + start + " out of a total: " + maxSize);

		@SuppressWarnings("unchecked")		
		List<Movie> allmovies = query.list();
				
		ArrayList<AList> aList = new ArrayList<AList>();
	
		for(Movie movie: allmovies)
		{
			//System.out.println("mID: " + movie.getmID());
			
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
	
	
	public void addMovie(AList movie)
	{
	}
	
}

