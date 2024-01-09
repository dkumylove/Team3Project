package org.team3.file.repositories;

import org.team3.file.entities.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, QuerydslPredicateExecutor<FileInfo> {
    List<FileInfo> findByGid(String gid);
    List<FileInfo> findByGidAndLocation(String gid, String location);
}