package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.dao.ContextDAO;
import com.mycu.dao.IgnoreDAO;
import com.mycu.dao.MovieDAO;
import com.mycu.dao.SearchDAO;
import com.mycu.dao.StrategyDAO;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;

public class IgnoreList implements StrategyLists
{
	public long mID,uID;
	public String movieTitle;
	public boolean wish,ignore;
	public int ratings;
	
	MovieDAO moviedao= new MovieDAO();
	

	public IgnoreList() 
	{
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Moviedisplayformat> fetchMovies(long uID)
	{
		ArrayList<AList> movies= new ArrayList<AList>();
		ArrayList<Moviedisplayformat> formatmovies= new ArrayList<Moviedisplayformat>();	
		ContextDAO context = new ContextDAO(new IgnoreDAO());
		
		System.out.println("UID in ignore page is "+uID);
		
		movies=context.executeFetchMovieStrategy(uID);
		
		for(AList mov: movies)
		{
			mID=mov.getmID();;
			movieTitle=moviedao.getMovie(mID);
			ratings=0;
			wish= false;
			ignore=true;
			
			Moviedisplayformat formatmovie = new Moviedisplayformat(movieTitle,wish,ignore,ratings);
			formatmovies.add(formatmovie);
			
		}
		
		return formatmovies;
	}
	
	public ArrayList<Moviedisplayformat> fetchMovies(long uID, SearchDAO searchDAO)
	{
		ArrayList<AList> movies= new ArrayList<AList>();
		ArrayList<Moviedisplayformat> formatmovies= new ArrayList<Moviedisplayformat>();	
		ContextDAO context = new ContextDAO(new IgnoreDAO());
		
		System.out.println("UID in ignore page is "+uID);
		
		movies=context.executeFetchMovieStrategy(uID);
		
		for(AList mov: movies)
		{
			mID=mov.getmID();;
			movieTitle=moviedao.getMovie(mID);
			ratings=0;
			wish= false;
			ignore=true;
			
			Moviedisplayformat formatmovie = new Moviedisplayformat(movieTitle,wish,ignore,ratings);
			formatmovies.add(formatmovie);
			
		}
		
		return formatmovies;
	}
	
	public void addMovie(AList movie)
	{
		
		ContextDAO context = new ContextDAO(new IgnoreDAO());
		context.executeAddMovieStrategy(movie);
		
	}
	
}
