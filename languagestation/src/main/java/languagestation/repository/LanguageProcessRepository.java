package languagestation.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import languagestation.domain.LanguageProcess;


public interface LanguageProcessRepository extends PagingAndSortingRepository<LanguageProcess, Long> {
	List<LanguageProcess> findByRequestStatus(Integer requestStatus);
}

