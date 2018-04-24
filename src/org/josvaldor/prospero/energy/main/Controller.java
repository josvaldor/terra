/* Controller.java
 * Date 201710
 * Author Joaquin Rodriguez
 */
package org.josvaldor.prospero.energy.main;

import org.josvaldor.prospero.energy.system.Solar;
import org.josvaldor.prospero.energy.system.planet.earth.Earth;
import org.josvaldor.prospero.energy.system.star.sun.Sun;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@EnableAutoConfiguration
@RestController
public class Controller {
    private Solar solar = new Solar();
    
    @RequestMapping("/space")
    public Object space(@RequestParam(value="time", defaultValue="null") String time){
    	Object object  = null;
    	if(time != null && !time.equals("null"))
    		object = solar.getEnergyList(time);//list does not work as return object
    	GsonBuilder builder = new GsonBuilder();
    	Gson gson = builder.create();
    	String json = gson.toJson(object);
    	return json;
    }
    
    @RequestMapping("/time")
    public Object time(@RequestParam(value="index", defaultValue="null") String index,
    				   @RequestParam(value="start", defaultValue="null") String start,
    				   @RequestParam(value="end", defaultValue="null") String end,
    				   @RequestParam(value="threshold", defaultValue="null") String threshold,
    				   @RequestParam(value="percent", defaultValue="null") String percent){
    	Object object = solar.searchSpace(index, start, end, Double.parseDouble(threshold), Double.parseDouble(percent));
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json = gson.toJson(object);
    	return json;
    }
    
    @RequestMapping("/earth")
    public Object earth(@RequestParam(value="time", defaultValue="null") String time){
    	Object object  = null;
    	if(time != null && !time.equals("null"))
    		object = solar.getCoordinateList(new Earth(solar.getCalendar(null,time), new Sun(solar.getCalendar(null,time))), solar.getEnergyList(solar.getCalendar(null,time)));
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json = gson.toJson(object);
    	return json;
    }
}