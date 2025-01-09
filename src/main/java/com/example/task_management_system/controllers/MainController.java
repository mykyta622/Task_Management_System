package com.example.task_management_system.controllers;

import com.example.task_management_system.repository.TaskRepository;
import com.example.task_management_system.tabels.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private TaskRepository taskRepository;


    @GetMapping("/")
    public String mainPage(Model model){
        System.out.println("Bearbeiten main Page /");
        Iterable<Task> tasks = taskRepository.findAll();
        for(Task t:tasks) System.out.println(t);
        model.addAttribute("tasks",tasks);
        return "main";
    }

    @GetMapping("/add_task")
    public String addingTask(Model model){
        return "add-task";
    }

    @PostMapping("/add_task")
    public String addTAskDB(@RequestParam String title, @RequestParam String description, Model model){
        Task task = new Task(title,description);
        taskRepository.save(task);
        System.out.println("Successfully Added!");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable(value = "id") long id, Model model){
        if(!taskRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Task> post = taskRepository.findById(id);
        ArrayList<Task> task = new ArrayList<>();
        post.ifPresent(task::add);
        model.addAttribute("task",task);
        return "edit-task";
    }


    @PostMapping("/edit/{id}")
    public String editTaskDB(@PathVariable(value = "id") long id,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam String status,
                             Model model) {
        if(!taskRepository.existsById(id)){
            return "redirect:/";
        }
        Task updateTask = taskRepository.findById(id).orElseThrow();
        updateTask.setTitle(title);
        updateTask.setDescription(description);
        updateTask.setStatus(status);

        taskRepository.save(updateTask);
        return "redirect:/";
    }

    @GetMapping("/remove/{id}")
    public String removeTaskDB(@PathVariable(value = "id") long id, Model model){
        Task removeTask = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(removeTask);
        System.out.println("Task Nr." + removeTask.getId() + " was removed!");
        return "redirect:/";
    }


}
