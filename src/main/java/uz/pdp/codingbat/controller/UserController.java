package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.UserDto;
import uz.pdp.codingbat.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public HttpEntity<User> getUser(@PathVariable Integer id){
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addUser(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer id){
        ApiResponse apiResponse = userService.editUser(userDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Integer id){
        ApiResponse apiResponse = userService.deleteUser(id);
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
