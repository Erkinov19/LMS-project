package uz.isystem.StudentWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.isystem.StudentWeb.model.GroupType;
import uz.isystem.StudentWeb.model.Room;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Integer> {
}
