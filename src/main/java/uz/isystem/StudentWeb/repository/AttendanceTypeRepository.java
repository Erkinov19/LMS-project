package uz.isystem.StudentWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.isystem.StudentWeb.model.AttendanceType;
@Repository
public interface AttendanceTypeRepository  extends JpaRepository<AttendanceType, Integer> {
}
