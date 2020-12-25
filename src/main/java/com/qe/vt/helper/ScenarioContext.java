package com.qe.vt.helper;

public class ScenarioContext {
	String jsonFile;
	static ScenarioContext scenarioContext;
	
	private ScenarioContext() {
	}
	
	public static ScenarioContext getInstance() {
		if(null!=scenarioContext) {
			scenarioContext = new ScenarioContext();
			}
		return scenarioContext;
	}

}
