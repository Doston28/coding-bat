package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.codingbat.entity.Course;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModuleDto {

    @NotNull(message = "name must be entered")
    private String name;

    @NotNull(message = "description must be entered")
    private String description;


    @NotNull(message = "courseId must be entered")
    private Integer courseId;

}
