package com.andersenlab.aadamovich.meetupprojection;

import com.andersenlab.aadamovich.meetupprojection.entity.Car;
import com.andersenlab.aadamovich.meetupprojection.entity.Owner;
import com.andersenlab.aadamovich.meetupprojection.projection.class_based.ClassBasedProjectionCar;
import com.andersenlab.aadamovich.meetupprojection.projection.class_based.ClassBasedProjectionOwner;
import com.andersenlab.aadamovich.meetupprojection.projection.closed.ClosedProjectionCar;
import com.andersenlab.aadamovich.meetupprojection.projection.closed.ClosedProjectionOwner;
import com.andersenlab.aadamovich.meetupprojection.projection.open.OpenProjectionCar;
import com.andersenlab.aadamovich.meetupprojection.projection.open.OpenProjectionOwner;
import com.andersenlab.aadamovich.meetupprojection.repository.CarRepository;
import com.andersenlab.aadamovich.meetupprojection.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeetupProjectionApplicationTests {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    CarRepository carRepository;


    @BeforeAll
    void prepareDatabase() {
        Car lexus = new Car(null, "Lexus", "LX", "1111 AA", null);
        Car rover = new Car(null, "Land Rover", "Freelander", "3333 CC", null);

        Owner ivan = new Owner(null, "Ivan", "Ivanov", null);
        ivan.addCarToOwner(rover);

        Owner semen = new Owner(null, "Semen", "Semenov", null);
        semen.addCarToOwner(lexus);
        ownerRepository.saveAll(Arrays.asList(ivan, semen));
    }

    @Test
    void testEntity() {
        System.out.println("===========ENTITY TEST===========");
        List<Owner> entities = ownerRepository.findAll();

        String className = entities.get(0).getClass().getSimpleName();
        System.out.printf("CLASS: %s%n%n", className);

        entities.
                forEach(e -> System.out.printf(
                        "FirstName = %-5s | LastName = %-7s | Manufacturer = %s%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getCar().getManufacturer()));
    }

    @Test
    void testClosedProjection() {
        System.out.println("===========CLOSED PROJECTION TEST===========");
        List<ClosedProjectionOwner> closedOwner = ownerRepository.findClosedProjectionBy();

        String className = closedOwner.get(0).getClass().getSimpleName();
        String targetProxyClass = AopUtils.getTargetClass(closedOwner.get(0)).getSimpleName();
        System.out.printf("CLASS: %s | TARGET PROXY CLASS: %s%n%n", className, targetProxyClass);

        closedOwner
                .forEach(o -> System.out.printf(
                        "FirstName = %-5s | Car = %s%n",
                        o.getFirstName(),
                        o.getCar()));

        System.out.println("--------------------");
        List<ClosedProjectionCar> closedCar = carRepository.findClosedProjectionBy();

        String classNameCar = closedCar.get(0).getClass().getSimpleName();
        String targetProxyClassCar = AopUtils.getTargetClass(closedCar.get(0)).getSimpleName();
        System.out.printf("CLASS: %s | TARGET PROXY CLASS: %s%n%n", classNameCar, targetProxyClassCar);

        closedCar
                .forEach(c -> System.out.printf(
                        "Manufacturer = %-10s | Number = %-7s | Owner = %s%n",
                        c.getManufacturer(),
                        c.getNumber(),
                        c.getOwner().getFirstName()));
    }

    @Test
    void testOpenProjection() {
        System.out.println("===========OPEN PROJECTION TEST===========");
        List<OpenProjectionOwner> openOwner = ownerRepository.findOpenProjectionBy();

        String className = openOwner.get(0).getClass().getSimpleName();
        String targetProxyClass = AopUtils.getTargetClass(openOwner.get(0)).getSimpleName();
        System.out.printf("CLASS: %s | TARGET PROXY CLASS: %s%n%n", className, targetProxyClass);

        openOwner
                .forEach(p -> System.out.printf(
                        "DefaultMethod = %-13s | TargetSpEL = %-13s | ObjectBeanSpEL = %s%n",
                        p.getFullNameDefaultMethod(),
                        p.getFullNameByTargetSpEl(),
                        p.getFullNameByObjectBeanSpEl()));

        System.out.println("--------------------");
        List<OpenProjectionCar> openCar = carRepository.findOpenProjectionBy();

        String classNameCar = openCar.get(0).getClass().getSimpleName();
        String targetProxyClassCar = AopUtils.getTargetClass(openCar.get(0)).getSimpleName();
        System.out.printf("CLASS: %s | TARGET PROXY CLASS: %s%n%n", classNameCar, targetProxyClassCar);

        openCar
                .forEach(c -> System.out.printf(
                        "Manufacturer = %-22s | Number = %-7s | Owner = %s%n",
                        c.getManufacturerEdited(),
                        c.getNumber(),
                        c.getOwner().getFullNameDefaultMethod()));
    }

    @Test
    void testClassBasedProjection() {
        System.out.println("===========CLASS BASED PROJECTION TEST===========");
        List<ClassBasedProjectionOwner> classOwner = ownerRepository.findClassBasedBy();

        String className = classOwner.get(0).getClass().getSimpleName();
        System.out.printf("CLASS: %s%n%n", className);

        classOwner
                .forEach(p -> System.out.printf(
                        "FirstName = %-5s | LastName = %s%n",
                        p.getFirstName(),
                        p.getLastName()));

        System.out.println("--------------------");
        List<ClassBasedProjectionCar> classCar = carRepository.findClassBasedBy();

        String classNameCar = classCar.get(0).getClass().getSimpleName();
        System.out.printf("CLASS: %s%n%n", classNameCar);

        classCar
                .forEach(c -> System.out.printf(
                        "Manufacturer = %-10s | Model = %-10s | Number = %s%n",
                        c.getManufacturer(),
                        c.getModel(),
                        c.getNumber()));
    }

    @Test
    void testDynamicProjection() {
        System.out.println("===========CLASS BASED PROJECTION TEST===========");
        String name = "Ivan";
        Owner owner = ownerRepository.findDynamicProjectionByFirstName(name, Owner.class);
        System.out.printf("ENTITY      | Class: %s | Name = %s%n", owner.getClass().getSimpleName(), owner.getFirstName());

        ClosedProjectionOwner closedOwner = ownerRepository.findDynamicProjectionByFirstName(name, ClosedProjectionOwner.class);
        System.out.printf("CLOSED      | Class: %s | Name = %s%n", closedOwner.getClass().getSimpleName(), closedOwner.getFirstName());

        OpenProjectionOwner openOwner = ownerRepository.findDynamicProjectionByFirstName(name, OpenProjectionOwner.class);
        System.out.printf("OPEN        | Class: %s | Name = %s%n", openOwner.getClass().getSimpleName(), openOwner.getFullNameDefaultMethod());

        ClassBasedProjectionOwner classOwner = ownerRepository.findDynamicProjectionByFirstName(name, ClassBasedProjectionOwner.class);
        System.out.printf("CLASS BASED | Class: %s | Name = %s%n", classOwner.getClass().getSimpleName(), classOwner.getFirstName());
    }

    @Transactional
    @Test
    void testPageAndStream() {
        System.out.println("===========STREAM TEST===========");
        Stream<ClosedProjectionOwner> stream = ownerRepository.findStreamDynamicProjectionBy(ClosedProjectionOwner.class);

        stream.
                forEach(o -> System.out.printf(
                        "FirstName = %-5s | Car = %s%n",
                        o.getFirstName(),
                        o.getCar()));

        System.out.println("===========PAGE TEST===========");
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "firstName");

        Page<ClassBasedProjectionOwner> page = ownerRepository.findPageDynamicProjectionBy
                (pageRequest, ClassBasedProjectionOwner.class);

        page.get()
                .forEach(p -> System.out.printf(
                        "FirstName = %-5s | LastName = %s%n",
                        p.getFirstName(),
                        p.getLastName()));
    }
}
