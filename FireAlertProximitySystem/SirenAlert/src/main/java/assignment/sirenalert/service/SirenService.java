package assignment.sirenalert.service;

import assignment.sirenalert.model.Siren;

import java.util.List;

public interface SirenService {

    //liste af sirens
    List<Siren> getAllSiren();

    //liste med specifik id
    Siren getSirenById(int id);

    //lav en sirene
    Siren createSiren(Siren siren);

    //opdater sirene
    Siren updateSiren(int id, Siren updatedSiren);

    //slet sirene
    void deleteSiren(int id);
}
