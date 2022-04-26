package uz.isystem.StudentWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.isystem.StudentWeb.model.Class;

@Repository
public interface ClassRepository  extends JpaRepository<Class, Integer> {
}
