package pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private String first_name;
    private String last_name;
    private String email;
    private String hire_date;
    private Integer job_id;
    private Double salary;
    private Integer department_id;
}
