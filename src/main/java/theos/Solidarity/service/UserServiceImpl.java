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

import static theos.Solidarity.service.TaskServiceImpl.getLists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskUserRepository taskUserRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    public List<List<Task>> getTasksForUser(Long userId) throws ResourceNotFoundException {
        List<List<Task>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id" + userId));
        List<TaskUser> taskUsers = taskUserRepository.findByUser(user);
        List<Task> temp = taskUsers.stream().map(TaskUser::getTask).toList();
        return getLists(temp, res);
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
