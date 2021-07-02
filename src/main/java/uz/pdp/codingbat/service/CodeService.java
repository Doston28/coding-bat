package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Code;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CodeDto;
import uz.pdp.codingbat.repository.CodeRepository;
import uz.pdp.codingbat.repository.TaskRepository;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<Code> getCodes() {
        return codeRepository.findAll();
    }

    public Code getCode(Integer id) {
        Optional<Code> optionalCode = codeRepository.findById(id);
        return optionalCode.orElse(null);
    }

    public ApiResponse addCode(CodeDto codeDto) {
        Code code = new Code();
        code.setCode(codeDto.getCode());
        Optional<Task> optionalTask = taskRepository.findById(codeDto.getCodeOfTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Code not added",false);
        code.setCodeOfTask(optionalTask.get());
        List<User> users=new ArrayList<>();
        for (Integer integer : codeDto.getUsersId()) {
            Optional<User> optionalUser = userRepository.findById(integer);
            if (!optionalUser.isPresent())
                continue;
            users.add(optionalUser.get());
        }
        code.setUsers(users);
        codeRepository.save(code);
        return new ApiResponse("Code added", true);
    }

    public ApiResponse deleteCode(Integer id) {
        try {
            codeRepository.deleteById(id);
            return new ApiResponse("Code deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Code not found",false);
        }
    }

    public ApiResponse editCode(CodeDto codeDto, Integer id) {
        Optional<Code> optionalCode = codeRepository.findById(id);
        if (!optionalCode.isPresent())
            return new ApiResponse("Code not edited", false);
        Code code = optionalCode.get();
        code.setCode(codeDto.getCode());
        Optional<Task> optionalTask = taskRepository.findById(codeDto.getCodeOfTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Code not edited",false);
        code.setCodeOfTask(optionalTask.get());
        List<User> users=new ArrayList<>();
        for (Integer integer : codeDto.getUsersId()) {
            Optional<User> optionalUser = userRepository.findById(integer);
            if (!optionalUser.isPresent())
                continue;
            users.add(optionalUser.get());
        }
        code.setUsers(users);
        codeRepository.save(code);
        return new ApiResponse("Code edited", true);
    }
}
