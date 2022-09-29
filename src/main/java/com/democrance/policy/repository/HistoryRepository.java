package com.democrance.policy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.democrance.policy.model.History;

public interface HistoryRepository extends JpaRepository<History, Long>{

	List<History> findHistoryByPolicyId(Long custId);

	
}
