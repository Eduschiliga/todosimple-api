package com.eduschiliga.todosimple.services;

import com.eduschiliga.todosimple.models.Task;
import com.eduschiliga.todosimple.models.User;
import com.eduschiliga.todosimple.repositories.TaskRepository;
import com.eduschiliga.todosimple.services.exceptions.DataBindingViolationException;
import com.eduschiliga.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                STR."Tarefa não encontrada! id:\{id}, Tipo: \{Task.class.getName()}"
        ));
    }

    public List<Task> findAllByUserId(Long userId) {
         List<Task> tasks;
         tasks = this.taskRepository.findByUser_id(userId);
         return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há um usuário relacionado a tarefa!");
        }
    }
}
