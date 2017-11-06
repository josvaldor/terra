/* Controller.java
 * Date 201710
 * Author Joaquin Rodriguez
 */
package org.josvaldor.prospero.terra.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class Controller {
    
    @RequestMapping("/space")
    public Object space(@RequestParam(value="time", defaultValue="null") String time){
    	Object object  = null;
    	if(time != null && !time.equals("null"))
    		object = null;//solar.getEnergyList(time);//list does not work as return object
    	return object;
    }
    
    @RequestMapping("/time")
    public Object time(@RequestParam(value="index") String index,
    				   @RequestParam(value="start") String start,
    				   @RequestParam(value="end") String end,
    				   @RequestParam(value="threshold") String threshold,
    				   @RequestParam(value="percent") String percent){
    	Object object = null;//solar.searchSpace(index, start, end, Double.parseDouble(threshold), Double.parseDouble(percent));
    	return object;
    }
}