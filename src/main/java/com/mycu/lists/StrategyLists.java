package com.mycu.lists;

import java.util.ArrayList;

import com.mycu.model.AList;
import com.mycu.model.Moviedisplayformat;
import com.mycu.dao.SearchDAO;

public interface StrategyLists 
{
	public ArrayList<Moviedisplayformat> fetchMovies(long uID);
	public ArrayList<Moviedisplayformat> fetchMovies(long uID, SearchDAO searchDAO);
	public void addMovie(AList movie);

}
