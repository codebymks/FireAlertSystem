package assignment.sirenalert.service;

import assignment.sirenalert.model.Siren;

import java.util.List;

public interface SirenService {

    List<Siren> getAllSiren();
    Siren getSirenById(int id);
    Siren createSiren(Siren siren);
    Siren updateSiren(int id, Siren updatedSiren);
    void deleteSiren(int id);
}
