package theos.Solidarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;
import theos.Solidarity.model.TaskUserModel;
import theos.Solidarity.service.JWTService;
import theos.Solidarity.service.TaskService;
import theos.Solidarity.service.TaskUserService;
import theos.Solidarity.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskUserService taskUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;

    @CrossOrigin
    @GetMapping("/{id}/listUsers")
    public List<User> getUsersForTask(@PathVariable("id") Long taskId) throws ResourceNotFoundException {
        return taskService.getUsersforTask(taskId);
    }

    @CrossOrigin
    @PostMapping("/addUserToTask")
    public String addUserToTask(@RequestBody TaskUserModel taskUserModel) throws ResourceNotFoundException {
        System.out.println("here");
        taskUserService.addTaskToUser(taskUserModel.getUserId(), taskUserModel.getTaskId());
        return "User added to task";
    }

    @CrossOrigin
    @GetMapping("/alltasks")
    public List<List<Task>> getAllTasks(){
        return taskService.getAllTaskStructured();
    }

    @CrossOrigin
    @PostMapping("/addtask")
    public ResponseEntity<Task> addTask(@RequestHeader(name="Authorization")String token, @RequestBody Task task) throws ResourceNotFoundException {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        task.setTaskCreator(username);
        task.setStartDate(LocalDate.now());
        System.out.println(task);
        Task saved = taskService.saveTask(task);
        taskService.addUserToTask(saved.getTaskId(), userService.findByEmail(username).get().getUserId());
        return ResponseEntity.ok(saved);
    }

    @CrossOrigin
    @PutMapping("/markComplete/{id}")
    public ResponseEntity<String> markComplete(@RequestHeader(name = "Authorization") String token,@PathVariable("id") Long taskId) throws ResourceNotFoundException {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        Task task = Optional.of(taskService.findTaskById(taskId)).get().orElseThrow(ResourceNotFoundException::new);
        if(!task.getTaskCreator().equals(username)){
            return ResponseEntity.status(403).body("You are not the creator of this task");
        }
        taskService.markComplete(taskId);
        return ResponseEntity.ok("task marked complete");
    }

    @CrossOrigin
    @DeleteMapping("/removeTask/{id}")
    public ResponseEntity<String> removeTask(@RequestHeader(name = "Authorization") String token,@PathVariable("id") Long taskId) throws ResourceNotFoundException {
        System.out.println("inside removeTask");
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        System.out.println(username);
        Task task = Optional.of(taskService.findTaskById(taskId)).get().orElseThrow(ResourceNotFoundException::new);
        if(!task.getTaskCreator().equals(username)){
            return ResponseEntity.status(403).body("You are not the creator of this task");
        }
        taskUserService.removeallWithtaskId(taskId);
        taskService.removeTask(taskId);
        return ResponseEntity.ok("task removed");
    }

}
