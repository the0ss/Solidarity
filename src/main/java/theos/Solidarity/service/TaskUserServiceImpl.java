package theos.Solidarity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.TaskUser;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;
import theos.Solidarity.repository.TaskRepository;
import theos.Solidarity.repository.TaskUserRepository;
import theos.Solidarity.repository.UserRepository;

@Service
public class TaskUserServiceImpl implements TaskUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;

    public void addTaskToUser(Long userId, Long taskId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id " + userId));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task id " + taskId));
        TaskUser taskUser = new TaskUser();
        taskUser.setTask(task);
        taskUser.setUser(user);
        taskUserRepository.save(taskUser);
    }

    @Override
    public void removeallWithtaskId(Long taskId) {
        taskUserRepository.deleteAllByTaskId(taskId);
    }

}
