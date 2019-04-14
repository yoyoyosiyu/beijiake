package com.beijiake.repository.resource;

import com.beijiake.data.domain.resouce.File;
import com.beijiake.data.domain.resouce.FileUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUsageRepository extends JpaRepository<FileUsage, Long> {

    Optional<FileUsage> findFirstByFileAndOwnerTypeAndOwnerId(File file, String ownerType, String ownerId);

}
