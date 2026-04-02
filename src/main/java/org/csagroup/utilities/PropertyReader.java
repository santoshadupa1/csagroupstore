package org.csagroup.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class PropertyReader 
{		
    Properties prop = new Properties();	
	public PropertyReader()
	{
		File file = new File("./src/test/resources/configurations/config.properties");	
		InputStream is = null;		
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public String getProperty(String key)
	{		
		return prop.getProperty(key);
	}	
	//For testing
	public static void main(String[] args) {
		PropertyReader pr = new PropertyReader();
		System.out.println(pr.getProperty("browser"));		
	}
	
}
