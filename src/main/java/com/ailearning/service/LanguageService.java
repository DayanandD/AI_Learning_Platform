package com.ailearning.service;

import com.ailearning.dto.response.LanguageResponse;
import com.ailearning.entity.Language;
import com.ailearning.exception.ResourceNotFoundException;
import com.ailearning.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<LanguageResponse> getAllActiveLanguages() {
        List<Language> languages = languageRepository.findActiveLanguagesOrderedBySortOrder();
        
        return languages.stream()
                .map(language -> modelMapper.map(language, LanguageResponse.class))
                .collect(Collectors.toList());
    }

    public LanguageResponse getLanguageById(Long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException("Language", "id", languageId));

        return modelMapper.map(language, LanguageResponse.class);
    }
}
