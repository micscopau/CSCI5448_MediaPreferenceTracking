package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;
import com.mycu.dao.SearchDAO;
import com.mycu.dao.StrategyDAO;

public class ContextLists 
{

	private StrategyLists strategy;
	
	public ContextLists(StrategyLists strategy) 
	{
		this.strategy=strategy;
	}
	
	public ArrayList<Moviedisplayformat> executeFetchMovieStrategy(long uID)
	{
		return strategy.fetchMovies(uID);
	}
	
	public ArrayList<Moviedisplayformat> executeFetchMovieStrategy(long uID, SearchDAO searchDAO)
	{
	      return strategy.fetchMovies(uID, searchDAO);
	}
	
	public void executeAddMovieStrategy(AList movie)
	{
		
			strategy.addMovie(movie);
		
	}
}
