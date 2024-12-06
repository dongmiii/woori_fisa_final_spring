package com.example.finalProject.service;

import com.example.finalProject.domain.entity.FinanceEntity;
import com.example.finalProject.domain.repository.FinanceRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FinanceService {
	
    private final FinanceRepository financeRepository;

    public FinanceService(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

//    public List<FinanceEntity> getAllNews() {
//        return newsRepository.findAll();
//    }
    
    public List<FinanceEntity> getLatestThreeDistinctNews() {
        List<Object[]> rawResults = financeRepository.findFinanceDistinct();
        
        for (Object[] row : rawResults) {
            System.out.println("Row length: " + row.length + ", Data: " + Arrays.toString(row));
        }
        
        List<FinanceEntity> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            FinanceEntity entity = new FinanceEntity();
            entity.setTitle((String) row[0]);
            entity.setSummary((String) row[1]);
            entity.setUrl((String) row[2]);
            result.add(entity);
        }

        return result;
    }

    
    // 페이징 처리된 뉴스 가져오기
	public List<Object[]> getPagedFinanceNews(Pageable pageable) {
		Page<Object[]> pageResults = financeRepository.findPagedFinanceDistinct(pageable);
		return pageResults.getContent();
	}
}