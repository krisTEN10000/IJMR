package com.sayit.ijmr.Repository;

import com.sayit.ijmr.Entities.VolumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VolumeRepository extends JpaRepository<VolumeEntity, Long> {


    @Query(value = "SELECT * FROM catogory WHERE volume_name = :volumeName", nativeQuery = true)
    Optional<VolumeEntity> findByVolumeNameNative(@Param("volumeName") String volumeName);

    @Query("SELECT v.volumeName FROM VolumeEntity v")
    List<String> findAllBWithRegex();

    @Query("SELECT v.volumeName  from VolumeEntity v")
    List<String> getAllVolumeName();
}
