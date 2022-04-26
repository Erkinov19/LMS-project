package uz.isystem.StudentWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.StudentWeb.dto.AttendanceTypeDto;
import uz.isystem.StudentWeb.service.AttendanceTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-type")
public class AttendanceTypeController {
    @Autowired
    private AttendanceTypeService attendanceTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        AttendanceTypeDto result = attendanceTypeService.get(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttendanceTypeDto dto){
        Boolean result = attendanceTypeService.create(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody AttendanceTypeDto dto){
        Boolean result = attendanceTypeService.update(id,dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Boolean result = attendanceTypeService.delete(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<AttendanceTypeDto> result =attendanceTypeService.getAll();
        return ResponseEntity.ok(result);
    }
}
