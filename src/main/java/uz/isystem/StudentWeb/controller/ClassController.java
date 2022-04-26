package uz.isystem.StudentWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.StudentWeb.dto.ClassDto;
import uz.isystem.StudentWeb.model.Course;
import uz.isystem.StudentWeb.service.ClassService;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        ClassDto result = classService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<ClassDto> result = classService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> create(@RequestBody ClassDto classDto){
        Boolean result = classService.create(classDto);
        return ResponseEntity.ok(result);
    }
}
