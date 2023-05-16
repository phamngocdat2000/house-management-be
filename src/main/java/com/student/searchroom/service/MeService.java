package com.student.searchroom.service;

import com.student.searchroom.entity.House;
import com.student.searchroom.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeService {

    @Autowired
    private HouseRepository houseRepository;

    public List<House> getAllByCreator(String username, int pageIndex, int pageSize) {
        Pageable paging = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        return houseRepository.findAllByCreatedBy(username, paging).getContent();
    }
}
