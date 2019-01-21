package at.htl.cinemamanagement.business;

import at.htl.cinemamanagement.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class InitBean {
    @PersistenceContext
    EntityManager entityManager;

    private InitBean() {
    }

    @PostConstruct
    public void init(){
        System.out.println("****** Init started ******");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cinema cineplexLinz = new Cinema("Cineplex Linz", "Prinz-Eugen-Straße 22, 4020 Linz", LocalDate.parse("1999-12-08", formatter));
        List<Hall> halls = new ArrayList<>();
        halls.add(new Hall("DOLBY CINEMA", 394, cineplexLinz));
        halls.add(new Hall("Saal 2", 133, cineplexLinz));
        halls.add(new Hall("Saal 3", 132, cineplexLinz));
        List<Person> persons = new ArrayList<>();
        persons.add(new Employee("Max", "Huber", "Maxstraße 12, 4040 Linz", LocalDate.of(1973,6,12), "max.huber@gmx.at", "066077006559", cineplexLinz, 3500, 23098, LocalDate.of(2005, 2,1)));
        persons.add(new Employee("Stefan", "Mustermann", "Wienerstraße 20, 4040 Linz", LocalDate.of(1978,3,22), "stefan.mustermann@gmx.at", "066033648665", cineplexLinz, 2400, 98043, LocalDate.of(2010, 5,1)));
        persons.add(new Employee("Rene", "Holzer", "SalzburgerStraße 8, 4040 Linz", LocalDate.of(1976,9,9), "rene.holzer@gmx.at", "06609988877", cineplexLinz,4000, 76873, LocalDate.of(2001, 3,1)));
        persons.add(new Customer("Thomas", "Egger", "SalzburgerStraße 20, 4040 Linz", LocalDate.of(2001,12,19), "thomas.egger@gmx.at", "06609933361", 1000, 100));
        persons.add(new Customer("Roman", "Wallner", "Thallingerweg 28, 4040 Linz", LocalDate.of(1999,4,16), "roman.wallner@gmx.at", "06608397737", 1001, 450));
        //cineplexLinz.setHalls(halls);
        //cineplexLinz.setPersonList(persons);
        entityManager.persist(cineplexLinz);
        halls.forEach(hall -> entityManager.persist(hall));
        persons.forEach(person -> entityManager.persist(person));


        Cinema cineplexGraz = new Cinema("Cineplex Graz", "Alte Poststraße 470, 8055 Graz", LocalDate.parse("2004-06-17", formatter));
        halls = new ArrayList<>();
        entityManager.persist(cineplexGraz);
        halls.add(new Hall("Saal 1", 247, cineplexGraz));
        halls.add( new Hall("Saal 2", 247, cineplexGraz));
        halls.add(new Hall("Saal 3", 184, cineplexGraz));
        persons = new ArrayList<>();
        persons.add(new Employee("Markus", "Graf", "Landhausgasse 12, 8010 Innere Stadt", LocalDate.of(1973,6,12), "markus.graf@gmx.at", "066077006559", cineplexGraz, 3500, 23098, LocalDate.of(2005, 2,1)));
        persons.add(new Employee("Lorenz", "Mair", "Raubergasse 20, 8010 Innere Stadt", LocalDate.of(1978,3,22), "lorenz.mair@gmx.at", "066033648665",cineplexGraz, 2400, 98043, LocalDate.of(2010, 5,1)));
        persons.add(new Employee("Samuel", "Lehner", "Joaneneumring 8, 8010 Innere Stadt", LocalDate.of(1976,9,9), "samuel.lehner@gmx.at", "06609988877",cineplexGraz, 4000, 76873, LocalDate.of(2001, 3,1)));
        persons.add(new Customer("Anton", "Wieser", "Kaiserfeldgasse 20, 8010 Innere Stadt", LocalDate.of(2001,12,19), "anton.wieder@gmx.at", "06609933361", 1000, 100));
        persons.add(new Customer("Liam", "Reiter", "Bischofplatz 28, 8010 Innere Stadt", LocalDate.of(1999,4,16), "liam.reiter@gmx.at", "06608397737", 1001, 450));
        //cineplexGraz.setHalls(halls);
        //cineplexLinz.setPersonList(persons);
        entityManager.persist(cineplexGraz);
        for (Hall hall : halls) entityManager.persist(hall);
        for (Person person : persons) entityManager.persist(person);

        Movie movie = new Movie("Hobbit",LocalDate.of(2001,12,19).toString() );
        Presentation presentation = new Presentation(
                LocalDateTime.of(2018,12,19, 12,15,0).toString(),
                LocalDateTime.of(2018,12,19, 12,15,0).toString(),
                halls.get(0), movie, null);

        entityManager.persist(movie);
        entityManager.persist(presentation);
    }
}
