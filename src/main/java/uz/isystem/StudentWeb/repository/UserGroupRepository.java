package uz.isystem.StudentWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.isystem.StudentWeb.model.UserGroup;

import java.util.List;
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    List<UserGroup> findAllByDeletedAtIsNull();
}
