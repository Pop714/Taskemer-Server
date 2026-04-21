package net.pop.taskemer.auth.presentation;

import lombok.RequiredArgsConstructor;
import net.pop.taskemer.auth.dto.AuthenticationRequest;
import net.pop.taskemer.auth.dto.AuthenticationResponse;
import net.pop.taskemer.auth.dto.RegisterRequest;
import net.pop.taskemer.auth.services.AuthenticationService;
import net.pop.taskemer.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse<>("User Registered", 200, service.register(request))
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>("Failure", 409, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>("Internal Server Error", 500, e.getMessage())
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<?>> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse<>("User Authenticated", 200, service.authenticate(request))
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>("Failure", 409, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>("Internal Server Error", 500, e.getMessage())
            );
        }
    }
}