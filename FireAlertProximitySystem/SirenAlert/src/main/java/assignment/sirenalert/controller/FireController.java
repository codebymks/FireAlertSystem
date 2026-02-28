package assignment.sirenalert.controller;

import assignment.sirenalert.model.Fire;
import assignment.sirenalert.service.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fires")
public class FireController {

    @Autowired
    private FireService fireService;

    // /fires?status=active
    @GetMapping()
    public List<Fire> getFires(@RequestParam(defaultValue = "active") String status) {
        return fireService.getAllFires();
    }

    // /fires
    @PostMapping()
    public Fire createFire(@RequestBody Fire fire) {
        return fireService.createFire(fire);
    }

    //  /fires/{id}/closure
    @PutMapping("/{id}")
    public Fire updateFire(@PathVariable int id, @RequestBody Fire fire) {
        return fireService.updateFire(id,fire);
    }

    @DeleteMapping("/{id}")
    public void deleteFire(@PathVariable int id) {
        fireService.deleteFire(id);
    }

}
