package com.mh.orderservice.service;

import com.mh.orderservice.dto.UserReqDto;
import com.mh.orderservice.dto.UserResDto;
import com.mh.orderservice.entity.UserEntity;
import com.mh.orderservice.repository.UserRepository;
import com.mh.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Environment env;
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username + ": not found");

        return new User(userEntity.getEmail(),
                        userEntity.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        new ArrayList<>());
    }

    public UserResDto createUser(UserReqDto userReqDto) {
        userReqDto.setId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();

        UserEntity userMapperDto = mapper.map(userReqDto, UserEntity.class);
        userMapperDto.setPassword(passwordEncoder.encode(userReqDto.getPassword()));

        userRepository.save(userMapperDto);

        UserResDto returnUserDto = mapper.map(userMapperDto, UserResDto.class);

        return returnUserDto;
    }

    public List<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    public UserResDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UsernameNotFoundException(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ResponseOrder> ordersList = new ArrayList<>();

        /* #1-1 Connect to order-service using a rest template */
        /* @LoadBalanced 로 선언헀으면, apigateway-service로 호출 못함 */
        /* http://ORDER-SERVICE/order-service/1234-45565-34343423432/orders */
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
        String orderUrl = String.format("http://127.0.0.1:8080/order-service/%s/orders", userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                                            new ParameterizedTypeReference<List<ResponseOrder>>() {
                });

        UserResDto userDto = mapper.map(userEntity, UserResDto.class);
        return userDto;
    }


    public UserResDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserResDto userDto = mapper.map(userEntity, UserResDto.class);
        return userDto;
    }
}