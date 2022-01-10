package main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import fol.Predicate;
import type.SortType;

public class Environment {
	private LinkedList<LinkedHashMap<String,Predicate>> aliasMappings;
	private LinkedList<LinkedHashMap<String,SortType>> params;
	private static Environment instance;
	
	private LinkedHashMap<String,Predicate> fromMappings;
	private LinkedHashMap<String,SortType> fromParams;

	public LinkedHashMap<String, Predicate> getFromMappings() {
		return fromMappings;
	}

	public void setFromMappings(LinkedHashMap<String, Predicate> fromMappings) {
		this.fromMappings = fromMappings;
	}

	public LinkedHashMap<String, SortType> getFromParams() {
		return fromParams;
	}

	public void setFromParams(LinkedHashMap<String, SortType> fromParams) {
		this.fromParams = fromParams;
	}

	private Environment() {
		aliasMappings = new LinkedList<LinkedHashMap<String,Predicate>>();
		params = new LinkedList<LinkedHashMap<String,SortType>>();
		fromMappings = new LinkedHashMap<String, Predicate>();
		fromParams = new LinkedHashMap<String, SortType>();
	}
	
	public static Environment initialize() {
		if(instance == null){
            instance = new Environment();
        } else {
        	instance.aliasMappings.clear();
        	instance.params.clear();
        	instance.fromMappings.clear();
        	instance.fromParams.clear();
        }
        return instance;
	}
	
	public static Environment getInstance() {
		return instance;
	}

	public HashMap<String, Predicate> getAliasMapping() {
		return aliasMappings.peek();
	}

	public LinkedHashMap<String,SortType> getParams() {
		return params.peek();
	}

	public void moveOn() {
		aliasMappings.push(new LinkedHashMap<String, Predicate>(instance.fromMappings));
		params.push(new LinkedHashMap<String, SortType>(instance.fromParams));
	}

}
