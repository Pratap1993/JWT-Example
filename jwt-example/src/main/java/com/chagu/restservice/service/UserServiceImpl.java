package com.chagu.restservice.service;

import com.chagu.restservice.exception.ErrorMessages;
import com.chagu.restservice.exception.UserServiceException;
import com.chagu.restservice.repository.UserRepository;
import com.chagu.restservice.dto.UserDto;
import com.chagu.restservice.entity.UserEntity;
import com.chagu.restservice.util.UserUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final UserUtil userUtil;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createNewUser(UserDto userDto) {
        Optional<UserEntity> storedEntity = userRepository.findByEmail(userDto.getEmail().trim());
        if (storedEntity.isPresent())
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        userDto.getAddresses().forEach(address -> {
            address.setUserDetails(userDto);
            address.setAddressId(userUtil.generatePublicId(8));
        });
        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        String publicUserId = userUtil.generatePublicId(8);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmailVerificationToken(UserUtil.generateEmailVerificationToken(publicUserId));
        userEntity.setEmailVerificationStatus(false);
        UserEntity savedUser = userRepository.save(userEntity);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username.trim());
        if (!userEntity.isPresent())
            throw new UsernameNotFoundException("'" + username + "' Email doesn't exist !!!");
        return new User(username, userEntity.get().getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        Optional<UserEntity> storedEntity = userRepository.findByEmail(email.trim());
        if (!storedEntity.isPresent())
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        UserEntity userEntity = storedEntity.get();
        UserDto returnDto = new UserDto();
        BeanUtils.copyProperties(userEntity, returnDto);
        return returnDto;
    }

    @Override
    public UserDto getUserDtoByUserId(String userId) {
        Optional<UserEntity> storedEntity = userRepository.findByUserId(userId);
        if (!storedEntity.isPresent())
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        UserEntity userEntity = storedEntity.get();
        UserDto returnDto = new UserDto();
        BeanUtils.copyProperties(userEntity, returnDto);
        return returnDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto dto) {
        Optional<UserEntity> storedEntity = userRepository.findByUserId(userId);
        if (!storedEntity.isPresent())
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        UserEntity entity = storedEntity.get();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        UserEntity updatedEntity = userRepository.save(entity);
        UserDto returnDto = new UserDto();
        BeanUtils.copyProperties(updatedEntity, returnDto);
        return returnDto;
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> storedEntity = userRepository.findByUserId(userId);
        if (!storedEntity.isPresent())
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        UserEntity entity = storedEntity.get();
        userRepository.delete(entity);
    }

    @Override
    public List<UserDto> getAllUser(Integer page, Integer limit) {
        if (page > 0)
            page -= 1;
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();
        users.forEach(entity -> {
            UserDto newDto = new UserDto();
            BeanUtils.copyProperties(entity, newDto);
            returnValue.add(newDto);
        });
        return returnValue;
    }

}
