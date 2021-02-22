package com.chagu.restservice.contoller;

import com.chagu.restservice.exception.ErrorMessages;
import com.chagu.restservice.exception.UserServiceException;
import com.chagu.restservice.dto.UserDto;
import com.chagu.restservice.model.request.UserDetailsRequest;
import com.chagu.restservice.model.response.OperationName;
import com.chagu.restservice.model.response.OperationStatus;
import com.chagu.restservice.model.response.OperationStatusResponse;
import com.chagu.restservice.model.response.UserDetailsResponse;
import com.chagu.restservice.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;

    @GetMapping("/")
    public String getFirstPage() {
        return "This Is First PAge !!!";
    }

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponse getUser(@PathVariable String userId) {
        UserDetailsResponse responseUser = new UserDetailsResponse();
        UserDto savedUser = userService.getUserDtoByUserId(userId);
        BeanUtils.copyProperties(savedUser, responseUser);
        return responseUser;
    }

    @PostMapping(path = "/saveUser", consumes = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) {
        if (userDetails.getEmail().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDetailsResponse responseUser;
        ModelMapper mapper = new ModelMapper();
        UserDto dto = mapper.map(userDetails, UserDto.class);
        UserDto savedUserDto = userService.createNewUser(dto);
        responseUser = mapper.map(savedUserDto, UserDetailsResponse.class);
        Link link = linkTo(UserController.class).slash("saveUser").withSelfRel();
        Link chaguLink = linkTo(methodOn(UserController.class).createUser(userDetails)).withRel("ChaguLink");
        responseUser.add(link);
        responseUser.add(chaguLink);
        return responseUser;
    }

    @PutMapping(path = "/update/{userId}", consumes = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponse updateUser(@PathVariable String userId, @RequestBody UserDetailsRequest userDetails) {
        UserDetailsResponse responseUser = new UserDetailsResponse();
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(userDetails, dto);
        UserDto updatedUserDto = userService.updateUser(userId, dto);
        BeanUtils.copyProperties(updatedUserDto, responseUser);
        return responseUser;
    }

    @DeleteMapping(path = "/delete/{userId}", produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusResponse deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new OperationStatusResponse(OperationStatus.SUCCESS.name(),
                OperationName.DELETE.name());
    }

    @GetMapping(path = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDetailsResponse> getAllUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "25") Integer limit) {
        List<UserDetailsResponse> returnValue = new ArrayList<>();
        List<UserDto> usersDto = userService.getAllUser(page, limit);
        usersDto.forEach(dto -> {
            UserDetailsResponse responseUser = new UserDetailsResponse();
            BeanUtils.copyProperties(dto, responseUser);
            returnValue.add(responseUser);
        });
        return returnValue;
    }
}
