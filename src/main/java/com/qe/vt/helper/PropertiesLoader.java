package com.qe.vt.helper;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesLoader {
		
	private static PropertiesLoader instance =null;

    private Properties props = null;

    private PropertiesLoader(){
    	try {
	        this.props=new Properties();
	        File fileUserProp = new File(Constants.PROPERTIES_FILE_CONFIG);
	        props.load(new FileInputStream(fileUserProp));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static PropertiesLoader getInstance(){
        if(instance==null){
            instance=new PropertiesLoader();
        }
        return instance;
    }

    public String getValue(String propKey){
        return props.getProperty(propKey);
    }

    public String getBaseUrl(){
        return props.getProperty("base.url");
    }

    public  String getValidateUrl(){
        return props.getProperty("resource.url.validate");
    }

    public  String getValidateBatchUrl(){
        return props.getProperty("resource.url.validateBatch");
    }

 }
