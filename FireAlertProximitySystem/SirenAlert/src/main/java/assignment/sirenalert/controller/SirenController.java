package assignment.sirenalert.controller;

import assignment.sirenalert.model.Siren;
import assignment.sirenalert.service.SirenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sirens")
public class SirenController {

    @Autowired
    private SirenService sirenService;

    // /sirens
    @GetMapping()
    public List<Siren> getSiren() {
        return sirenService.getAllSiren();
    }

    // /sirens
    @PostMapping()
    public Siren addSiren(@RequestBody Siren siren) {
        return sirenService.createSiren(siren);
    }

    // /sirens/{id}
    @PutMapping("/{id}")
    public Siren updateSiren(@PathVariable int id, @RequestBody Siren siren) {
        System.out.println("Received update: " + siren);
        return sirenService.updateSiren(id, siren);
    }

    // /sirens/{id}
    @DeleteMapping("/{id}")
    public void deleteSiren(@PathVariable int id) {
        sirenService.deleteSiren(id);
    }
}
