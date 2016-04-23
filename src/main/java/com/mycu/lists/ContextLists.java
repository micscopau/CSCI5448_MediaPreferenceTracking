package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;

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
	
	public void executeAddMovieStrategy(AList movie)
	{
		
			strategy.addMovie(movie);
		
	}
}
