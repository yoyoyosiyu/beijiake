package com.beijiake.repository.resource;

import com.beijiake.data.domain.resouce.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFilename(String filename);

}
