package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Course;
import uz.pdp.codingbat.entity.Module;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ModuleDto;
import uz.pdp.codingbat.repository.CourseRepository;
import uz.pdp.codingbat.repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Module> getModules() {
        return moduleRepository.findAll();
    }

    public Module getModule(Integer id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        return optionalModule.orElse(null);
    }

    public ApiResponse addModule(ModuleDto moduleDto) {
        Module module = new Module();
        module.setName(moduleDto.getName());
        module.setDescription(moduleDto.getDescription());
        Optional<Course> optionalCourse = courseRepository.findById(moduleDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("CourseId not found", false);
        module.setCourse(optionalCourse.get());
        moduleRepository.save(module);
        return new ApiResponse("Module added", true);
    }

    public ApiResponse deleteModule(Integer id) {
        try {
            moduleRepository.deleteById(id);
            return new ApiResponse("Module deleted", true);
        }catch (Exception e){
            return new ApiResponse("Module not found", false);
        }
    }

    public ApiResponse editModule(ModuleDto moduleDto, Integer id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if (!optionalModule.isPresent())
            return new ApiResponse("Module not edited", false);
        Module module = optionalModule.get();
        module.setName(moduleDto.getName());
        module.setDescription(moduleDto.getDescription());
        Optional<Course> optionalCourse = courseRepository.findById(moduleDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("CourseId not found", false);
        module.setCourse(optionalCourse.get());
        moduleRepository.save(module);
        return new ApiResponse("Module edited", true);
    }
}
