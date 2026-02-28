package assignment.sirenalert.service;

import assignment.sirenalert.model.Fire;
import assignment.sirenalert.model.Siren;
import assignment.sirenalert.model.SirenStatus;
import assignment.sirenalert.repository.FireRepository;
import assignment.sirenalert.repository.SirenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FireServiceImpl implements FireService {
    private final FireRepository fireRepository;
    private final SirenRepository sirenRepository;
    private final DistanceCalculation distanceCalculation;

    public FireServiceImpl(FireRepository fireRepository, SirenRepository sirenRepository, DistanceCalculation distanceCalculation) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
        this.distanceCalculation = distanceCalculation;
    }

    @Override
    public Fire createFire(Fire fireCreated) {
        Fire fire = new Fire();
        fire.setFireLatitude(fireCreated.getFireLatitude());
        fire.setFireLongitude(fireCreated.getFireLongitude());
        fire.setTimestamp(LocalDateTime.now());


        List<Siren> nearby = sirenRepository.findAll().stream()
                .filter(s -> s.getOutOfServiceSince() == null)
                .filter(s -> distanceCalculation.calculateDistanceKM(
                        s.getSirenLatitude(), s.getSirenLongitude(),
                        fire.getFireLatitude(), fire.getFireLongitude()
                ) <= 10)
                .toList();

        nearby.forEach(s -> s.setStatus(SirenStatus.DANGER));

        fire.setSirens(nearby);
        fireRepository.save(fire);
        sirenRepository.saveAll(nearby);

        return fire;
    }


    @Override
    public Fire updateFire(int id, Fire updated) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fire not found"));
        fire.setFireLatitude(updated.getFireLatitude());
        fire.setFireLongitude(updated.getFireLongitude());
        fire.setTimestamp(LocalDateTime.now());
        return fireRepository.save(fire);
    }

    @Override
    public void deleteFire(int id) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fire not found"));

        // Reset all affected sirens
        List<Siren> affectedSirens = fire.getSirens();
        affectedSirens.forEach(s -> s.setStatus(SirenStatus.PEACE));
        sirenRepository.saveAll(affectedSirens);

        // Delete fire
        fireRepository.delete(fire);
    }


    @Override
    public List<Fire> getAllFires() {
        return fireRepository.findAll();
    }


    @Override
    public void recalcAllSirens() {
        List<Fire> activeFires = fireRepository.findAll();

        List<Siren> sirens = sirenRepository.findAll();

        for (Siren s : sirens) {
            if (s.getOutOfServiceSince() != null) {
                continue;
            }

            boolean danger = activeFires.stream().anyMatch(f ->
                    distanceCalculation.calculateDistanceKM(
                            s.getSirenLatitude(),
                            s.getSirenLongitude(),
                            f.getFireLatitude(),
                            f.getFireLongitude()
                    ) <= 10
            );

            s.setStatus(danger ? SirenStatus.DANGER : SirenStatus.PEACE);
        }

        sirenRepository.saveAll(sirens);
    }

}
