package com.pratap.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratap.location.entities.Location;
import com.pratap.location.repos.LocationRepository;
import com.pratap.location.service.LocationService;
import com.pratap.location.util.EmailUtil;
import com.pratap.location.util.ReportUtil;

@Controller
public class LocationController {

	@Autowired
	LocationService service;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	LocationRepository repository;
	
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation";
	}
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		Location locationSaved = service.saveLocation(location);
		String msg = "Location saved with location id : "+locationSaved.getId();
		modelMap.addAttribute("msg", msg);
		//java.net.ConnectException: Connection timed out
//		emailUtil.sendEmail("narayanpratap30", "Location saved", "Location saved is successfully and about to return a response");
		return "createLocation";
	}
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap) {
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	@RequestMapping("/deleteLocation")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap) {
//		Location location = service.getLocationById(id);
		// reduce the database call
		Location location = new Location();
		location.setId(id);
		service.deleteLocation(location);
		// after deleting the record, fetched the updated locations & return back to the response with modelMap
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id") int id, ModelMap modelMap) {
		Location location = service.getLocationById(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}
	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		service.updateLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	@RequestMapping("/generateReport")
	public String generateReport() {
		String path = servletContext.getRealPath("/");
		// 1.get the data from db
		List<Object[]> data = repository.findTypeAndTypeCount();
		// 2. need utility class, which report pass the data
		// 2.1 use servlet context for path
		reportUtil.generatePieChart(path, data);
		return "report";
	}
}
