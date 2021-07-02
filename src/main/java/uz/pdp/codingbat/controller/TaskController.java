package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.TaskDto;
import uz.pdp.codingbat.service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public HttpEntity<List<Task>> getTasks(){
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public HttpEntity<Task> getTask(@PathVariable Integer id){
        Task task = taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addTask(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editTask(@Valid @RequestBody TaskDto taskDto,@PathVariable Integer id){
        ApiResponse apiResponse = taskService.editTask(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteTask(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
