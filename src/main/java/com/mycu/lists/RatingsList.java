package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.dao.ContextDAO;

import com.mycu.dao.MovieDAO;
import com.mycu.dao.RatingsDAO;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;

public class RatingsList implements StrategyLists
{
	public long mID,uID;
	public String movieTitle;
	public boolean wish,ignore;
	public int ratings;

	MovieDAO moviedao= new MovieDAO();

	public RatingsList() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Moviedisplayformat> fetchMovies(long uID)
	{
		ArrayList<AList> movies= new ArrayList<AList>();
		ArrayList<Moviedisplayformat> formatmovies= new ArrayList<Moviedisplayformat>();	
		ContextDAO context = new ContextDAO(new RatingsDAO());
		
		System.out.println("UID in rate page is "+uID);
		
		movies=context.executeFetchMovieStrategy(uID);
		
		for(AList mov: movies)
		{
			mID=mov.getmID();
			movieTitle=moviedao.getMovie(mID);
			ratings=mov.getRating();
			wish= false;
			ignore=false;
			
			Moviedisplayformat formatmovie = new Moviedisplayformat(movieTitle,wish,ignore,ratings);
			formatmovies.add(formatmovie);
			
		}
		
		return formatmovies;
	}
	
	
	public void addMovie(AList movie)
	{
		
		ContextDAO context = new ContextDAO(new RatingsDAO());
		context.executeAddMovieStrategy(movie);
			
	}
	
}
