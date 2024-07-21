package com.example.geological.service;

import com.example.geological.model.Section;
import com.example.geological.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public List<Section> getSectionsByCode(String code) {
        return sectionRepository.findByGeologicalClassesCode(code);
    }
}
