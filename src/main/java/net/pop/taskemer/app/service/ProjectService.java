package net.pop.taskemer.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.ProjectRequest;
import net.pop.taskemer.app.data.dto.ProjectResponse;
import net.pop.taskemer.app.data.model.entities.Project;
import net.pop.taskemer.app.data.model.enums.ProjectStatus;
import net.pop.taskemer.app.data.repo.ProjectRepository;
import net.pop.taskemer.auth.entities.User;
import net.pop.taskemer.auth.repos.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProjectResponse addProject(ProjectRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .priority(request.getPriority())
                .status(ProjectStatus.ONGOING)
                .user(user)
                .build();

        Project addedProject = projectRepository.save(project);
        return mapToProjectResponse(addedProject);
    }

    @Transactional
    public ProjectResponse editProject(Long projectId, ProjectRequest updatedData) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        existingProject.setTitle(updatedData.getTitle());
        existingProject.setDescription(updatedData.getDescription());
        existingProject.setDeadline(updatedData.getDeadline());
        existingProject.setPriority(updatedData.getPriority());
        existingProject.setStatus(updatedData.getStatus());

        Project updatedProject = projectRepository.save(existingProject);
        return mapToProjectResponse(updatedProject);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with ID: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getUserProjects(Integer userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(this::mapToProjectResponse).toList();
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .priority(project.getPriority())
                .status(project.getStatus())
                .createdAt(project.getCreatedAt())
                .lastUpdated(project.getLastUpdated())
                .deadline(project.getDeadline())
                .build();
    }
}
