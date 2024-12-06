package com.example.finalProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.finalProject.domain.entity.LifeEntity;
import com.example.finalProject.domain.repository.LifeRepository;

@Service
public class LifeService {
    private final LifeRepository lifeRepository;

    public LifeService(LifeRepository lifeRepository) {
        this.lifeRepository = lifeRepository;
    }

    
    public List<LifeEntity> getLatestThreeDistinctNews() {
        List<Object[]> rawResults = lifeRepository.findlifeDistinct();
        List<LifeEntity> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            LifeEntity entity = new LifeEntity();
            entity.setTitle((String) row[0]);
            entity.setSummary((String) row[1]);
            entity.setUrl((String) row[2]);
            result.add(entity);
        }

        return result;
    }

    
    // 페이징 처리된 뉴스 가져오기
	public List<Object[]> getPagedFinanceNews(Pageable pageable) {
		Page<Object[]> pageResults = lifeRepository.findPagedlifeDistinct(pageable);
		return pageResults.getContent();
	}
}