package assignment.sirenalert.service;

import assignment.sirenalert.model.Fire;

import java.util.List;

public interface FireService {
    Fire createFire(Fire fire);
    Fire updateFire(int id, Fire fire);
    void deleteFire(int id);
    List<Fire> getAllFires();
    void recalcAllSirens();
}

