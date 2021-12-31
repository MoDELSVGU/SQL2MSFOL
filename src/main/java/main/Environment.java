package main;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Environment {
	private LinkedHashMap<String, String> aliasMapping;
	private static Environment instance;

	private Environment() {
		aliasMapping = new LinkedHashMap<String, String>();
	}
	
	public static Environment initialize() {
		if(instance == null){
            instance = new Environment();
        } else {
        	instance.aliasMapping.clear();
        }
        return instance;
	}
	
	public static Environment getInstance() {
		return instance;
	}

	public HashMap<String, String> getAliasMapping() {
		return aliasMapping;
	}
}
