package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.codingbat.entity.Module;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {

    @NotNull(message = "Name must be entered")
    private String name;

    @NotNull(message = "description must be entered")
    private String description;

    private String showSolution;
    private boolean active;

    @NotNull(message = "moduleId must be entered")
    private Integer moduleId;
}
