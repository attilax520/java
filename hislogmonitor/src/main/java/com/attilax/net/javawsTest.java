package com.attilax.net;

import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Maps;

 
//   http://localhost/clinewadvice/clinicvaf1insert?lver=22
// http://localhost/cloud/clinewadvice/clinicvaf1insert?lver=22
@Path("clinewadvice")
public class javawsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(javax.ws.rs.core.Application.class);

	}
	
	   @SuppressWarnings({"unchecked", "rawtypes"})
	    @GET
	    @Path("clinicvaf1insert")
	    @Produces({MediaType.APPLICATION_JSON})
	    public Map clinicVAF1Insert(@FormParam("lver")String lver )
	            
	    {
		   
		   Map map=Maps.newHashMap();map.put("lver", lver);
			return map;
		   
	    }

}
