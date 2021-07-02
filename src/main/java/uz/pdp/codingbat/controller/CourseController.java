package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Course;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CourseDto;
import uz.pdp.codingbat.service.CourseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    public HttpEntity<List<Course>> getCourses(){
        List<Course> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public HttpEntity<Course> getCourse(@PathVariable Integer id){
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addCourse(@Valid @RequestBody CourseDto courseDto){
        ApiResponse apiResponse = courseService.addCourse(courseDto);
       return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editCourse(@Valid @RequestBody CourseDto courseDto,@PathVariable Integer id){
        ApiResponse apiResponse = courseService.editCourse(courseDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteCourse(@PathVariable Integer id){
        ApiResponse apiResponse = courseService.deleteCourse(id);
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
