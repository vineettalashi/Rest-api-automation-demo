package com.qe.vt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import com.qe.vt.constants.Constants;

public class PropertiesLoader {
		
	private static PropertiesLoader instance =null;

    private Properties props = null;

    private PropertiesLoader() throws URISyntaxException{
    	this.props=new Properties();
        File fileUserProp = new File(this.getClass().getClassLoader().getResource(Constants.CONFIG_PATH).toURI());
    	try(FileInputStream fis =new FileInputStream(fileUserProp);)
    	{
    		props.load(fis);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static PropertiesLoader getInstance(){
        if(instance==null){
            try {
				instance=new PropertiesLoader();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
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
