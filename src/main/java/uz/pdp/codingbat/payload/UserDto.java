package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String email;
    private String password;
    private List<Integer> courseId;
    private boolean active;

}

