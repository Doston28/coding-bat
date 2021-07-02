package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Course;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CourseDto;
import uz.pdp.codingbat.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getCourses(){
       return courseRepository.findAll();
    }

    public Course getCourse(Integer id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);
    }

    public ApiResponse addCourse(CourseDto courseDto){
        Course course=new Course();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return new ApiResponse("Course added",true);
    }

    public ApiResponse deleteCourse(Integer id){
        try {
            courseRepository.deleteById(id);
            return new ApiResponse("Course deleted",true);
        }
        catch (Exception e){
            return new ApiResponse("Course not found",false);
        }
    }

    public ApiResponse editCourse(CourseDto courseDto, Integer id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return new ApiResponse("Course not edited",false);
        Course course = optionalCourse.get();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return new ApiResponse("Course edited",true);
    }
}
