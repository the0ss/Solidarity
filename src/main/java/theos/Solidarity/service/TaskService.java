package theos.Solidarity.service;

import org.springframework.http.ResponseEntity;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;


public interface TaskService {


    public List<User> getUsersforTask(Long taskId) throws ResourceNotFoundException;
    public void addUserToTask(Long taskId, Long userId) throws ResourceNotFoundException;
    public Optional<Task> findTaskById(Long id);
    public Optional<Task> getTaskByName(String name);
    public List<Task> getAllTask();
    public List<List<Task>> getAllTaskStructured();
    public Task saveTask(Task task);

    public void removeTask(Long taskId);

    ResponseEntity<Task> markComplete(Long taskId) throws ResourceNotFoundException;
}
