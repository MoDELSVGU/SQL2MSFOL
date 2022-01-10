package fol;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import type.SortType;

public class Predicate {
	private String name;
	private LinkedHashMap<String, SortType> parameters;
	private HashMap<String, Object> referedObjects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedHashMap<String, SortType> getParameters() {
		return parameters;
	}

	public void setParameters(LinkedHashMap<String, SortType> parameters) {
		this.parameters = parameters;
	}

	public HashMap<String, Object> getReferedObjects() {
		return referedObjects;
	}

	public void setReferedObjects(HashMap<String, Object> referedObjects) {
		this.referedObjects = referedObjects;
	}

	public Predicate(String name) {
		this.name = name;
		parameters = new LinkedHashMap<String, SortType>();
		referedObjects = new LinkedHashMap<String, Object>();
	}
	
	@Override
	public String toString() {
		String template = "%s";
		Set<String> keys = parameters.keySet();
		for (String key : keys) {
			template = template.concat(" ").concat(key);
		}
		return String.format(template, name);
	}
}
