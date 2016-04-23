package com.mycu.dao;

import java.util.ArrayList;

import com.mycu.model.AList;

public interface StrategyDAO 
{
	public ArrayList<AList> fetchMovies(long uID);
	public void addMovie(AList movie);
}
