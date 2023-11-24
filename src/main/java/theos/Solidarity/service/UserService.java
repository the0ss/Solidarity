package theos.Solidarity.service;

import java.util.List;
import java.util.Optional;

import theos.Solidarity.entity.Task;
import theos.Solidarity.entity.User;
import theos.Solidarity.error.ResourceNotFoundException;

public interface UserService {

    User saveUser(User user);

    List<List<Task>> getTasksForUser(Long userId) throws ResourceNotFoundException;

    void deleteUser(Long id);

    Optional<User> findByEmail(String email);

}
