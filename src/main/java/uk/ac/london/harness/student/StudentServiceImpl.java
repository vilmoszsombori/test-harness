package uk.ac.london.harness.student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Service;

import uk.ac.london.harness.student.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

	private static final Pattern IGNORED_CHARS_PATTERN = Pattern.compile("\\p{Punct}");

	private StudentRepository studentRepository;

	@Override
	public Page<Student> findByName(String searchTerm, Pageable pageable) {
		if (StringUtils.isBlank(searchTerm)) {
			return studentRepository.findAll(pageable);
		}

		return studentRepository.findByNameIn(splitSearchTermAndRemoveIgnoredCharacters(searchTerm), pageable);
	}

	@Override
	public Student findById(String id) {
		return studentRepository.findOne(id);
	}

	@Override
	public FacetPage<Student> autocompleteNameFragment(String fragment, Pageable pageable) {
		if (StringUtils.isBlank(fragment)) {
			return new SolrResultPage<Student>(Collections.<Student>emptyList());
		}
		return studentRepository.findByNameStartsWith(splitSearchTermAndRemoveIgnoredCharacters(fragment), pageable);
	}

	private Collection<String> splitSearchTermAndRemoveIgnoredCharacters(String searchTerm) {
		String[] searchTerms = StringUtils.split(searchTerm, " ");
		List<String> result = new ArrayList<String>(searchTerms.length);
		for (String term : searchTerms) {
			if (StringUtils.isNotEmpty(term)) {
				result.add(IGNORED_CHARS_PATTERN.matcher(term).replaceAll(" "));
			}
		}
		return result;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
}
