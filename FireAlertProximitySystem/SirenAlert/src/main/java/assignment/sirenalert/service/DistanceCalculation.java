package assignment.sirenalert.service;

import org.springframework.stereotype.Component;

@Component
public class DistanceCalculation {

    public double calculateDistanceKM(double latitude1, double longitude1, double latitude2, double longitude2) {
        int earthRadiusKM = 6371;
        //Trækker breddegraderne og længdegraderne fra hinanden. Laver det til radianer (java sin, cos virker kun med radianer)
        double differenceInLatitudeInRadians = Math.toRadians(latitude2 - latitude1);
        double differenceInLongitudeInRadians = Math.toRadians(longitude2 - longitude1);

        //forskel mellem punkterne.
        double proximityValue = Math.pow(Math.sin(differenceInLatitudeInRadians / 2), 2)
                //retter så det passer til at jorden er en kugle.
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2))
                * Math.pow(Math.sin(differenceInLongitudeInRadians / 2), 2);

        //beregner vinklen mellem punkterne.
        double centralAngleBetweenPoints = 2 * Math.atan2(Math.sqrt(proximityValue), Math.sqrt(1 - proximityValue));

        //hvor langt du går langs jordens overflade.
        return earthRadiusKM * centralAngleBetweenPoints;
    }
}
