package theos.Solidarity.service;

import theos.Solidarity.error.ResourceNotFoundException;

public interface TaskUserService {

    void addTaskToUser(Long userId, Long taskId) throws ResourceNotFoundException;

    void removeallWithtaskId(Long taskId);
}
