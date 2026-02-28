package assignment.sirenalert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Siren {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sirenId;

    private double sirenLatitude;
    private double sirenLongitude;

    @Enumerated(EnumType.STRING)
    private SirenStatus status;

    private LocalDateTime outOfServiceSince;

    @JsonIgnore
    @ManyToMany(mappedBy = "sirens")
    private List<Fire> fires = new ArrayList<>();


    public int getSirenId() {
        return sirenId;
    }

    public void setSirenId(int sirenId) {
        this.sirenId = sirenId;
    }

    public double getSirenLatitude() {
        return sirenLatitude;
    }

    public void setSirenLatitude(double sirenLatitude) {
        this.sirenLatitude = sirenLatitude;
    }

    public double getSirenLongitude() {
        return sirenLongitude;
    }

    public void setSirenLongitude(double sirenLongitude) {
        this.sirenLongitude = sirenLongitude;
    }

    public SirenStatus getStatus() {
        return status;
    }

    public void setStatus(SirenStatus status) {
        this.status = status;
    }

    public LocalDateTime getOutOfServiceSince() {
        return outOfServiceSince;
    }

    public void setOutOfServiceSince(LocalDateTime outOfServiceSince) {
        this.outOfServiceSince = outOfServiceSince;
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void setFires(List<Fire> fires) {
        this.fires = fires;
    }
}
