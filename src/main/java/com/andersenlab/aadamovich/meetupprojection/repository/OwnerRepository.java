package com.andersenlab.aadamovich.meetupprojection.repository;

import com.andersenlab.aadamovich.meetupprojection.entity.Owner;
import com.andersenlab.aadamovich.meetupprojection.projection.class_based.ClassBasedProjectionOwner;
import com.andersenlab.aadamovich.meetupprojection.projection.closed.ClosedProjectionOwner;
import com.andersenlab.aadamovich.meetupprojection.projection.open.OpenProjectionOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<ClosedProjectionOwner> findClosedProjectionBy();

    List<OpenProjectionOwner> findOpenProjectionBy();

    List<ClassBasedProjectionOwner> findClassBasedBy();

    <T> T findDynamicProjectionByFirstName(String firstName, Class<T> type);

    <T>Stream<T> findStreamDynamicProjectionBy(Class<T> type);

    <T> Page<T> findPageDynamicProjectionBy(Pageable pageable, Class<T> type);
}
