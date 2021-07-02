package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Module;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.TaskDto;
import uz.pdp.codingbat.repository.ModuleRepository;
import uz.pdp.codingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public ApiResponse addTask(TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setActive(taskDto.isActive());
        Optional<Module> optionalModule = moduleRepository.findById(taskDto.getModuleId());
        if (!optionalModule.isPresent())
            return new ApiResponse("ModuleId not found", false);
        task.setModule(optionalModule.get());
        taskRepository.save(task);
        return new ApiResponse("Task added", true);
    }

    public ApiResponse deleteTask(Integer id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Task not found", false);
        }
    }

    public ApiResponse editTask(TaskDto taskDto, Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not edited", false);
        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setActive(taskDto.isActive());
        Optional<Module> optionalModule = moduleRepository.findById(taskDto.getModuleId());
        if (!optionalModule.isPresent())
            return new ApiResponse("ModuleId not found", false);
        task.setModule(optionalModule.get());
        taskRepository.save(task);
        return new ApiResponse("Task edited", true);
    }
}
