package com.mycu.dao;

import java.util.ArrayList;

import com.mycu.model.AList;

public class ContextDAO 
{
	private StrategyDAO strategy;
	
	public ContextDAO(StrategyDAO strategy) 
	{
		this.strategy=strategy;
	}
	
	public ArrayList<AList> executeFetchMovieStrategy(long uID)
	{
	      return strategy.fetchMovies(uID);
	}
	
	public void executeAddMovieStrategy(AList movie)
	{
		strategy.addMovie(movie);
	}

}
