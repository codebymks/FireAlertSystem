package assignment.sirenalert.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Fire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fireId;
    private double fireLatitude;
    private double fireLongitude;
    private LocalDateTime timestamp;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "fire_Siren",
            joinColumns = @JoinColumn(name = "fire_Id"),
            inverseJoinColumns = @JoinColumn(name = "siren_Id")
    )
    private List<Siren> sirens;

    public int getFireId() {
        return fireId;
    }

    public void setFireId(int fireId) {
        this.fireId = fireId;
    }

    public double getFireLatitude() {
        return fireLatitude;
    }

    public void setFireLatitude(double fireLatitude) {
        this.fireLatitude = fireLatitude;
    }

    public double getFireLongitude() {
        return fireLongitude;
    }

    public void setFireLongitude(double fireLongitude) {
        this.fireLongitude = fireLongitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Siren> getSirens() {
        return sirens;
    }

    public void setSirens(List<Siren> sirens) {
        this.sirens = sirens;
    }
}
