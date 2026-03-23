package assignment.sirenalert.service;

import assignment.sirenalert.model.Fire;
import assignment.sirenalert.model.Siren;
import assignment.sirenalert.repository.FireRepository;
import assignment.sirenalert.repository.SirenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SirenServiceImpl implements SirenService {

    private final FireService fireService;
    private SirenRepository sirenRepository;
    private FireRepository fireRepository;

    @Autowired
    public SirenServiceImpl(FireService fireService, SirenRepository sirenRepository, FireRepository fireRepository) {
        this.fireService = fireService;
        this.sirenRepository = sirenRepository;
        this.fireRepository = fireRepository;
    }

    @Override
    public List<Siren> getAllSiren() {
        return sirenRepository.findAll();
    }

    @Override
    public Siren getSirenById(int id) {
        return sirenRepository.findById(id).orElseThrow(() -> new RuntimeException("Siren not found"));
    }

    @Override
    public Siren createSiren(Siren siren) {
        return sirenRepository.save(siren);
    }

    @Override
    public Siren updateSiren(int id, Siren updatedSiren) {
        Siren siren = getSirenById(id);

        siren.setSirenLatitude(updatedSiren.getSirenLatitude());
        siren.setSirenLongitude(updatedSiren.getSirenLongitude());
        siren.setStatus(updatedSiren.getStatus());
        siren.setOutOfServiceSince(updatedSiren.getOutOfServiceSince());

        fireService.recalcAllSirens();

        return sirenRepository.save(siren);
    }

    @Override
    public void deleteSiren(int id) {
        Siren siren = getSirenById(id);
        List<Fire> fires = fireRepository.findAll();
        //Remove the siren from each fire 
        fires.forEach(f -> f.getSirens().removeIf(s -> s.getSirenId() == id));
        fireRepository.saveAll(fires);

        sirenRepository.deleteById(id);
    }
}
