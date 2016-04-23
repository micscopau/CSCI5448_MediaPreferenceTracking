package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;

public interface StrategyLists 
{
	public ArrayList<Moviedisplayformat> fetchMovies(long uID);
	public void addMovie(AList movie);

}
