package theos.Solidarity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_id_generator"
    )
    private Long taskId;
    private String taskName;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private List<TaskUser> taskUsers = new ArrayList<>();
    @JsonIgnore
    private String taskCreator;
    private String status;
    private String priority;
    @JsonIgnore
    private LocalDate startDate;
//    private Date endTime;
}
