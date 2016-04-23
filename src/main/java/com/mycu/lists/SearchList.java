package com.mycu.lists;

import java.util.ArrayList;


import com.mycu.dao.MovieDAO;

import com.mycu.dao.SearchDAO;

import com.mycu.model.Moviedisplayformat;
import com.mycu.model.AList;
import com.mycu.model.Movie;

public class SearchList implements StrategyLists
{
	public long mID,uID;
	public String movieTitle;
	public boolean wish,ignore;
	public int ratings;
	
	SearchDAO searchdao = new SearchDAO();
	MovieDAO moviedao= new MovieDAO();
	
	public SearchList() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Moviedisplayformat> fetchMovies(long uID)
	{
		ArrayList<Movie> movies= new ArrayList<Movie>();
		ArrayList<Moviedisplayformat> formatmovies= new ArrayList<Moviedisplayformat>();
		
		System.out.println("UID in search page is "+uID);
		
		movies=searchdao.fetchMovies(uID);
		
		for(Movie mov: movies)
		{
			
			movieTitle=mov.getMovieTitle();
			ratings=0;
			wish= false;
			ignore=false;
			
			Moviedisplayformat formatmovie = new Moviedisplayformat(movieTitle,wish,ignore,ratings);
			formatmovies.add(formatmovie);
			
		}
		
		return formatmovies;
	}


	public void addMovie(AList movie)
	{
		
	}
}
