package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.request.LoginDTO;
import com.projects.app.models.response.LoginResponse;
import com.projects.app.models.user.CustomUserDetail;
import com.projects.app.models.user.Manager;
import com.projects.app.models.user.Staff;
import com.projects.app.models.user.User;
import com.projects.app.services.UserService;
import com.projects.app.utils.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(description = "Login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginDTO user) throws BackendError {
        System.out.println(user.toString());
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
        System.out.println("Auth:  " + authentication.getAuthorities().toString());
        SecurityContextHolder.getContext().
                setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccess_token(jwt);
        return ResponseTool.POST_OK(loginResponse);
    }

    @PostMapping("/staff")
    @Operation(description = "Create staff account")
    public ResponseEntity<APIResponse> registerStaff(@Valid @RequestBody Staff staff) throws BackendError {
        Staff newStaff = userService.createStaff(staff);
        return ResponseTool.POST_OK(newStaff);
    }

    @PostMapping("/manager")
    @Operation(description = "Create manager account")
    public ResponseEntity<APIResponse> registerManager(@RequestBody Manager manager) throws BackendError {
        Manager newManager = userService.createManager(manager);
        return ResponseTool.POST_OK(newManager);
    }

    @GetMapping("/getMe")
    @Operation(description = "Get profile", summary = "Get profile", security = @SecurityRequirement(name =
            "bearerAuth"))
    @PreAuthorize("@EndpointAuthorizer.authorizer({'admin', 'staff', 'manager'})")
    public ResponseEntity<APIResponse> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().toString());
        User user = userService.getUserByUsername(authentication.getName());
        return ResponseTool.GET_OK(user);
    }

    @GetMapping("")
    @Operation(description = "Get users", summary = "Get users", security = @SecurityRequirement(name =
            "bearerAuth"))
    @PreAuthorize("@EndpointAuthorizer.authorizer({'admin'})")
    public ResponseEntity<APIResponse> getUsers() {
        List<User> expense = userService.getAll();
        return ResponseTool.GET_OK(expense);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<APIResponse> getExpense(@PathVariable(name = "userID") long userID) throws BackendError {
        User user = userService.getOne(userID);
        if (user == null) {
            String message = "Invalid user ID";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return ResponseTool.GET_OK(user);
    }

    @ApiOperation(value = "delete an user")
    @DeleteMapping("/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> deleteRevenue(
            @PathVariable(name = "userID") long userID
    ) throws BackendError {
        boolean ok = userService.deleteOne(userID);
        if (ok) {
            return ResponseTool.DELETE_OK();
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "There is no user  with this id");
        }
    }

}
