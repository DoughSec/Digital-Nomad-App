package com.nomad.dao;

import com.nomad.model.Location;
import com.nomad.model.User;

import java.util.List;

public interface LocationDao {
    List<Location> getLocations();
    Location getLocationById(int id);
    Location createLocation(Location Location);
    Location updateLocation(Location changedLocation);
    int deleteLocationById(int id);
}
