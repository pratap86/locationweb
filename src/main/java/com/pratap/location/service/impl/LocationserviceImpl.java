package com.pratap.location.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratap.location.entities.Location;
import com.pratap.location.repos.LocationRepository;
import com.pratap.location.service.LocationService;
@Service
public class LocationserviceImpl implements LocationService {

	@Autowired
	LocationRepository repository;
	
	@Override
	public Location saveLocation(Location location) {
		return repository.save(location);
	}

	@Override
	public Location updateLocation(Location location) {
		return repository.save(location);
	}

	@Override
	public void deleteLocation(Location location) {
		repository.delete(location);
	}

	@Override
	public Location getLocationById(int id) {
		Optional<Location> optional = repository.findById(id);
		Location location =null;
		if(optional.isPresent()) {
			location = optional.get();
		}
		return location;
	}

	@Override
	public List<Location> getAllLocations() {
		return repository.findAll();
	}

	public LocationRepository getRepository() {
		return repository;
	}

	public void setRepository(LocationRepository repository) {
		this.repository = repository;
	}

}
