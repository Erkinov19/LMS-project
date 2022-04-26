package uz.isystem.StudentWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.StudentWeb.dto.GroupTypeDto;
import uz.isystem.StudentWeb.service.GroupTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/group-type")
public class GroupTypeController {
    @Autowired
    private GroupTypeService groupTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id){
        GroupTypeDto result = groupTypeService.get(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<GroupTypeDto> result = groupTypeService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GroupTypeDto dto){
        GroupTypeDto result = groupTypeService.create(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody GroupTypeDto dto){
        GroupTypeDto result = groupTypeService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Boolean result = groupTypeService.delete(id);
        return ResponseEntity.ok(result);
    }
}
