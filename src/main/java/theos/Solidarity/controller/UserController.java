package theos.Solidarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;
import theos.Solidarity.service.JWTService;
import theos.Solidarity.service.TaskService;
import theos.Solidarity.service.TaskUserService;
import theos.Solidarity.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskUserService taskUserService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TaskService taskService;

    @CrossOrigin
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }


    @CrossOrigin
    @PostMapping("/joinTask/{id}")
    public ResponseEntity<String> joinTask(@RequestHeader(name="Authorization") String token, @PathVariable("id") Long taskId) throws ResourceNotFoundException, IllegalAccessException {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        User user = Optional.of(userService.findByEmail(username)).get().orElseThrow(IllegalAccessException::new);
        List<User> users = taskService.getUsersforTask(taskId);
        if(users.contains(user)){
            return ResponseEntity.ok("Already joined task");
        }
        else{
            taskUserService.addTaskToUser(user.getUserId(),taskId);
            return ResponseEntity.ok("Joined task");
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/listTasks")
    public List<List<Task>> getTasksForUser(@PathVariable("id") Long userId) throws ResourceNotFoundException {
        return userService.getTasksForUser(userId);
    }
}
