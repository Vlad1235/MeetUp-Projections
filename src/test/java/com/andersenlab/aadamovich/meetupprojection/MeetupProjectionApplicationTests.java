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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Car porsche = new Car(null, "Porsche", "Cayenne", "2222 BB", null);
        Car rover = new Car(null, "Land Rover", "Freelander", "3333 CC", null);

        Owner ivan = new Owner(null, "Ivan", "Ivanov", new ArrayList<>());
        ivan.addCarToOwner(rover);

        Owner semen = new Owner(null, "Semen", "Semenov", new ArrayList<>());
        semen.addCarToOwner(lexus);
        semen.addCarToOwner(porsche);
        ownerRepository.saveAll(Arrays.asList(ivan, semen));
    }

    @Test
    void testEntity() {
		System.out.println("-------------ENTITY TEST-----------------");
        List<Owner> entities = ownerRepository.findAll();
        entities.forEach(System.out::println);
    }

    @Test
	void testClosedProjection() {
		System.out.println("-------------CLOSED PROJECTION TEST-----------------");
		List<ClosedProjectionOwner> closedProjections = ownerRepository.findClosedProjectionBy();
		closedProjections
				.forEach(p -> System.out.printf(
						"Class: %s | FirstName = %s | Manufacturer = %s%n", p.getClass(), p.getFirstName(), p.getCars().get(0).getManufacturer()));


		List<ClosedProjectionCar> by = carRepository.findClosedProjectionBy();
		for (ClosedProjectionCar closedProjectionCar : by) {
			System.out.println(closedProjectionCar.getClass());
			System.out.printf("Manufacturer = %s | Number = %s | Owner = %s", closedProjectionCar.getManufacturer(), closedProjectionCar.getNumber(), closedProjectionCar.getOwner().getFirstName());
		}
	}

	@Test
	void testOpenProjection() {
		System.out.println("-------------OPEN PROJECTION TEST-----------------");
		List<OpenProjectionOwner> openProjections = ownerRepository.findOpenProjectionBy();
		openProjections
				.forEach(p -> System.out.printf(
						"Class: %s | FullName = %s | Manufacturer = %s%n", p.getClass(), p.getFullName(), p.getCars().get(0).getManufacturerEdited()));


		List<OpenProjectionCar> by = carRepository.findOpenProjectionBy();
		for (OpenProjectionCar openProjectionCar : by) {
			System.out.println(openProjectionCar.getClass().getName());
			System.out.printf("Manufacturer = %s | Number = %s | Owner = %s", openProjectionCar.getManufacturerEdited(), openProjectionCar.getNumber(), openProjectionCar.getOwner().getFullName());
		}
	}

	@Test
	void testClassBasedProjection() {
		System.out.println("-------------CLASS BASED PROJECTION TEST-----------------");
		List<ClassBasedProjectionOwner> classProjection = ownerRepository.findClassBasedBy();
		classProjection
				.forEach(p -> System.out.printf(
						"Class: %s | FullName = %s | Manufacturer = %s%n", p.getClass().getSimpleName(), p.getFirstName(), p.getLastName()));

		List<ClassBasedProjectionCar> classBasedBy = carRepository.findClassBasedBy();
		for (ClassBasedProjectionCar classBasedProjectionCar : classBasedBy) {
			System.out.println(classBasedProjectionCar.getClass());
			System.out.printf("Manufacturer = %s%n",classBasedProjectionCar.getManufacturer());
		}
	}

	@Test
	void testDynamicProjection() {
		System.out.println("-------------CLASS BASED PROJECTION TEST-----------------");
		String name = "Ivan";
		Owner owner = ownerRepository.findDynamicProjectionByFirstName(name, Owner.class);
		System.out.printf("OWNER | Class: %s | Name = %s%n", owner.getClass().getSimpleName(), owner.getFirstName());

		ClosedProjectionOwner closedOwner = ownerRepository.findDynamicProjectionByFirstName(name, ClosedProjectionOwner.class);
		System.out.printf("CLOSED | Class: %s | Name = %s%n", closedOwner.getClass().getSimpleName(), closedOwner.getFirstName());

		OpenProjectionOwner openOwner = ownerRepository.findDynamicProjectionByFirstName(name, OpenProjectionOwner.class);
		System.out.printf("OPEN | Class: %s | Name = %s%n", openOwner.getClass().getSimpleName(), openOwner.getFullName());

		ClassBasedProjectionOwner classOwner = ownerRepository.findDynamicProjectionByFirstName(name, ClassBasedProjectionOwner.class);
		System.out.printf("CLASS BASED | Class: %s | Name = %s%n", classOwner.getClass().getSimpleName(), classOwner.getFirstName());
	}

}
