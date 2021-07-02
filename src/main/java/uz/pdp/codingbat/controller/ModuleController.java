package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Module;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ModuleDto;
import uz.pdp.codingbat.service.ModuleService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @GetMapping
    public HttpEntity<List<Module>> getModule(){
        List<Module> modules = moduleService.getModules();
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/{id}")
    public HttpEntity<Module> getModule(@PathVariable Integer id){
        Module module = moduleService.getModule(id);
        return ResponseEntity.ok(module);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addModule(@Valid @RequestBody ModuleDto moduleDto){
        ApiResponse apiResponse = moduleService.addModule(moduleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editModule(@Valid @RequestBody ModuleDto moduleDto,@PathVariable Integer id){
        ApiResponse apiResponse = moduleService.editModule(moduleDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteModule(@PathVariable Integer id){
        ApiResponse apiResponse = moduleService.deleteModule(id);
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
