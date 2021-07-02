package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Code;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CodeDto;
import uz.pdp.codingbat.service.CodeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @Autowired
    CodeService codeService;

    @GetMapping
    public HttpEntity<List<Code>> getCodes(){
        List<Code> codes = codeService.getCodes();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/{id}")
    public HttpEntity<Code> getCode(@PathVariable Integer id){
        Code code = codeService.getCode(id);
        return ResponseEntity.ok(code);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addCode(@Valid @RequestBody CodeDto codeDto){
        ApiResponse apiResponse = codeService.addCode(codeDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editCode(@Valid @RequestBody CodeDto codeDto,@PathVariable Integer id){
        ApiResponse apiResponse = codeService.editCode(codeDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteCode(@PathVariable Integer id){
        ApiResponse apiResponse = codeService.deleteCode(id);
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
