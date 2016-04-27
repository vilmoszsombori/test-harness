package uk.ac.london.harness.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

import uk.ac.london.harness.student.model.Student;

public interface StudentService {

	public int DEFAULT_PAGE_SIZE = 3;

	public Page<Student> findByName(String searchTerm, Pageable pageable);

	public Student findById(String id);

	public FacetPage<Student> autocompleteNameFragment(String fragment, Pageable pageable);

}