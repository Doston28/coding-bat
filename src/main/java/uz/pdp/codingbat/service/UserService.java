package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Course;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.UserDto;
import uz.pdp.codingbat.repository.CourseRepository;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public ApiResponse addUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.isActive());
        List<Course> courses=new ArrayList<>();
        for (Integer integer : userDto.getCourseId()) {
            Optional<Course> optionalCourse = courseRepository.findById(integer);
            if (!optionalCourse.isPresent())
                continue;
           courses.add(optionalCourse.get());
        }
        user.setCourse(courses);
        userRepository.save(user);
        return new ApiResponse("User added", true);
    }

    public ApiResponse deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("User deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("User not found", false);

        }
    }

    public ApiResponse editUser(UserDto userDto, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("User not edited", false);
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.isActive());
        List<Course> courses=new ArrayList<>();
        for (Integer integer : userDto.getCourseId()) {
            Optional<Course> optionalCourse = courseRepository.findById(integer);
            if (!optionalCourse.isPresent())
                continue;
            courses.add(optionalCourse.get());
        }
        user.setCourse(courses);
        userRepository.save(user);
        return new ApiResponse("Task edited", true);
    }
}
