package com.restapi.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class PostXMl
{
	public static void main(String args[])
	{
		String xmlString = "<Person name= 'test'> "
				+ " <PhoneNumber number='12345678'>"
				
				+ " <Application name='Yahoo'>"
				+ "<PlayList name='' time='' />"
				+ "</Application>"
				
				+ "<Application name='test'> "
				+ "<PlayList name='' time='' />"
				+ "</Application>"
				
				+ "</PhoneNumber>"
				+ "</Person>";
		boolean success = false;
		@SuppressWarnings("deprecation")
		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpPost httppost = new HttpPost("http://localhost:8080/kafkaWebService/kafkaWebService");
			InputStream inputStream=new ByteArrayInputStream(xmlString.getBytes());//init your own inputstream
			InputStreamEntity inputStreamEntity=new InputStreamEntity(inputStream);
			httppost.setEntity(inputStreamEntity);
			HttpResponse r = httpclient.execute(httppost);
			System.out.println(r.getStatusLine().getStatusCode());
			if(r.getStatusLine().getStatusCode() == 200)
			{
				System.out.println("Posted success");
			}
			else
			{
				System.out.println("Posted failuer");
			}
			HttpEntity entity = r.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseString);
			
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	
}