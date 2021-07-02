package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDto {

    @NotNull(message = "name must be entered")
    private String name;
}
