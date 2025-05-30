package com.nomad.dao;

import com.nomad.model.Lodging;
import com.nomad.model.User;

import java.util.List;

public interface LodgingDao {
    List<Lodging> getLodgings();
    Lodging getLodgingById(int id);
    Lodging createLodging(Lodging Lodging);
    Lodging updateLodging(Lodging changedLodging);
    int deleteLodgingById(int id);
}
