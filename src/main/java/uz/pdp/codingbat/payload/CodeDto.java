package uz.pdp.codingbat.payload;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CodeDto {

    private String code;
    private Integer codeOfTaskId;
    private List<Integer> usersId;
}
