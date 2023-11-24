package theos.Solidarity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.TaskUser;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;
import theos.Solidarity.repository.TaskRepository;
import theos.Solidarity.repository.TaskUserRepository;
import theos.Solidarity.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersforTask(Long taskId) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task id " + taskId));
        List<TaskUser> taskUsers = taskUserRepository.findByTask(task);
        return taskUsers.stream().map(TaskUser::getUser).toList();
    }

    public void addUserToTask(Long taskId, Long userId) throws ResourceNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task id " + taskId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id " + userId));
        TaskUser taskUser = new TaskUser();
        taskUser.setTask(task);
        taskUser.setUser(user);
        taskUserRepository.save(taskUser);
    }
    @Override
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByName(String name) {
        return taskRepository.findByTaskNameIgnoreCase(name);
    }

    @Override
    public List<Task> getAllTask() {
       return taskRepository.findAll();
    }

    @Override
    public List<List<Task>> getAllTaskStructured() {
        List<Task> tasks = taskRepository.findAll();
        List<List<Task>> structuredTasks = new ArrayList<>();
        structuredTasks.add(new ArrayList<>());
        structuredTasks.add(new ArrayList<>());
        structuredTasks.add(new ArrayList<>());
        return getLists(tasks, structuredTasks);

    }

    static List<List<Task>> getLists(List<Task> tasks, List<List<Task>> structuredTasks) {
        for (Task task : tasks) {
            if(task.getStatus().equals("pending")){
                structuredTasks.get(0).add(task);
            }
            else if(task.getStatus().equals("inprogress")){
                structuredTasks.get(1).add(task);
            }
            else{
                structuredTasks.get(2).add(task);
            }
        }
        return structuredTasks;
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void removeTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public ResponseEntity<Task> markComplete(Long taskId) throws ResourceNotFoundException {
        Task t = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task id " + taskId));
        t.setStatus("completed");
        taskRepository.save(t);
        return ResponseEntity.ok(t);
    }

}
