package theos.Solidarity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
